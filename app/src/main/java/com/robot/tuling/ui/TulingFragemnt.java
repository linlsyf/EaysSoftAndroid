package com.robot.tuling.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.business.ServiceCallBack;
import com.business.bean.ResponseMsgData;
import com.business.bean.ShopRecorder;
import com.core.base.BaseFragment;
import com.core.utils.FragmentHelper;
import com.easysoft.costumes.R;
import com.robot.tuling.adapter.ChatMessageAdapter;
import com.robot.tuling.constant.TulingParams;
import com.robot.tuling.entity.MessageEntity;
import com.robot.tuling.entity.NewsEntity;
import com.robot.tuling.util.IsNullOrEmpty;
import com.robot.tuling.util.KeyBoardUtil;
import com.robot.tuling.util.TimeUtil;
import com.ui.HttpService;
import com.ui.car.MyCallback;
import com.view.toolbar.NavigationBar;
import com.view.toolbar.NavigationBarListener;
import com.view.toolbar.TopBarBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TulingFragemnt extends BaseFragment {


    @Bind(R.id.lv_message)
    ListView lvMessage;
    @Bind(R.id.iv_send_msg)
    ImageView ivSendMsg;
    @Bind(R.id.et_msg)
    EditText etMsg;


    private List<MessageEntity> msgList = new ArrayList<>();
    private ChatMessageAdapter msgAdapter;
    private NavigationBar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.activity_tuling, null);

        setRootView(rootView);
        return rootView;

    }

    @Override
    public void initFragment() {
        ButterKnife.bind(getActivity());
        initView();
        initData();
        initListener();
        requestApiByRetrofit_RxJava("新闻");
    }

    public void initData() {
        if (msgList.size() == 0) {
            MessageEntity entity = new MessageEntity(ChatMessageAdapter.TYPE_LEFT, TimeUtil.getCurrentTimeMillis());
            entity.setText("你好！俺是图灵机器人！\n咱俩聊点什么呢？\n你有什么要问的么？");
            msgList.add(entity);
        }
        msgAdapter = new ChatMessageAdapter(getActivity(), msgList);
        lvMessage.setAdapter(msgAdapter);
        lvMessage.setSelection(msgAdapter.getCount());
    }

    private void initView() {
        lvMessage=getViewById(R.id.lv_message);
        toolbar=getViewById(R.id.toolbar);
        ivSendMsg=getViewById(R.id.iv_send_msg);
        etMsg=getViewById(R.id.et_msg);
        TopBarBuilder.buildCenterTextTitle(toolbar, getActivity(), "小Q", 0);
        toolbar.setNavigationBarListener(new NavigationBarListener() {

            @Override
            public void onClick(ViewGroup containView, NavigationBar.Location location) {
                if (location== NavigationBar.Location.LEFT_FIRST) {
                    FragmentHelper.popBackFragment(getActivity());
                }

            }
        });
    }

    public void initListener() {

         ivSendMsg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 sendMessage();
             }
         });

        lvMessage.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                KeyBoardUtil.hideKeyboard(getActivity());
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    @Override
    public void getBroadcastReceiverMessage(String type, Object mode) {

    }



    // 给Turing发送问题
    public void sendMessage() {
        String msg = etMsg.getText().toString().trim();

        if (!IsNullOrEmpty.isEmpty(msg)) {
            MessageEntity entity = new MessageEntity(ChatMessageAdapter.TYPE_RIGHT, TimeUtil.getCurrentTimeMillis(), msg);
            msgList.add(entity);
            msgAdapter.notifyDataSetChanged();
            etMsg.setText("");


            requestApiByRetrofit_RxJava(msg);
        }
    }


    // 请求图灵API接口，获得问答信息
    private void requestApiByRetrofit_RxJava(final String info) {
        HttpService 		service=new HttpService();
        final String url = TulingParams.TULING_URL+"?key="+TulingParams.TULING_KEY+"&info="+info;
        service.request( url , "",new MyCallback(new MyCallback.IResponse() {
            @Override
            public void onFailure(ServiceCallBack serviceCallBack) {
            }

            @Override
            public void onResponse(ServiceCallBack serviceCallBack) {
                if (serviceCallBack.isSucess()){
                    ResponseMsgData data =JSON.parseObject(serviceCallBack.getResponseMsg().getData().toString(),
                            ResponseMsgData.class);
                    List<NewsEntity> orderList =  JSON.parseArray(data.getList().toString(), NewsEntity.class) ;

                    final MessageEntity  messageEntity=new MessageEntity();
                    messageEntity.setCode(data.getCode());

                    messageEntity.setText(info);
                    messageEntity.setList(orderList);
                   getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handleResponseMessage(messageEntity);

                        }
                    });
                }

            }
        }).setOutside(true));
    }


    // 处理获得到的问答信息
    private void handleResponseMessage(MessageEntity entity) {
        if (entity == null) return;

        entity.setTime(TimeUtil.getCurrentTimeMillis());
        entity.setType(ChatMessageAdapter.TYPE_LEFT);

        switch (entity.getCode()) {
            case TulingParams.TulingCode.URL:
                entity.setText(entity.getText() + "，点击网址查看：" + entity.getUrl());
                break;
            case TulingParams.TulingCode.NEWS:
                entity.setText(entity.getText() + "，点击查看");
                break;
        }

        msgList.add(entity);
        msgAdapter.notifyDataSetChanged();
    }

}
