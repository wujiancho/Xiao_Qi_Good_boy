package com.lx.xqgg.ui.order;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.order.adapter.OrderAdapter;
import com.lx.xqgg.ui.order.bean.OrderBean;

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

//订单分类
public class OrderTypepassFragment extends OrderTypeFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.allitem)
    RadioButton allitem;
    @BindView(R.id.shengok)
    RadioButton shengok;
    @BindView(R.id.shengno)
    RadioButton shengno;

    private String search_words;
    private String status;
    private String createTimeStart;
    private String createTimeEnd;
    private int userid = -1;
    private int page = 1;
    private  boolean resch=false;
    private List<OrderBean.RecordsBean> list =new ArrayList<>();
    private List<OrderBean.RecordsBean> passlist=new ArrayList<>();
    private List<OrderBean.RecordsBean> nopasslist=new ArrayList<>();
    private OrderAdapter orderAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_type_pass;
    }

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

    @Override
    protected void initView() {
        /*allitem.setOnCheckedChangeListener(this);
        shengok.setOnCheckedChangeListener(this);
        shengno.setOnCheckedChangeListener(this);*/
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
                        Log.e("zlz2", new Gson().toJson(orderBean));
                        list =new ArrayList<>();
                        orderAdapter = new OrderAdapter(list);
                        rvProduct.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        rvProduct.setAdapter(orderAdapter);
                        orderAdapter.setEmptyView(R.layout.layout_empty, rvProduct);
                        orderAdapter.setOnLoadMoreListener(OrderTypepassFragment.this::onLoadMoreRequested);
                        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                            }
                        });
                        orderAdapter.bindToRecyclerView(rvProduct);
                        refreshLayout.setOnRefreshListener(OrderTypepassFragment.this::onRefresh);
                        if (orderBean.isIsSuccess()) {
                            if (orderBean.getRecords() != null && orderBean.getRecords().size() > 0) {
                                list.clear();
                                list.addAll(orderBean.getRecords());
                                Order();
                                if (list.size() < 10) {
                                    if (list.size() < 3) {
                                        orderAdapter.disableLoadMoreIfNotFullPage();
                                    } else {
//                                    orderAdapter.disableLoadMoreIfNotFullPage();
                                        orderAdapter.loadMoreEnd();
                                    }
                                }
                                orderAdapter.notifyDataSetChanged();
                            }
                        } else {
                            toast(orderBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.toString());
                        orderAdapter = new OrderAdapter(list);
                        rvProduct.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        rvProduct.setAdapter(orderAdapter);
                        orderAdapter.setEmptyView(R.layout.layout_empty, rvProduct);
                        orderAdapter.setOnLoadMoreListener(OrderTypepassFragment.this::onLoadMoreRequested);
                        orderAdapter.bindToRecyclerView(rvProduct);
                        refreshLayout.setOnRefreshListener(OrderTypepassFragment.this::onRefresh);
                    }
                }));
    }

    public static OrderTypepassFragment newInstance(String search_words, String status, String createTimeStart, String createTimeEnd, int userid) {
        OrderTypepassFragment orderpassTypeFragment = new OrderTypepassFragment();
        Bundle bundle = new Bundle();
        bundle.putString("search_words", search_words);
        bundle.putString("status", status);
        bundle.putString("createTimeStart", createTimeStart);
        bundle.putString("createTimeEnd", createTimeEnd);
        bundle.putInt("userId", userid);
        orderpassTypeFragment.setArguments(bundle);
        Log.e("zlz", "instance" + status);
        return orderpassTypeFragment;
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
       if (resch=true){
            refreshLayout.setRefreshing(false);
            return;
        }
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
                        list=new ArrayList<>();
                        orderAdapter = new OrderAdapter(list);
                        rvProduct.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        rvProduct.setAdapter(orderAdapter);
                        orderAdapter.setEmptyView(R.layout.layout_empty, rvProduct);
                        orderAdapter.setOnLoadMoreListener(OrderTypepassFragment.this::onLoadMoreRequested);
                        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                            }
                        });
                        orderAdapter.bindToRecyclerView(rvProduct);
                        refreshLayout.setOnRefreshListener(OrderTypepassFragment.this::onRefresh);
                        if (orderBean.isIsSuccess()) {
                            if (orderBean.getRecords() != null && orderBean.getRecords().size() > 0) {
                                list.clear();
                                list.addAll(orderBean.getRecords());

                                if (list.size() < 10) {
                                    if (list.size() < 3) {
                                        orderAdapter.disableLoadMoreIfNotFullPage();
                                    } else {
//                                    orderAdapter.disableLoadMoreIfNotFullPage();
                                        orderAdapter.loadMoreEnd();
                                    }
                                }
                                Order();
                                orderAdapter.notifyDataSetChanged();

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
                        orderAdapter = new OrderAdapter(list);
                        rvProduct.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        rvProduct.setAdapter(orderAdapter);
                        orderAdapter.setEmptyView(R.layout.layout_empty, rvProduct);
                        orderAdapter.setOnLoadMoreListener(OrderTypepassFragment.this::onLoadMoreRequested);
                        orderAdapter.bindToRecyclerView(rvProduct);
                        refreshLayout.setOnRefreshListener(OrderTypepassFragment.this::onRefresh);
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
                                orderAdapter.loadMoreEnd();
                            } else {
                                orderAdapter.addData(productBean.getRecords());
//                            list.addAll();
                                orderAdapter.setNewData(list);
                                orderAdapter.notifyDataSetChanged();
                                orderAdapter.loadMoreComplete();
                            }
                        } else {
                            toast(productBean.getMessage());
                            orderAdapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Log.e("test", t.toString() + page);
                        orderAdapter.loadMoreFail();
                    }
                }));
    }

    @OnClick({R.id.allitem, R.id.shengok, R.id.shengno})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.allitem:
                orderAdapter.setNewData(list);
                orderAdapter.notifyDataSetChanged();
                resch=false;
                break;
            case R.id.shengok:
                orderAdapter.setNewData(passlist);
                orderAdapter.notifyDataSetChanged();
                resch=true;
                break;
            case R.id.shengno:
                orderAdapter.setNewData(nopasslist);
                orderAdapter.notifyDataSetChanged();
                resch=true;
                break;
        }
    }
//设置筛选订单适配器
    private void Order(){
        passlist.clear();
        nopasslist.clear();
        for (int i = 0; i < list.size(); i++) {
            OrderBean.RecordsBean  recordsBean = list.get(i);
            String status1=recordsBean.getStatus();
            if ("pass".equals(status1)){
                passlist.add(recordsBean);
            }else  if("nopass".equals(status1)){
                nopasslist.add(recordsBean);
            }
        }
    }

  /*  @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            switch (buttonView.getId()) {
                case R.id.allitem:
                    orderAdapter.setNewData(list);
                    orderAdapter.notifyDataSetChanged();
                    break;
                case R.id.shengok:
                    orderAdapter.setNewData(passlist);
                    orderAdapter.notifyDataSetChanged();

                    break;
                case R.id.shengno:
                    orderAdapter.setNewData(nopasslist);
                    orderAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }*/
}
