package com.ui.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alipay.sdk.pay.demo.PayDemoActivity;
import com.core.base.BaseFragment;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.sectionview.Section;
import com.core.utils.FragmentHelper;
import com.easysoft.costumes.R;
import com.ui.other.tuling.TulingFragemnt;
import com.ui.other.view.OtherContentView;
import com.view.toolbar.NavigationBar;
import com.view.toolbar.TopBarBuilder;

import butterknife.ButterKnife;

/**
 * Created by chengxi on 17/4/26.
 */
public class TabOtherFragment extends BaseFragment implements IOtherView{
    OtherPresenter presenter;
    OtherContentView recycleView;
    protected Context mContext;

    /**
     * 原生插屏广告控件容器
     */
    private RelativeLayout mNativeSpotAdLayout;
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

    	View rootView=inflater.inflate(R.layout.fragment_tab_other, null);
    	setRootView(rootView);
       return rootView;

    }

    @Override
    public void initFragment() {
      initUIView();
       initData();
    }

    @Override
    public void initUIView() {
        recycleView= getViewById(R.id.recycleView);

        ButterKnife.bind(getActivity());
        NavigationBar toolbar=getViewById(R.id.toolbar);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "资讯", 0);
    }

    @Override
    public void initData() {
        presenter=new OtherPresenter(this);
        presenter.init();
        mContext=getActivity();
        // 设置原生插屏广告
//        setupNativeSpotAd();


    }

//    @Override
//    public void onBackPressed() {
//        //原生控件点击后退关闭
//        if (mNativeSpotAdLayout != null && mNativeSpotAdLayout.getVisibility() != View.GONE) {
//            mNativeSpotAdLayout.removeAllViews();
//            mNativeSpotAdLayout.setVisibility(View.GONE);
//            return;
//        }
//        super.onBackPressed();
//    }
    @Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		// TODO Auto-generated method stub
		
	}


    @Override
    public void updateSection(Section section) {
        recycleView.updateSection(section);

    }



    @Override
    public void updateItem(AddressItemBean imgBean) {

    }

    @Override
    public void showNews() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Bundle bundle=new Bundle();

                FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new TulingFragemnt(), bundle);

            }
        });
    }

    @Override
    public void showApay() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getActivity(),PayDemoActivity.class));

            }
        });
    }

}
