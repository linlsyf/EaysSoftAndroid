package com.ui.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.BusinessBroadcastUtils;
import com.business.ServiceCallBack;
import com.business.bean.ResponseMsg;
import com.business.bean.ResponseMsgData;
import com.business.login.User;
import com.core.ServerUrl;
import com.core.utils.SpUtils;
import com.ui.HttpService;
import com.ui.car.MyCallback;

/**
 * Created by lindanghong on 2018/2/10.
 */
public class LoginPresenter {
    IlogInView ilogInView;
    HttpService service;
    public LoginPresenter(IlogInView ilogInView) {
        this.ilogInView=ilogInView;
        service=new HttpService();
    }
    public void login(final String logInId, final String pwd){

         String url =ServerUrl.baseUrl+ ServerUrl.loginUrl;
        User loginUser=new User();
        loginUser.setLoginId(logInId);
        loginUser.setPwd(pwd);
        final String json= JSON.toJSONString(loginUser);
        url=ServerUrl.getFinalUrl(url,json);

        service.request( url ,new MyCallback(new MyCallback.IResponse() {
            @Override
            public void onFailure(ServiceCallBack serviceCallBack) {
                ilogInView.showToast("登录失败");
            }

            @Override
            public void onResponse(ServiceCallBack serviceCallBack) {
                 if (serviceCallBack.isSucess()){
                     ResponseMsg msg=   serviceCallBack.getResponseMsg();
                     ResponseMsgData  serverUserResponseMsgData=JSONObject.parseObject(msg.getMsg(), ResponseMsgData.class);
                     User  serverUser=JSONObject.parseObject(serverUserResponseMsgData.getData().toString(), User.class);
                     BusinessBroadcastUtils.USER_VALUE_LOGIN_ID =logInId ;
                     BusinessBroadcastUtils.USER_VALUE_PWD 	   =pwd;
                     BusinessBroadcastUtils.USER_VALUE_USER_ID  =serverUser.getId() ;
                     BusinessBroadcastUtils.loginUser=serverUser;

                     SpUtils.putString(ilogInView.getContext(),BusinessBroadcastUtils.STRING_LOGIN_USER_ID,serverUser.getId());
                     SpUtils.putString(ilogInView.getContext(),BusinessBroadcastUtils.STRING_LOGIN_USER_PWD,serverUser.getPwd());
                     SpUtils.putString(ilogInView.getContext(),BusinessBroadcastUtils.STRING_LOGIN_ID,serverUser.getLoginId());
                     BusinessBroadcastUtils.sendBroadcast(ilogInView.getContext(),BusinessBroadcastUtils.TYPE_LOGIN_SUCESS,null);
                     ilogInView.loginSucess();
                 }else{
                     ilogInView.loginFails();
                     BusinessBroadcastUtils.sendBroadcast(ilogInView.getContext(),BusinessBroadcastUtils.TYPE_LOGIN_FAILS,null);

                 }

//                ilogInView.showToast("登录成功");
            }
        }));

    }
}
