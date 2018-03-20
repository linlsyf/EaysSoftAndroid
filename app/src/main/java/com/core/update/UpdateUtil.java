package com.core.update;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
/**
 * 工具类，用来获取服务器上的json更新配置文件
 * @author zw	
 */
public class UpdateUtil {
	public static int loading_process;
	
	public static JSONObject getJsonObject(String Url) {
		JSONObject obj = null;
		try {
			String josoString=getContent(Url);
			System.out.println("josoString返回："+josoString);
//			String josoString = "[{'appname':'newVersion','apkname':'newVersion.apk','verName':1.0.1,'verCode':2}]";
			JSONArray array = new JSONArray(josoString);
			if (array.length() > 0) {
				obj = array.getJSONObject(0);
			}
		} catch (Exception e) {
			System.out.println("异常--->下载,转化JSON");
			return null;
		}
		return obj;
	}
	
	/**
	 * 获取json文件内容
	 */
	public static String getContent(String url) throws Exception {
		StringBuffer buffer = new StringBuffer();
//		// 建立HTTP Post联机
//		HttpPost httpRequest = new HttpPost(url);
		
		// 建立HTTP Get联机
		HttpGet httpRequest = new HttpGet(url);
		// 取得默认的HttpClient
		HttpClient httpClient = new DefaultHttpClient();
		/*
		 * Post运作传送变量必须用NameValuePair[]数组储存
		 * List <NameValuePair> params = new ArrayList <NameValuePair>();
		 * params.add(new BasicNameValuePair("str", "I am Post String"));
		 */
		HttpParams params = httpClient.getParams();
		// 设置网络超时参数
		HttpConnectionParams.setConnectionTimeout(params, 15000);
		HttpConnectionParams.setSoTimeout(params, 15000);
		// 取得HttpResponse
		HttpResponse response = httpClient.execute(httpRequest);
		HttpEntity entity = response.getEntity();
		// 若状态码为200,表示连接成功
		Log.i("infor", "执行请求,状态码为:"+response.getStatusLine().getStatusCode());
		if (response.getStatusLine().getStatusCode() == 200) {
			if (entity != null) {
				// entity.getContent()取得返回的字符串
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
				String line = null;
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
			}
		}else if(response.getStatusLine().getStatusCode() == 404){
			return "请求更新apk 返回：404";
		}
		return buffer.toString();
	}
	
	public static boolean isConnect(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static int getVerCode(Context _context,String _package) {
		int verCode = -1;
		try {
			verCode = _context.getPackageManager().getPackageInfo(_package, 0).versionCode;
			
		} catch (NameNotFoundException e) {
		}
		return verCode;
	}
//	/**获取版本号v1.0.X*/
//	public static String getVersionName(Context _context,String _package) {
//
//		return _context.getString(R.string.version_value); 
//	}
	/**
	 * 获取SDCARD根目录
	 */
	public static String getSDPath() {
		File sdDir = null;
		// 判断sd卡是否存在
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取根目录
		} else {
			return "";
		}
		return sdDir.toString();
	}
	
}
