package com.robot.tuling.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.business.ServiceCallBack;
import com.business.bean.ResponseMsgData;
import com.business.bean.ShopRecorder;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TulingActivity extends BaseActivity {

//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @Bind(R.id.lv_message)
    ListView lvMessage;
    @Bind(R.id.iv_send_msg)
    ImageView ivSendMsg;
    @Bind(R.id.et_msg)
    EditText etMsg;
    @Bind(R.id.rl_msg)
    RelativeLayout rlMsg;

    private List<MessageEntity> msgList = new ArrayList<>();
    private ChatMessageAdapter msgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuling);
        ButterKnife.bind(this);

//        new FirUpdater(this, "3c57fb226edf7facf821501e4eba08d2", "5704953c00fc74127000000a").checkVersion();

        initData();
        initView();
        initListener();
        requestApiByRetrofit_RxJava("新闻");
    }

    private void initData() {
        if (msgList.size() == 0) {
            MessageEntity entity = new MessageEntity(ChatMessageAdapter.TYPE_LEFT, TimeUtil.getCurrentTimeMillis());
            entity.setText("你好！俺是图灵机器人！\n咱俩聊点什么呢？\n你有什么要问的么？");
            msgList.add(entity);
        }
        msgAdapter = new ChatMessageAdapter(this, msgList);
        lvMessage.setAdapter(msgAdapter);
        lvMessage.setSelection(msgAdapter.getCount());
    }

    private void initView() {
//        toolbar.setTitle(getString(R.string.app_name));
//        setSupportActionBar(toolbar);
    }

    private void initListener() {
//        ivSendMsg.setOnClickListener(v -> sendMessage());
//        ivSendMsg.setOnClickListener(v -> funcDemo());
         ivSendMsg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 sendMessage();
             }
         });

        lvMessage.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                KeyBoardUtil.hideKeyboard(mActivity);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//        switch (item.getItemId()) {
//            case R.id.action_about:
//                NavigateManager.gotoAboutActivity(mContext);
//                return true;
//            default:
//                return false;
//        }
//    }

    // 给Turing发送问题
    public void sendMessage() {
        String msg = etMsg.getText().toString().trim();

        if (!IsNullOrEmpty.isEmpty(msg)) {
            MessageEntity entity = new MessageEntity(ChatMessageAdapter.TYPE_RIGHT, TimeUtil.getCurrentTimeMillis(), msg);
            msgList.add(entity);
            msgAdapter.notifyDataSetChanged();
            etMsg.setText("");

            // 仅使用 Retrofit 请求接口
//            requestApiByRetrofit(msg);

            // 使用 Retrofit 和 RxJava 请求接口
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
//                    List<NewsEntity> orderList =  JSON.parseArray(data.getData()
//                            .toString(), NewsEntity.class);
                    final MessageEntity  messageEntity=new MessageEntity();
                    messageEntity.setCode(data.getCode());
//                    messageEntity.setCode(TulingParams.TulingCode.NEWS);
//                    messageEntity.setType(TulingParams.TulingCode.NEWS);
                    messageEntity.setText(info);
                    messageEntity.setList(orderList);
                    TulingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handleResponseMessage(messageEntity);

                        }
                    });
                }

//                ilogInView.showToast("登录成功");
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
