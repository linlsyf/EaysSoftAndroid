package com.ui.message;


import com.business.bean.Goods;
import com.core.base.BaseFragment;
import com.core.recycleview.AddressRecycleView;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.sectionview.Section;
import com.core.utils.FragmentHelper;
import com.easysoft.costumes.R;
import com.ui.message.add.AddFragment;
import com.view.toolbar.NavigationBar;
import com.view.toolbar.TopBarBuilder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * 展示商品
 */

public class GoodsFragment extends BaseFragment implements IGoodsView{
    GoodsPersenter persenter;


    AddressRecycleView recycleView;

    private NavigationBar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

    	View rootView=inflater.inflate(R.layout.fragment_tab_goods, null);
    	setRootView(rootView);
       return rootView;

    }
    @Override
    public void initFragment() {
       initUIView();
       initListener();
    }
    @Override
    public void initUIView() {
        persenter=new GoodsPersenter(this);
        recycleView = getViewById(R.id.recycleView);
        toolbar=getViewById(R.id.toolbar);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "商品列表", 0);
        recycleView.postDelayed(new Runnable() {

            @Override
            public void run() {
                persenter.list();

            }
        }, 500);


    }
    @Override
    public void initListener() {

    }

	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		// TODO Auto-generated method stub
		
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
    public void showItem(AddressItemBean imgBean) {

    }

    @Override
    public void toOrder(Goods goods) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("goods", (Serializable) goods);
        bundle.putSerializable("type", "edit");
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new AddFragment(), bundle);

    }
}
