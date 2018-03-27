package com.ui.other;

import java.io.LineNumberInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.pay.demo.SignUtils;
import com.business.BusinessBroadcastUtils;
import com.business.ServiceCallBack;
import com.business.bean.ResponseMsg;
import com.business.bean.ResponseMsgData;
import com.business.bean.ShopOrder;
import com.business.bean.ShopRecorder;
import com.business.login.User;
import com.core.CoreApplication;
import com.core.ServerUrl;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.item.IItemView;
import com.core.recycleview.sectionview.Section;
import com.core.threadpool.ThreadFactory;
import com.core.update.UpdateAPK;
import com.core.utils.SpUtils;
import com.core.utils.StringUtils;
import com.core.utils.ToastUtils;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Weather;
import com.ui.HttpService;
import com.ui.car.MyCallback;
import com.ui.other.IOtherView;
import com.ui.other.bean.WeatherMsg;

import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.widget.Toast;

/**
 * Created by vincent_tung on 2017/3/2.
 */
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
//				  weartherSectionList.addAll(futureWeatherList);
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


				  final String url = ServerUrl.baseUrl+ServerUrl.Order_GETSIGN;
				  ShopOrder loginUser=new ShopOrder();
				  final String json= JSON.toJSONString(loginUser);
				  service.request( url , json,new MyCallback(new MyCallback.IResponse() {
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

//                ilogInView.showToast("登录成功");
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

//		  AddressItemBean newItemBean=new AddressItemBean();
//		  newItemBean.setTitle("助手小Q");
//		  newItemBean.setOnItemListener(new IItemView.onItemClick() {
//			  @Override
//			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, AddressItemBean bean) {
//				  iOtherView.showNews();
//			  }
//		  });
//		  newSectionList.add(newItemBean);
		  newSection.setDataMaps(newSectionList);
		  iOtherView.updateSection(newSection);
    }
}
