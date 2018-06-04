package com.utils;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.business.bean.VideoItem;

public class VideoUtils {
	
	static ArrayList       listItems ;

	 public  static ArrayList<VideoItem> getVideodData(final Context context) {
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
	                    String name = cursor.getString(0);//��Ƶ������
	                    long duration = cursor.getLong(1);//��Ƶ��ʱ��
	                    long size = cursor.getLong(2);//��Ƶ�Ĵ�С
	                    String data = cursor.getString(3);//��Ƶsd���µľ���·��

	                    VideoItem item = new VideoItem();
	                    item.setName(name);
	                    item.setDuration(duration);
	                    item.setSize(size);
	                    item.setData(data);
						item.setThumbPath(cursor.getString(cursor
								.getColumnIndex(MediaStore.Video.Thumbnails.DATA)));


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
