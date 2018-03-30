package com.ui.common.register.check;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.BusinessBroadcastUtils;
import com.business.ServiceCallBack;
import com.business.bean.ResponseMsg;
import com.business.bean.ResponseMsgData;
import com.business.login.User;
import com.core.ServerUrl;
import com.core.utils.SpUtils;
import com.core.utils.ToastUtils;
import com.ui.HttpService;
import com.ui.car.MyCallback;

/**
 * Created by lindanghong on 2018/2/23.
 */
public class CheckUserExitPersenter {
    IcheckPhoneView icheckPhoneView;
    HttpService service;
    public CheckUserExitPersenter(IcheckPhoneView icheckPhoneView) {
        this.icheckPhoneView =icheckPhoneView;
        service=new HttpService();
    }
    public void checkPhoneExit(String logInId){
         String url = ServerUrl.baseUrl+ ServerUrl.checkUserExitUrl;
        User loginUser=new User();
        loginUser.setLoginId(logInId);
        final String json= JSON.toJSONString(loginUser);
        url=ServerUrl.getFinalUrl(url,json);

        service.request( url ,new MyCallback(new MyCallback.IResponse() {
            @Override
            public void onFailure(ServiceCallBack serviceCallBack) {
                icheckPhoneView.checkPhoneUserExit(false);
            }

            @Override
            public void onResponse(ServiceCallBack serviceCallBack) {
                if (serviceCallBack.isSucess()){
                    ResponseMsg msg=   serviceCallBack.getResponseMsg();
                    ResponseMsgData  serverUserResponseMsgData=JSONObject.parseObject(msg.getMsg(), ResponseMsgData.class);
//                    User  serverUser=JSONObject.parseObject(msg.getMsg().toString(), User.class);
                    icheckPhoneView.checkPhoneUserExit(msg.isSuccess());
                }else{

                    icheckPhoneView.showToast("连接服务器失败");
//                    icheckPhoneView.checkPhoneUserExit(false);
                }

//                ilogInView.showToast("登录成功");
            }
        }));

    }
}
