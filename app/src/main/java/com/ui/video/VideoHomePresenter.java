package com.ui.video;

import android.app.Activity;
import android.os.Environment;

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
import com.easy.recycleview.utils.ToastUtils;
import com.easysoft.costumes.R;
import com.easysoft.utils.lib.system.DensityUtil;
import com.ui.HttpService;
import com.utils.PermissionCheckUtils;
import com.utils.VideoItem;
import com.utils.VideoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class VideoHomePresenter   {
	HttpService service;
	IVideoHomeView iVideoHomeView;
	public static  String KEY_SETTING="setting";
	private Section settingSection;
	private VideoDBDao mVideoDao;
	private boolean mIsCanSelect=false;

	public VideoHomePresenter(IVideoHomeView iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
		mVideoDao = CoreApplication.getInstance().getDaoSession().getVideoDBDao();
	}

      public void init(Activity activity){
		  List<AddressItemBean> settingMaps=new ArrayList<>();
		   settingSection=new Section(KEY_SETTING);
          settingSection.setShowSection(false);

		  boolean isHas=   VideoUtils.checkPermission(activity);
		  ArrayList<VideoItem>  videoList=new ArrayList<>() ;
		    	videoList=VideoUtils. getVideodData(CoreApplication.getAppContext());
//			}else{
//				ToastUtils.show(CoreApplication.getAppContext(),CoreApplication.getAppContext().getString(R.string.storepermitrequst));
//
//			}
		   int headImgSize= DensityUtil.dip2px(CoreApplication.getAppContext(),80);
		   int i=0;
		  for (VideoItem item : videoList ) {
		  	   String  path=item.getData();
			 final  VideoBussinessItem updateBean=new VideoBussinessItem();
			  updateBean.setTitle(item.getName());
			  updateBean.setId(path);
			  updateBean.setData(item.getData());
			  updateBean.setThumbPath(item.getThumbPath());
			  updateBean.setHint(item.getDurationString());
			  updateBean.setHintShow(true);
			  updateBean.setContentBgResid(R.drawable.corners_bg);
			  AddressHeadImgeSettings headImgeSettings=new AddressHeadImgeSettings();
			  headImgeSettings.setHeadImgPath(item.getThumbPath());
			  headImgeSettings.setHeadImgRadius(headImgSize);
			  headImgeSettings.setBitmap(item.getBitmap());
              updateBean.setHeadImgeSettings(headImgeSettings);
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
				itemBean.setOnItemClickAble(!mIsCanSelect);
			}
		}
		iVideoHomeView.initUI(settingSection);
          return   mIsCanSelect;
	}


	public void setHide(  List<SelectBean>  selectBeanList) {
		List<AddressItemBean>  resourceList=settingSection.getDataMaps();
		List<AddressItemBean>  newList=new ArrayList<>();
		List<SelectBindBean>  chaneList=new ArrayList<>();
		List<VideoDB>  chaneDbList=new ArrayList<>();
		for (SelectBean itemBean:selectBeanList) {
			String selectId=    itemBean.getId();
			File   oldeFile=new File(selectId);
			String newPathName=oldeFile.getAbsolutePath().substring(0,oldeFile.getAbsolutePath().lastIndexOf("/")+1);
			File   newFile=new File(newPathName+"."+oldeFile.getName()+".hide");
			boolean isSucess=oldeFile.renameTo(newFile);
			if (isSucess){
				SelectBindBean bindBean=new SelectBindBean();
				bindBean.setId(itemBean.getId());
				bindBean.setHidePath(newFile.getAbsolutePath());
				chaneList.add(bindBean);
			}
		}
		for (AddressItemBean  oldItem :resourceList ) {
			boolean isHide=false;
			for (SelectBindBean  itemSelectBean:chaneList ) {

				if (itemSelectBean.getId().equals(oldItem.getId())){
					isHide=true;
					VideoDB   videoDBItem=new VideoDB();
					videoDBItem.setName(oldItem.getTitle());
					videoDBItem.setDurationString(oldItem.getHint());
					videoDBItem.setOldFilePath(oldItem.getId());
					videoDBItem.setData(itemSelectBean.getHidePath());
					chaneDbList.add(videoDBItem);
					UUID uuid = UUID.randomUUID();
					String uniqueId = uuid.toString();
					videoDBItem.setId(uniqueId);
					mVideoDao.insert(videoDBItem);
					break;
				}
			}
			if (!isHide){
				newList.add(oldItem);
			}
		}
		settingSection.setDataMaps(newList);
		iVideoHomeView.initUI(settingSection);
		BusinessBroadcastUtils.sendBroadcast(iVideoHomeView.getContext(), BusinessBroadcastUtils.TYPE_REFRESH_VIDEO_HIDE, null);
	}
}
