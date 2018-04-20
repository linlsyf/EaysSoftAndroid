package com.ui.other.view;

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

import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.item.IItemView;
import com.easy.recycleview.recycleview.sectionview.MutiTypeSelectUtils;
import com.easysoft.costumes.R;
import com.ui.setting.InfoCardBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ldh on 2017/5/11.
 */

public class InfoCardView extends LinearLayout implements IItemView {
    Context mContext;
   
    /** 企业名 */
    @Bind(R.id.companyNameTv)
    TextView mCompanyNameTv;
    /** 用户头像 */
    @Bind(R.id.userImg)
    ImageView mUserImg;
    /** 用户名*/
    @Bind(R.id.nameTv)
    TextView mNameTv;
    /** 职位 */
    @Bind(R.id.postionTv)
    TextView mPostionTv;
    /** 邮件 */
    @Bind(R.id.emailTv)
    TextView mEmailTv;
    public InfoCardView(Context context) {
        super(context);
        initUI(context);
    }

    public InfoCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    private void initUI(Context context) {
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.view_infocard, this, true);
        ButterKnife.bind(this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    @Override
    public void initSelectUtils(MutiTypeSelectUtils selectUtils) {

    }

    @Override
    public void initData(final AddressItemBean map) {

        final InfoCardBean cardBean=(InfoCardBean)map;
        mCompanyNameTv.setText(cardBean.getCompanyName());
        mNameTv.setText(cardBean.getUserName());
        mPostionTv.setText(cardBean.getPostionName());
        mEmailTv.setText(cardBean.getEmilName());


        String name = cardBean.getHeadImgeSettings().getHeadImgUserName();
//        String headLoadType = ChatType.USER.getCode();

        RelativeLayout.LayoutParams mHeadParams = (RelativeLayout.LayoutParams) mUserImg.getLayoutParams();
        if (cardBean.getHeadImgeSettings().getHeadImgRadius() != 0) {
            mHeadParams.width = cardBean.getHeadImgeSettings().getHeadImgRadius();
            mHeadParams.height = cardBean.getHeadImgeSettings().getHeadImgRadius();
            mUserImg.setLayoutParams(mHeadParams);
        }

//        ImageUtils.getInStance().load("http://192.168.155.1:8090/api/v1/file/down?type=2&name=48cead4a-7ba1-41bb-8cb1-5da76d144dd9",mUserImg);
//        if(StringUtils.isNotEmpty(BusinessBroadcastUtils.USER_VALUE_USER_ID)){
//
//        }else{
            ImageUtils.getInStance().loadResourceId(R.drawable.ic_login_default_user, mUserImg);

//        }


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

