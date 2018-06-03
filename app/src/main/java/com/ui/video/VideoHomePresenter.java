package com.ui.video;

import com.business.bean.VideoItem;
import com.core.CoreApplication;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.item.IItemView;
import com.easy.recycleview.recycleview.item.bean.AddressHeadImgeSettings;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.easysoft.utils.lib.system.DensityUtil;
import com.ui.HttpService;
import com.ui.setting.ISafeSettingView;
import com.ui.setting.InfoCardBean;
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

	public VideoHomePresenter(IVideoHomeView iSafeSettingView) {
    	this.iVideoHomeView =iSafeSettingView;
		service=new HttpService();
	}

      public void init(){
		  List<AddressItemBean> settingMaps=new ArrayList<>();
		  Section settingSection=new Section(KEY_SETTING);

		  ArrayList<VideoItem>  videoList= VideoUtils. getVideodData(CoreApplication.getAppContext());


		   int headImgSize= DensityUtil.dip2px(CoreApplication.getAppContext(),80);
		  for (VideoItem  item : videoList ) {
			  AddressItemBean updateBean=new AddressItemBean();
			  updateBean.setTitle(item.getName());
			  updateBean.setId(item.getThumbPath());
			  updateBean.setHint(item.getSize()+"");
			  AddressHeadImgeSettings headImgeSettings=new AddressHeadImgeSettings();
			  headImgeSettings.setHeadImgUrl(item.getThumbPath());
			  headImgeSettings.setHeadImgRadius(headImgSize);
              updateBean.setHeadImgeSettings(headImgeSettings);
			  updateBean.setOnItemListener(new IItemView.onItemClick() {
				  @Override
				  public void onItemClick(IItemView.ClickTypeEnum typeEnum, AddressItemBean bean) {
					  iVideoHomeView.showItem(bean);
				  }
			  });
			  settingMaps.add(updateBean);

		  }
    	  iVideoHomeView.initUI(settingSection);
    	
      }


}
