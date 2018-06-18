package com.ui.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.core.utils.ImageLoadUtils;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.item.IItemView;
import com.easy.recycleview.recycleview.sectionview.MutiTypeSelectUtils;
import com.easysoft.costumes.R;
import com.ui.app.AppListPresenter;
import com.ui.setting.InfoCardBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ldh on 2017/5/11.
 */

public class AppItemView extends LinearLayout implements IItemView {
    Context mContext;


    /** 用户头像 */
    @Bind(R.id.img)
    ImageView mUserImg;
    /** 用户名*/
    @Bind(R.id.nameTv)
    TextView mNameTv;

    public AppItemView(Context context) {
        super(context);
        initUI(context);
    }

    public AppItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context) {
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.view_app_item, this, true);
        ButterKnife.bind(this);

    }

    @Override
    public void initSelectUtils(MutiTypeSelectUtils selectUtils) {

    }

    @Override
    public void initData(final AddressItemBean itemBean) {

        mNameTv.setText(itemBean.getTitle());
        mUserImg.setImageResource(itemBean.getHeadImgeSettings().getHeadImgDrawableId());
//        mUserImg.setImageResource(R.drawable.app_icon);
//        if (!itemBean.getId().equals(AppListPresenter.ID_EMPTY)){
//              ImageLoadUtils.getInStance().loadResourceId(itemBean.getHeadImgeSettings().getHeadImgDrawableId(),mUserImg);
//
//        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemBean.getOnItemListener()!=null){
                    itemBean.getOnItemListener().onItemClick(ClickTypeEnum.ITEM,itemBean);
                }
            }
        });
    }
}

