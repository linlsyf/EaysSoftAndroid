package com.view.toolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class TopBar  extends RelativeLayout {
	Context context;
	public TopBar(Context context) {
		super(context);
		this.context=context;
	}
	public TopBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
	}
	public void initUI(){
		
	}
}
