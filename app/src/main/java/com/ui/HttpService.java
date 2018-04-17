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

	public void request(final String url ,MyCallback  callback){

		OkHttpUtils.getInStance().post(url,  callback);
		
	}

	public ServiceCallBack getServiceCallBack() {
		return serviceCallBack;
	}
}
