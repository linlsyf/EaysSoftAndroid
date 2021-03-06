package com.ui.common;

import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;

import com.core.base.BaseFragment;
import com.easy.recycleview.recycleview.EdittextLayoutView;
import com.easysoft.costumes.R;
import com.easysoft.utils.lib.system.DensityUtil;
import com.easysoft.utils.lib.system.FragmentHelper;
import com.easysoft.utils.lib.system.KeyboardUtils;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.ui.message.add.OrderDetailPersenter;


/**
 * 输入信息界面
 */
public class InformationInputFragment extends BaseFragment implements InformationInputContract.IInformationInputView {

   
    EdittextLayoutView mEt;

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String CONTENT = "content";

    /**标题*/
    private String mTitle;
    /**输入框内容*/
    private String mContent;

    private String mId;

	private NavigationBar toolbar;

    public OnUpdateSuccessListener listener;

    public void setOnUpdateSuccessListener(OnUpdateSuccessListener listener) {
        this.listener = listener;
    }

    public interface OnUpdateSuccessListener{
        void updateSuccess(String mContent, String id);
    }

   @Override
    public int getLayoutResId() {

        return R.layout.fragment_infoinput;
    }
            @Override
              public void initFragment() {
            	
	            initUIView();
	            initData();
	            initListener();
          }

                 @Override
              public void initUIView() {
            	 toolbar=getViewById(R.id.toolbar);
        		   TopBarBuilder.buildOnlyText(toolbar, getActivity(), NavigationBar.Location.LEFT_FIRST, "返回", 0);

      		   TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "输入内容", 0);
      		   TopBarBuilder.buildOnlyText(toolbar, getActivity(), NavigationBar.Location.RIGHT_FIRST, "确定", 0);
            mEt=getViewById(R.id.mNameEt);
       
        mEt.requestFocus();
        mEt.setPadding(DensityUtil.dip2px(getActivity(),5),0,0,0);
        KeyboardUtils.openKeybord(getActivity());
    }

    @Override
	public void initListener() {
    	toolbar.setNavigationBarListener(new NavigationBarListener() {
      
			@Override
			public void onClick(ViewGroup containView, NavigationBar.Location location) {
				  switch (location){
                  case LEFT_FIRST:
                	  KeyboardUtils.closeKeybord(getActivity());
                      FragmentHelper.popBackFragment(getActivity());
                      break;
                  case RIGHT_FIRST:
                	  listener.updateSuccess(mEt.getText(),mId);
                	  KeyboardUtils.closeKeybord(getActivity());
                      FragmentHelper.popBackFragment(getActivity());
//                      getIPresenter().saveInfo(mEt.getText(),mId);
                      break;
              }
				
			}
        });
    }
    @Override
    public void initData() {
        Bundle bundle = getArguments();
        mTitle = bundle.getString(TITLE);
        mContent = bundle.getString(CONTENT);
        mId = bundle.getString(ID);
//        initTopBar();
//        if (StringUtils.isEmpty(mContent)){
//            mEt.setHint(getString(R.string.unset));
//        }
        if(mId.equals(OrderDetailPersenter.KEY_NUM)|mId.equals(OrderDetailPersenter.KEY_PRICE)){
        	mEt.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        
        mEt.setText(mContent);
    }
//
//    /**
//     * 初始化顶部栏
//     */
//    private void initTopBar() {
////        TopBarBuilder.buildCenterTextTitle(mTopBar,getContext(),mTitle);
////        TopBarBuilder.buildLeftArrowTextById(mTopBar,getContext(),R.string.back);
////        TopBarBuilder.buildOnlyTextById(mTopBar,getContext(),NavigationBar.Location.RIGHT_FIRST,R.string.save);
////        mTopBar.setBackgroundColor(getResources().getColor(R.color.white));
//    }
//
//  


	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {
		// TODO Auto-generated method stub
		
	}



@Override
public void saveSucceed(boolean isSucceed) {
	// TODO Auto-generated method stub
	
}



@Override
public void saveError(String msg) {
	// TODO Auto-generated method stub
	
}
}
