package com.ui.message.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.core.imge.ImageUtils;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.item.IItemView;
import com.core.recycleview.sectionview.MutiTypeSelectUtils;
import com.core.utils.DensityUtil;
import com.easysoft.costumes.R;
import com.ui.message.GoodsInfoBean;
import com.ui.setting.InfoCardBean;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ldh on 2017/5/11.
 */

public class GoodInfoView extends LinearLayout implements IItemView {
    Context mContext;
    @Bind(R.id.img)
    ImageView mImg;
    @Bind(R.id.name)
    TextView mNameTv;
    @Bind(R.id.price)
    TextView mPriceTv;
    @Bind(R.id.content)
    TextView mContentTv;
    public GoodInfoView(Context context) {
        super(context);
        initUI(context);
    }

    public GoodInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context) {
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.view_goods_item, this, true);
        ButterKnife.bind(this);
//        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    @Override
    public void initSelectUtils(MutiTypeSelectUtils selectUtils) {

    }

    @Override
    public void initData(final AddressItemBean map) {
        final GoodsInfoBean infoBean=(GoodsInfoBean)map;
        mNameTv.setText(map.getTitle());
        mPriceTv.setText(infoBean.getPrice());
        mContentTv.setText(infoBean.getContent());
        String url=map.getHeadImgeSettings().getHeadImgUrl();
        ImageUtils.getInStance().load(url,mImg);

        LinearLayout.LayoutParams mHeadParams = (LinearLayout.LayoutParams) mImg.getLayoutParams();
//        if (cardBean.getHeadImgeSettings().getHeadImgRadius() != 0) {
//            mHeadParams.width = cardBean.getHeadImgeSettings().getHeadImgRadius();
//            mHeadParams.height = cardBean.getHeadImgeSettings().getHeadImgRadius();
//            mImg.setLayoutParams(mHeadParams);
//        }
//         Random random = new Random();
//            mHeadParams.width = DensityUtil.dip2px(getContext(),230);
//            mHeadParams.height = DensityUtil.dip2px(getContext(),230);
//            mImg.setLayoutParams(mHeadParams);
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

