package com.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.business.BusinessBroadcastUtils;
import com.core.CoreApplication;
import com.core.base.BasicActivity;
import com.core.utils.StringUtils;
import com.core.utils.ToastUtils;
import com.easysoft.costumes.R;
import com.tencent.bugly.crashreport.CrashReport;
import com.ui.car.ShopOrderListFragment;
import com.ui.login.IlogInView;
import com.ui.login.LoginActivity;
import com.ui.login.LoginPresenter;
import com.ui.message.GoodsFragment;
import com.ui.message.add.AddFragment;
import com.ui.other.TabOtherFragment;
import com.ui.setting.SettingFragment;
import com.view.tabview.adapter.MainViewAdapter;
import com.view.tabview.listener.OnTabSelectedListener;
import com.view.tabview.widget.Tab;
import com.view.tabview.widget.TabContainerView;


public class HomeActivity extends BasicActivity implements IlogInView,IHomeView {
	TabContainerView tabContainerView;
	LoginPresenter loginPresenter;
	HomePresenter  homePresenter;
	@Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);


         tabContainerView = (TabContainerView) findViewById(R.id.tab_container);

        MainViewAdapter mainViewAdapter=new MainViewAdapter(getSupportFragmentManager(),
                new Fragment[] {new GoodsFragment(),  new ShopOrderListFragment(),new TabOtherFragment(),new SettingFragment()});
//        MainViewAdapter mainViewAdapter=new MainViewAdapter(getSupportFragmentManager(),
//                new Fragment[] {new AddFragment(),  new ShopOrderListFragment(),new TabOtherFragment(),new SettingFragment()});
        mainViewAdapter.setHasMsgIndex(1);
        tabContainerView.setAdapter(mainViewAdapter);
        tabContainerView.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {

            }
        });

//		FragmentHelper.showFrag(this,R.id.container_framelayout,new RegisterSuccessFragment(),null);
//		Intent dbIntent=new Intent(this, LoginActivity.class);
//		 startActivity(dbIntent);
//		sendCode(this);
//		Intent intent = new Intent(this, TulingFragemnt.class);
//		startActivity(intent);
//		CrashReport.testJavaCrash();
		homePresenter=new HomePresenter(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	protected void onResume() {
		super.onResume();

//		 if (CoreApplication.getInstance().isDubug){
////		      gotoMainOrloginUI(wellComeActivity);
//		 }else
		 if(StringUtils.isEmpty(BusinessBroadcastUtils.USER_VALUE_LOGIN_ID)){
			 Intent  homeIntent=new Intent(this,LoginActivity.class);
			 startActivity(homeIntent);
		 }else if (BusinessBroadcastUtils.loginUser==null){
           login();
		 }
	}
   private void login(){
	   loginPresenter=new LoginPresenter(this);
	   loginPresenter.login(BusinessBroadcastUtils.USER_VALUE_LOGIN_ID,BusinessBroadcastUtils.USER_VALUE_PWD);

   }
	@Override
	public void initUIView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loginSucess() {
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				BusinessBroadcastUtils.sendBroadcast(getContext(), BusinessBroadcastUtils.TYPE_RELOGIN_SUCESS, null);
//
//			}
//		});
	}

	@Override
	public void loginFails() {
//		BusinessBroadcastUtils.sendBroadcast(getContext(), BusinessBroadcastUtils.TYPE_RELOGIN_FAILS, null);

	}

	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		if(type.equals(BusinessBroadcastUtils.Type_Local_HOME_PAGE_CHANGE)){
			tabContainerView.getViewPager().setCurrentItem((int)mode);
		}else if(type.equals(BusinessBroadcastUtils.TYPE_RELOGIN)){

			login();
		}
		
	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void showToast(final String text) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ToastUtils.show(HomeActivity.this,text);

			}
		});
	}
}
