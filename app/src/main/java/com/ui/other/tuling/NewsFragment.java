package com.ui.other.tuling;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.business.ServiceCallBack;
import com.business.bean.ResponseMsgData;
import com.core.base.BaseFragment;
import com.easysoft.costumes.R;
import com.easysoft.utils.lib.system.FragmentHelper;
import com.easysoft.widget.toolbar.NavigationBar;
import com.easysoft.widget.toolbar.TopBarBuilder;
import com.ui.HttpService;
import com.ui.car.MyCallback;
import com.ui.other.tuling.adapter.NewsAdapter;
import com.ui.other.tuling.constant.TulingParams;
import com.ui.other.tuling.entity.MessageEntity;
import com.ui.other.tuling.entity.NewsEntity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunfusheng on 2015/2/5.
 */
public class NewsFragment extends BaseFragment  {

//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
//    @Bind(R.id.xlv_listView)
    ListView xlvListView;

    private List<NewsEntity> newsList=new ArrayList<>();
    private NewsAdapter newsAdapter;
    private NavigationBar toolbar;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_news;
    }

    @Override
    public void initFragment() {

      initUIView();
        initData();
        initView();
    }

    @Override
    public void initUIView() {
      xlvListView=getViewById(R.id.xlv_listView);
        toolbar=getViewById(R.id.toolbar);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "新闻", 0);

    }

    public void initData() {

         if (null!=getArguments()){
             MessageEntity messageEntity = (MessageEntity) getArguments().getSerializable("messageEntity");
             if (messageEntity != null && messageEntity.getList() != null && messageEntity.getList().size() > 0) {
                 newsList = messageEntity.getList();
             }
         }

        else {
             requestApiByRetrofit_RxJava("新闻");

//            FragmentHelper.popBackFragment(getActivity());
        }
    }

    // 请求图灵API接口，获得问答信息
    private void requestApiByRetrofit_RxJava(final String info) {
        HttpService service=new HttpService();
        final String url = TulingParams.TULING_URL+"?key="+TulingParams.TULING_KEY+"&info="+info;
//        url= ServerUrl.getFinalUrl(url,json);

        service.request( url , new MyCallback(new MyCallback.IResponse() {
            @Override
            public void onFailure(ServiceCallBack serviceCallBack) {
            }

            @Override
            public void onResponse(ServiceCallBack serviceCallBack) {
                if (serviceCallBack.isSucess()){
                    ResponseMsgData data = JSON.parseObject(serviceCallBack.getResponseMsg().getData().toString(),
                            ResponseMsgData.class);
                    newsList =  JSON.parseArray(data.getList().toString(), NewsEntity.class) ;

//                    initView();


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initView();
                        }
                    });
                }

            }
        }).setOutside(true));
    }

    @Override
    public void getBroadcastReceiverMessage(String type, Object mode) {

    }

    private void initView() {
        initXlistView();
        newsAdapter = new NewsAdapter(getActivity(), newsList);
        xlvListView.setAdapter(newsAdapter);

    }

    private void initXlistView() {

        xlvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundleDetail=new Bundle();
                bundleDetail.putString("url",  newsList.get(position - 1).getDetailurl());
                FragmentHelper.showFrag(getActivity(), R.id.container_framelayout, new NewDetailFragment(), bundleDetail);

            }
        });
    }



    private void deleteNewsListItem(int position) {
        NewsEntity entity = newsList.get(position);
        newsList.remove(entity);
        newsAdapter.notifyDataSetChanged();
    }

}
