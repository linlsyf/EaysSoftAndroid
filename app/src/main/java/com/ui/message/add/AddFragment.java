package com.ui.message.add;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.business.BusinessBroadcastUtils;
import com.business.bean.Goods;
import com.business.bean.ShopOrder;
import com.core.ServerUrl;
import com.core.base.BaseFragment;
import com.easy.recycleview.recycleview.AddressRecycleView;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.easysoft.costumes.R;
import com.easysoft.utils.lib.system.FragmentHelper;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.utils.lib.system.ToastUtils;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.example.choose.ChooseFragmentActivity;
import com.ui.common.InformationInputFragment;
import com.ui.common.InformationInputFragment.OnUpdateSuccessListener;


import java.io.Serializable;


public class AddFragment extends BaseFragment implements IShopOrderItemView{
	 public Goods editGoods=new Goods();
	 public ShopOrder editOrder=new ShopOrder();
	private String chooseImgPath="";

	private AddressRecycleView recycleView;
	private NavigationBar toolbar;
	private OrderDetailPersenter persenter;
	Button buyBtn;
	Button addToChartBtn;
	Button addGoodsBtn;
	public final static String TYPE_SHOW="show";
	public final static String TYPE_ADD="add";
	public final static String TYPE_Admin_ADD_GOODS ="addGoods";

	public final static String TYPE_EDIT="edit";


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
		addGoodsBtn=getViewById(R.id.addGoods);


    }
    @Override
    public void initListener() {
    	 toolbar.setNavigationBarListener(new NavigationBarListener() {

			@Override
			public void onClick(ViewGroup containView, NavigationBar.Location location) {
				  if (location== NavigationBar.Location.LEFT_FIRST) {
						FragmentHelper.popBackFragment(getActivity());
				  }
				
			}
		});
		 addToChartBtn.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View view) {

					 editOrder.setType(2);
				 editOrder.setCreatorId(BusinessBroadcastUtils.loginUser.getId() );
				 editOrder.setCreator(BusinessBroadcastUtils.loginUser.getName() );

				 persenter.add(editOrder);
			 }
		 });
		 buyBtn.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View view) {
				 editOrder.setCreatorId(BusinessBroadcastUtils.loginUser.getId() );
				 editOrder.setCreator(BusinessBroadcastUtils.loginUser.getName() );

					 editOrder.setType(1);

					 persenter.add(editOrder);

			 }
		 });
		addGoodsBtn.setOnClickListener(new View.OnClickListener() {
			 @Override
			 public void onClick(View view) {
					 if(StringUtils.isEmpty(editGoods.getImageId())){
						 ToastUtils.show(getActivity(), "图片路径为空");
						 return;
					 }
					 persenter.addGoods(editGoods);

			 }
		 });

    }
    @Override
    public void initData() {
  	  persenter=new OrderDetailPersenter(this);
  	editOrder = new ShopOrder();
    Bundle bundle=	getArguments();
    boolean isBuy=false;
    boolean isAddGoods=false;
     if (bundle!=null&&bundle.containsKey("type")) {
		String type=bundle.getString("type");
		if (type.equals(TYPE_ADD)) {
			isBuy=true;

		}
		if (type.equals(TYPE_Admin_ADD_GOODS)) {
			isAddGoods=true;
		}
			if (bundle.containsKey("order")){//查看订单

				editOrder=(ShopOrder)bundle.getSerializable("order");
			}
			if (bundle.containsKey("goods")){//新增订单
				editGoods=(Goods)bundle.getSerializable("goods");
			}
	}
		editOrder.setGoodsId(editGoods.getId());
       if (isAddGoods){
		   TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "新增商品", 0);
		   TopBarBuilder.buildLeftArrowText(toolbar, getActivity(),  "返回", 0);
		   buyBtn.setVisibility(View.GONE);
		   addToChartBtn.setVisibility(View.GONE);
		   addGoodsBtn.setVisibility(View.VISIBLE);
	   }
     else if(!isBuy){
         TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "订单", 0);
  	   TopBarBuilder.buildLeftArrowText(toolbar, getActivity(),  "返回", 0);
         buyBtn.setVisibility(View.GONE);
         addToChartBtn.setVisibility(View.GONE);
     }else{
         TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "添加订单", 0);
		 TopBarBuilder.buildLeftArrowText(toolbar, getActivity(),  "返回", 0);
     }
     persenter.initUI( editOrder,editGoods,isBuy,isAddGoods);
    }
	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {

	}
	 @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
		   super.onActivityResult(requestCode, resultCode, data);
		 if(data != null ){//有数据返回直接使用返回的图片地址
			 chooseImgPath= data.getStringExtra(ChooseFragmentActivity.key_path);
           String url=ServerUrl.baseUrl+ServerUrl.UPLOAD_URL;
          persenter.getCompressPath(url,chooseImgPath);

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
	public void addSucess() {
		FragmentHelper.popBackFragment(getActivity());
	}

	@Override
	public void addGoodsSucess() {
		FragmentHelper.popBackFragment(getActivity());
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
