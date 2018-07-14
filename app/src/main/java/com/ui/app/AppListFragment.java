package com.ui.app;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.business.BusinessBroadcastUtils;
import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.easy.recycleview.recycleview.AddressRecycleView;
import com.easy.recycleview.recycleview.item.bean.SelectBean;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.easysoft.costumes.R;
import com.easysoft.utils.lib.system.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.ui.app.view.AppItemView;
import com.ui.other.tuling.news.NewsFragment;
import com.ui.video.IVideoHomeView;
import com.ui.video.VideoHideListFragment;

import java.util.List;

/**
 * 展示商品
 */

public class AppListFragment extends BaseFragment implements IVideoHomeView {
    AppListPresenter persenter;
    AddressRecycleView recycleView;
    private NavigationBar toolbar;
    LinearLayout editLayout;
    TextView  addTv;
    TextView  selectAllTv;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
    	View rootView=inflater.inflate(R.layout.fragment_common, null);
    	setRootView(rootView);
       return rootView;

    }
    @Override
    public void initFragment() {
       initUIView();
       initListener();
    }
    @Override
    public void initUIView() {
        persenter=new AppListPresenter(this);
        recycleView = getViewById(R.id.goodsGridview);
        persenter.init();
        toolbar=getViewById(R.id.toolbar);
        editLayout=getViewById(R.id.editLayout);
        addTv=getViewById(R.id.add);
        selectAllTv=getViewById(R.id.selectAll);

        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "应用列表", 0);
       recycleView.initCustomViewCallBack(new AddressRecycleView.CustomViewCallBack() {
           @Override
           public View getCustomView(Context context, int type) {
               View  itemView = null;
               if (type==3){
                   itemView=new AppItemView(context);
               }
               return itemView;
           }
       });
    }
    @Override
    public void initListener() {
        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {
                if (location== NavigationBar.Location.RIGHT_FIRST) {
                  boolean isShow=  persenter.setCanEdit();
                   if (isShow){
                       editLayout.setVisibility(View.VISIBLE);
                       addTv.setVisibility(View.VISIBLE);
                       selectAllTv.setVisibility(View.VISIBLE);
                   }else{
                       editLayout.setVisibility(View.GONE);
                   }


                }

            }
        });
        addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SelectBean>  selectBeanList= recycleView.getSectionAdapterHelper().getSelect(AppListPresenter.KEY_SETTING);
                persenter.setHide(selectBeanList);

            }
        });
        selectAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isShow=  persenter.setCanEdit();
                if (isShow){
                    editLayout.setVisibility(View.VISIBLE);
                    addTv.setVisibility(View.VISIBLE);
                    selectAllTv.setVisibility(View.VISIBLE);
                }else{
                    editLayout.setVisibility(View.GONE);
                }
            }
        });

    }

	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {

         if(type.equals(BusinessBroadcastUtils.TYPE_REFRESH_VIDEO)){
           persenter.init();
        }
//        if(type.equals(BusinessBroadcastUtils.TYPE_LOGIN_FAILS)){
//
//            noticeTv.setVisibility(View.VISIBLE);
//        }
//        else if(type.equals(BusinessBroadcastUtils.TYPE_GOODS_ADD_SUCESS)){
//            persenter.list();
//        }
	}

    @Override
    public void initUI(final Section nextSection) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                recycleView.updateSection(nextSection,true);
            }
        });

    }

    @Override
    public void showItem(final VideoBussinessItem imgBean) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (imgBean.getId().equals(AppListPresenter.ID_NEWS)){
//                    Bundle bundle=new Bundle();
//                    bundle.putSerializable("messageEntity", entity);
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new NewsFragment(), null);

                }else{
                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new VideoHideListFragment(), null);

                }

            }
        });
    }


//    @Override
//    public void addLayoutHelper(LayoutHelper helper,boolean isRefresh) {
//        recycleView.addLayoutHelper(helper,isRefresh);
//    }
}
