package com.view.process;



import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.easysoft.costumes.R;


/**
 * 
 *<br> 创建者：ldh
 *<br>时间：2015年11月3日 下午2:11:46
 *<br>注释：显示加载框
 *<br>
 */
public class ProgressHUD extends Dialog {
	
	/**是否正在加载中*/
	boolean hasinit =false;
	/**是否正在加载中*/
	boolean isshowing=false;
	public ProgressHUD(Context context) {
		super(context, R.style.ProgressHUD);
	}

	public ProgressHUD(Context context, int theme) {
		
		super(context, theme);
	}


	public void onWindowFocusChanged(boolean hasFocus){
		ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        spinner.start();
    }
	
	public void setMessage(CharSequence message) {
		if(message != null && message.length() > 0) {
			findViewById(R.id.message).setVisibility(View.VISIBLE);			
			TextView txt = (TextView)findViewById(R.id.message);
			txt.setText(message);
			txt.invalidate();
		}
		else {
			this.dismiss();
		}
	}
	
	@Override
	public void show() {
		isshowing=true;
		super.show();
	}
	@Override
	public void dismiss() {
		isshowing=false;
		super.dismiss();
	}
	@Override
	public boolean isShowing() {
		return isshowing;
//		return super.isShowing();
	}
	/**
	 * 
	 *<br> 创建者：ldh
	 *<br>时间：2015年7月22日 下午4:09:53
	 *<br>注释：
	 *<br>@param context
	 *<br>@param message
	 *<br>@param indeterminate 是否可取消
	 *<br>@param cancelable   是否屏蔽点击返回键
	 *<br>@param cancelListener
	 *<br>@param onKeyListener
	 *<br>@return
	 */
	public  ProgressHUD showDialog(Context context, CharSequence message, boolean indeterminate, boolean cancelable,
			OnCancelListener cancelListener, OnKeyListener onKeyListener ) {
		
		if (hasinit) {
			return this;
		}
			
		setTitle("");
		setContentView(R.layout.progress_hud);
		getWindow().getAttributes().gravity=Gravity.CENTER;
		WindowManager.LayoutParams lp = getWindow().getAttributes();  
		lp.dimAmount=0.2f;
		getWindow().setAttributes(lp); 
	
		if(message == null || message.length() == 0) {
			findViewById(R.id.message).setVisibility(View.GONE);			
		} else {
			TextView txt = (TextView)findViewById(R.id.message);
			txt.setText(message);
		}
		setCancelable(cancelable);//默认可以按返回键取消
		setCanceledOnTouchOutside(cancelable);
		setOnCancelListener(cancelListener);
		setOnKeyListener(onKeyListener);
		show();
		hasinit = true;

		return this;
	}	
	
	
	/**
	 * 
	 *<br> 创建者：ldh
	 *<br>时间：2015年7月22日 下午4:09:53
	 *<br>注释：
	 *<br>@param context
	 *<br>@param message
	 *<br>@param indeterminate	能否点击屏幕取消
	 *<br>@param cancelable		能否点击返回键取消
	 *<br>@param cancelListener
	 *<br>@param onKeyListener
	 *<br>@return
	 */
	public static ProgressHUD show(Context context, CharSequence message, boolean indeterminate, boolean cancelable,
			OnCancelListener cancelListener, OnKeyListener onKeyListener ) {
		ProgressHUD dialog = new ProgressHUD(context,R.style.ProgressHUD);
		dialog.setTitle("");
		dialog.setContentView(R.layout.progress_hud);
		if(message == null || message.length() == 0) {
			dialog.findViewById(R.id.message).setVisibility(View.GONE);			
		} else {
			TextView txt = (TextView)dialog.findViewById(R.id.message);
			txt.setText(message);
		}
		dialog.setCancelable(cancelable);//默认可以按返回键取消
		dialog.setCanceledOnTouchOutside(indeterminate);
		dialog.setOnCancelListener(cancelListener);
		dialog.setOnKeyListener(onKeyListener);
		dialog.getWindow().getAttributes().gravity=Gravity.CENTER;
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();  
		lp.dimAmount=0.2f;
		dialog.getWindow().setAttributes(lp); 
		//dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		dialog.show();
		return dialog;
	}	
	public static ProgressHUD show(Context context, CharSequence message, boolean indeterminate, boolean cancelable,boolean issingle,
			OnCancelListener cancelListener, OnKeyListener onKeyListener ) {
		ProgressHUD dialog = new ProgressHUD(context,R.style.ProgressHUD);
		dialog.setTitle("");
		dialog.setContentView(R.layout.progress_hud);
		if(message == null || message.length() == 0) {
			dialog.findViewById(R.id.message).setVisibility(View.GONE);			
		} else {
			TextView txt = (TextView)dialog.findViewById(R.id.message);
			txt.setText(message);
		}
		dialog.setCancelable(cancelable);//默认可以按返回键取消
		dialog.setCanceledOnTouchOutside(cancelable);
		dialog.setOnCancelListener(cancelListener);
		dialog.setOnKeyListener(onKeyListener);
		dialog.getWindow().getAttributes().gravity=Gravity.CENTER;
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();  
		lp.dimAmount=0.2f;
		dialog.getWindow().setAttributes(lp); 
		//dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		dialog.show();
		return dialog;
	}	
	
	
	
	
	/**
	 * 
	 *<br> 创建者：ldh
	 *<br>时间：2015年7月22日 下午4:09:53
	 *<br>注释：自定义显示转圈图片
	 *<br>@param context
	 *<br>@param message
	 *<br>@param indeterminate 是否可取消
	 *<br>@param cancelable   是否屏蔽点击返回键
	 *<br>@param cancelListener
	 *<br>@param onKeyListener
	 *<br>@param 自定义转圈界面
	 *<br>@return
	 */
	public static ProgressHUD show(Context context, CharSequence message, boolean indeterminate, boolean cancelable,
			OnCancelListener cancelListener, OnKeyListener onKeyListener,int  ContentViewId) {
		ProgressHUD dialog = new ProgressHUD(context,R.style.ProgressHUD);
		dialog.setTitle("");
		
		dialog.setContentView(ContentViewId);
		if(message == null || message.length() == 0) {
			dialog.findViewById(R.id.message).setVisibility(View.GONE);			
		} else {
			TextView txt = (TextView)dialog.findViewById(R.id.message);
			txt.setText(message);
		}
		dialog.setCancelable(cancelable);//默认可以按返回键取消
		dialog.setCanceledOnTouchOutside(cancelable);
		dialog.setOnCancelListener(cancelListener);
		dialog.setOnKeyListener(onKeyListener);
		dialog.getWindow().getAttributes().gravity=Gravity.CENTER;
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();  
		lp.dimAmount=0.2f;
		dialog.getWindow().setAttributes(lp); 
		//dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		dialog.show();
		return dialog;
	}	
	public static ProgressHUD show(Context context, CharSequence message) {
		ProgressHUD dialog = new ProgressHUD(context,R.style.ProgressHUD);
		dialog.setTitle("");
		dialog.setContentView(R.layout.progress_hud);
		if(message == null || message.length() == 0) {
			dialog.findViewById(R.id.message).setVisibility(View.GONE);			
		} else {
			TextView txt = (TextView)dialog.findViewById(R.id.message);
			txt.setText(message);
		}
		
		dialog.getWindow().getAttributes().gravity=Gravity.CENTER;
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();  
		lp.dimAmount=0.2f;
		dialog.getWindow().setAttributes(lp); 
		//dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		dialog.show();
		return dialog;
	}	
	
}
