package com.lx.xqgg.ui.message;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.home.bean.HotMsgBean;
import com.lx.xqgg.ui.hot.adapter.HotMsgAdapter;
import com.lx.xqgg.ui.message.adapter.MessageAdapter;
import com.lx.xqgg.ui.message.bean.MessageBean;
import com.lx.xqgg.ui.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MessageActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,MessageAdapter.onDelListener {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    private int page = 1;
    private int pagesize = 10;
    private MessageAdapter messageAdapter;
    private List<MessageBean.RecordsBean> list=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_swipe_layout;
    }

    @Override
    protected void initView() {
        tvTitle.setText("消息中心");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        messageAdapter = new MessageAdapter(list);
        recyclerView.setAdapter(messageAdapter);
        swipeLayout.setOnRefreshListener(this);
        messageAdapter.setEmptyView(R.layout.layout_empty, recyclerView);
        messageAdapter.setOnDelListener(this);
        messageAdapter.setOnLoadMoreListener(this, recyclerView);
    }

    @Override
    protected void initData() {
        page = 1;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("page", page);
        paramsMap.put("pageSize", pagesize);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getMsgByPhone(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<MessageBean>(mContext, null) {
                    @Override
                    public void onNext(MessageBean messageBean) {
                        if (messageBean.isIsSuccess()) {
                            if (messageBean.getRecords() != null) {
                                list.clear();
                                list.addAll(messageBean.getRecords());
                                if (list.size() < pagesize) {
                                    messageAdapter.disableLoadMoreIfNotFullPage();
                                }
                                messageAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }));
    }


    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        page=1;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("page", page);
        paramsMap.put("pageSize", pagesize);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getMsgByPhone(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<MessageBean>(mContext, null) {
                    @Override
                    public void onNext(MessageBean messageBean) {
                        Log.e("zlz",new Gson().toJson(messageBean));
                        if (messageBean.isIsSuccess()) {
                            if (messageBean.getRecords() != null) {
                                list.clear();
                                list.addAll(messageBean.getRecords());
                                if (list.size() < pagesize) {
                                    messageAdapter.disableLoadMoreIfNotFullPage();
                                }
                                messageAdapter.notifyDataSetChanged();
                                swipeLayout.setRefreshing(false);
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.toString());
                        swipeLayout.setRefreshing(false);
                    }
                }));
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("page", page);
        paramsMap.put("pageSize", pagesize);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getMsgByPhone(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<MessageBean>(mContext, null) {
                    @Override
                    public void onNext(MessageBean messageBean) {
                        if (messageBean.isIsSuccess()) {
                            if (messageBean.getRecords() == null || messageBean.getRecords().size() == 0) {
                                messageAdapter.loadMoreEnd();
                            } else {
                                messageAdapter.addData(messageBean.getRecords());
                                messageAdapter.notifyDataSetChanged();
                                messageAdapter.loadMoreComplete();
                            }
                        } else {
                            messageAdapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.toString());
                        messageAdapter.loadMoreFail();
                    }
                }));
    }

    @Override
    public void onDel(MessageBean.RecordsBean bean) {
        addSubscribe(ApiManage.getInstance().getMainApi().delMsgById(bean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData>(mContext, null) {
                    @Override
                    public void onNext(BaseData baseData) {
                        if(baseData.isSuccess()){
                            list=messageAdapter.getData();
                            list.remove(bean);
                            messageAdapter.setNewData(list);
                            messageAdapter.notifyDataSetChanged();
                        }else {
                            toast(baseData.getMessage());
                        }
                    }
                }));

    }
}
