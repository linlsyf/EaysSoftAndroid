package com.ui.message.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.core.imge.ImageUtils;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.item.IItemView;
import com.core.recycleview.sectionview.MutiTypeSelectUtils;
import com.easysoft.costumes.R;
import com.ui.message.GoodsInfoBean;
import com.ui.setting.InfoCardBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ldh on 2017/5/11.
 */

public class GoodsView extends LinearLayout implements IItemView {
    Context mContext;

    /** 企业名 */
    @Bind(R.id.goodsGridview)
    GridView gridView;

    public GoodsView(Context context) {
        super(context);
        initUI(context);
    }

    public GoodsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context) {
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.view_goods, this, true);
        ButterKnife.bind(this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    @Override
    public void initSelectUtils(MutiTypeSelectUtils selectUtils) {

    }

    @Override
    public void initData(final AddressItemBean map) {

        final GoodsInfoBean cardBean=(GoodsInfoBean)map;


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardBean.getOnItemListener()!=null){
                    cardBean.getOnItemListener().onItemClick(ClickTypeEnum.ITEM,cardBean);
                }
            }
        });
    }
}

