package com.example.test;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

public class VideoUtils {
	
	static ArrayList       listItems ;

	 public  static ArrayList getVideodData(final Context context) {
//	        new Thread(new Runnable() {
//	            @Override
//	            public void run() {
	            	       listItems = new ArrayList<VideoItem>();
	                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	                String[] projection = { MediaStore.Video.Media.DISPLAY_NAME,
	                        MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE,
	                        MediaStore.Video.Media.DATA };
	                Cursor cursor = getContentResolver(context).query(uri, projection, null, null, null);
	                while (cursor.moveToNext()) {
	                    String name = cursor.getString(0);//视频的名称
	                    long duration = cursor.getLong(1);//视频的时长
	                    long size = cursor.getLong(2);//视频的大小
	                    String data = cursor.getString(3);//视频sd卡下的绝对路劲
	                    //一个视频信息对应一个对象
	                    VideoItem item = new VideoItem();
	                    item.setName(name);
	                    item.setDuration(duration);
	                    item.setSize(size);
	                    item.setData(data);


	                    listItems.add(item);
	                }


	                cursor.close();
//	                Message message = handler.obtainMessage();
//	                int what = 1;
//	                handler.sendEmptyMessage(what);

	                 return listItems;
//	            }
//	        }).start();


	    }

	protected static ContentResolver getContentResolver(Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		
	
		return contentResolver;
	}
}
