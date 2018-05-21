package com.ui.message.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easysoft.costumes.R;
import com.easysoft.widget.search.SearchHeadView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ldh on 2017/5/11.
 */

public class HomeView extends LinearLayout  {
    Context mContext;
    @Bind(R.id.searchView)
    SearchHeadView searchHeadView;
    @Bind(R.id.goodsGridview)
    GoodsView goodsView;

    public HomeView(Context context) {
        super(context);
        initUI(context);
    }

    public HomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context) {
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.view_home, this, true);
        ButterKnife.bind(this);
//        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    public void initData(final AddressItemBean map) {
//        searchHeadView.setOnTextChangerListener(new SearchHeadView.onTextChangerListener() {
//            @Override
//            public void onTextChanger(String text) {
//                if (map.getOnItemListener()!=null){
//
//                    map.setTitle(text);
//                    map.getOnItemListener().onItemClick(ClickTypeEnum.ITEM,map);
//                }
//            }
//        });

    }
}

