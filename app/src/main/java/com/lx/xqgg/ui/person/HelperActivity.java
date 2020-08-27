package com.lx.xqgg.ui.person;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.ui.person.adapter.HelperAdapter;
import com.lx.xqgg.ui.person.bean.HelperBean;
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

public class HelperActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    private List<HelperBean.RecordsBean> list = new ArrayList<>();
    private HelperAdapter helperAdapter;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void initView() {
        tvTitle.setText("帮助中心");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        helperAdapter = new HelperAdapter(list);
        recyclerView.setAdapter(helperAdapter);
        swipeLayout.setOnRefreshListener(this);
        helperAdapter.setOnLoadMoreListener(this, recyclerView);
        helperAdapter.setEmptyView(R.layout.layout_empty, recyclerView);
        helperAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WebViewActivity.open(new WebViewActivity.Builder()
                        .setContext(mContext)
                        .setAutoTitle(false)
                        .setIsFwb(false)
                        .setTitle(list.get(position).getTitle())
                        .setNeedShare(true)
                        .setUrl(Config.URL+"view/helpInfo.html?id="+list.get(position).getId()),false);
            }
        });

//        etSearch.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
//                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
//                    //处理事件
//                    HashMap<String, Object> paramsMap = new HashMap<>();
//                    paramsMap.put("title", etSearch.getText().toString());
//                    paramsMap.put("appId", Constans.APPID);
//                    paramsMap.put("page", 1);
//                    paramsMap.put("pageSize", 15);
//                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
//                    addSubscribe(ApiManage.getInstance().getMainApi().getHelpList(body)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribeWith(new BaseSubscriber<HelperBean>(mContext, null) {
//                                @Override
//                                public void onNext(HelperBean helperBean) {
//                                    if (helperBean.isIsSuccess()) {
//                                        if (helperBean.getRecords() != null) {
//                                            list.clear();
//                                            list.addAll(helperBean.getRecords());
//                                            if (list.size() < 15) {
//                                                helperAdapter.disableLoadMoreIfNotFullPage();
//                                            }
//                                            helperAdapter.notifyDataSetChanged();
//                                        }
//                                    }
//                                }
//                            }));
//                    return true;
//                }
//                return false;
//            }
//        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                page=1;
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("title", etSearch.getText().toString());
                paramsMap.put("appId", Constans.APPID);
                paramsMap.put("page", page);
                paramsMap.put("pageSize", 15);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                addSubscribe(ApiManage.getInstance().getMainApi().getHelpList(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseSubscriber<HelperBean>(mContext, null) {
                            @Override
                            public void onNext(HelperBean helperBean) {
                                if (helperBean.isIsSuccess()) {
                                    if (helperBean.getRecords() != null) {
                                        list.clear();
                                        list.addAll(helperBean.getRecords());
                                        if (list.size() < 15) {
                                            helperAdapter.disableLoadMoreIfNotFullPage();
                                        }
                                        helperAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }));
            }
        });
    }

    @Override
    protected void initData() {
        getList("");
    }

    private void getList(String title) {
        page=1;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("title", title);
        paramsMap.put("appId", Constans.APPID);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", 15);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getHelpList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<HelperBean>(mContext, null) {
                    @Override
                    public void onNext(HelperBean helperBean) {
                        Log.e("zlz",new Gson().toJson(helperBean));
                        if (helperBean.isIsSuccess()) {
                            if (helperBean.getRecords() != null) {
                                list.clear();
                                list.addAll(helperBean.getRecords());
                                if (list.size() < 15) {
                                    helperAdapter.disableLoadMoreIfNotFullPage();
                                }
                                helperAdapter.notifyDataSetChanged();
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
        paramsMap.put("title", "");
        paramsMap.put("appId", Constans.APPID);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", 15);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getHelpList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<HelperBean>(mContext, null) {
                    @Override
                    public void onNext(HelperBean helperBean) {
                        if (helperBean.isIsSuccess()) {
                            if (helperBean.getRecords() != null) {
                                list.clear();
                                list.addAll(helperBean.getRecords());
                                if (list.size() < 15) {
                                    helperAdapter.disableLoadMoreIfNotFullPage();
                                }
                                helperAdapter.loadMoreComplete();
                                helperAdapter.notifyDataSetChanged();
                                swipeLayout.setRefreshing(false);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        swipeLayout.setRefreshing(false);
                    }
                }));
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("title", "");
        paramsMap.put("appId", Constans.APPID);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", 15);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getHelpList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<HelperBean>(mContext, null) {
                    @Override
                    public void onNext(HelperBean helperBean) {
                        if (helperBean.isIsSuccess()) {
                            if (helperBean.getRecords() == null || helperBean.getRecords().size() == 0) {
                                helperAdapter.loadMoreEnd();
                            } else {
                                helperAdapter.addData(helperBean.getRecords());
                                helperAdapter.notifyDataSetChanged();
                                helperAdapter.loadMoreComplete();
                            }
                        } else {
                            helperAdapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        helperAdapter.loadMoreFail();
                    }
                }));
    }
}
