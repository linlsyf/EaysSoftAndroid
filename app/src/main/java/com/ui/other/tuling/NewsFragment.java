package com.ui.other.tuling;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.core.CoreApplication;
import com.core.base.BaseFragment;
import com.easysoft.costumes.R;
import com.easysoft.utils.lib.system.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.ui.other.tuling.adapter.NewsAdapter;
import com.ui.other.tuling.entity.MessageEntity;
import com.ui.other.tuling.entity.NewsEntity;
import com.ui.other.tuling.util.DisplayUtil;


import java.util.List;

/**
 * Created by sunfusheng on 2015/2/5.
 */
public class NewsFragment extends BaseFragment  {

//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
//    @Bind(R.id.xlv_listView)
    ListView xlvListView;

    private List<NewsEntity> newsList;
    private NewsAdapter newsAdapter;
    private NavigationBar toolbar;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_news;
    }

    @Override
    public void initFragment() {

      initUIView();
        initData();
        initView();
    }

    @Override
    public void initUIView() {
      xlvListView=getViewById(R.id.xlv_listView);
        toolbar=getViewById(R.id.toolbar);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "新闻", 0);

    }

    public void initData() {
        MessageEntity messageEntity = (MessageEntity) getArguments().getSerializable("messageEntity");
        if (messageEntity != null && messageEntity.getList() != null && messageEntity.getList().size() > 0) {
            newsList = messageEntity.getList();
        } else {
            FragmentHelper.popBackFragment(getActivity());
        }
    }

    @Override
    public void getBroadcastReceiverMessage(String type, Object mode) {

    }

    private void initView() {
        initXlistView();
        newsAdapter = new NewsAdapter(getActivity(), newsList);
        xlvListView.setAdapter(newsAdapter);

    }

    private void initXlistView() {

        xlvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundleDetail=new Bundle();
                bundleDetail.putString("url",  newsList.get(position - 1).getDetailurl());
                FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new NewDetailFragment(), bundleDetail);

            }
        });
    }



    private void deleteNewsListItem(int position) {
        NewsEntity entity = newsList.get(position);
        newsList.remove(entity);
        newsAdapter.notifyDataSetChanged();
    }

}
