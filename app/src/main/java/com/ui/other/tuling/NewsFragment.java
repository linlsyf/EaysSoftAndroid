package com.ui.other.tuling;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.core.CoreApplication;
import com.core.base.BaseFragment;
import com.easysoft.costumes.R;
import com.easysoft.utils.lib.system.FragmentHelper;
import com.ui.other.tuling.adapter.NewsAdapter;
import com.ui.other.tuling.entity.MessageEntity;
import com.ui.other.tuling.entity.NewsEntity;
import com.ui.other.tuling.util.DisplayUtil;
import com.view.refreshswipemenulistview.XListView;
import com.view.swipemenulistview.SwipeMenu;
import com.view.swipemenulistview.SwipeMenuCreator;
import com.view.swipemenulistview.SwipeMenuItem;
import com.view.swipemenulistview.SwipeMenuListView;
import com.view.toolbar.NavigationBar;
import com.view.toolbar.TopBarBuilder;

import java.util.List;

/**
 * Created by sunfusheng on 2015/2/5.
 */
public class NewsFragment extends BaseFragment implements XListView.IXListViewListener {

//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
//    @Bind(R.id.xlv_listView)
    XListView xlvListView;

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
        setSwipeMenuCreator();
        initSwipeMenuItemClickListener();
    }

    private void initXlistView() {
        xlvListView.setXListViewListener(this);
        xlvListView.setPullRefreshEnable(true);
        xlvListView.setPullLoadEnable(true);
        xlvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundleDetail=new Bundle();
                bundleDetail.putString("url",  newsList.get(position - 1).getDetailurl());
                FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new NewDetailFragment(), bundleDetail);

            }
        });
    }

    private void setSwipeMenuCreator() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(CoreApplication.getAppContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                openItem.setWidth(DisplayUtil.dip2px(getActivity(), 90));
                openItem.setTitle("删除");
                openItem.setTitleSize(16);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
            }
        };
        xlvListView.setMenuCreator(creator);
    }

    private void initSwipeMenuItemClickListener() {
        xlvListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        deleteNewsListItem(position);
                        break;
                }
                return true;
            }
        });
    }

    private void deleteNewsListItem(int position) {
        NewsEntity entity = newsList.get(position);
        newsList.remove(entity);
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xlvListView.stopRefresh();
                xlvListView.setRefreshTime("刚刚");
                newsAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xlvListView.stopLoadMore();
                newsAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }
}
