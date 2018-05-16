package com.ui;

import com.business.ServiceCallBack;
import com.core.http.HttpUtils;
import com.ui.car.MyCallback;
public class HttpService {
	ServiceCallBack  serviceCallBack=new ServiceCallBack();

	public void request(final String url ,MyCallback  callback){

		HttpUtils.getInStance().post(url,  callback);
		
	}

	public ServiceCallBack getServiceCallBack() {
		return serviceCallBack;
	}
}
