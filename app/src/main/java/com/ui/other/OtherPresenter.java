package com.ui.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.pay.demo.SignUtils;
import com.business.ServiceCallBack;
import com.business.bean.ResponseMsgData;
import com.business.bean.ShopOrder;
import com.core.ServerUrl;

import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.item.IItemView;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Weather;
import com.ui.HttpService;
import com.ui.car.MyCallback;
import com.ui.other.bean.WeatherMsg;

public class OtherPresenter   {
	HttpService service;

	IOtherView iOtherView;
	private String SECTION_WEATHER="weather";
	private String SECTION_APAY="apay";
	private String SECTION_NEW="new";

	public OtherPresenter(IOtherView IOtherView) {
    	this.iOtherView=IOtherView;
		service=new HttpService();

	}

      public void init(){
		  Weather api = (Weather) MobAPI.getAPI(Weather.NAME);
		  api.queryByCityName("广州", new APICallback() {
			  @Override
			  public void onSuccess(API api, int i, Map<String, Object> map) {
				ArrayList<Map> reusltList=   (ArrayList<Map> ) map.get("result");
				  List<WeatherMsg> futureWeatherList=new ArrayList<WeatherMsg>();
				     for (Map itemMap:reusltList){
						 if (itemMap.containsKey("future")){
							 futureWeatherList=JSON.parseArray(JSON.toJSONString(itemMap.get("future")),WeatherMsg.class);

							 break;
						 }

					 }
				  Section  weatherSection=new Section(SECTION_WEATHER);
				  weatherSection.setPosition(0);
				  weatherSection.setName("天气");
				  List<AddressItemBean>  weartherSectionList=new ArrayList<AddressItemBean>();
				  for (WeatherMsg itemBean:futureWeatherList) {
					  if (weartherSectionList.size()<=1){
						  //itemBean.setViewType(IItemView.ViewTypeEnum.INFO_CARD_VIEW.value());
						  itemBean.setTitle(itemBean.getWeek());
						  itemBean.setLeftSecondText(itemBean.getTemperature());
						  itemBean.setRightFirstText(itemBean.getDayTime()+" "+itemBean.getWind());
						  weartherSectionList.add(itemBean);
					  }else{
						  break;
					  }

				  }
				  weatherSection.setDataMaps(weartherSectionList);
				  iOtherView.updateSection(weatherSection);
			  }
			  @Override
			  public void onError(API api, int i, Throwable throwable) {
				  iOtherView.showToast("请求服务器失败");
			  }
		  });
		  Section  apaySection=new Section(SECTION_APAY);
		  apaySection.setPosition(1);
		  apaySection.setName("支付");
		  List<AddressItemBean>  paySectionList=new ArrayList<AddressItemBean>();
		  AddressItemBean itemBean=new AddressItemBean();
		  itemBean.setTitle("测试支付功能");
		  itemBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, AddressItemBean bean) {
				   String url = ServerUrl.baseUrl+ServerUrl.Order_GETSIGN;
				  ShopOrder loginUser=new ShopOrder();
				  final String json= JSON.toJSONString(loginUser);
				  url=ServerUrl.getFinalUrl(url,json);
				  service.request( url , new MyCallback(new MyCallback.IResponse() {
					  @Override
					  public void onFailure(ServiceCallBack serviceCallBack) {
						  iOtherView.showToast("服务器获取数据失败");
					  }
					  @Override
					  public void onResponse(ServiceCallBack serviceCallBack) {
						  if (serviceCallBack.isSucess()){

							  ResponseMsgData data =JSON.parseObject(serviceCallBack.getResponseMsg().getMsg(),
									  ResponseMsgData.class);

							  SignUtils.payString=data.getData().toString();
							  iOtherView.showApay();
						  }else{
							  iOtherView.showToast("服务器获取数据失败");
						  }

					  }
				  }));

			  }
		  });
		  paySectionList.add(itemBean);

		  apaySection.setDataMaps(paySectionList);

		  Section  newSection=new Section(SECTION_NEW);
		  newSection.setPosition(0);

		  newSection.setName("圈子");
		  List<AddressItemBean>  newSectionList=new ArrayList<AddressItemBean>();

		  newSection.setDataMaps(newSectionList);
		  iOtherView.updateSection(newSection);
    }
}
