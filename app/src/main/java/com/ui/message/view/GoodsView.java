package com.ui.message.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.easy.recycleview.recycleview.AddressRecycleView;
import com.easy.recycleview.recycleview.item.IItemView;

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
        if (viewType==3){
            itemView=new GoodInfoView(getContext());
            return itemView;
        }
       else if (viewType== 4){
            itemView=new HomeCirclerView(getContext());
            return itemView;
        }
//       else if (viewType== IItemView.ViewTypeEnum.TOP_VIEW.value()){
//            itemView=new HomeView(getContext());
//            return itemView;
//        }
        return super.addItemView(viewType);
    }


}

