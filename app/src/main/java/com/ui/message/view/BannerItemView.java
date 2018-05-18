package com.ui.message.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.item.IItemView;
import com.easy.recycleview.recycleview.sectionview.MutiTypeSelectUtils;
import com.easysoft.costumes.R;
import com.easysoft.widget.banner.BannerView;
import com.easysoft.widget.imgeview.MultiShapeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lindanghong on 2018/5/18.
 */

public class BannerItemView extends LinearLayout implements IItemView {
    Context mContext;
    private List<View> viewList;

    @Bind(R.id.banner)
    BannerView bannerView;

    private int[] imgs = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d};

    public BannerItemView(Context context) {
        super(context);
        initUI(context);
    }

    public BannerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context) {
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.view_banner, this, true);
        ButterKnife.bind(this);

        viewList = new ArrayList<View>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView image = new ImageView(getContext());
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //璁剧疆鏄剧ず鏍煎紡
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setImageResource(imgs[i]);
            viewList.add(image);
        }
        bannerView.startLoop(true);

        bannerView.setViewList(viewList);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    @Override
    public void initSelectUtils(MutiTypeSelectUtils selectUtils) {

    }

    @Override
    public void initData(final AddressItemBean map) {


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map.getOnItemListener()!=null){
                    map.getOnItemListener().onItemClick(ClickTypeEnum.ITEM,map);
                }
            }
        });
    }
}
