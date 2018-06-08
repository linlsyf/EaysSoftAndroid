package com.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.base.BaseFragment;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.easysoft.costumes.R;
import com.ui.setting.view.MySettingContentView;

/**
 * Created by lindanghong on 2018/6/6.
 */

public class AboutFragment extends BaseFragment implements ISafeSettingView{
    MySettingContentView recycleView;
    SettingPresenter presenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_about, null);
//    	if(recycleView==null){
//    		recycleView=new MySettingContentView(getActivity());
//    	}

        setRootView(rootView);
        return rootView;

    }

    @Override
    public void initUI(Section section) {

    }

    @Override
    public void showUpdate() {

    }

    @Override
    public void logOut() {

    }

    @Override
    public void updateItem(AddressItemBean imgBean) {

    }

    @Override
    public void showNews() {

    }

    @Override
    public void getBroadcastReceiverMessage(String type, Object mode) {

    }
}
