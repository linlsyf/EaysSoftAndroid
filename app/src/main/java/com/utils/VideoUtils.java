package com.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.widget.Toast;

import com.core.CoreApplication;
import com.dyhdyh.compat.mmrc.MediaMetadataRetrieverCompat;
import com.easy.recycleview.utils.ToastUtils;
import com.easysoft.costumes.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class VideoUtils {
	private static StringBuilder mFormatBuilder = new StringBuilder();
	private static Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

	static ArrayList listItems ;


	private  static MediaMetadataRetrieverCompat  mmrc = new MediaMetadataRetrieverCompat();
	private static final String[] VIDEOTHUMBNAIL_TABLE = new String[] {
			MediaStore.Video.Media._ID, // 0
			MediaStore.Video.Media.DATA, // 1 from android.provider.MediaStore.VideoDB
	};
	 public  static ArrayList<VideoItem> getVideodData(final Context context) {
//	        new Thread(new Runnable() {
//	            @Override
//	            public void run() {

		 try {
			 listItems = new ArrayList<VideoItem>();
			 Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
			 String[] projection = {MediaStore.Video.Media.DISPLAY_NAME,
					 MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE,
					 MediaStore.Video.Media.DATA};
			 Cursor cursor = getContentResolver(context).query(uri, projection, null, null, null);
			 while (cursor.moveToNext()) {
				 String name = cursor.getString(0);//文件名
				 long duration = cursor.getLong(1);//文件时长
				 long size = cursor.getLong(2);//文件大小
				 String data = cursor.getString(3);//文件路径
				 File storeFile = new File(data);
				 if (!storeFile.exists()) {
					 continue;
				 }
				 VideoItem item = new VideoItem();
				 item.setName(name);
				 item.setDuration(duration);
				 item.setDurationString(stringForTime((int) duration));
				 item.setSize(size);
				 item.setData(data);
				 String thumbPath = "";
				 item.setThumbPath(thumbPath);
				 item.setBitmap(getThumbnail(data, 200, 100));
				 listItems.add(item);
			 }

			 cursor.close();


		 } catch(Exception e){

		 }

		 return listItems;
//	            }
//	        }).start();

	    }
	/**
	 * 把毫秒转换成：1:20:30这里形式
	 * @param timeMs
	 * @return
	 */
	public static String stringForTime(int timeMs) {
		int totalSeconds = timeMs / 1000;
		int seconds = totalSeconds % 60;


		int minutes = (totalSeconds / 60) % 60;


		int hours = totalSeconds / 3600;


		mFormatBuilder.setLength(0);
		if (hours > 0) {
			return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
		} else {
			return mFormatter.format("%02d:%02d", minutes, seconds).toString();
		}
	}

	protected static ContentResolver getContentResolver(Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		
	
		return contentResolver;
	}




	private  static Bitmap getThumbnail(final String imagePath, int width, int height) {
		Bitmap bitmap =null;
				try {
					mmrc.setMediaDataSource(imagePath);
					//注意这里传的是微秒
					 bitmap = mmrc.getScaledFrameAtTime(2 * 1000 * 1000, MediaMetadataRetrieverCompat.OPTION_CLOSEST,
							100, 100);

				} catch (Exception e) {
					e.printStackTrace();
				}
		return bitmap;
	}


	public static  boolean checkPermission(Activity activity) {
		boolean isHas=false;
		if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
		  isHas=true;
		}
		return isHas;
	}


	//		  	     String type=path.substring(path.lastIndexOf(".")+1,path.length());  //获取文件后缀名

}

