package com.ui.other.tuling.news;

import com.alibaba.fastjson.JSON;
import com.business.ServiceCallBack;
import com.business.bean.ResponseMsgData;
import com.core.CoreApplication;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.item.IItemView;
import com.easy.recycleview.recycleview.item.bean.AddressHeadImgeSettings;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.easysoft.utils.lib.system.DensityUtil;
import com.ui.HttpService;
import com.ui.car.MyCallback;
import com.ui.other.tuling.constant.TulingParams;
import com.ui.other.tuling.entity.NewsEntity;

import java.util.ArrayList;
import java.util.List;


public class NewsPresenter {
	HttpService service;

	INewsView iNewsView;
	public static  String KEY_SETTING="setting";
	private List<NewsEntity> newsList=new ArrayList<>();

	private Section settingSection;


	public NewsPresenter(INewsView iSafeSettingView) {
    	this.iNewsView =iSafeSettingView;
		service=new HttpService();
	}

      public void init(){

		  requestApiByRetrofit_RxJava("新闻");

    	
      }

	// 请求图灵API接口，获得问答信息
	private void requestApiByRetrofit_RxJava(final String info) {
		service=new HttpService();
		final String url = TulingParams.TULING_URL+"?key="+TulingParams.TULING_KEY+"&info="+info;

		service.request( url , new MyCallback(new MyCallback.IResponse() {
			@Override
			public void onFailure(ServiceCallBack serviceCallBack) {
			}

			@Override
			public void onResponse(ServiceCallBack serviceCallBack) {
				if (serviceCallBack.isSucess()){
					ResponseMsgData data = JSON.parseObject(serviceCallBack.getResponseMsg().getData().toString(),
							ResponseMsgData.class);
					newsList =  JSON.parseArray(data.getList().toString(), NewsEntity.class) ;

					List<AddressItemBean> settingMaps=new ArrayList<>();
					settingSection=new Section(KEY_SETTING);
					settingSection.setShowSection(false);

					//int headImgSize= DensityUtil.dip2px(CoreApplication.getAppContext(),80);
					int i=0;
					for (final NewsEntity item : newsList ) {

						item.setTitle(item.getArticle());
						item.setOnItemListener(new IItemView.onItemClick() {
							@Override
							public void onItemClick(IItemView.ClickTypeEnum typeEnum, AddressItemBean bean) {
								iNewsView.showItem(item);
							}
						});

//						AddressHeadImgeSettings headImgeSettings=new AddressHeadImgeSettings();
//						headImgeSettings.setHeadImgRadius(headImgSize);
//						headImgeSettings.setBitmap(item.getBitmap());
//						item.setHeadImgeSettings(headImgeSettings);
						if (i!=0){
							AddressItemBean spliteItem=new AddressItemBean();
							spliteItem.setViewType(IItemView.ViewTypeEnum.SPLITE.value());
							settingMaps.add(spliteItem);
						}

						settingMaps.add(item);
						i++;

					}
					settingSection.setDataMaps(settingMaps);
					iNewsView.initUI(settingSection);
				}

			}
		}).setOutside(true));
	}

}
