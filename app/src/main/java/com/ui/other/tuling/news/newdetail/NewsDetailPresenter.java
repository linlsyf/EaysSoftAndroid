package com.ui.other.tuling.news.newdetail;

import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.ui.HttpService;
import com.ui.other.tuling.entity.NewsEntity;

import java.util.ArrayList;
import java.util.List;


public class NewsDetailPresenter {
	HttpService service;

	INewsDetailsView iNewsView;
	public static  String KEY_SETTING="setting";
	private List<NewsEntity> newsList=new ArrayList<>();

	private Section settingSection;


	public NewsDetailPresenter(INewsDetailsView iSafeSettingView) {
    	this.iNewsView =iSafeSettingView;
		service=new HttpService();
	}

      public void init(){
//		  List<AddressItemBean> settingMaps=new ArrayList<>();
//		  settingSection=new Section(KEY_SETTING);
//		  settingSection.setShowSection(false);
//		  AddressItemBean itemBean=new AddressItemBean();
//		  itemBean.setViewType(3);
//		  settingMaps.add(itemBean);
//		  iNewsView.initUI(settingSection);

      }



}
