package com.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.business.BusinessBroadcastUtils;
import com.core.base.BaseFragment;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.sectionview.Section;
import com.core.update.UpdateAPK;
import com.core.utils.FragmentHelper;
import com.core.utils.ToastUtils;
import com.easysoft.costumes.R;
import com.ui.login.LoginActivity;
import com.ui.message.add.OrderDetailPersenter;
import com.ui.other.tuling.TulingFragemnt;
import com.ui.setting.view.MySettingContentView;
import com.view.toolbar.NavigationBar;
import com.view.toolbar.TopBarBuilder;

import cn.smssdk.SMSSDK;



public class SettingFragment extends BaseFragment implements ISafeSettingView{
	MySettingContentView recycleView;
	  SettingPresenter presenter;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

    	View rootView=inflater.inflate(R.layout.view_my_setting, null);
//    	if(recycleView==null){
//    		recycleView=new MySettingContentView(getActivity());
//    	}
    	
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
    	  NavigationBar  toolbar=getViewById(R.id.toolbar);
		  TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "设置", 0);
     
      }
      @Override
    public void initData() {
    presenter=new SettingPresenter(this);
    presenter.init();
    		
    }
	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
        if(type.equals(BusinessBroadcastUtils.TYPE_LOGIN_SUCESS)){
            presenter.updateUserInfo();
        }
		
	}
	@Override
	public void initUI(Section section) {
		recycleView.updateSection(section);
		
	}

    @Override
    public void showUpdate() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UpdateAPK  apk=new UpdateAPK( getActivity());
                apk.Beginning();
            }
        });

    }

    @Override
    public void logOut() {
        Intent homeIntent=new Intent(getActivity(),LoginActivity.class);
        startActivity(homeIntent);
    }



    @Override
    public void onDetach() {
        super.onDetach();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showToast(String text) {
    ToastUtils.show(getActivity(),text);
    }

    @Override
    public void updateItem(final AddressItemBean imgBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recycleView.getSectionAdapterHelper().updateItem(OrderDetailPersenter.KEY_ShopOrderInfo, imgBean);
            }
        });
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
}

