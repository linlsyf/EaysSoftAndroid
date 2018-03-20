package com.ui.message;

import com.alibaba.fastjson.JSON;
import com.business.BusinessBroadcastUtils;
import com.business.ServiceCallBack;
import com.business.bean.FileRecorder;
import com.business.bean.Goods;
import com.business.bean.ResponseMsg;
import com.business.bean.ResponseMsgData;
import com.business.bean.ShopOrder;
import com.business.bean.ShopOrderMsg;
import com.core.CoreApplication;
import com.core.ServerUrl;
import com.core.http.OkHttpUtils;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.item.IItemView;
import com.core.recycleview.item.IItemView.ClickTypeEnum;
import com.core.recycleview.item.IItemView.onItemClick;
import com.core.recycleview.item.bean.AddressHeadImgeSettings;
import com.core.recycleview.item.bean.AddressRightSecondImgSettings;
import com.core.recycleview.sectionview.Section;
import com.core.utils.DensityUtil;
import com.core.utils.StringUtils;
import com.core.utils.ToastUtils;
import com.easysoft.costumes.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ui.HttpService;
import com.ui.car.MyCallback;
import com.ui.message.add.IShopOrderItemView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GoodsPersenter {
	private HttpService service;
	private IGoodsView iGoodsView;
	private AddressItemBean totalBean;
	public static String KEY_ShopOrderInfo="ShopOrderInfo";
	public static String KEY_NAME="name";
	public static String KEY_IMG="image";
	public static String KEY_COLOR_NAME="colorName";
	public static String KEY_COLOR_NUM="colorNum";
	public static String KEY_CONTENT="content";
	public static String KEY_NUM="num";
	public static String KEY_TOTAL="total";
	public static String KEY_PRICE="price";

	public GoodsPersenter() {
	}
	public GoodsPersenter( IGoodsView iGoodsView) {
		service=new HttpService();
		this.iGoodsView=iGoodsView;
	}
	
	
	public onItemClick getNormelItemClick(final AddressItemBean itemNameBean){
	onItemClick itemclick =new onItemClick() {
		
		@Override
		public void onItemClick(ClickTypeEnum typeEnum, AddressItemBean bean) {
			iGoodsView.showItem(itemNameBean);
			
		}};
		return itemclick;
	}

	public AddressItemBean  getAddressItemBean(final Goods goods){
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
					iGoodsView.toOrder(goods);
//					get(getUrl, json);

				}


			}
		});

//			ShopOrder order=orderList.get(i);


		itembean.setTitle(goods.getName());
		itembean.setId(goods.getId());
		AddressHeadImgeSettings headImgeSettings = new AddressHeadImgeSettings();
		String  imgURL=ServerUrl.baseUrl+ServerUrl.IMG_URL+goods.getImageId();
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
			service.request(url, json,new MyCallback(new MyCallback.IResponse() {

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
							if (i != orderList.size()-1) {
								AddressItemBean itembeanSpace = new AddressItemBean();
								itembeanSpace.setViewType(IItemView.ViewTypeEnum.SPLITE
										.value());
								dataMaps.add(itembeanSpace);

							}

						}
						nextSection.setDataMaps(dataMaps);
						nextSection.setShowSection(false);
						iGoodsView.showUi(nextSection);


					}
				}

				@Override
				public void onFailure(ServiceCallBack  serviceCallBack) {
					ToastUtils.show(iGoodsView.getContext(), "服务器响应失败");


				}
			}));
////
		Section nextSection=new Section(KEY_ShopOrderInfo);
		List<AddressItemBean> dataMaps=new ArrayList<>();
		for (int i=0;i<10;i++ ) {
			AddressItemBean itemBean=new AddressItemBean();
			itemBean.setTitle("测试"+i);
			dataMaps.add(itemBean);
		}

		nextSection.setDataMaps(dataMaps);
		nextSection.setShowSection(false);
		iGoodsView.showUi(nextSection);



	}

}