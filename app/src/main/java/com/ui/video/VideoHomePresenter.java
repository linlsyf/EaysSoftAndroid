package com.ui.video;

import com.business.bean.VideoBussinessItem;
import com.core.CoreApplication;
import com.core.db.greenDao.entity.Video;
import com.core.db.greenDao.gen.UserDao;
import com.core.db.greenDao.gen.VideoDao;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.item.IItemView;
import com.easy.recycleview.recycleview.item.bean.AddressHeadImgeSettings;
import com.easy.recycleview.recycleview.item.bean.SelectBean;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.easysoft.utils.lib.system.DensityUtil;
import com.ui.HttpService;
import com.ui.setting.InfoCardBean;
import com.ui.video.IVideoHomeView;
import com.utils.VideoItem;
import com.utils.VideoUtils;

import java.util.ArrayList;
import java.util.List;


public class VideoHomePresenter   {
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
	private VideoDao mVideo;
	private boolean mIsCanSelect=false;

	public VideoHomePresenter(IVideoHomeView iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
	}

      public void init(){
		  List<AddressItemBean> settingMaps=new ArrayList<>();
		   settingSection=new Section(KEY_SETTING);

		  ArrayList<VideoItem>  videoList= VideoUtils. getVideodData(CoreApplication.getAppContext());
		        mVideo= CoreApplication.getInstance().getDaoSession().getVideoDao();

//		    List<Video> videoDbList = mVideo.loadAll();
		   int headImgSize= DensityUtil.dip2px(CoreApplication.getAppContext(),80);
		   int i=0;

		  ArrayList<Video>  videoNewList=new ArrayList<>();
		  for (VideoItem item : videoList ) {
			 final  VideoBussinessItem updateBean=new VideoBussinessItem();
			  updateBean.setTitle(item.getName());
			  updateBean.setId(item.getData());
			  updateBean.setData(item.getData());
			  updateBean.setThumbPath(item.getThumbPath());
			  updateBean.setHint(item.getDurationString());
			  updateBean.setHintShow(true);
			  AddressHeadImgeSettings headImgeSettings=new AddressHeadImgeSettings();
			  headImgeSettings.setHeadImgPath(item.getThumbPath());
			  headImgeSettings.setHeadImgRadius(headImgSize);
			  headImgeSettings.setBitmap(item.getBitmap());
              updateBean.setHeadImgeSettings(headImgeSettings);
//			  updateBean.setShowLeftCheckBox(true);
//			  updateBean.setLeftCheckBoxIsChecked(false);
			  updateBean.setOnItemListener(new IItemView.onItemClick() {
				  @Override
				  public void onItemClick(IItemView.ClickTypeEnum typeEnum, AddressItemBean bean) {
					  iVideoHomeView.showItem(updateBean);
				  }
			  });

//			 Video  dbVideo=new Video();
//
//			  videoNewList.add(dbVideo);

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
			}
		}
		iVideoHomeView.initUI(settingSection);
          return   mIsCanSelect;
	}


	public void setHide(  List<SelectBean>  selectBeanList) {

		for (SelectBean itemBean:selectBeanList) {
			String selectId=    itemBean.getId();
         //			 renamefile
		}

	}
}
