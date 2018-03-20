package com.core.imge;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.core.CoreApplication;
import com.core.threadpool.ThreadFactory;

public class ImageUtils {
	
	static ImageUtils  utils;
	 public static ImageUtils getInStance(){
		 if(utils==null){
			 utils=new    ImageUtils();
		 }
		 
		 return  utils;
	 }
	
	public void load(String url,ImageView myImageView){
//		  url="http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
//		url="http://192.168.155.1:8090/api/v1/file/down?type=2&name=48cead4a-7ba1-41bb-8cb1-5da76d144dd9";
		Glide.with(CoreApplication.getAppContext())
        .load(url)


        .into(myImageView);

	}
	public void loadPath(final String path, final ImageView myImageView){


				Glide.with(CoreApplication.getAppContext())
						.load(new File(path))
						.into(myImageView);


		
	}
	public void loadResourceId(int id,ImageView myImageView){
		Glide.with(CoreApplication.getAppContext())
		.load(id)
		.into(myImageView);
		
	}
	  public static class BitmapUtil {

	        /**
	         * 鍥剧墖缂╂斁
	         * @param bitmap 闇€瑕佺缉鏀剧殑鍥剧墖
	         * @param scale 缂╂斁鐜?         * @return 缂╂斁鍚庡浘鐗?         */
	        public static Bitmap zoomBitmap(Bitmap bitmap, float scale) {
	            int width = bitmap.getWidth();// 鑾峰彇鍘熷浘鐨勫
	            int height = bitmap.getHeight();// 鑾峰彇鍘熷浘鐨勯珮
	            Matrix matrix = new Matrix();// 鍒涘缓Matrix鐭╅樀瀵硅薄
	            matrix.setScale(scale, scale);// 璁剧疆瀹介珮鐨勭缉鏀炬瘮
	            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	        }

	        public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
	            return new BitmapDrawable(context.getResources(), bitmap);
	        }

	        public static Bitmap getBitmapById(Context context, int id) {
	            Drawable drawable = context.getResources().getDrawable(id);
	            return DrawableUtil.drawableToBitmap(drawable);
	        }

	        public static Bitmap buildRoundBitmap(Bitmap bitmap, float round) {
	            int width = bitmap.getWidth();
	            int height = bitmap.getHeight();
	            RectF rectF = new RectF(0, 0, width, height);

	            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	            paint.setColor(Color.GREEN);

	            Bitmap roundBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	            Canvas canvas = new Canvas(roundBitmap);
	            canvas.drawBitmap(bitmap, 0, 0, paint);
	            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	            canvas.drawRoundRect(rectF, round, round, paint);

	            return roundBitmap;
	        }
	    }

	    public static class DrawableUtil {

	        public static Bitmap drawableToBitmap(Drawable drawable) {
	            int width = drawable.getIntrinsicWidth();
	            int height = drawable.getIntrinsicHeight();
	            return buildBitmap(drawable, width, height);
	        }

	        public static Bitmap drawableToBitmap(Drawable drawable, float scale) {
	            int width = (int) (drawable.getIntrinsicWidth() * scale);
	            int height = (int) (drawable.getIntrinsicHeight() * scale);
	            return buildBitmap(drawable, width, height);
	        }

	        private static Bitmap buildBitmap(Drawable drawable, int width, int height) {
	            Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
	            Canvas canvas = new Canvas(bitmap);
	            drawable.setBounds(0, 0, width, height);
	            drawable.draw(canvas);
	            return bitmap;
	        }

	    }

	

}