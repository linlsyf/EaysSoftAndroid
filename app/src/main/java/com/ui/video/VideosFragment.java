package com.ui.video;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.business.bean.VideoBussinessItem;
import com.core.base.BaseFragment;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.easysoft.costumes.R;
import com.easysoft.widget.search.SearchHeadView;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.NavigationBarListener;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.ui.message.view.GoodsView;
import com.utils.OpenFileUtils;

/**
 * 展示商品
 */

public class VideosFragment extends BaseFragment implements IVideoHomeView {
    VideoHomePresenter persenter;
    GoodsView recycleView;
    private NavigationBar toolbar;
    TextView noticeTv;
    SearchHeadView searchHeadView;

//    private List<View> viewList;
//    BannerView bannerView;

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
        persenter=new VideoHomePresenter(this);
        recycleView = getViewById(R.id.goodsGridview);
        persenter.init();
        toolbar=getViewById(R.id.toolbar);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "本地视频", 0);
        TopBarBuilder.buildOnlyText(toolbar, getActivity(), NavigationBar.Location.RIGHT_FIRST, "选择", 0);

    }
    @Override
    public void initListener() {
        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {
                if (location== NavigationBar.Location.RIGHT_FIRST) {
                    persenter.setCanEdit();
//                    Bundle bundle=new Bundle();
////                    bundle.putSerializable("goods", (Serializable) goods);
//                    bundle.putSerializable("type",AddFragment.TYPE_Admin_ADD_GOODS);
//                    FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new AddFragment(), bundle);
                }

            }
        });
//        noticeTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BusinessBroadcastUtils.sendBroadcast(getContext(), BusinessBroadcastUtils.TYPE_RELOGIN, null);
//
//            }
//        });

    }

	@Override
	public void getBroadcastReceiverMessage(String type, Object mode) {

//         if(type.equals(BusinessBroadcastUtils.TYPE_LOGIN_SUCESS)){
//           persenter.list();
//            persenter.reInitToolBar();
//            noticeTv.setVisibility(View.GONE);
//        }
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
                OpenFileUtils.openVideo(getActivity(),imgBean.getData());
            }
        });
    }


//    @Override
//    public void addLayoutHelper(LayoutHelper helper,boolean isRefresh) {
//        recycleView.addLayoutHelper(helper,isRefresh);
//    }
}
