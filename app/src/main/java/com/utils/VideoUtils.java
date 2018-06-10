package com.utils;

import android.Manifest;
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
import android.widget.Toast;

import com.dyhdyh.compat.mmrc.MediaMetadataRetrieverCompat;

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
	            	       listItems = new ArrayList<VideoItem>();



		 Uri videoUri = MediaStore.Video.Thumbnails.getContentUri("external");


	                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	                String[] projection = { MediaStore.Video.Media.DISPLAY_NAME,
	                        MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE,
	                        MediaStore.Video.Media.DATA };
	                Cursor cursor = getContentResolver(context).query(uri, projection, null, null, null);
	                while (cursor.moveToNext()) {
	                    String name = cursor.getString(0);//文件名
	                    long duration = cursor.getLong(1);//文件时长
	                    long size = cursor.getLong(2);//文件大小
	                    String data = cursor.getString(3);//文件路径
						File storeFile=new File(data);
                        if (!storeFile.exists()){
                        	continue;
						}
	                    VideoItem item = new VideoItem();
	                    item.setName(name);
                       item.setDuration(duration);
	                    item.setDurationString(stringForTime((int)duration));
	                    item.setSize(size);
	                    item.setData(data);
						String thumbPath="";
						item.setThumbPath(thumbPath);
						item.setBitmap(getThumbnail(data,200,100));
//						 getImageThumbnail(data,200,100);

	                    listItems.add(item);
	                }

	                cursor.close();



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

	public static Bitmap geThumbnailByMediaMetadata(String videoPath, int width, int height) {
//		String path  = Environment.getExternalStorageDirectory().getPath();
		MediaMetadataRetriever media = new MediaMetadataRetriever();
		media.setDataSource(videoPath);
		Bitmap bitmap = media.getFrameAtTime();


		return bitmap;
	}


	/**
	 * 得到本地图片文件
	 * @param context
	 * @return
	 */
	public static ArrayList<VideoItem> getAllPictures(Context context) {
		ArrayList<VideoItem> picturemaps = new ArrayList<>();
		VideoItem item;
		ContentResolver cr = context.getContentResolver();
		//先得到缩略图的URL和对应的图片id
		Cursor cursor = cr.query(
				MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
				new String[]{
						MediaStore.Images.Thumbnails.IMAGE_ID,
						MediaStore.Video.Thumbnails.DATA
				},
				null,
				null,
				null);
		if (cursor.moveToFirst()) {
			do {
				 	item=new VideoItem();
				item.setId(cursor.getInt(0)+"");
				item.setThumbPath(cursor.getString(1));


				picturemaps.add(item);
			} while (cursor.moveToNext());
			cursor.close();
		}

		return picturemaps;
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

	private  static void getImageThumbnail(final String imagePath, int width, int height) {
		Observable.create(new ObservableOnSubscribe<VideoItem>() {
			@Override
			public void subscribe(@NonNull ObservableEmitter<VideoItem> s) throws Exception {
				try {
						mmrc.setMediaDataSource(imagePath);
						//注意这里传的是微秒
						Bitmap bitmap = mmrc.getScaledFrameAtTime(2 * 1000 * 1000, MediaMetadataRetrieverCompat.OPTION_CLOSEST,
								100, 100);
						VideoItem  item=new VideoItem();
						s.onNext(item.setBitmap(bitmap));

				} catch (Exception e) {
					s.onError(e);
					e.printStackTrace();
				}
				s.onComplete();
			}
		}).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnNext(new Consumer<VideoItem>() {
					@Override
					public void accept(@NonNull VideoItem item) throws Exception {

						 int size=0;
					}
				}).subscribe();
        }
//	private void checkPermission(Activity ) {
//		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED ||
//				ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
//			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
//		}
//	}


	//		  	     String type=path.substring(path.lastIndexOf(".")+1,path.length());  //获取文件后缀名

}

