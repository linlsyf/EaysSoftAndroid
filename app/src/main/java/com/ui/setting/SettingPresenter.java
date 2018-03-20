package com.ui.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.BusinessBroadcastUtils;
import com.business.ServiceCallBack;
import com.business.bean.ResponseMsg;
import com.business.bean.ResponseMsgData;
import com.business.login.User;
import com.core.CoreApplication;
import com.core.ServerUrl;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.item.IItemView;
import com.core.recycleview.sectionview.Section;
import com.core.threadpool.ThreadFactory;
import com.core.update.UpdateAPK;
import com.core.utils.SpUtils;
import com.core.utils.StringUtils;
import com.core.utils.ToastUtils;
import com.ui.HttpService;
import com.ui.car.MyCallback;

import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.widget.Toast;

/**
 * Created by vincent_tung on 2017/3/2.
 */
public class SettingPresenter   {
	HttpService service;

	ISafeSettingView iSafeSettingView;
	private String KEY_SETTING="setting";
	private String KEY_INFO="info";
	private String KEY_LOGOUT="logout";
	private String KEY_UPDATE="update";
	private String KEY_USER_INFO="userInfo";
	private InfoCardBean infoCardBean;

	public SettingPresenter(ISafeSettingView iSafeSettingView) {
    	this.iSafeSettingView=iSafeSettingView;
		service=new HttpService();

	}

      public void init(){
    	  
		  List<AddressItemBean> dataMaps=new ArrayList<>();
		  List<AddressItemBean> settingMaps=new ArrayList<>();
		    infoCardBean=new InfoCardBean();
		    infoCardBean.setId(KEY_USER_INFO);
		    infoCardBean.setUserName("用户");
		    infoCardBean.setViewType(IItemView.ViewTypeEnum.INFO_CARD_VIEW.value());
		    dataMaps.add(infoCardBean);
		    
		    AddressItemBean updateBean=new AddressItemBean();
		    updateBean.setId(KEY_UPDATE);
		    updateBean.setTitle("检查更新");
		  updateBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, AddressItemBean bean) {
				iSafeSettingView.showUpdate();
//				  sendCode("86","15217636960");
			  }
		  });
		  settingMaps.add(updateBean);
		    
			AddressItemBean itembeanSpace = new AddressItemBean();
			itembeanSpace.setViewType(IItemView.ViewTypeEnum.SPLITE
							.value());
			settingMaps.add(itembeanSpace);
			
		    AddressItemBean exitBean=new AddressItemBean();
		    exitBean.setId(KEY_LOGOUT);
		    exitBean.setTitle("退出登录");
		  exitBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, AddressItemBean bean) {
				  Logout();
			  }
		  });
		    settingMaps.add(exitBean);
		    
		  Section nextSection=new Section(KEY_INFO);
		
    	  nextSection.setDataMaps(dataMaps);
    	  Section settingSection=new Section(KEY_SETTING);
    	  settingSection.setDataMaps(settingMaps);

    	  iSafeSettingView.initUI(nextSection);
    	  iSafeSettingView.initUI(settingSection);
    	  initJpush();
		  getLoginUserMsg();
      }
	  public void getLoginUserMsg(){
		  if(StringUtils.isNotEmpty(BusinessBroadcastUtils.USER_VALUE_USER_ID)){{

			  final String url = ServerUrl.baseUrl+ServerUrl.Get_UserUrl;
			  User loginUser=new User();
			  loginUser.setId(BusinessBroadcastUtils.USER_VALUE_USER_ID);
			  final String json= JSON.toJSONString(loginUser);
			  service.request( url , json,new MyCallback(new MyCallback.IResponse() {
				  @Override
				  public void onFailure(ServiceCallBack serviceCallBack) {
				  }

				  @Override
				  public void onResponse(ServiceCallBack serviceCallBack) {
					  if (serviceCallBack.isSucess()){
						  ResponseMsg msg=   serviceCallBack.getResponseMsg();
						  ResponseMsgData serverUserResponseMsgData= JSONObject.parseObject(msg.getMsg(), ResponseMsgData.class);
						  User  serverUser=JSONObject.parseObject(serverUserResponseMsgData.getData().toString(), User.class);

						   infoCardBean.setId(serverUser.getId());
						   infoCardBean.setUserName(serverUser.getName());
						  iSafeSettingView.updateItem(infoCardBean);

					  }

//                ilogInView.showToast("登录成功");
				  }
			  }));
		  }}
	  }
	// 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
	public void sendCode(String country, String phone) {
		// 注册一个事件回调，用于处理发送验证码操作的结果
		SMSSDK.registerEventHandler(new EventHandler() {
			public void afterEvent(int event, int result, Object data) {
				if (result == SMSSDK.RESULT_COMPLETE) {
					// TODO 处理成功得到验证码的结果
					// 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
					iSafeSettingView.showToast("已发送");
				} else{
					iSafeSettingView.showToast("发送失败");
				}

			}
		});
		// 触发操作
		SMSSDK.getVerificationCode(country, phone);
	}

    public void initJpush(){
//    	JPushInterface.setAlias(CoreApplication.getAppContext(), 0, "ldh");
//    	  JPushInterface.setAlias(CoreApplication.getAppContext(),"ldh", new TagAliasCallback() {
//              @Override
//              public void gotResult(int i, String s, Set<String> set) {
//            	  ToastUtils.show(CoreApplication.getAppContext(), "设置成功");
////                  tvAlias.setText("当前alias："+alias);
//              }
//
//		
//          });
    }
    public void Logout() {
		BusinessBroadcastUtils.USER_VALUE_LOGIN_ID = "";
		BusinessBroadcastUtils.USER_VALUE_PWD 	   = "";
		BusinessBroadcastUtils.USER_VALUE_USER_ID  ="";
		SpUtils.clear(iSafeSettingView.getContext(), BusinessBroadcastUtils.STRING_LOGIN_USER_ID);
		SpUtils.clear(iSafeSettingView.getContext(), BusinessBroadcastUtils.STRING_LOGIN_USER_PWD);
		SpUtils.clear(iSafeSettingView.getContext(), BusinessBroadcastUtils.STRING_LOGIN_ID);
		iSafeSettingView.logOut();
    }
}
