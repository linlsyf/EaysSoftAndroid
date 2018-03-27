package com.view.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.core.db.manger.StringUtils;
import com.core.utils.KeyboardUtils;
import com.easysoft.costumes.R;
import com.view.edittextview.DelayListenerEditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SearchHeadView extends LinearLayout {
    /**返回点击控件*/
    @Bind(R.id.iv_back)
    View  mBackLayout;
    /**清除点击控件 */
    @Bind(R.id.cleanLayout)
    View  mCleanLayout;
    /**输入框 */
    @Bind(R.id.searchEditText)
    DelayListenerEditText mSearchEditText;

    Context mContext;
    private onTextChangerListener mListener;

    public SearchHeadView(Context context) {
        super(context);
        init(context);

    }

    public SearchHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }
    public void init(Context context){
        mContext=context;
        initUI();
        initListener();

    }

    private void initUI() {
        LayoutInflater.from(mContext).inflate(R.layout.view_searchhead, this, true);
        ButterKnife.bind(this);

    }

    public void initListener(){
        DelayListenerEditText.onTextChangerListener
        mTextChangeListener=new DelayListenerEditText.onTextChangerListener() {
            @Override
            public void onTextChanger(String text) {

                mListener.onTextChanger(text);

                if (StringUtils.isBlank(text.trim())){
                    mCleanLayout.setVisibility(View.GONE);

                }else{
                    mCleanLayout.setVisibility(View.VISIBLE);

                }

            }
        };
        mSearchEditText.setOnTextChangerListener(mTextChangeListener);
        //清除点击
        mCleanLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchEditText.setText("");
            }
        });

        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    KeyboardUtils.closeKeybord(getContext(),mSearchEditText);
                    return true;
                }
                return false;
            }
        });

    }

    public View getBackLayout() {
        return mBackLayout;
    }

    public void setOnTextChangerListener(onTextChangerListener listener) {
        this.mListener = listener;
    }

    public interface onTextChangerListener {
        public void onTextChanger(String text);
    }

    public EditText getSearchEditText() {
        return mSearchEditText;
    }
}
