package com.ui.type;
import com.core.base.BaseFragment;
import com.easysoft.costumes.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by chengxi on 17/4/26.
 */
public class TabFragment3 extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

    	View rootView=inflater.inflate(R.layout.fragment_tab_3, null);
    	setRootView(rootView);
       return rootView;

    }

	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		// TODO Auto-generated method stub
		
	}
}

