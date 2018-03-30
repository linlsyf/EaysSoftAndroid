package com.ui.message.add;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.alibaba.fastjson.JSON;
import com.business.BusinessBroadcastUtils;
import com.business.ServiceCallBack;
import com.business.bean.FileRecorder;
import com.business.bean.Goods;
import com.business.bean.ResponseMsg;
import com.business.bean.ResponseMsgData;
import com.business.bean.ShopOrder;
import com.business.bean.ShopOrderMsg;
import com.business.login.User;
import com.core.CoreApplication;
import com.core.ServerUrl;
import com.core.base.GlobalConstants;
import com.core.http.OkHttpUtils;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.item.IItemView;
import com.core.recycleview.item.IItemView.ClickTypeEnum;
import com.core.recycleview.item.IItemView.onItemClick;
import com.core.recycleview.item.bean.AddressRightFistImgeSettings;
import com.core.recycleview.item.bean.AddressRightSecondImgSettings;
import com.core.recycleview.sectionview.Section;
import com.core.utils.DensityUtil;
import com.core.utils.StringUtils;
import com.core.utils.ToastUtils;
import com.core.utils.system.AndroidUtil;
import com.easysoft.costumes.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ui.HttpService;
import com.ui.car.IShopOrderListView;
import com.ui.car.MyCallback;
import com.view.toolbar.NavigationBarBean;
import com.view.toolbar.NavigationBar.Location;
import com.view.toolbar.NavigationBar.Style;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class OrderDetailPersenter {
	private HttpService service;
	private IShopOrderItemView iShopOrderItemView;
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

	public OrderDetailPersenter() {
	}
	public OrderDetailPersenter(IShopOrderItemView iShopOrderItemView) {
		service=new HttpService();
		this.iShopOrderItemView=iShopOrderItemView;
	}
	
	
	public onItemClick getNormelItemClick(final AddressItemBean itemNameBean){
	onItemClick itemclick =new onItemClick() {
		
		@Override
		public void onItemClick(ClickTypeEnum typeEnum, AddressItemBean bean) {
			iShopOrderItemView.inputItem(itemNameBean);
			
		}};
		return itemclick;
	} 
	public AddressItemBean getTotalItemData(){
		return totalBean;
	}
	
	public void initUI(ShopOrder order,Goods goods,boolean isAdd){
		
   	Section nextSection=new Section(KEY_ShopOrderInfo);
   	List<AddressItemBean> dataMaps=new ArrayList<>();
 
   	
   	final AddressItemBean itemNameBean=new AddressItemBean();
   	itemNameBean.setTitle("名称");
   	
   	itemNameBean.setId(KEY_NAME);
   	itemNameBean.setRightFirstText(goods.getName());
   	if(isAdd){
   	   	itemNameBean.setOnItemListener(getNormelItemClick(itemNameBean));
   	}
   	dataMaps.add(itemNameBean);
   	
   	
   	AddressItemBean itembeanSpace = new AddressItemBean();
	itembeanSpace.setViewType(IItemView.ViewTypeEnum.SPLITE
						.value());
	dataMaps.add(itembeanSpace);

   	AddressItemBean imgBean=new AddressItemBean();
   	imgBean.setTitle("图片");
   	imgBean.setId(KEY_IMG);

   	AddressRightSecondImgSettings  imgSetting=new AddressRightSecondImgSettings();
   	imgSetting.setRightSecondImgRadius(DensityUtil.dip2px(CoreApplication.getAppContext(), 25));
   	if (StringUtils.isNotEmpty(goods.getImagUrl())) {
   		imgSetting.setRightSecondImgURL(goods.getImagUrl());
	}else{
	   	imgSetting.setRightSecondImgResId(R.drawable.address_book_list_empty);

	}
   	
   	imgBean.setAddressRightSecondImgSettings(imgSetting);
   	dataMaps.add(imgBean);
   	
	if(isAdd){
		imgBean.setOnItemListener(new onItemClick() {
			
			@Override
			public void onItemClick(ClickTypeEnum typeEnum, AddressItemBean bean) {
				iShopOrderItemView.selectImg();
				
			}
		});
   	}
   	
   	AddressItemBean itembeanSpace2 = new AddressItemBean();
	itembeanSpace2.setViewType(IItemView.ViewTypeEnum.SPLITE
						.value());
	dataMaps.add(itembeanSpace2);
	AddressItemBean colorNameBean=new AddressItemBean();
	colorNameBean.setTitle("颜色");
	colorNameBean.setId(KEY_COLOR_NAME);
	colorNameBean.setRightFirstText(goods.getColorName());
	if(isAdd){
		colorNameBean.setOnItemListener(getNormelItemClick(colorNameBean));
   	}
	dataMaps.add(colorNameBean);
	
	AddressItemBean itembeanSpace3 = new AddressItemBean();
	itembeanSpace3.setViewType(IItemView.ViewTypeEnum.SPLITE
						.value());
	dataMaps.add(itembeanSpace3);
	AddressItemBean colorNumBean=new AddressItemBean();
	colorNumBean.setTitle("色号");
	colorNumBean.setId(KEY_COLOR_NUM);
	colorNumBean.setRightFirstText(goods.getColorNum());
	if(isAdd){
		colorNumBean.setOnItemListener(getNormelItemClick(colorNumBean));
   	}
	dataMaps.add(colorNumBean);
	
	AddressItemBean itembeanSpace4 = new AddressItemBean();
	itembeanSpace4.setViewType(IItemView.ViewTypeEnum.SPLITE
						.value());
	dataMaps.add(itembeanSpace4);
	AddressItemBean  priceBean=new AddressItemBean();
	priceBean.setTitle("价格");
	priceBean.setId(KEY_PRICE);
	priceBean.setRightFirstText(goods.getPrice()+"");
	if(isAdd){
		priceBean.setOnItemListener(getNormelItemClick(priceBean));
   	}
	dataMaps.add(priceBean);
	
	AddressItemBean itembeanSpace5 = new AddressItemBean();
	itembeanSpace5.setViewType(IItemView.ViewTypeEnum.SPLITE
						.value());
	dataMaps.add(itembeanSpace5);
	
	AddressItemBean  numBean=new AddressItemBean();
	numBean.setTitle("数量");
	numBean.setId(KEY_NUM);
	numBean.setRightFirstText(order.getNum()+"");
	if(isAdd){
		numBean.setOnItemListener(getNormelItemClick(numBean));
   	}
	dataMaps.add(numBean);
	
	
	AddressItemBean itembeanSpace6 = new AddressItemBean();
	itembeanSpace6.setViewType(IItemView.ViewTypeEnum.SPLITE
						.value());
	dataMaps.add(itembeanSpace6);
	
	  totalBean=new AddressItemBean();
	totalBean.setTitle("总价");
	totalBean.setId(KEY_TOTAL);
	totalBean.setRightFirstText(order.getTotal()+"");
//	if(!isShow){
//		totalBean.setOnItemListener(getNormelItemClick(noteBean));
//   	}
	dataMaps.add(totalBean);
	
	
	AddressItemBean itembeanSpace7 = new AddressItemBean();
	itembeanSpace7.setViewType(IItemView.ViewTypeEnum.SPLITE
						.value());
	dataMaps.add(itembeanSpace7);
	AddressItemBean  noteBean=new AddressItemBean();
	noteBean.setTitle("备注");
	noteBean.setId(KEY_CONTENT);
	noteBean.setRightFirstText(goods.getContent());
	if(isAdd){
		noteBean.setOnItemListener(getNormelItemClick(noteBean));
   	}
	dataMaps.add(noteBean);
	
   	 nextSection.setDataMaps(dataMaps);
   	iShopOrderItemView.showUi(nextSection);
	}
	public void updateImg(String path){
	 	AddressItemBean imgBean=new AddressItemBean();
	   	imgBean.setTitle("图片");
	   	imgBean.setId(KEY_IMG);
	 	AddressRightSecondImgSettings  imgSetting=new AddressRightSecondImgSettings();
	   	imgSetting.setRightSecondImgRadius(DensityUtil.dip2px(CoreApplication.getAppContext(), 25));
	   	imgSetting.setRightSecondImgStorePath(path);
	   	imgBean.setAddressRightSecondImgSettings(imgSetting);
	   	imgBean.setOnItemListener(new onItemClick() {
			
			@Override
			public void onItemClick(ClickTypeEnum typeEnum, AddressItemBean bean) {
				iShopOrderItemView.selectImg();
				
			}
		});
	   	
		iShopOrderItemView.updateItem(imgBean);
	}


	 public void getCompressPath(final String url, final String chooseImgPath){
		 Luban.with(iShopOrderItemView.getContext())
				 .load(chooseImgPath)                                   // 传人要压缩的图片列表
				 .ignoreBy(100)                                  // 忽略不压缩图片的大小
				 .setTargetDir(GlobalConstants.getInstance().getAppDocumentHomePath())                        // 设置压缩后文件存储位置
				 .setCompressListener(new OnCompressListener() { //设置回调
					 @Override
					 public void onStart() {
						 // TODO 压缩开始前调用，可以在方法内启动 loading UI
					 }

					 @Override
					 public void onSuccess(File file) {
						 // TODO 压缩成功后调用，返回压缩后的图片文件
						 upLoadImg( url, file.getAbsolutePath());
					 }

					 @Override
					 public void onError(Throwable e) {
						 iShopOrderItemView.showToast("获取压缩图片失败");
					 }
				 }).launch();    //启动压缩
	 }


	public void upLoadImg(String url, final String chooseImgPath) {


		OkHttpUtils.getInStance().uploadFile(url, chooseImgPath,"", new Callback() {

			@Override
			public void onResponse(Call call, Response response) throws IOException {

				if (response.isSuccessful()) {
					ResponseBody body=response.body();
					String  msg=body.string();
					ResponseMsgData responseMsg= JSON.parseObject(msg,ResponseMsgData.class);

					FileRecorder file=JSON.parseObject(responseMsg.getData().toString(),FileRecorder.class);

					iShopOrderItemView.getImgeDbId(file.getId());

					updateImg(chooseImgPath);
				}else{
					iShopOrderItemView.showToast( "上传图片失败");
				}
			}

			@Override
			public void onFailure(Call call, IOException areg1) {
				String  result=	call.toString();
				iShopOrderItemView.showToast( "上传图片失败");

			}
		});
	}

	public void add(ShopOrder editOrder, Goods goods) {

		String url= ServerUrl.baseUrl+ServerUrl.addOrder;
		ObjectMapper mapper = new ObjectMapper();

		String shopOrderMsgJson = null;

		ShopOrderMsg  shopOrderMsg=new ShopOrderMsg();
		try {

			String orderJson = mapper.writeValueAsString(editOrder);
			shopOrderMsg.setOrder(orderJson);
			String goodsJson = mapper.writeValueAsString(goods);
			shopOrderMsg.setGoods(goodsJson);
			shopOrderMsgJson = mapper.writeValueAsString(shopOrderMsg);

		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		url=ServerUrl.getFinalUrl(url,shopOrderMsgJson);

		service.request(url, new MyCallback(new MyCallback.IResponse() {

			@Override
			public void onResponse(ServiceCallBack callBack) {
				if(callBack.isSucess()&&callBack.getResponseMsg()!=null){

					ResponseMsg responseMsg = JSON.parseObject(callBack.getResponseMsg().getMsg(), ResponseMsg.class);
					if (responseMsg.isSuccess()) {
						iShopOrderItemView.addSucess();
						BusinessBroadcastUtils.sendBroadcast(iShopOrderItemView.getContext(), BusinessBroadcastUtils.TYPE_SHOPCAR_LIST, null);
						BusinessBroadcastUtils.sendBroadcast(iShopOrderItemView.getContext(), BusinessBroadcastUtils.TYPE_GOODS_ADD_SUCESS, null);
						BusinessBroadcastUtils.sendBroadcast(iShopOrderItemView.getContext(), BusinessBroadcastUtils.Type_Local_HOME_PAGE_CHANGE, 1);
					}
				}else{
					iShopOrderItemView.showToast("服务器响应失败");
				}
			}


			@Override
			public void onFailure(ServiceCallBack  serviceCallBack) {
						// TODO Auto-generated method stub
//						String  result=	serviceCallBack.toString();
						ToastUtils.show(iShopOrderItemView.getContext(),"请求失败");
			}
		}));

	}

}
