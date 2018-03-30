package com.ui;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import com.business.ServiceCallBack;
import com.business.bean.ResponseMsg;
import com.core.http.OkHttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ui.car.MyCallback;
public class HttpService {
	ServiceCallBack  serviceCallBack=new ServiceCallBack();

	
//	public ServiceCallBack request(final String url ,final String json){
//
////		ThreadFactory.io().getExecutor().execute(new  Runnable() {
////
////			@Override
////			public void run() {
//				OkHttpUtils.getInStance().post(url, json, new Callback() {
//
//					@Override
//					public void onResponse(Call call, Response response)
//							throws IOException {
//						if (response.isSuccessful()) {
//							serviceCallBack.setSucess(true);
//							ResponseBody body = response.body();
//							String msg = body.string();
//
//
//							ObjectMapper mapper = new ObjectMapper();
//
//							ResponseMsg responseMsg = mapper.readValue(msg,
//									ResponseMsg.class);
//							serviceCallBack.setResponseMsg(responseMsg);
//					    }
//						serviceCallBack.setCall(call);
//						serviceCallBack.setResponse(response);
//					}
//
//					@Override
//					public void onFailure(final Call call, IOException e) {
//						serviceCallBack.setCall(call);
//						serviceCallBack.setExcept(e);
//
//					}
//				});
//
////			}
////		});
//
//
//		return serviceCallBack;
//	}
	public void request(final String url ,MyCallback  callback){
		

		OkHttpUtils.getInStance().post(url,  callback);
		
		
	}

	public ServiceCallBack getServiceCallBack() {
		return serviceCallBack;
	}
}
