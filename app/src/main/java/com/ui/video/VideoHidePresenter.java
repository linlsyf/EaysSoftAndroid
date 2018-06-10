package com.ui.video;

import com.business.BusinessBroadcastUtils;
import com.business.bean.SelectBindBean;
import com.business.bean.VideoBussinessItem;
import com.core.CoreApplication;
import com.core.db.greenDao.entity.VideoDB;
import com.core.db.greenDao.gen.VideoDBDao;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.item.IItemView;
import com.easy.recycleview.recycleview.item.bean.AddressHeadImgeSettings;
import com.easy.recycleview.recycleview.item.bean.SelectBean;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.easysoft.utils.lib.system.DensityUtil;
import com.ui.HttpService;
import com.ui.setting.InfoCardBean;
import com.utils.VideoItem;
import com.utils.VideoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VideoHidePresenter {
	HttpService service;

	IVideoHomeView iVideoHomeView;
	public static  String KEY_SETTING="setting";
	private String KEY_INFO="info";
	private String KEY_LOGOUT="logout";
	private String KEY_UPDATE="update";
	private String KEY_USER_INFO="userInfo";
	private InfoCardBean infoCardBean;
	private String SECTION_NEW="new";
	private String KEY_ABOUT="about";
	private Section settingSection;
	private Map mDbFileMap=new HashMap();
	private boolean mIsCanSelect=false;
private VideoDBDao mVideoDao;
	private List<VideoDB> videoList;

	public VideoHidePresenter(IVideoHomeView iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
		mVideoDao = CoreApplication.getInstance().getDaoSession().getVideoDBDao();
	}

      public void init(){
		  List<AddressItemBean> settingMaps=new ArrayList<>();
		   settingSection=new Section(KEY_SETTING);

		   int headImgSize= DensityUtil.dip2px(CoreApplication.getAppContext(),80);
		   int i=0;
		  videoList  = mVideoDao.loadAll();
		  ArrayList<VideoDB>  videoNewList=new ArrayList<>();
		  for (VideoDB item : videoList ) {
			  mDbFileMap.put(item.getId(),item.getData());
			 final  VideoBussinessItem updateBean=new VideoBussinessItem();
			  updateBean.setTitle(item.getName());
			  updateBean.setId(item.getId());
			  updateBean.setData(item.getData());
			  updateBean.setThumbPath(item.getThumbPath());
			  updateBean.setHint(item.getDurationString());
			  updateBean.setHintShow(true);
			  AddressHeadImgeSettings headImgeSettings=new AddressHeadImgeSettings();
			  headImgeSettings.setHeadImgPath(item.getThumbPath());
			  headImgeSettings.setHeadImgRadius(headImgSize);
//			  headImgeSettings.setBitmap(item.getBitmap());
//              updateBean.setHeadImgeSettings(headImgeSettings);
			  updateBean.setOnItemListener(new IItemView.onItemClick() {
				  @Override
				  public void onItemClick(IItemView.ClickTypeEnum typeEnum, AddressItemBean bean) {
					  iVideoHomeView.showItem(updateBean);
				  }
			  });


              if (i!=0){
              	AddressItemBean spliteItem=new AddressItemBean();
              	spliteItem.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
				  settingMaps.add(spliteItem);
			  }

			  settingMaps.add(updateBean);
			  i++;

		  }
		  settingSection.setDataMaps(settingMaps);
    	  iVideoHomeView.initUI(settingSection);
    	
      }


	public boolean setCanEdit() {
		List<AddressItemBean>  resourceList=settingSection.getDataMaps();
		mIsCanSelect=!mIsCanSelect;
		for (AddressItemBean itemBean: resourceList ) {
			if (itemBean.getViewType()== IItemView.ViewTypeEnum.ITEM.value()){
				itemBean.setItemCanEdit(mIsCanSelect);
				itemBean.setShowLeftCheckBox(mIsCanSelect);
				itemBean.setSelectType(settingSection.getId());
				itemBean.setOnItemClickAble(mIsCanSelect);

			}
		}
		iVideoHomeView.initUI(settingSection);
		return   mIsCanSelect;
	}


	public void setHide(  List<SelectBean>  selectBeanList) {
		List<SelectBindBean>  chaneList=new ArrayList<>();

		for (SelectBean itemBean:selectBeanList) {
			String selectId=    itemBean.getId();
			for (VideoDB item : videoList ) {
				if (selectId.equals(item.getId())){
					File oldeFile=new File((String) mDbFileMap.get(selectId));
					File   newFile=new File(item.getOldFilePath());
					boolean isSucess=oldeFile.renameTo(newFile);
					if (isSucess){
						mVideoDao.delete(item);
					}
				}

			}


		}
		init();
		BusinessBroadcastUtils.sendBroadcast(iVideoHomeView.getContext(), BusinessBroadcastUtils.TYPE_REFRESH_VIDEO, null);
	}
}
