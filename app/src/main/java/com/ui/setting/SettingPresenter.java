package com.ui.setting;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.BusinessBroadcastUtils;
import com.business.ServiceCallBack;
import com.business.bean.ResponseMsg;
import com.business.bean.ResponseMsgData;
import com.business.login.User;
import com.core.CoreApplication;
import com.core.ServerUrl;
import com.core.utils.SpUtils;
import com.core.utils.StringUtils;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.item.IItemView;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.ui.HttpService;
import com.ui.car.MyCallback;
import com.utils.AppInfo;

import java.util.ArrayList;
import java.util.List;


public class SettingPresenter   {
	HttpService service;

	ISafeSettingView iSafeSettingView;
	private String KEY_SETTING="setting";
	private String KEY_INFO="info";
	private String KEY_LOGOUT="logout";
	private String KEY_UPDATE="update";
	private String KEY_USER_INFO="userInfo";
	private InfoCardBean infoCardBean;
	private String SECTION_NEW="new";
	private String KEY_ABOUT="about";

	public SettingPresenter(ISafeSettingView iSafeSettingView) {
    	this.iSafeSettingView=iSafeSettingView;
		service=new HttpService();
	}

      public void init(){
		  List<AddressItemBean> dataMaps=new ArrayList<>();
		  List<AddressItemBean> settingMaps=new ArrayList<>();
		    infoCardBean=new InfoCardBean();
		    infoCardBean.setId(KEY_USER_INFO);
		  String loginName="用户";
		   if (BusinessBroadcastUtils.loginUser!=null){
			   loginName=BusinessBroadcastUtils.loginUser.getName();
			   if (BusinessBroadcastUtils.loginUser.getIsAdmin().equals("1")){
				   loginName=loginName+" (管理)";
			   }
		   }
		    infoCardBean.setUserName(loginName);
		    infoCardBean.setViewType(3);
		    dataMaps.add(infoCardBean);
		    
		    AddressItemBean updateBean=new AddressItemBean();
		    updateBean.setId(KEY_UPDATE);
		    String  verson="检查更新";
		     verson=verson+"("+ AppInfo.getAppVersion(CoreApplication.getAppContext())+")";
		    updateBean.setTitle(verson);
		  updateBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, AddressItemBean bean) {
				iSafeSettingView.showUpdate();
			  }
		  });
		  settingMaps.add(updateBean);
		    
			AddressItemBean itembeanSpace = new AddressItemBean();
			itembeanSpace.setViewType(IItemView.ViewTypeEnum.SPLITE
							.value());
			settingMaps.add(itembeanSpace);
			
		    AddressItemBean aboutBean=new AddressItemBean();
		  aboutBean.setId(KEY_ABOUT);
		  aboutBean.setTitle("关于");
		  aboutBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, AddressItemBean bean) {
//				  Logout();
			  }
		  });
		    settingMaps.add(aboutBean);
		    
		  Section nextSection=new Section(KEY_INFO);
		
    	  nextSection.setDataMaps(dataMaps);
    	  Section settingSection=new Section(KEY_SETTING);
    	  settingSection.setDataMaps(settingMaps);

    	  iSafeSettingView.initUI(nextSection);
    	  iSafeSettingView.initUI(settingSection);
    	  initJpush();

		  Section  newSection=new Section(SECTION_NEW);
		  newSection.setPosition(0);

		  newSection.setName("其他");
		  List<AddressItemBean>  newSectionList=new ArrayList<AddressItemBean>();

		  AddressItemBean newItemBean=new AddressItemBean();
		  newItemBean.setTitle("助手小Q");
		  newItemBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, AddressItemBean bean) {
				  iSafeSettingView.showNews();
			  }
		  });
		  newSectionList.add(newItemBean);

		  AddressItemBean itembeanSpaceLogOut= new AddressItemBean();
		  itembeanSpaceLogOut.setViewType(IItemView.ViewTypeEnum.SPLITE
				  .value());
		  newSectionList.add(itembeanSpaceLogOut);

		  AddressItemBean exitBean=new AddressItemBean();
		  exitBean.setId(KEY_LOGOUT);
		  exitBean.setTitle("退出登录");
		  exitBean.setOnItemListener(new IItemView.onItemClick() {
			  @Override
			  public void onItemClick(IItemView.ClickTypeEnum typeEnum, AddressItemBean bean) {
				  Logout();
			  }
		  });
		  newSectionList.add(exitBean);

		  newSection.setDataMaps(newSectionList);
		  iSafeSettingView.initUI(newSection);
//		  getLoginUserMsg();
      }
	  public void getLoginUserMsg(){
		  if(StringUtils.isNotEmpty(BusinessBroadcastUtils.USER_VALUE_USER_ID)){{

			   String url = ServerUrl.baseUrl+ServerUrl.Get_UserUrl;
			  User loginUser=new User();
			  loginUser.setId(BusinessBroadcastUtils.USER_VALUE_USER_ID);
			  final String json= JSON.toJSONString(loginUser);
			  url=ServerUrl.getFinalUrl(url,json);

			  service.request( url , new MyCallback(new MyCallback.IResponse() {
				  @Override
				  public void onFailure(ServiceCallBack serviceCallBack) {
				  }

				  @Override
				  public void onResponse(ServiceCallBack serviceCallBack) {
					  if (serviceCallBack.isSucess()){
						  ResponseMsg msg=   serviceCallBack.getResponseMsg();
						  ResponseMsgData serverUserResponseMsgData= JSONObject.parseObject(msg.getMsg(), ResponseMsgData.class);

						  if (StringUtils.isNotEmpty(serverUserResponseMsgData.getData().toString())){
							  User  serverUser=JSONObject.parseObject(serverUserResponseMsgData.getData().toString(), User.class);

							  infoCardBean.setId(serverUser.getId());
							  infoCardBean.setUserName(serverUser.getName());
							  iSafeSettingView.updateItem(infoCardBean);
						  }


					  }

//                ilogInView.showToast("登录成功");
				  }
			  }));
		  }}
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

	public void updateUserInfo() {
		infoCardBean.setId(BusinessBroadcastUtils.loginUser.getId());
		String 	name=BusinessBroadcastUtils.loginUser.getName();
		if (BusinessBroadcastUtils.loginUser.getIsAdmin().equals("1")){
			name=name+" (管理)";
		}
		infoCardBean.setUserName(name);
		iSafeSettingView.updateItem(infoCardBean);
	}
}
