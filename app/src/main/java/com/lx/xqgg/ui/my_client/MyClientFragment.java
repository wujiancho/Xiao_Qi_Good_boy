package com.lx.xqgg.ui.my_client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseFragment;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.my_client.adapter.ClientAdapter;
import com.lx.xqgg.ui.my_client.bean.ServiceCustomerBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyClientFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, ClientAdapter.OnStarClickListener {
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;


    private String search_words;
    private String type;
    private int page = 1;

    private ClientAdapter clientAdapter;
    private List<ServiceCustomerBean.RecordsBean> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            search_words = getArguments().getString("search_words");
            type = getArguments().getString("type");
        }
        getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_type;
    }

    @Override
    protected void initView() {

    }

    private void getData() {
        page=1;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("search_words", search_words);
        paramsMap.put("type", type);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", 10);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getServiceCustomer(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<ServiceCustomerBean>(mContext, null) {
                    @Override
                    public void onNext(ServiceCustomerBean orderBean) {
                        Log.e("zlz", new Gson().toJson(orderBean));
                        list = new ArrayList<>();
                        clientAdapter = new ClientAdapter(list);
                        rvProduct.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        rvProduct.setAdapter(clientAdapter);
                        clientAdapter.setEmptyView(R.layout.layout_empty, rvProduct);
                        clientAdapter.setOnLoadMoreListener(MyClientFragment.this::onLoadMoreRequested);
                        clientAdapter.setStarListener(MyClientFragment.this::onClick);
                        clientAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                ServiceCustomerBean.RecordsBean bean = list.get(position);
                                Intent intent1 = new Intent(mContext, ClientDetailActivity.class);
                                intent1.putExtra("customerName", bean.getLink_man() + "");
                                intent1.putExtra("commpanyName", bean.getCompany() + "");
                                intent1.putExtra("phone", bean.getLink_phone() + "");
                                startActivity(intent1);
                            }
                        });
                        clientAdapter.bindToRecyclerView(rvProduct);
                        refreshLayout.setOnRefreshListener(MyClientFragment.this::onRefresh);
                        if (orderBean.isIsSuccess()) {
                            if (orderBean.getRecords() != null && orderBean.getRecords().size() > 0) {
                                list.clear();
                                list.addAll(orderBean.getRecords());
                                if (list.size() < 10) {
                                    clientAdapter.disableLoadMoreIfNotFullPage();
                                }
                                clientAdapter.notifyDataSetChanged();
                            }
                        }else {
                            toast(orderBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.toString());
                        list = new ArrayList<>();
                        clientAdapter = new ClientAdapter(list);
                        rvProduct.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        rvProduct.setAdapter(clientAdapter);
                        clientAdapter.setEmptyView(R.layout.layout_empty, rvProduct);
                        clientAdapter.setOnLoadMoreListener(MyClientFragment.this::onLoadMoreRequested);
                        clientAdapter.bindToRecyclerView(rvProduct);
                        refreshLayout.setOnRefreshListener(MyClientFragment.this::onRefresh);
                        clientAdapter.setStarListener(MyClientFragment.this::onClick);
                    }
                }));
    }

    @Override
    protected void initData() {

    }

    public void update(String search_words, String type) {
        this.search_words = search_words;
        this.type = type;
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundle.putString("search_words", search_words);
            bundle.putString("type", type);
        }
        page = 1;
    }

    public static MyClientFragment newInstance(String search_words, String type) {
        MyClientFragment myClientFragment = new MyClientFragment();
        Bundle bundle = new Bundle();
        bundle.putString("search_words", search_words);
        bundle.putString("type", type);
        myClientFragment.setArguments(bundle);
        return myClientFragment;
    }

    @Override
    public void onRefresh() {
        page=1;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("search_words", search_words);
        paramsMap.put("type", type);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", 10);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getServiceCustomer(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<ServiceCustomerBean>(mContext, null) {
                    @Override
                    public void onNext(ServiceCustomerBean orderBean) {
                        Log.e("zlz", "下啦" + new Gson().toJson(orderBean));
                        if (orderBean.isIsSuccess()) {
                            list.clear();
                            list.addAll(orderBean.getRecords());
                            clientAdapter.notifyDataSetChanged();
                            page = 1;
                            if (list.size() < 10) {
                                clientAdapter.disableLoadMoreIfNotFullPage();
                            }
                            clientAdapter.loadMoreComplete();
                        }else {
                            toast(orderBean.getMessage());
                        }
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.toString());
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
        paramsMap.put("type", type);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", 10);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getServiceCustomer(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<ServiceCustomerBean>(mContext, null) {
                    @Override
                    public void onNext(ServiceCustomerBean orderBean) {
                        Log.e("zlz", "上啦" + new Gson().toJson(orderBean));
                        if (orderBean.isIsSuccess()) {
                            if (orderBean.getRecords() == null || orderBean.getRecords().size() == 0) {
                                clientAdapter.loadMoreEnd();
                            } else {
                                clientAdapter.addData(orderBean.getRecords());
                                clientAdapter.notifyDataSetChanged();
                                clientAdapter.loadMoreComplete();
                            }
                        } else {
                            toast(orderBean.getMessage());
                            clientAdapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.toString());
                        clientAdapter.loadMoreFail();
                    }
                }));
    }

    @Override
    public void onClick(ServiceCustomerBean.RecordsBean item) {
        Log.e("zlz", "star");
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("id", item.getId());
        paramsMap.put("isTop", item.getIsTop().equals("0") ? "1" : "0");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().starCustomer(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<Object>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<Object> objectBaseData) {
                        Log.e("zlz", new Gson().toJson(objectBaseData));
                        if (objectBaseData.isSuccess()) {
                            onRefresh();
                        } else {
                            toast(objectBaseData.getMessage());
                        }
                    }
                }));
    }
}
