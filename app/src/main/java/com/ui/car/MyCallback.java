package com.ui.car;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import com.business.ServiceCallBack;
import com.business.bean.ResponseMsg;
import com.fasterxml.jackson.databind.ObjectMapper;

public   class MyCallback implements Callback{
	ServiceCallBack  serviceCallBack=new ServiceCallBack();

	IResponse  iResponse;
	
	
	
	public MyCallback(IResponse iResponse) {
		super();
		this.iResponse = iResponse;
	}


	@Override
	public void onFailure(final Call call, IOException e) {
		serviceCallBack.setCall(call);
		serviceCallBack.setExcept(e);
		iResponse.onResponse(serviceCallBack);
	}

	@Override
	public void onResponse(Call call, Response response) throws IOException{
		if (response.isSuccessful()) {
			serviceCallBack.setSucess(true);
			ResponseBody body = response.body();
			String msg = body.string();

			
			ObjectMapper mapper = new ObjectMapper();
			
			ResponseMsg responseMsg = mapper.readValue(msg,
					ResponseMsg.class);
			
			serviceCallBack.setResponseMsg(responseMsg);
	    }
		serviceCallBack.setCall(call);
		serviceCallBack.setResponse(response);
		iResponse.onResponse(serviceCallBack);
		

		
	}
	public ServiceCallBack getServiceCallBack() {
		return serviceCallBack;
	}

	public void setServiceCallBack(ServiceCallBack serviceCallBack) {
		this.serviceCallBack = serviceCallBack;
	}
	 
	  public  interface IResponse{
		  void onFailure(ServiceCallBack serviceCallBack);
		  void onResponse(ServiceCallBack serviceCallBack);
	  }
}
