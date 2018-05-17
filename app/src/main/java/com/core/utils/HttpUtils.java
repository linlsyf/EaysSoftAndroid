package com.core.utils;

import com.easysoft.utils.lib.http.OkHttpUtils;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class HttpUtils {
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public static  OkHttpClient client ;


	static HttpUtils utils;
	static OkHttpUtils  okHttpUtils;
	 public static HttpUtils getInStance(){
		 if(utils==null){
			 utils=new HttpUtils();
			 client = new OkHttpClient();
		 }
		 okHttpUtils=OkHttpUtils.getInStance();
		 return  utils;
	 }
	
	public void post(String url,   Callback callBack)  {

		okHttpUtils.post(url,callBack);
	}
	public void uploadFile(String url, String filepath, String fileName,  Callback callBack)  {
		okHttpUtils.uploadFile(url,filepath,fileName,callBack);
	}
	/**
	 * @param url 下载连接
	 * @param saveDir 储存下载文件的SDCard目录
	 * @param listener 下载监听
	 */
	public void download(final String url, final String saveDir, final OkHttpUtils.OnDownloadListener listener) {
		okHttpUtils.download(url,saveDir,listener );
	}





}
