package com.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easysoft.costumes.R;


/**
 * 白底灰边圆角样式的Dialog(待完善)
 * @author htz
 *
 */
public class CustomDialog {
	private Dialog dialog;
	
	private View view;
	/**标题*/
	private TextView title;
	/**正文文本控件*/
	private TextView bodyTV;
	/**确认按钮*/
	private TextView ok;
	/**取消按钮*/
	private TextView cancel;
	/**可添加自定义View的帧布局*/
	private FrameLayout customLayout;
	/**按钮栏的线性布局*/
	private LinearLayout btnLayout;
	/**根布局可以用来设置背景图*/
	private LinearLayout mRootLayout;
	/**灰色分割线*/
	private View titleLine;
	private View flLine;
	private View btnLine;
	/**是否可以按返回键取消*/
	private boolean cancelByBack;
	/** list 对话框弹出效果*/
	int mListDialogAnim = R.style.dialogAnim;
	/** list 对话框弹出效果*/
	int mListGravity = Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
	Context context;

	private String mark;
	/**
	 * 
	 * @param context				上下文
	 * @param cancelable			是否可按返回键取消
	 * @param cancelOnTouchOutside	是否可按弹窗以外的地方取消
	 */
	public CustomDialog(Context context, boolean cancelable, boolean cancelOnTouchOutside) {
		this.context=context;
		dialog = new Dialog(context, R.style.DialogStyle);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(cancelOnTouchOutside);
		// 让dialog覆盖在软键盘之上
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
				 WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		setCancelByBack(cancelable);
		initView(dialog.getContext());
		initListener();
	}

	/**
	 *
	 * @param context				上下文
	 * @param cancelable			是否可按返回键取消
	 * @param cancelOnTouchOutside	是否可按弹窗以外的地方取消
	 */
	public CustomDialog(Context context, boolean cancelable, boolean cancelOnTouchOutside,boolean isAboveSoft) {
		this.context=context;
		dialog = new Dialog(context, R.style.DialogStyle);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(cancelOnTouchOutside);
		if (isAboveSoft){
			// 让dialog覆盖在软键盘之上
			dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
					WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		}
		setCancelByBack(cancelable);
		initView(dialog.getContext());
		initListener();
	}

	/**
	 * 
	 * @param context				上下文
	 * @param cancelable			是否可按返回键取消
	 * @param cancelOnTouchOutside	是否可按弹窗以外的地方取消
	 * @param titleVisible			是否显示标题栏
	 * @param title					标题内容
	 * @param okVisible				是否显示确定按钮
	 * @param okListener			确定按钮监听，可为NULL
	 * @param cancelVisible			是否显示取消按钮
	 * @param cancelListener		取消按钮监听，可为NULL
	 */
	public CustomDialog(Context context, boolean cancelable, boolean cancelOnTouchOutside,
			boolean titleVisible, String title,
			boolean okVisible, View.OnClickListener okListener, 
			boolean cancelVisible, View.OnClickListener cancelListener){
		this(context, cancelable, cancelOnTouchOutside);
		setTitle(title);
		setOKListener(okListener);
		setCancelListener(cancelListener);
		if(!titleVisible){
			this.title.setVisibility(View.GONE);
			this.titleLine.setVisibility(View.GONE);
		}
		if(!okVisible){
			setOKVisible(View.GONE);
		}
		if(!cancelVisible){
			setCancelVisible(View.GONE);
		}
	}

	private void initView(Context context){
		view = View.inflate(context, R.layout.dialog_custom_layout, null);
		//标题
		title = (TextView) view.findViewById(R.id.resend_ask_title);
		// 确认
		ok = (TextView) view.findViewById(R.id.resend_ask_confirm);
		// 取消
		cancel = (TextView) view.findViewById(R.id.resend_ask_cancel);
		// 添加自定义View的帧布局
		customLayout = (FrameLayout) view.findViewById(R.id.resend_ask_custom_fl);
		// 按钮栏的线性布局
		btnLayout = (LinearLayout) view.findViewById(R.id.resend_ask_btn_ll);
		// 按钮栏的线性布局
		mRootLayout = (LinearLayout) view.findViewById(R.id.rootLayout);
		// 正文文本
		bodyTV = new TextView(context);
		
		titleLine = view.findViewById(R.id.resend_ask_title_line);
		flLine = view.findViewById(R.id.resend_ask_fl_line);
		btnLine = view.findViewById(R.id.resend_ask_btn_line);
		
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.width = context.getResources().getDisplayMetrics().widthPixels * 7 / 9 ;
		params.height = LayoutParams.WRAP_CONTENT;
		dialog.setContentView(view, params);
	}
	
	private void initListener(){
		// 确认和取消按钮的默认点击事件
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(dialog.isShowing()){
					dialog.dismiss();
				}
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(dialog.isShowing()){
					dialog.dismiss();
				}
			}
		});
		dialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface arg0, int keycode, KeyEvent event) {
				if(keycode == KeyEvent.KEYCODE_BACK){
					if(event.getAction() == KeyEvent.ACTION_UP && cancelByBack){
						view.post(new Runnable() {
							@Override
							public void run() {
								dialog.dismiss();
							}
						});
					}
					return true;
				}
				return false;
			}
		});
	}
/*------------------------------不需要暴露的接口---------------------------*/
	/**
	 * 判断按钮栏是否需要隐藏或显示
	 */
	private void setBtnVisible(){
		view.post(new Runnable() {
			
			@Override
			public void run() {
				if(!ok.isShown() && !cancel.isShown()){
					setBtnVisible(View.GONE);
				} else {
					setBtnVisible(View.VISIBLE);
				}
			}
		});
	}
	/**
	 * 判断是否需要显示确认和取消按钮间的分割线
	 */
	private void setBtnLineVisible(){
		view.post(new Runnable() {			
			@Override
			public void run() {
				if(ok.isShown() && cancel.isShown()){
					btnLine.setVisibility(View.VISIBLE);
				} else {
					btnLine.setVisibility(View.GONE);
				}
			}
		});
	}
	/**
	 * 判断是否需要显示正文控件与按钮栏之间的分割线
	 */
	private void setFLLineVisible(){
		view.post(new Runnable() {
			@Override
			public void run() {
				if(customLayout.isShown() && btnLayout.isShown()){
					flLine.setVisibility(View.VISIBLE);
				} else {
					flLine.setVisibility(View.GONE);
				}
			}
		});
	}
/*------------------------------需要暴露的接口---------------------------*/
	/**
	 * 设置是否可以按返回键取消
	 * @param cancel
	 */
	public void setCancelByBack(boolean cancel){
		cancelByBack = cancel;
	}
	/**
	 * 设置标题
	 * @param title
	 */
	public void setTitle(CharSequence title) {
		this.title.setText(title);
	}
	/**
	 * 标题加粗
	 * @param bold
	 */
	public void setTitleBold(boolean bold){
		this.title.getPaint().setFakeBoldText(bold);
	}
	/**
	 * 设置标题文字大小
	 * @param size
	 */
	public void setTitleSize(float size){
		title.setTextSize(size);
	}
	/**
	 * 设置标题文字颜色
	 * @param color
	 */
	public void setTitleColor(int color){
		title.setTextColor(color);
	}
	/**
	 * 设置标题位置
	 * @param gravity
	 */
	public void setTitleGravity(int gravity){
		title.setGravity(gravity);
	}
	/**
	 * 设置标题可见性
	 * @param visible
	 */
	public void setTitleVisible(int visible){
		title.setVisibility(visible);
		titleLine.setVisibility(visible);
	}
	/**
	 * 设置标题分割线可见性
	 * @param visible
	 */
	public void setTitleLineVisible(int visible){
		titleLine.setVisibility(visible);
	}
	/**
	 * 确定按钮可见性
	 * @param visible
	 */
	public void setOKVisible(int visible){
		ok.setVisibility(visible);
		setBtnLineVisible();
		setBtnVisible();
	}
	/**
	 * 取消按钮可见性
	 * @param visible
	 */
	public void setCancelVisible(int visible){
		cancel.setVisibility(visible);
		setBtnLineVisible();
		setBtnVisible();
	}
	  /**
	     *<br> 创建者：林党宏
	     *<br>时间：2016/8/19 14:57
	     *<br>注释：设置根目录背景图片
	     */
	public  void setRootLayoutBg(int resId){
		mRootLayout.setBackgroundResource(resId);
	}

	public void setRootLayoutBg(Drawable drawable) {
		mRootLayout.setBackgroundDrawable(drawable);
	}
	
	/**
	 * 设置确认按钮点击事件
	 * @param okListener
	 */
	public void setOKListener(final View.OnClickListener okListener){
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(okListener != null){
					okListener.onClick(ok);
				}
				if(dialog.isShowing()){
					dialog.dismiss();
				}
			}
		});
	}
	/**
	 * 设置取消按钮点击事件
	 * @param cancelListener
	 */
	public void setCancelListener(final View.OnClickListener cancelListener){
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(cancelListener != null){					
					cancelListener.onClick(cancel);
				}
				if(dialog.isShowing()){
					dialog.dismiss();
				}
			}
		});
	}
	/**
	 * 获取ok按钮控件
	 * @return	TextView
	 */
	public TextView getOkView(){
		return ok;
	}
	/**
	 * 获取cancel按钮控件
	 * @return TextView
	 */
	public TextView getCancelView(){
		return cancel;
	}
	/**
	 * 添加正文控件
	 * @param view
	 */
	public void setBodyView(View view) {
		customLayout.removeAllViews();

		FrameLayout.LayoutParams params = 
				new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		view.setLayoutParams(params);
		customLayout.addView(view);
		customLayout.setVisibility(View.VISIBLE);
		setFLLineVisible();
	}

	public void setBodyView(View view, int width, int height) {
		customLayout.removeAllViews();

		FrameLayout.LayoutParams params =
				new FrameLayout.LayoutParams(width, height);
		params.gravity = Gravity.CENTER;
		view.setLayoutParams(params);
		customLayout.addView(view);
		customLayout.setVisibility(View.VISIBLE);
		setFLLineVisible();
	}

	public View getBodyView() {
		return customLayout.getChildAt(0);
	}
	/**
	 * 设置正文文本
	 * @param text	正文
	 */
	public void setBodyText(String text){
		bodyTV.setText(text);
		bodyTV.setPadding(25, 50, 25, 50);
		bodyTV.setGravity(Gravity.CENTER_HORIZONTAL);
		setBodyView(bodyTV);
	}
	/**
	 * 设置正文文本
	 * @param text	正文
	 * @param size	文字大小
	 */
	public void setBodyText(String text, float size){
		bodyTV.setTextSize(size);
		setBodyText(text);
	}

	public void setBodyText(int resId){
		bodyTV.setText(resId);
		bodyTV.setPadding(25, 50, 25, 50);
		bodyTV.setGravity(Gravity.CENTER_HORIZONTAL);
		setBodyView(bodyTV);
	}

	public void setBodyText(int resId, float size){
		bodyTV.setTextSize(size);
		setBodyText(resId);
	}
	/**
	 * 设置正文控件可见性
	 * @param visible
	 */
	public void setBodyViewVisible(int visible){
		customLayout.setVisibility(visible);
		setFLLineVisible();
	}
	/**
	 * 设置分割线可见性
	 * @param visible
	 */
	public void setLineVisible(int visible){
		titleLine.setVisibility(visible);
		flLine.setVisibility(visible);
		btnLine.setVisibility(visible);
	}
	/**
	 * 设置分割线宽度
	 * @param width
	 */
	public void setLineWidth(int width){
		LinearLayout.LayoutParams titleP = (LinearLayout.LayoutParams) titleLine.getLayoutParams();
		titleP.height = width;
		titleLine.setLayoutParams(titleP);
		LinearLayout.LayoutParams flP = (LinearLayout.LayoutParams) flLine.getLayoutParams();
		flP.height = width;
		flLine.setLayoutParams(flP);
		LinearLayout.LayoutParams btnP = (LinearLayout.LayoutParams) btnLine.getLayoutParams();
		btnP.height = width;
		btnLine.setLayoutParams(btnP);
	}
	
	public void setLineColor(int color){
		titleLine.setBackgroundColor(color);
		flLine.setBackgroundColor(color);
		btnLine.setBackgroundColor(color);
	}
	/**
	 * 设置按纽栏的可见性
	 * @param visible
	 */
	public void setBtnVisible(int visible){
		btnLayout.setVisibility(visible);
		setFLLineVisible();
	}
/*------------------------调用Dialog原有方法的接口-------------------------*/
	/**
	 * 展示Dialog
	 */
	public void show(){
		if(!dialog.isShowing()){
			dialog.show();
		}
	}
	/**
	 * 隐藏Dialog
	 */
	public void dismiss(){
		if(dialog.isShowing()){
			dialog.dismiss();
		}
	}
	/**
	 * 取消Dialog，实现过程其实跟dismiss差不多，只是把CancelListener也给取消了
	 */
	public void cancel(){
		if(dialog.isShowing()){
			dialog.cancel();
		}
	}
	
	public boolean isShowing(){
		return dialog == null ? false : dialog.isShowing();
	}
	
	/**
	 * 
	 *<br> 创建者：林党宏
	 *<br>时间：2016-6-21 下午4:43:52
	 *<br>注释：监听弹出框已经显示  那么接下来可以弹出键盘了
	 */
	public void setOnShowListener( OnShowListener onShowListener){
		if (dialog!=null) {
			dialog.setOnShowListener(onShowListener);
		}
	}


	  /**
	     *<br> 创建者：林党宏
	     *<br>时间：2016/8/19 16:50
	     *<br>注释：设置list 对话框弹出动画
	     */
	public void setListDialogAnim(int mListDialogAnim) {
		this.mListDialogAnim = mListDialogAnim;
	}

	  /**
	     *<br> 创建者：林党宏
	     *<br>时间：2016/8/24 10:18
	     *<br>注释：设置list弹出位置
	     */
	public void setListDialogGravity(int listGravity) {
		this. mListGravity=listGravity;
	}

	/**
	     *<br> 创建者：林党宏
	     *<br>时间：2016/8/19 15:53
	     *<br>注释：按照list样式从底部显示dialog
	     */
	public void showListDialog(){

		setTitleVisible(View.GONE);
		setBtnVisible(View.GONE);
		setRootLayoutBg(R.drawable.transparent);

		Window mWindow = dialog.getWindow();
		if (mListDialogAnim!=0){
			mWindow.setWindowAnimations(mListDialogAnim);
		}


		int width=context.getResources().getDisplayMetrics().widthPixels *9/ 10 ;
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.width =width;
		params.height = LayoutParams.WRAP_CONTENT;
		params.gravity=Gravity.BOTTOM;
		dialog.setContentView(view, params);
     //设置list 对话框位置
		Window dialogWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(mListGravity);

		dialog.show();
	}


	/**
	 * 
	 *<br> 创建者：林党宏
	 *<br>时间：2016-6-21 下午5:40:22
	 *<br>注释：设置键盘弹出在对话框外部
	 */
	public void setSoftInputCoverResize(){
		if (dialog!=null) {
			dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		}

	}
	public Dialog getDialog() {
		return dialog;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}
