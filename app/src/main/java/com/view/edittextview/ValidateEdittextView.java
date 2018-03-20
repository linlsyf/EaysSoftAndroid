package com.view.edittextview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.core.base.BaseCustomView;
import com.easysoft.costumes.R;


/**
 * 
 *<br> 创建者：林党宏
 *<br>时间：2016年4月20日 下午4:08:07
 *<br>注释：输入验证名界面
 */
public class ValidateEdittextView extends BaseCustomView {

	/** 第一位验证码 */
	private TextView verifyCodeOneText;
	/** 第一自定义光标 */
	private ImageView mCursorImgOne;
	/**第一个内容框*/
	RelativeLayout mcontentOneRL;

	/** 第二位验证码 */
	private TextView verifyCodeTwoText;
	/** 第二自定义光标 */
	private ImageView mCursorImgTwo;
	/**第二个内容框*/
	RelativeLayout mcontentTwoRL;

	/** 第三位验证码 */
	private TextView verifyCodeThreeText;
	/** 第三自定义光标 */
	private ImageView mCursorImgThree;
	/**第三个内容框*/
	RelativeLayout mcontentThreeRL;

	/** 第四位验证码 */
	private TextView verifyCodeFourText;
	/** 第四自定义光标 */
	private ImageView mCursorImgFour;
	/**第四个内容框*/
	RelativeLayout mcontentFourRL;

	/** 第五位验证码 */
	private TextView verifyCodeFiveText;
	/** 第五自定义光标 */
	private ImageView mCursorImgFive;
	/**第五个内容框*/
	RelativeLayout mcontentFiveRL;

	/** 第六位验证码 */
	private TextView verifyCodeSixText;
	/** 第六自定义光标 */
	private ImageView mCursorImgSix;
	/**第六个内容框*/
	RelativeLayout mcontentSixRL;

	/******* 实际输入的editext 其余留个textview 为显示 **********/
	/** 输入验证码 */
	private BoundEditText verifyCodeEditText;
	public BoundEditText getVerifyCodeEditText() {
		return verifyCodeEditText;
	}



	public ValidateEdittextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initUI(context);
		initListener(null);
	}

	public void initUI(Context context) {

		rootView = LayoutInflater.from(context).inflate(
				R.layout.common_verify_layout, this, true);
		// 四个输入框 只用于显示
		verifyCodeOneText = getViewById(R.id.tv_one);
		mCursorImgOne = getViewById(R.id.cursorImgOne);
		 mcontentOneRL = getViewById(R.id.contentOneRL);



		verifyCodeTwoText = getViewById(R.id.tv_two);
		mCursorImgTwo = getViewById(R.id.cursorImgTwo);
		mcontentTwoRL = getViewById(R.id.contentTwoRL);


		verifyCodeThreeText = getViewById(R.id.tv_three);
		mCursorImgThree = getViewById(R.id.cursorImgThree);
		mcontentThreeRL = getViewById(R.id.contentThreeRL);


		verifyCodeFourText = getViewById(R.id.tv_four);
		mCursorImgFour = getViewById(R.id.cursorImgfour);
		mcontentFourRL = getViewById(R.id.contentFourRL);


		verifyCodeFiveText = getViewById(R.id.tv_five);
		mCursorImgFive = getViewById(R.id.cursorImgFive);
		mcontentFiveRL = getViewById(R.id.contentFiveRL);


		verifyCodeSixText = getViewById(R.id.tv_six);
		mCursorImgSix = getViewById(R.id.cursorImgSix);
		mcontentSixRL = getViewById(R.id.contentSixRL);


		// 真正获得输入内容的控件 只用于显示
		verifyCodeEditText = getViewById(R.id.et_validate);

		AnimationDrawable spinner = (AnimationDrawable) mCursorImgOne.getBackground();
		spinner.start();
		AnimationDrawable spinnerTwo = (AnimationDrawable) mCursorImgTwo.getBackground();
		spinnerTwo.start();
		AnimationDrawable spinneThree = (AnimationDrawable) mCursorImgThree.getBackground();
		spinneThree.start();
		AnimationDrawable spinneFour = (AnimationDrawable) mCursorImgFour.getBackground();
		spinneFour.start();
		AnimationDrawable spinnerFive = (AnimationDrawable) mCursorImgFive.getBackground();
		spinnerFive.start();
		AnimationDrawable spinnerSix = (AnimationDrawable) mCursorImgSix.getBackground();
		spinnerSix.start();
	}

	@Override
	public void initListener(OnClickListener listener) {

		verifyCodeEditText.setCursorInEnd(true);
		verifyCodeEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus){
					mCursorImgOne.setVisibility(View.INVISIBLE);

					mCursorImgTwo.setVisibility(View.INVISIBLE);

					mCursorImgThree.setVisibility(View.INVISIBLE);

					mCursorImgFour.setVisibility(View.INVISIBLE);

					mCursorImgFive.setVisibility(View.INVISIBLE);

					mCursorImgSix.setVisibility(View.INVISIBLE);
				}else{
					checkLengthSetCursorShow(verifyCodeEditText.getText().toString().trim().length());
				}

			}
		});
		verifyCodeEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {

				String currentVerifyText = verifyCodeEditText.getText()
						.toString().trim();

				verifyCodeOneText.setText("");
				mCursorImgOne.setVisibility(View.INVISIBLE);

				verifyCodeTwoText.setText("");
				mCursorImgTwo.setVisibility(View.INVISIBLE);

				verifyCodeThreeText.setText("");
				mCursorImgThree.setVisibility(View.INVISIBLE);

				verifyCodeFourText.setText("");
				mCursorImgFour.setVisibility(View.INVISIBLE);

				verifyCodeFiveText.setText("");
				mCursorImgFive.setVisibility(View.INVISIBLE);

				verifyCodeSixText.setText("");
				mCursorImgSix.setVisibility(View.INVISIBLE);

				int length = currentVerifyText.length();
				checkLengthSetCursorShow(length);
				if (length > 0) {
					verifyCodeOneText.setText(currentVerifyText.subSequence(0,
							1));

					if (length > 1) {
						verifyCodeTwoText.setText(currentVerifyText
								.subSequence(1, 2));

						if (length > 2) {
							verifyCodeThreeText.setText(currentVerifyText
									.subSequence(2, 3));

							if (length > 3) {
								verifyCodeFourText.setText(currentVerifyText
										.subSequence(3, 4));

								if (length > 4) {
									verifyCodeFiveText.setText(currentVerifyText
													.subSequence(4, 5));

									if (length > 5) {
										verifyCodeSixText.setText(currentVerifyText
														.subSequence(5, 6));

									}
								}
							}
						}
					}
				}

			}
		});
		//layout 点击设置光标位置

	}

	  /**
	     *<br> 创建者：林党宏
	     *<br>时间：2016/8/31 10:13
	     *<br>注释：检查并设置光标位置
	     */
	private void checkLengthSetCursorShow(int length){
		if (length==0){
			mCursorImgOne.setVisibility(View.VISIBLE);
		}else if (length==1){
			mCursorImgTwo.setVisibility(View.VISIBLE);

		}else if (length==2){
			mCursorImgThree.setVisibility(View.VISIBLE);

		}else 	if (length==3){
			mCursorImgFour.setVisibility(View.VISIBLE);

		}else if (length==4){
			mCursorImgFive.setVisibility(View.VISIBLE);

		}else if (length==5){
			mCursorImgSix.setVisibility(View.VISIBLE);

		}else if (length==6){
			mCursorImgSix.setVisibility(View.VISIBLE);

		}
	}

	/**
	 * 
	 * <br>
	 * 创建者：ldh <br>
	 * 时间：2015年8月27日 下午7:45:29 <br>
	 * 注释：获得验证码 <br>
	 */
	public String getValidateCode() {
		return verifyCodeEditText.getText().toString();
	}

}
