package com.ui.car;

import java.io.Serializable;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.business.BusinessBroadcastUtils;
import com.business.bean.Goods;
import com.business.bean.ShopOrder;
import com.core.base.BaseFragment;
import com.core.recycleview.AddressRecycleView;
import com.core.recycleview.sectionview.Section;
import com.core.utils.FragmentHelper;
import com.easysoft.costumes.R;
import com.ui.login.IlogInView;
import com.ui.login.LoginPresenter;
import com.ui.message.add.AddFragment;
import com.view.toolbar.NavigationBar;
import com.view.toolbar.TopBarBuilder;
import com.view.toolbar.NavigationBar.Location;

/**
 * Created by chengxi on 17/4/26.
 */
public  class ShopOrderListFragment extends BaseFragment implements IShopOrderListView {
	Section nextSection = new Section("");
	ShopOrderPersenter persenter;
	AddressRecycleView recycleView;

	private NavigationBar  toolbar;
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_tab_4, null);
		setRootView(rootView);
		return rootView;

	}

	@Override
	public void initFragment() {
		initData();
		initUIView();
		initListener();
	}

	@Override
	public void initUIView() {
		recycleView = getViewById(R.id.recycleView);
 	   toolbar=getViewById(R.id.toolbar);
		  TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "订单列表", 0);
		recycleView.postDelayed(new Runnable() {
			
			@Override
			public void run() {

				if (BusinessBroadcastUtils.loginUser!=null){


					persenter.list();
				}

			}
		}, 500);
		

	}

	@Override
	public void onResume() {
		super.onResume();
		persenter=new ShopOrderPersenter((IShopOrderListView)this);
//		persenter.list();
	}

	@Override
	public void initData() {
		persenter=new ShopOrderPersenter((IShopOrderListView)this);
	}
	@Override
	public void initListener() {
	
	}

	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		if(type.equals(BusinessBroadcastUtils.TYPE_SHOPCAR_LIST)|type.equals(BusinessBroadcastUtils.TYPE_RELOGIN_SUCESS)){
			persenter.list();
		}
		

	}

	@Override
	public void list(final Section nextSection ) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				recycleView.updateSection(nextSection);

			}
		});
//
	}

	@Override
	public void showOrder(ShopOrder order, Goods goods) {
		
		Bundle bundle=new Bundle();
		bundle.putSerializable("order", (Serializable) order);
		bundle.putSerializable("goods", (Serializable) goods);
		bundle.putSerializable("type", "show");
		 FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new AddFragment(), bundle);
	}

	@Override
	public void showToast(String text) {
		super.showToast(text);
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return getActivity();
	}

}
