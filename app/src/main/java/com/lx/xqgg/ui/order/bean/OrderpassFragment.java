package com.lx.xqgg.ui.order.bean;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseFragment;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.order.adapter.OrderpassAdapter;
import com.lx.xqgg.util.DensityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OrderpassFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.allsheng)
    RadioButton allsheng;
    @BindView(R.id.shengok)
    RadioButton shengok;
    @BindView(R.id.shengno)
    RadioButton shengno;
    @BindView(R.id.allitem)
    RadioGroup allitem;
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private String search_words;
    private String status;
    private String createTimeStart;
    private String createTimeEnd;
    private int userid = -1;
    private int page = 1;
    private List<OrderBean.RecordsBean> list;
    private OrderpassAdapter orderpassAdapter;
    private int userId = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            search_words = getArguments().getString("search_words");
            status = getArguments().getString("status");
            createTimeStart = getArguments().getString("createTimeStart");
            createTimeEnd = getArguments().getString("createTimeEnd");
            userid = getArguments().getInt("userId");
        }
        getOrderList();
    }

    @SuppressLint("ResourceType")
    private void setRaidBtnAttribute(final RadioButton codeBtn, String btnContent, String id) {
        if (null == codeBtn) {
            return;
        }
        codeBtn.setBackgroundResource(R.drawable.rb_user_selector);
        codeBtn.setTextColor(mContext.getResources().getColorStateList(R.drawable.txt_selector));
        codeBtn.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        //codeBtn.setTextSize( ( textSize > 16 )?textSize:24 );
        codeBtn.setId(Integer.parseInt(id));
        codeBtn.setText(btnContent);
        codeBtn.setPadding(DensityUtils.dip2px(mContext, 12), DensityUtils.dip2px(mContext, 4), DensityUtils.dip2px(mContext, 12), DensityUtils.dip2px(mContext, 4));

        codeBtn.setGravity(Gravity.CENTER);
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //刷新列表
                if (codeBtn.getText().equals("全部")) {
                    userId = -1;
                } else {
                    userId = codeBtn.getId();
                }
                Log.e("test", userId + "");

            }
        });
        //DensityUtilHelps.Dp2Px(this,40)
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        codeBtn.setLayoutParams(rlp);
    }

    public void update(String search_words, String status, String createTimeStart, String createTimeEnd, int userid) {
        this.search_words = search_words;
        this.status = status;
        this.createTimeStart = createTimeStart;
        this.createTimeEnd = createTimeEnd;
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundle.putString("search_words", search_words);
            bundle.putString("status", status);
            bundle.putString("createTimeStart", createTimeStart);
            bundle.putString("createTimeEnd", createTimeEnd);
            bundle.putInt("userId", userid);
        }
        page = 1;
    }

    @Override
    public void onRefresh() {
        page = 1;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("search_words", search_words);
        paramsMap.put("status", status);
        paramsMap.put("createTimeStart", createTimeStart);
        paramsMap.put("createTimeEnd", createTimeEnd);
        paramsMap.put("userId", userid == -1 ? null : userid);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", 10);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getMyLoanOrder(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<OrderBean>(mContext, null) {
                    @Override
                    public void onNext(OrderBean orderBean) {
                        Log.e("zlz", new Gson().toJson(orderBean));
                        list = new ArrayList<>();
                        orderpassAdapter = new OrderpassAdapter(list);
                        rvProduct.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        rvProduct.setAdapter(orderpassAdapter);
                        orderpassAdapter.setEmptyView(R.layout.layout_empty, rvProduct);
                        orderpassAdapter.setOnLoadMoreListener(OrderpassFragment.this::onLoadMoreRequested);
                        orderpassAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                            }
                        });
                        orderpassAdapter.bindToRecyclerView(rvProduct);
                        refreshLayout.setOnRefreshListener(OrderpassFragment.this::onRefresh);
                        if (orderBean.isIsSuccess()) {
                            if (orderBean.getRecords() != null && orderBean.getRecords().size() > 0) {
                                list.clear();
                                list.addAll(orderBean.getRecords());
                                if (list.size() < 10) {
                                    if (list.size() < 3) {
                                        orderpassAdapter.disableLoadMoreIfNotFullPage();
                                    } else {
//                                    orderAdapter.disableLoadMoreIfNotFullPage();
                                        orderpassAdapter.loadMoreEnd();
                                    }
                                }
                                orderpassAdapter.notifyDataSetChanged();
                            }
                        } else {
                            toast(orderBean.getMessage());
                        }
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.toString());
                        list = new ArrayList<>();
                        orderpassAdapter = new OrderpassAdapter(list);
                        rvProduct.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        rvProduct.setAdapter(orderpassAdapter);
                        orderpassAdapter.setEmptyView(R.layout.layout_empty, rvProduct);
                        orderpassAdapter.setOnLoadMoreListener(OrderpassFragment.this::onLoadMoreRequested);
                        orderpassAdapter.bindToRecyclerView(rvProduct);
                        refreshLayout.setOnRefreshListener(OrderpassFragment.this::onRefresh);
                        refreshLayout.setRefreshing(false);
                    }
                }));
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("search_words", search_words);
        paramsMap.put("status", status);
        paramsMap.put("createTimeStart", createTimeStart);
        paramsMap.put("createTimeEnd", createTimeEnd);
        paramsMap.put("userId", userid == -1 ? null : userid);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", 10);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getMyLoanOrder(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<OrderBean>(mContext, null) {
                    @Override
                    public void onNext(OrderBean productBean) {
                        Log.e("test", "上拉" + page + new Gson().toJson(productBean));
                        if (productBean.isIsSuccess()) {
                            if (productBean.getRecords() == null || productBean.getRecords().size() == 0) {
                                orderpassAdapter.loadMoreEnd();
                            } else {
                                orderpassAdapter.addData(productBean.getRecords());
//                            list.addAll();
                                orderpassAdapter.notifyDataSetChanged();
                                orderpassAdapter.loadMoreComplete();
                            }
                        } else {
                            toast(productBean.getMessage());
                            orderpassAdapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Log.e("test", t.toString() + page);
                        orderpassAdapter.loadMoreFail();
                    }
                }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.orderpass_list;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    private void getOrderList() {
        page = 1;
        Log.e("zlz", "getData" + status);
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("search_words", search_words);
        paramsMap.put("status", status);
        paramsMap.put("createTimeStart", createTimeStart);
        paramsMap.put("createTimeEnd", createTimeEnd);
        paramsMap.put("userId", userid == -1 ? null : userid);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", 10);
        Log.e("test", userid + "");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getMyLoanOrder(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<OrderBean>(mContext, null) {
                    @Override
                    public void onNext(OrderBean orderBean) {
                        Log.e("zlz", new Gson().toJson(orderBean));
                        list = new ArrayList<>();
                        orderpassAdapter = new OrderpassAdapter(list);
                        rvProduct.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        rvProduct.setAdapter(orderpassAdapter);
                        orderpassAdapter.setEmptyView(R.layout.layout_empty, rvProduct);
                        orderpassAdapter.setOnLoadMoreListener(OrderpassFragment.this::onLoadMoreRequested);
                        orderpassAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                            }
                        });
                        orderpassAdapter.bindToRecyclerView(rvProduct);
                        refreshLayout.setOnRefreshListener(OrderpassFragment.this::onRefresh);
                        if (orderBean.isIsSuccess()) {
                            if (orderBean.getRecords() != null && orderBean.getRecords().size() > 0) {
                                list.clear();
                                list.addAll(orderBean.getRecords());
                                if (list.size() < 10) {
                                    if (list.size() < 3) {
                                        orderpassAdapter.disableLoadMoreIfNotFullPage();
                                    } else {
//                                    orderAdapter.disableLoadMoreIfNotFullPage();
                                        orderpassAdapter.loadMoreEnd();
                                    }
                                }
                                orderpassAdapter.notifyDataSetChanged();
                            }
                        } else {
                            toast(orderBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.toString());
                        list = new ArrayList<>();
                        orderpassAdapter = new OrderpassAdapter(list);
                        rvProduct.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        rvProduct.setAdapter(orderpassAdapter);
                        orderpassAdapter.setEmptyView(R.layout.layout_empty, rvProduct);
                        orderpassAdapter.setOnLoadMoreListener(OrderpassFragment.this::onLoadMoreRequested);
                        orderpassAdapter.bindToRecyclerView(rvProduct);
                        refreshLayout.setOnRefreshListener(OrderpassFragment.this::onRefresh);
                    }
                }));
    }

    public static OrderpassFragment newInstance(String search_words, String status, String createTimeStart, String createTimeEnd, int userid) {
        OrderpassFragment orderpassFragment = new OrderpassFragment();
        Bundle bundle = new Bundle();
        bundle.putString("search_words", search_words);
        bundle.putString("status", status);
        bundle.putString("createTimeStart", createTimeStart);
        bundle.putString("createTimeEnd", createTimeEnd);
        bundle.putInt("userId", userid);
        orderpassFragment.setArguments(bundle);
        Log.e("zlz", "instance" + status);
        return orderpassFragment;
    }


    @OnClick({R.id.allsheng, R.id.shengok, R.id.shengno})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.allsheng:
                toast("全部");
                break;
            case R.id.shengok:
                break;
            case R.id.shengno:
                break;
        }
    }
}
