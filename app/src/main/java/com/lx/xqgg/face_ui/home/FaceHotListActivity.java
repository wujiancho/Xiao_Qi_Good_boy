package com.lx.xqgg.face_ui.home;

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
import com.lx.xqgg.ui.home.bean.HotMsgBean;
import com.lx.xqgg.ui.hot.adapter.HotMsgAdapter;
import com.lx.xqgg.ui.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FaceHotListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
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

    private List<HotMsgBean.RecordsBean> list = new ArrayList<>();
    private HotMsgAdapter hotMsgAdapter;
    private int page = 1;
    private int pagesize = 10;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_swipe_layout;
    }

    @Override
    protected void initView() {
        tvTitle.setText("热点动态");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        hotMsgAdapter = new HotMsgAdapter(list);
        recyclerView.setAdapter(hotMsgAdapter);
        swipeLayout.setOnRefreshListener(this);
        hotMsgAdapter.setEmptyView(R.layout.layout_empty, recyclerView);
        hotMsgAdapter.setOnLoadMoreListener(this, recyclerView);
        hotMsgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                addSubscribe(ApiManage.getInstance().getMainApi().getNoticeById(list.get(position).getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseSubscriber<BaseData<HotMsgBean.RecordsBean>>(mContext, null) {
                            @Override
                            public void onNext(BaseData<HotMsgBean.RecordsBean> recordsBeanBaseData) {
                                Log.e("zlz", new Gson().toJson(recordsBeanBaseData));
                                if (recordsBeanBaseData.isSuccess()) {
                                    WebViewActivity.open(new WebViewActivity.Builder()
                                            .setContext(mContext)
                                            .setAutoTitle(false)
                                            .setIsFwb(true)
                                            .setTitle(recordsBeanBaseData.getData().getTitle())
                                            .setUrl(recordsBeanBaseData.getData().getContent()));
                                } else {
                                    showDialog(recordsBeanBaseData.getMessage());
                                }
                            }
                        }));
            }
        });
    }

    @Override
    protected void initData() {
        page=1;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("title", "");
        paramsMap.put("appid", Constans.APPID);
        paramsMap.put("type", Constans.FACE);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", pagesize);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getHotNotice(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<HotMsgBean>(mContext, null) {
                    @Override
                    public void onNext(HotMsgBean hotMsgBean) {
                        Log.e("zlz", new Gson().toJson(hotMsgBean));
                        if (hotMsgBean.isIsSuccess()) {
                            if (hotMsgBean.getRecords() != null) {
                                list.clear();
                                list.addAll(hotMsgBean.getRecords());
                                if (list.size() < pagesize) {
                                    hotMsgAdapter.disableLoadMoreIfNotFullPage();
                                }
                                hotMsgAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.toString());
                    }
                }));
    }

    @Override
    public void onRefresh() {
        page=1;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("title", "");
        paramsMap.put("appid", Constans.APPID);
        paramsMap.put("type", Constans.FACE);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", pagesize);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getHotNotice(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<HotMsgBean>(mContext, null) {
                    @Override
                    public void onNext(HotMsgBean hotMsgBean) {
                        if (hotMsgBean.isIsSuccess()) {
                            if (hotMsgBean.getRecords() != null) {
                                list.clear();
                                list.addAll(hotMsgBean.getRecords());
                                if (list.size() < 15) {
                                    hotMsgAdapter.disableLoadMoreIfNotFullPage();
                                }
                                hotMsgAdapter.notifyDataSetChanged();
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
        paramsMap.put("title", "");
        paramsMap.put("appid", Constans.APPID);
        paramsMap.put("type", Constans.FACE);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", pagesize);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getHotNotice(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<HotMsgBean>(mContext, null) {
                    @Override
                    public void onNext(HotMsgBean hotMsgBean) {
                        if (hotMsgBean.isIsSuccess()) {
                            if (hotMsgBean.getRecords() == null || hotMsgBean.getRecords().size() == 0) {
                                hotMsgAdapter.loadMoreEnd();
                            } else {
                                hotMsgAdapter.addData(hotMsgBean.getRecords());
                                hotMsgAdapter.notifyDataSetChanged();
                                hotMsgAdapter.loadMoreComplete();
                            }
                        } else {
                            hotMsgAdapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        hotMsgAdapter.loadMoreFail();
                    }
                }));
    }

    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }
}
