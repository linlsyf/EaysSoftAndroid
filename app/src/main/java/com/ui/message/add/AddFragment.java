package com.ui.message.add;


import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.business.BusinessBroadcastUtils;
import com.business.bean.FileRecorder;
import com.business.bean.Goods;
import com.business.bean.ResponseMsg;
import com.business.bean.ResponseMsgData;
import com.business.bean.ShopOrder;
import com.business.bean.ShopOrderMsg;
import com.core.ServerUrl;
import com.core.base.BaseFragment;
import com.core.http.OkHttpUtils;
import com.core.recycleview.AddressRecycleView;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.sectionview.Section;
import com.core.utils.FragmentHelper;
import com.core.utils.StringUtils;
import com.core.utils.ToastUtils;
import com.easysoft.costumes.R;
import com.example.choose.ChooseFragmentActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ui.common.InformationInputFragment;
import com.ui.common.InformationInputFragment.OnUpdateSuccessListener;
import com.view.toolbar.NavigationBar;
import com.view.toolbar.NavigationBar.Location;
import com.view.toolbar.NavigationBarListener;
import com.view.toolbar.TopBarBuilder;

/**
 * Created by chengxi on 17/4/26.
 */
public class AddFragment extends BaseFragment implements IShopOrderItemView{

	  
	 public Goods editGoods=new Goods();
	 public ShopOrder editOrder=new ShopOrder();
//	 public ShopOrderMsg editOrderMsg=new ShopOrderMsg();
	private String chooseImgPath="";

	private AddressRecycleView recycleView;
	private NavigationBar  toolbar;
	private OrderDetailPersenter persenter;
	Button buyBtn;
	Button addToChartBtn;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

    	View rootView=inflater.inflate(R.layout.fragment_add, null);

    	setRootView(rootView);
       return rootView;

    }

    @Override
    public void initFragment() {
    	
  	  initUIView();

       initData();
       initListener();
    }
    @Override
    public void initUIView() {
    	  recycleView=getViewById(R.id.recycleView);
    	   toolbar=getViewById(R.id.toolbar);
			
    	   buyBtn=getViewById(R.id.buy);
    	   addToChartBtn=getViewById(R.id.add);


    }
    @Override
    public void initListener() {
    	 

    	 toolbar.setNavigationBarListener(new NavigationBarListener() {

			@Override
			public void onClick(ViewGroup containView, Location location) {
				  if (location==Location.LEFT_FIRST) {
						FragmentHelper.popBackFragment(getActivity());
				  }
				
			}
		});
		 addToChartBtn.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View view) {

					 if(StringUtils.isEmpty(editGoods.getImageId())){
						 ToastUtils.show(getActivity(), "图片路径为空");
						 return;
					 }
					 editOrder.setType(1);
					 persenter.add(editOrder,editGoods);

			 }
		 });
		 buyBtn.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View view) {

					 editOrder.setType(2);

					 if(StringUtils.isEmpty(editGoods.getImageId())){
						 ToastUtils.show(getActivity(), "图片路径为空");
						 return;
					 }
					 persenter.add(editOrder,editGoods);

			 }
		 });

    }
    @Override
    public void initData() {
  	  persenter=new OrderDetailPersenter(this);
		persenter.test();
  	editOrder = new ShopOrder();
    Bundle bundle=	getArguments();
    boolean isShow=false;
     if (bundle!=null&&bundle.containsKey("type")) {
		String type=bundle.getString("type");
		if (type.equals("show")) {
			if (bundle.containsKey("order")){

				editOrder=(ShopOrder)bundle.getSerializable("order");
			}
			if (bundle.containsKey("goods")){

				editGoods=(Goods)bundle.getSerializable("goods");
			}
			 isShow=true;
		}

	}
     
     if(isShow){
         TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "订单", 0);

  	   TopBarBuilder.buildLeftArrowText(toolbar, getActivity(),  "返回", 0);
         buyBtn.setVisibility(View.GONE);
         addToChartBtn.setVisibility(View.GONE);
     }else{
         TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "添加订单", 0);

//  	   TopBarBuilder.buildOnlyText(toolbar, getActivity(),Location.RIGHT_SECOND, "加入", 0);
//  	   TopBarBuilder.buildOnlyText(toolbar, getActivity(),Location.RIGHT_FIRST, "结算", 0);

     }
     
     persenter.initUI( editOrder,editGoods,isShow);
     
    }
	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		// TODO Auto-generated method stub
		
	}
	 @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
		   super.onActivityResult(requestCode, resultCode, data);
		 if(data != null ){//有数据返回直接使用返回的图片地址
			 chooseImgPath= data.getStringExtra(ChooseFragmentActivity.key_path);
           String url=ServerUrl.baseUrl+ServerUrl.UPLOAD_URL;
          persenter.upLoadImg(url,chooseImgPath);

		 }
		}
	@Override
	public void showUi(final Section nextSection) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				recycleView.updateSection(nextSection);

			}
		});

	}
	@Override
	public void selectImg() {
		editGoods.setImageId("");

		Intent intent=new Intent(getActivity(),ChooseFragmentActivity.class);
		 startActivityForResult(intent, 10001);
		
	}
	@Override
	public void updateItem(final AddressItemBean imgBean) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				recycleView.getSectionAdapterHelper().updateItem(OrderDetailPersenter.KEY_ShopOrderInfo, imgBean);
			}
		});
	}

	@Override
	public void getImgeDbId(String id) {
		editGoods.setImageId(id);
	}

	@Override
	public void inputItem(final AddressItemBean itemBean) {
		Bundle bundle=new Bundle();
		bundle.putSerializable("data", (Serializable) itemBean);
		bundle.putSerializable("type", "show");
		bundle.putSerializable(InformationInputFragment.ID, itemBean.getId());
		InformationInputFragment inputFragment= new InformationInputFragment();
		inputFragment.setOnUpdateSuccessListener(new OnUpdateSuccessListener() {
			
			@Override
			public void updateSuccess(String mContent,String  mId) {
				if (mId.equals(OrderDetailPersenter.KEY_NAME)) {
					editGoods.setName(mContent);
				}
				else if(mId.equals(OrderDetailPersenter.KEY_COLOR_NAME)){
					editGoods.setColorName(mContent);
				}
				else if(mId.equals(OrderDetailPersenter.KEY_COLOR_NUM)){
					editGoods.setColorNum(mContent);
				}
				else if(mId.equals(OrderDetailPersenter.KEY_PRICE)){
					editGoods.setPrice(Integer.parseInt(mContent));
					
					double total=editOrder.getNum()*editGoods.getPrice();
					editOrder.setTotal(total);
			
					AddressItemBean totalBean=persenter.getTotalItemData();
					totalBean.setRightFirstText(total+"");
					recycleView.getSectionAdapterHelper().updateItem(OrderDetailPersenter.KEY_ShopOrderInfo, totalBean);			
				}
				
				else if(mId.equals(OrderDetailPersenter.KEY_NUM)){
					editOrder.setNum(Integer.parseInt(mContent));
					double total=Integer.parseInt(mContent)*editGoods.getPrice();
					editOrder.setTotal(total);
			
					AddressItemBean totalBean=persenter.getTotalItemData();
					totalBean.setRightFirstText(total+"");
					recycleView.getSectionAdapterHelper().updateItem(OrderDetailPersenter.KEY_ShopOrderInfo, totalBean);			
					
				}
				else if(mId.equals(OrderDetailPersenter.KEY_CONTENT)){
					editGoods.setContent(mContent);
				}
				itemBean.setRightFirstText(mContent);
				recycleView.getSectionAdapterHelper().updateItem(OrderDetailPersenter.KEY_ShopOrderInfo, itemBean);			
				
			}
		});
		FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, inputFragment, bundle);
		
		
	}

	@Override
	public Context getContext() {
		return getActivity();
	}

	@Override
	public void showToast(final String text) {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ToastUtils.show(getActivity(),text);
			}
		});
	}
}
