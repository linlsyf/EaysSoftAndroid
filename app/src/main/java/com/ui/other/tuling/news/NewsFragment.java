package com.ui.other.tuling.news;

import android.os.Bundle;

import com.core.base.BaseFragment;
import com.easy.recycleview.recycleview.AddressRecycleView;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.easysoft.costumes.R;
import com.easysoft.utils.lib.system.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.ui.other.tuling.entity.NewsEntity;
import com.ui.other.tuling.news.newdetail.NewDetailFragment;

/**
 * Created by sunfusheng on 2015/2/5.
 */
public class NewsFragment extends BaseFragment implements INewsView {


    private NavigationBar toolbar;

    AddressRecycleView recycleView;

    NewsPresenter persenter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_common;
    }

    @Override
    public void initFragment() {

      initUIView();
        initData();
        initView();
    }

    @Override
    public void initUIView() {

        recycleView = getViewById(R.id.goodsGridview);

        toolbar=getViewById(R.id.toolbar);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "新闻", 0);

    }

    public void initData() {
        persenter=new NewsPresenter(this);
        persenter.init();


    }



    @Override
    public void onDestroy() {
//        service.
        super.onDestroy();
    }

    @Override
    public void getBroadcastReceiverMessage(String type, Object mode) {

    }

    private void initView() {
//        initXlistView();


    }




    @Override
    public void initUI(final Section section) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                recycleView.updateSection(section,true);
            }
        });
    }

    @Override
    public void showItem(final NewsEntity itemBean) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Bundle bundleDetail=new Bundle();
                bundleDetail.putString("url",  itemBean.getDetailurl());
                FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new NewDetailFragment(), bundleDetail);
            }
        });
    }
}
