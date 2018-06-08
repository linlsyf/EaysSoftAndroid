package com.ui.video;

import com.business.bean.VideoBussinessItem;
import com.core.CoreApplication;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.item.IItemView;
import com.easy.recycleview.recycleview.item.bean.AddressHeadImgeSettings;
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
	private String KEY_SETTING="setting";
	private String KEY_INFO="info";
	private String KEY_LOGOUT="logout";
	private String KEY_UPDATE="update";
	private String KEY_USER_INFO="userInfo";
	private InfoCardBean infoCardBean;
	private String SECTION_NEW="new";
	private String KEY_ABOUT="about";
	private Section settingSection;

	public VideoHomePresenter(IVideoHomeView iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
	}

      public void init(){
		  List<AddressItemBean> settingMaps=new ArrayList<>();
		   settingSection=new Section(KEY_SETTING);

		  ArrayList<VideoItem>  videoList= VideoUtils. getVideodData(CoreApplication.getAppContext());


		   int headImgSize= DensityUtil.dip2px(CoreApplication.getAppContext(),80);
		   int i=0;
		  for (VideoItem item : videoList ) {
			 final  VideoBussinessItem updateBean=new VideoBussinessItem();
			  updateBean.setTitle(item.getName());
			  updateBean.setId(item.getThumbPath());
			  updateBean.setData(item.getData());
			  updateBean.setThumbPath(item.getThumbPath());
			  updateBean.setHint(item.getDurationString());
			  updateBean.setHintShow(true);
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


	public void setCanEdit() {
		List<AddressItemBean>  resourceList=settingSection.getDataMaps();
		for (AddressItemBean itemBean: resourceList ) {
			if (itemBean.getViewType()== IItemView.ViewTypeEnum.ITEM.value()){
				itemBean.setItemCanEdit(true);
			}
		}
		iVideoHomeView.initUI(settingSection);

	}
}
