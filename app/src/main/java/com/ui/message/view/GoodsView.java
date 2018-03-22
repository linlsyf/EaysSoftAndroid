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
import com.core.recycleview.AddressRecycleView;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.item.IItemView;
import com.core.recycleview.sectionview.MutiTypeSelectUtils;
import com.easysoft.costumes.R;
import com.ui.message.GoodsInfoBean;
import com.ui.setting.InfoCardBean;
import com.ui.setting.view.InfoCardView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ldh on 2017/5/11.
 */

public class GoodsView extends AddressRecycleView {

    public GoodsView(Context context) {
        super(context);
    }

    public GoodsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View addItemView(int viewType) {
        View  itemView;
        if (viewType== IItemView.ViewTypeEnum.INFO_CARD_VIEW.value()){
            itemView=new GoodInfoView(getContext());
            return itemView;
        }

        return super.addItemView(viewType);
    }
}

