package com.ui.message.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.item.IItemView;
import com.easy.recycleview.recycleview.sectionview.MutiTypeSelectUtils;
import com.easysoft.costumes.R;
import com.easysoft.widget.imgeview.MultiShapeView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ldh on 2017/5/11.
 */

public class HomeCirclerView extends LinearLayout implements IItemView {
    Context mContext;
    @Bind(R.id.img)
    MultiShapeView mImg;
    @Bind(R.id.name)
    TextView mNameTv;
//    @Bind(R.id.price)
//    TextView mPriceTv;
//    @Bind(R.id.content)
//    TextView mContentTv;
    public HomeCirclerView(Context context) {
        super(context);
        initUI(context);
    }

    public HomeCirclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context) {
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.index_recycler_circler_show, this, true);
        ButterKnife.bind(this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    @Override
    public void initSelectUtils(MutiTypeSelectUtils selectUtils) {

    }

    @Override
    public void initData(final AddressItemBean map) {

        mImg.setImageResource(map.getHeadImgeSettings().getHeadImgDrawableId());
        mNameTv.setText(map.getTitle());
//        final GoodsInfoBean infoBean=(GoodsInfoBean)map;
//        mNameTv.setText(map.getTitle());
//        mPriceTv.setText(infoBean.getPrice());
//        mContentTv.setText(infoBean.getContent());
//        String url=map.getHeadImgeSettings().getHeadImgUrl();
//        ImageUtils.getInStance().load(url,mImg);
//
//        LayoutParams mHeadParams = (LayoutParams) mImg.getLayoutParams();
////        if (cardBean.getHeadImgeSettings().getHeadImgRadius() != 0) {
////            mHeadParams.width = cardBean.getHeadImgeSettings().getHeadImgRadius();
////            mHeadParams.height = cardBean.getHeadImgeSettings().getHeadImgRadius();
////            mImg.setLayoutParams(mHeadParams);
////        }
////         Random random = new Random();
////            mHeadParams.width = DensityUtil.dip2px(getContext(),230);
////            mHeadParams.height = DensityUtil.dip2px(getContext(),230);
////            mImg.setLayoutParams(mHeadParams);
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

