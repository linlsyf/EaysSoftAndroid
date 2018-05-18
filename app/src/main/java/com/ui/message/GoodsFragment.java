package com.ui.message;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.business.BusinessBroadcastUtils;
import com.business.bean.Goods;
import com.core.base.BaseFragment;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.easysoft.costumes.R;
import com.easysoft.utils.lib.system.FragmentHelper;
import com.easysoft.utils.lib.system.StringUtils;
import com.easysoft.widget.banner.BannerView;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.ui.message.add.AddFragment;
import com.ui.message.view.GoodsView;
import com.view.search.SearchHeadView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 展示商品
 */

public class GoodsFragment extends BaseFragment implements IGoodsView{
    GoodsPersenter persenter;
    GoodsView recycleView;
    private NavigationBar toolbar;
    TextView noticeTv;
    SearchHeadView searchHeadView;

//    private List<View> viewList;
//    BannerView bannerView;

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
        recycleView = getViewById(R.id.goodsGridview);
        noticeTv = getViewById(R.id.noticeTv);
        toolbar=getViewById(R.id.toolbar);
        searchHeadView=getViewById(R.id.searchView);
        recycleView.requestFocus();
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "首页", 0);
        if (BusinessBroadcastUtils.loginUser!=null){
            if (BusinessBroadcastUtils.loginUser.getIsAdmin().equals("1")){
                TopBarBuilder.buildOnlyText(toolbar, getActivity(), NavigationBar.Location.RIGHT_FIRST, "新加", 0);
            }
        }
        persenter.initTop();
        if (BusinessBroadcastUtils.loginUser!=null){
            recycleView.postDelayed(new Runnable() {

                @Override
                public void run() {
                    persenter.list();

                }
            }, 500);
        }

//        swipeRefreshLayout = (SwipeRefreshLayout) getViewById(R.id.swipeRefreshLayout);
//        recyclerView = (RecyclerView) getViewById(R.id.recyclerView);
//        recycleView.getSectionAdapterHelper().initLayoutManager(new GridLayoutManager(getActivity(),3));
//        recycleView.getSectionAdapterHelper().initLayoutManager(new StaggerGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //设置瀑布流Item
//        SpacesItemDecoration decoration = new SpacesItemDecoration(20);
//        recycleView.getSectionAdapterHelper().addItemDecoration(decoration);

//        /**
//         * 下拉刷新
//         * */
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        list.remove(sum);
////                        swipeRefreshLayout.setRefreshing(false);
////                        myAdapter.notifyDataSetChanged();
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 2000);
//            }
//        });



//        bannerView.setTransformAnim(true);
    }
    @Override
    public void initListener() {
        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {
                if (location== NavigationBar.Location.RIGHT_FIRST) {
                    Bundle bundle=new Bundle();
//                    bundle.putSerializable("goods", (Serializable) goods);
                    bundle.putSerializable("type",AddFragment.TYPE_Admin_ADD_GOODS);
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new AddFragment(), bundle);
                }

            }
        });
        noticeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessBroadcastUtils.sendBroadcast(getContext(), BusinessBroadcastUtils.TYPE_RELOGIN, null);

            }
        });
        searchHeadView.getBackLayout().setVisibility(View.GONE);
        searchHeadView.setOnTextChangerListener(new SearchHeadView.onTextChangerListener() {
            @Override
            public void onTextChanger(String text) {
                 if(StringUtils.isEmpty(text)){
                     persenter.list();
                 }else{
                     persenter.search( text);
                 }
            }
        });
    }

	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {

         if(type.equals(BusinessBroadcastUtils.TYPE_LOGIN_SUCESS)){
           persenter.list();
            persenter.reInitToolBar();
            noticeTv.setVisibility(View.GONE);
        }
        if(type.equals(BusinessBroadcastUtils.TYPE_LOGIN_FAILS)){

            noticeTv.setVisibility(View.VISIBLE);
        }
        else if(type.equals(BusinessBroadcastUtils.TYPE_GOODS_ADD_SUCESS)){
            persenter.list();
        }
	}

    @Override
    public void showUi(final Section nextSection) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                recycleView.updateSection(nextSection,true);
            }
        });

    }

    @Override
    public void addSection(Section nextSection) {
        recycleView.addSection(nextSection);
    }


    @Override
    public void showItem(AddressItemBean imgBean) {

    }

    @Override
    public void toOrder(Goods goods) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("goods", (Serializable) goods);
             bundle.putSerializable("type", AddFragment.TYPE_ADD);
        FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new AddFragment(), bundle);
    }

    @Override
    public void resetToolBar(boolean isAdmin) {
         if (isAdmin){
            TopBarBuilder.buildOnlyText(toolbar, getActivity(), NavigationBar.Location.RIGHT_FIRST, "新加", 0);

        }
    }

//    @Override
//    public void addLayoutHelper(LayoutHelper helper,boolean isRefresh) {
//        recycleView.addLayoutHelper(helper,isRefresh);
//    }
}
