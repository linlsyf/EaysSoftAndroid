package com.ui.car;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.business.ServiceCallBack;
import com.business.bean.Goods;
import com.business.bean.ResponseMsgData;
import com.business.bean.ShopOrder;
import com.business.bean.ShopOrderMsg;
import com.business.bean.ShopRecorder;
import com.core.CoreApplication;
import com.core.ServerUrl;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.item.IItemView;
import com.core.recycleview.item.IItemView.ClickTypeEnum;
import com.core.recycleview.item.IItemView.onItemClick;
import com.core.recycleview.item.bean.AddressHeadImgeSettings;
import com.core.recycleview.sectionview.Section;
import com.core.utils.DensityUtil;
import com.core.utils.ToastUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ui.HttpService;
import com.ui.car.MyCallback.IResponse;

public class ShopOrderPersenter  {
	HttpService service;
	IShopOrderListView iShopOrderListView;
	Section nextSection = new Section("");

	public ShopOrderPersenter() {
	}
	public ShopOrderPersenter(IShopOrderListView iShopOrderListView) {
		service=new HttpService();
		this.iShopOrderListView=iShopOrderListView;
	}
	
	  public AddressItemBean  getAddressItemBean(ShopRecorder order){
			AddressItemBean itembean = new AddressItemBean();
		
			itembean.setOnItemListener(new onItemClick() {
				@Override
				public void onItemClick(ClickTypeEnum typeEnum,
						AddressItemBean bean) {
					if (typeEnum==ClickTypeEnum.ITEM) {
			
						String getUrl = ServerUrl.baseUrl+ServerUrl.ORDER_GET;
						ShopOrder order = new ShopOrder();
						 order.setId(bean.getId());

						 ObjectMapper mapper = new ObjectMapper();
					        String json = null;
							try {
								json = mapper.writeValueAsString(order);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						 get(getUrl, json);
				
					}else if(typeEnum==ClickTypeEnum.ITEM_LONG){
						String getUrl = ServerUrl.baseUrl+ServerUrl.ORDER_REMOVE;
						ShopOrder order = new ShopOrder();
						 order.setId(bean.getId());

						 ObjectMapper mapper = new ObjectMapper();
					        String json = null;
							try {
								json = mapper.writeValueAsString(order);
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						 remove(getUrl, json);
					}
				
					
				}
			});
			
//			ShopOrder order=orderList.get(i);

//		  Goods goods=JSON.parseObject(order.getGoods(),Goods.class);
//		  ShopOrder shopOrder=JSON.parseObject(order.getOrder(),ShopOrder.class);

//		   if (goods==null){
//			   goods=new Goods();
//		   }
//		  if (shopOrder==null){
//			  shopOrder=new ShopOrder();
//		  }
			   itembean.setTitle(order.getName());

			itembean.setId(order.getId());
			AddressHeadImgeSettings headImgeSettings = new AddressHeadImgeSettings();
			String  imgURL=ServerUrl.baseUrl+ServerUrl.IMG_URL+order.getImageId();
			headImgeSettings.setHeadImgUrl(imgURL);
			   int  imgRadius= DensityUtil.dip2px(CoreApplication.getInstance(), 45);
			headImgeSettings.setHeadImgRadius(imgRadius);
			itembean.setHeadImgeSettings(headImgeSettings);
			return  itembean;
	  }
	
	
	public void list(){
		
		String url = ServerUrl.baseUrl+ServerUrl.LIST_ORDERED;
		ShopOrder order = new ShopOrder();

		
		 ObjectMapper mapper = new ObjectMapper();
	        String json = null;
			try {
				json = mapper.writeValueAsString(order);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		service.request(url, json,new MyCallback(new IResponse() {
			
			@Override
			public void onResponse(ServiceCallBack  callBack) {
				if(callBack.isSucess()&&callBack.getResponseMsg()!=null){
					
					ResponseMsgData data =JSON.parseObject(callBack.getResponseMsg().getMsg(),
						ResponseMsgData.class);
					    List<ShopRecorder> orderList = (List<ShopRecorder>)JSON.parseArray(data.getData()
								.toString(), ShopRecorder.class);
				    
					List<AddressItemBean> dataMaps = new ArrayList<>();

					for (int i = 0; i < orderList.size(); i++) {
						ShopRecorder order=orderList.get(i);
						AddressItemBean itembean=getAddressItemBean(order);

							 dataMaps.add(itembean);
						if (i != orderList.size()-1) {
							AddressItemBean itembeanSpace = new AddressItemBean();
							itembeanSpace.setViewType(IItemView.ViewTypeEnum.SPLITE
											.value());
							dataMaps.add(itembeanSpace);

						}

					}
					nextSection.setDataMaps(dataMaps);
					nextSection.setShowSection(false);
				   iShopOrderListView.list(nextSection);

					
					
			
			}else{
					iShopOrderListView.showToast( "服务器响应失败");
				}
			}
			
			@Override
			public void onFailure(ServiceCallBack  serviceCallBack) {
				iShopOrderListView.showToast( "服务器响应失败");

				
			}
		}));
////		


	

	}
	
	public void get(String url ,String json){
	
		service.request(url, json, new MyCallback(new IResponse() {
			
			@Override
			public void onResponse(ServiceCallBack callBack) {
				 ObjectMapper mapper = new ObjectMapper();

				ResponseMsgData data = JSON.parseObject(callBack.getResponseMsg().getMsg(),
							ResponseMsgData.class);
				 ShopOrderMsg order = JSON.parseObject(data.getData().toString(), ShopOrderMsg.class);

				Goods goods=JSON.parseObject(order.getGoods(),Goods.class);
				ShopOrder shopOrder=JSON.parseObject(order.getOrder(),ShopOrder.class);
				             String  imgURL=ServerUrl.IMG_URL+goods.getImageId();
				goods.setImagUrl(imgURL);
				         iShopOrderListView.showOrder(shopOrder,goods);
			
			      
			}
			
			@Override
			public void onFailure(ServiceCallBack serviceCallBack) {
				ToastUtils.show(CoreApplication.getInstance(), "服务器响应失败");

				
			}
		}));
	
	}
	public void remove(String url ,String json){
		
		
service.request(url, json, new MyCallback(new IResponse() {
			
			@Override
			public void onResponse(ServiceCallBack callBack) {
				if(callBack.isSucess()){
					ToastUtils.show(CoreApplication.getInstance(), "删除数据成功");
				  list();
				
				}else{
					ToastUtils.show(CoreApplication.getInstance(), "服务器响应失败");
				}
			
			}
			
			@Override
			public void onFailure(ServiceCallBack serviceCallBack) {
				ToastUtils.show(CoreApplication.getInstance(), "服务器响应失败");
				
			}
		}));
		
	}
}
