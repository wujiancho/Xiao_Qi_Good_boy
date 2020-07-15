package com.lx.xqgg.ui.my_client;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.order.adapter.OrderAdapter;
import com.lx.xqgg.ui.order.bean.OrderBean;

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

public class ClientDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private List<OrderBean.RecordsBean> list;
    private OrderAdapter orderAdapter;
    private int page = 1;
    private String customerName;
    private String customerName1;
    private String commpanyName;
    private String commpanyName1;
    private String phone;
    private String phone1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_client_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText("客户详情");
        list = new ArrayList<>();
        orderAdapter = new OrderAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(orderAdapter);
        orderAdapter.setEmptyView(R.layout.layout_empty, recyclerView);
        orderAdapter.setOnLoadMoreListener(this);
        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        orderAdapter.bindToRecyclerView(recyclerView);
        refreshLayout.setOnRefreshListener(this);
        customerName = getIntent().getStringExtra("customerName");
        customerName1 = getIntent().getStringExtra("customerName1");
        commpanyName = getIntent().getStringExtra("commpanyName");
        commpanyName1 = getIntent().getStringExtra("commpanyName1");
        phone = getIntent().getStringExtra("phone");
        phone1= getIntent().getStringExtra("phone1");
        tvName.setText(commpanyName1);
        tvPhone.setText(customerName1 + " " + phone1);
    }

    @Override
    protected void initData() {
        getOrder();
    }

    private void getOrder() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("customerName", customerName);
        paramsMap.put("commpanyName", commpanyName);
        paramsMap.put("phone", phone);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", "10");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getCustomerOrder(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<OrderBean>(mContext, null) {
                    @Override
                    public void onNext(OrderBean orderBean) {
                        if (orderBean.isIsSuccess()) {
                            if (orderBean.getRecords() != null && orderBean.getRecords().size() > 0) {
                                list.clear();
                                list.addAll(orderBean.getRecords());
                                if (list.size() < 10) {
                                    orderAdapter.disableLoadMoreIfNotFullPage();
                                }
                                orderAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }));
    }


    @OnClick({R.id.v_close,R.id.layout})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.layout:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setMessage("是否拨打客户电话"+phone);
                builder1.setTitle("温馨提示");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mIntent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:"+phone);
                        mIntent.setData(data);
                        //Android6.0以后的动态获取打电话权限
                        startActivity(mIntent);
                    }
                });
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.show();
                break;
        }
    }

    @Override
    public void onRefresh() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("customerName", customerName);
        paramsMap.put("commpanyName", commpanyName);
        paramsMap.put("phone", phone);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", "10");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getCustomerOrder(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<OrderBean>(mContext, null) {
                    @Override
                    public void onNext(OrderBean orderBean) {
                        if (orderBean.isIsSuccess()) {
                            list.clear();
                            list.addAll(orderBean.getRecords());
                            orderAdapter.notifyDataSetChanged();
                            page = 1;
                            if (list.size() < 10) {
                                orderAdapter.disableLoadMoreIfNotFullPage();
                            }
                            orderAdapter.loadMoreComplete();
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
        paramsMap.put("customerName", customerName);
        paramsMap.put("commpanyName", commpanyName);
        paramsMap.put("phone", phone);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", "10");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getCustomerOrder(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<OrderBean>(mContext, null) {
                    @Override
                    public void onNext(OrderBean orderBean) {
                        Log.e("zlz", "上啦" + new Gson().toJson(orderBean));
                        if (orderBean.isIsSuccess()) {
                            if (orderBean.getRecords() == null || orderBean.getRecords().size() == 0) {
                                orderAdapter.loadMoreEnd();
                            } else {
                                orderAdapter.addData(orderBean.getRecords());
                                orderAdapter.notifyDataSetChanged();
                                orderAdapter.loadMoreComplete();
                            }
                        } else {
                            orderAdapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        orderAdapter.loadMoreFail();
                    }
                }));
    }
}
