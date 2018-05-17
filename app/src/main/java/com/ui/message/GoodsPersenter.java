package com.ui.message;


import com.alibaba.fastjson.JSON;
import com.business.BusinessBroadcastUtils;
import com.business.ServiceCallBack;
import com.business.bean.Goods;
import com.business.bean.ResponseMsgData;
import com.business.bean.ShopOrder;
import com.core.CoreApplication;
import com.core.ServerUrl;
import com.core.utils.DensityUtil;
import com.core.utils.ToastUtils;

import com.easysoft.costumes.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ui.HttpService;
import com.ui.car.MyCallback;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.item.IItemView;
import com.easy.recycleview.recycleview.item.bean.AddressHeadImgeSettings;
import com.easy.recycleview.recycleview.sectionview.Section;

public class GoodsPersenter {


	private HttpService service;
	private IGoodsView iGoodsView;
	private AddressItemBean totalBean;
	public static String KEY_ShopOrderInfo="ShopOrderInfo";
	public static String KEY_SHOP_TYPE="KEY_SHOP_TYPE";
	public static String KEY_TOP="top";
	public static String KEY_NAME="name";
	public static String KEY_IMG="image";
	public static String KEY_COLOR_NAME="colorName";
	public static String KEY_COLOR_NUM="colorNum";
	public static String KEY_CONTENT="content";
	public static String KEY_NUM="num";
	public static String KEY_TOTAL="total";
	public static String KEY_PRICE="price";

	public GoodsPersenter( IGoodsView iGoodsView) {
		service=new HttpService();
		this.iGoodsView=iGoodsView;
	}
	
	
	public IItemView.onItemClick getNormelItemClick(final AddressItemBean itemNameBean){
	IItemView.onItemClick itemclick =new IItemView.onItemClick() {
		
		@Override
		public void onItemClick(IItemView.ClickTypeEnum typeEnum, AddressItemBean bean) {
			iGoodsView.showItem(itemNameBean);
			
		}};
		return itemclick;
	}

	public AddressItemBean  getAddressItemBean(final Goods goods){
		GoodsInfoBean itembean = new GoodsInfoBean();

		String  imgURL=ServerUrl.baseUrl+ServerUrl.IMG_URL+goods.getImageId();
       goods.setImagUrl(imgURL);

		itembean.setOnItemListener(new IItemView.onItemClick() {
			@Override
			public void onItemClick(IItemView.ClickTypeEnum typeEnum,
									AddressItemBean bean) {
				if (typeEnum== IItemView.ClickTypeEnum.ITEM) {

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
					iGoodsView.toOrder(goods);
				}

			}
		});

		itembean.setViewType(3);

		itembean.setTitle(goods.getName());
		itembean.setPrice(goods.getPrice()+"元");
		itembean.setId(goods.getId());
		AddressHeadImgeSettings headImgeSettings = new AddressHeadImgeSettings();
//		String  imgURL=ServerUrl.baseUrl+ServerUrl.IMG_URL+goods.getImageId();
		headImgeSettings.setHeadImgUrl(imgURL);
		int  imgRadius= DensityUtil.dip2px(CoreApplication.getInstance(), 45);
		headImgeSettings.setHeadImgRadius(imgRadius);
		itembean.setHeadImgeSettings(headImgeSettings);
		return  itembean;
	}
	public AddressItemBean getTotalItemData(){
		return totalBean;
	}
	
	public void list(){

		Section nextSection=new Section(KEY_SHOP_TYPE);
		nextSection.setName("分类");
		List<AddressItemBean> dataMaps=new ArrayList<>();

		for (int i=0;i<12;i++){
			AddressItemBean  itemBean=new AddressItemBean();
			itemBean.setViewType(4);
			itemBean.setSpanSize(1);
			if (i==0){
				itemBean.getHeadImgeSettings().setHeadImgDrawableId(R.drawable.new_find_icon);
				itemBean.setTitle("鞋子");
			}
			else if (i==1){
				itemBean.getHeadImgeSettings().setHeadImgDrawableId(R.drawable.new_myhome_icon);
				itemBean.setTitle("衣服");
			}
			else if (i==2){
				itemBean.getHeadImgeSettings().setHeadImgDrawableId(R.drawable.new_shoppingcar_icon);
				itemBean.setTitle("裤子");
			}
			else if (i==3){
				itemBean.getHeadImgeSettings().setHeadImgDrawableId(R.drawable.welcome_page);
				itemBean.setTitle("家具");
			}
			else if (i==4){
				itemBean.getHeadImgeSettings().setHeadImgDrawableId(R.drawable.new_life_icon);
				itemBean.setTitle("化妆品");
			}
			else if (i==5){
				itemBean.getHeadImgeSettings().setHeadImgDrawableId(R.drawable.visualization);
				itemBean.setTitle("其他");
			}
			else if (i==6){
				itemBean.getHeadImgeSettings().setHeadImgDrawableId(R.drawable.weather_bg);
				itemBean.setTitle("其他");
			}
			else{
				itemBean.getHeadImgeSettings().setHeadImgDrawableId(R.drawable.new_life_icon);
				itemBean.setTitle("更多"+(i+1));
			}
			itemBean.setOnItemListener(new IItemView.onItemClick() {
				@Override
				public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, AddressItemBean addressItemBean) {
					iGoodsView.showToast(addressItemBean.getTitle());
				}
			});
			itemBean.setId(itemBean.getTitle());
			dataMaps.add(itemBean);
		}

		nextSection.setDataMaps(dataMaps);
		iGoodsView.showUi(nextSection);


			String url = ServerUrl.baseUrl+ServerUrl.GOODSLIST;
			Goods order = new Goods();


			ObjectMapper mapper = new ObjectMapper();
			String json = null;
			try {
				json = mapper.writeValueAsString(order);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		url=ServerUrl.getFinalUrl(url,json);

		service.request(url, new MyCallback(new MyCallback.IResponse() {

				@Override
				public void onResponse(ServiceCallBack  callBack) {
					if(callBack.isSucess()&&callBack.getResponseMsg()!=null){
						Section nextSection=new Section(KEY_ShopOrderInfo);
						List<AddressItemBean> dataMaps=new ArrayList<>();
						ResponseMsgData data =JSON.parseObject(callBack.getResponseMsg().getMsg(),
								ResponseMsgData.class);
						List<Goods> orderList = (List<Goods>)JSON.parseArray(data.getData()
								.toString(), Goods.class);
						for (int i = 0; i < orderList.size(); i++) {
							Goods order=orderList.get(i);
							AddressItemBean itembean=getAddressItemBean(order);
							itembean.setSpanSize(3);
//							if (i==0){
//								dataMaps.add(itembean);
//                              break;
//							}
							dataMaps.add(itembean);

						}
						nextSection.setDataMaps(dataMaps);
						nextSection.setName("热销");
						iGoodsView.showUi(nextSection);


					}
				}

				@Override
				public void onFailure(ServiceCallBack  serviceCallBack) {
					ToastUtils.show(iGoodsView.getContext(), "服务器响应失败");


				}
			}));
////
//		Section nextSection=new Section(KEY_ShopOrderInfo);
//		List<AddressItemBean> dataMaps=new ArrayList<>();
//
//		nextSection.setDataMaps(dataMaps);
//		nextSection.setShowSection(false);
//		iGoodsView.showUi(nextSection);

		Map<String, String> map = new TreeMap<String, String>(

				new Comparator<String>() {
                        @Override
					public int compare(String obj1, String obj2) {

						// 降序排序

						return obj2.compareTo(obj1);

					}

				});

	}

	public void reInitToolBar() {
		boolean isAdmin=false;
		if (BusinessBroadcastUtils.loginUser!=null){
			if (BusinessBroadcastUtils.loginUser.getIsAdmin().equals("1")){
				isAdmin=true;

			}
			iGoodsView.resetToolBar( isAdmin);
		}
	}

	public void initTop() {
//		Section nextSection=new Section(KEY_TOP);
//		List<AddressItemBean> dataMaps=new ArrayList<>();
//
//       AddressItemBean itembean=new AddressItemBean();
//		itembean.setViewType(IItemView.ViewTypeEnum.TOP_VIEW.value());
//		itembean.setOnItemListener(new onItemClick() {
//			@Override
//			public void onItemClick(ClickTypeEnum typeEnum, AddressItemBean bean) {
//				iGoodsView.showToast("搜索"+bean.getTitle());
//			}
//		});
//		dataMaps.add(itembean);
//		nextSection.setDataMaps(dataMaps);
//		nextSection.setShowSection(false);
//		iGoodsView.showUi(nextSection);

	}

	public void search(String text) {
		String url = ServerUrl.baseUrl+ServerUrl.GOODS_SEARCH;
		String json = text;
		url=ServerUrl.getFinalUrl(url,json);
		service.request(url, new MyCallback(new MyCallback.IResponse() {
			@Override
			public void onResponse(ServiceCallBack  callBack) {
				if(callBack.isSucess()&&callBack.getResponseMsg()!=null){
					Section nextSection=new Section(KEY_ShopOrderInfo);
					List<AddressItemBean> dataMaps=new ArrayList<>();
					ResponseMsgData data =JSON.parseObject(callBack.getResponseMsg().getMsg(),
							ResponseMsgData.class);
					List<Goods> orderList = (List<Goods>)JSON.parseArray(data.getData()
							.toString(), Goods.class);
					for (int i = 0; i < orderList.size(); i++) {
						Goods order=orderList.get(i);
						AddressItemBean itembean=getAddressItemBean(order);
						dataMaps.add(itembean);

					}
					nextSection.setDataMaps(dataMaps);
					nextSection.setShowSection(false);
					iGoodsView.showUi(nextSection);
				}
			}

			@Override
			public void onFailure(ServiceCallBack  serviceCallBack) {
				iGoodsView.showToast( "服务器响应失败");
			}
		}));

	}
}
