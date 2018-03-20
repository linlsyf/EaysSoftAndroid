package com.view.statusview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 
 * <br>创建者：ldh
 * <br>修改时间：2015年6月2日 下午3:10:28
 * <br>作用：用于防止软键盘遮挡登陆按钮的控件
 */
public class SoftKeyBoardSatusView extends LinearLayout {
	/**登陆界面向上移动的高度*/
	int scroll_dx;
	/**屏幕的高度*/
	int screenHeight;
	private final int CHANGE_SIZE = 100;
	/** 最低不能遮挡按钮部位*/
	View mBottomView;
	/** 移动整体view*/
	View mContentLayout;

	public SoftKeyBoardSatusView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	  /**
	     *<br> 创建者：林党宏
	     *<br>时间：2016/8/25 10:24
	     *<br>注释：初始化，设置后可以自动监听
	   * @param  screenHeight 屏幕高度
	   * @param  mContentLayout 移动布局
	   * @param  mBottomView 最低不能遮挡的view
	     */
	public void init(int screenHeight,View mContentLayout,View mBottomView){
		this.screenHeight=screenHeight;
		this.mContentLayout=mContentLayout;
		this.mBottomView =mBottomView;
//		setSoftKeyBoardListener(this);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		if (oldw == 0 || oldh == 0)
			return;

//		if ( oldh == 0){
//			oldw=w;
//			oldh=h+mBottomView.getMeasuredHeight();
//		}
		if ((mContentLayout!=null&& mBottomView !=null)|boardListener != null) {
			if (boardListener!=null){

				boardListener.keyBoardStatus(w, h, oldw, oldh);
			}
			if (oldw != 0 && h - oldh < -CHANGE_SIZE) {// 监听到显示
				if (boardListener!=null){

					boardListener.keyBoardVisable(Math.abs(h - oldh));
				}
				keyBoardVisable(Math.abs(h - oldh));
			}

			if (oldw != 0 && h - oldh > CHANGE_SIZE) {// 监听到隐藏
				if (boardListener!=null){

					boardListener.keyBoardInvisable(Math.abs(h - oldh));
				}
				keyBoardInvisable(Math.abs(h - oldh));
			}
		}
	}

	public void keyBoardInvisable(int move) {
		if (mContentLayout!=null){

			mContentLayout.scrollBy(0, -scroll_dx);
		}
	}

	public void keyBoardStatus(int w, int h, int oldw, int oldh) {

	}
	public void keyBoardVisable(int move) {
		//设置登陆布局向上移动的高度 如果是从登陆按钮开始算起那么 登陆按钮以上不遮挡
		if (mContentLayout!=null&& mBottomView !=null){

			int[] location = new int[2];
			mBottomView.getLocationOnScreen(location);
			int btnToBottom = screenHeight - location[1] - mBottomView.getHeight();
			scroll_dx = btnToBottom > move ? 0 : move - btnToBottom;
			mContentLayout.scrollBy(0, scroll_dx);
		}
	}
/**
 * 
 * <br>创建者：ldh
 * <br>修改时间：2015年6月2日 下午3:13:48
 * <br>作用：监听软键盘
 */
	public interface SoftkeyBoardListener {
		/**软键盘的当前状态*/
		public void keyBoardStatus(int w, int h, int oldw, int oldh);
		/**软键盘的显示*/
		public void keyBoardVisable(int move);
		/**软键盘的隐藏*/
		public void keyBoardInvisable(int move);
	}

	SoftkeyBoardListener boardListener;

	public void setSoftKeyBoardListener(SoftkeyBoardListener boardListener) {
		this.boardListener = boardListener;
	}
}
