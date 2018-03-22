package com.ui.message;


import com.business.BusinessBroadcastUtils;
import com.business.bean.Goods;
import com.core.base.BaseFragment;
import com.core.recycleview.AddressRecycleView;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.sectionview.Section;
import com.core.utils.FragmentHelper;
import com.easysoft.costumes.R;
import com.ui.message.add.AddFragment;
import com.ui.message.view.GoodsView;
import com.view.toolbar.NavigationBar;
import com.view.toolbar.NavigationBarListener;
import com.view.toolbar.TopBarBuilder;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 展示商品
 */

public class GoodsFragment extends BaseFragment implements IGoodsView{
    GoodsPersenter persenter;
    GoodsView recycleView;
    private NavigationBar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    int sum = 0;

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
        TopBarBuilder.buildOnlyText(toolbar, getActivity(), NavigationBar.Location.RIGHT_FIRST, "新加", 0);
//        if (BusinessBroadcastUtils.loginUser!=null){
            recycleView.postDelayed(new Runnable() {

                @Override
                public void run() {
                    persenter.list();

                }
            }, 500);
//        }


//        swipeRefreshLayout = (SwipeRefreshLayout) getViewById(R.id.swipeRefreshLayout);
//        recyclerView = (RecyclerView) getViewById(R.id.recyclerView);
        recycleView.getSectionAdapterHelper().initLayoutManager(new GridLayoutManager(getActivity(),2));
//        recycleView.getSectionAdapterHelper().initLayoutManager(new StaggerGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //设置瀑布流Item
        SpacesItemDecoration decoration = new SpacesItemDecoration(20);
        recycleView.getSectionAdapterHelper().addItemDecoration(decoration);


//        /**
//         * 下拉刷新
//         * */
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        list.remove(sum);
//                        swipeRefreshLayout.setRefreshing(false);
//                        myAdapter.notifyDataSetChanged();
//                    }
//                }, 2000);
//            }
//        });
    }
    @Override
    public void initListener() {
        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {
                if (location== NavigationBar.Location.RIGHT_FIRST) {
                    Bundle bundle=new Bundle();
//                    bundle.putSerializable("goods", (Serializable) goods);
                    bundle.putSerializable("type",AddFragment.TYPE_ADD);
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new AddFragment(), bundle);



                }

            }
        });
    }

	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
        if(type.equals(BusinessBroadcastUtils.TYPE_RELOGIN_SUCESS)){
           persenter.list();
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
