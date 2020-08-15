package com.lx.xqgg.ui.product;

import android.content.Context;
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
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.event.ProductDetailEvent;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.home.UserServiceFragment;
import com.lx.xqgg.ui.home.bean.UserServiceBean;
import com.lx.xqgg.ui.person.bean.ProductDetailBean;
import com.lx.xqgg.ui.product.adapter.ProductAdapter;
import com.lx.xqgg.ui.product.bean.ProductBean;
import com.lx.xqgg.ui.webview.WebViewActivity;
import com.lx.xqgg.util.Base64;

import org.greenrobot.eventbus.EventBus;

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

public class ProductTypeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, ProductAdapter.OnApplyClickListener {
    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private String title = "";
    private String classifyId = "";
    private String guarantee = "";
    private String orderby = "";
    private String orderway = "";
    private String proFeature = "";
    private int page = 1;

    private boolean isInit = false;

    private ProductAdapter productAdapter;
    private List<ProductBean.RecordsBean> list;
    private ApplyFragment applyFragment;

    private UserServiceFragment userServiceFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
            classifyId = getArguments().getString("classifyId");
            guarantee = getArguments().getString("guarantee");
            orderby = getArguments().getString("orderby");
            orderway = getArguments().getString("orderway");
        }

        getProductList();
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected void initData() {

    }

    /**
     * 获取产品列表
     */
    public void getProductList() {
        Log.e("zlz", "getData");
        page = 1;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("title", title);
        paramsMap.put("classifyId", classifyId);
        paramsMap.put("appid", Constans.APPID);
        paramsMap.put("loantype", "");
        paramsMap.put("guarantee", guarantee);
        paramsMap.put("cityName", Constans.CITY);
        paramsMap.put("orderby", orderby);
        paramsMap.put("orderway", orderway);
        paramsMap.put("proFeature", proFeature);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", 10);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getProductList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<ProductBean>(mContext, null) {
                    @Override
                    public void onNext(ProductBean productBean) {
                        Log.e("zlz", "classifyId:" + classifyId);
                        list = new ArrayList<>();
                        productAdapter = new ProductAdapter(list);
                        rvProduct.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        rvProduct.setAdapter(productAdapter);
                        productAdapter.setEmptyView(R.layout.layout_empty, rvProduct);
                        productAdapter.setOnLoadMoreListener(ProductTypeFragment.this::onLoadMoreRequested);
                        productAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {

                            private ProductDetailBean productDetailBean;

                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                /*Intent intent = new Intent(mContext, ProductDetailActivity.class);
                                intent.putExtra("data", list.get(position).getId());
                                startActivity(intent);*/
                              //  toast("跳转产品详细页H5");
                                //接受的数据生成jsonbean数据
                                String userphone= SharedPrefManager.getUser().getMobile();
                                int userid= productBean.getRecords().get(position).getId();
                                String image = productBean.getRecords().get(position).getImage();
                                String  title = productBean.getRecords().get(position).getTitle();
                                int quota = productBean.getRecords().get(position).getQuota();
                                String rate = productBean.getRecords().get(position).getRate();
                                String  ed= SharedPrefManager.getImitationexamination().getPro_bal();
                                String  fee= SharedPrefManager.getImitationexamination().getPro_fee();
                                String count=ed+":"+(quota/10000)+",日"+fee+":" + rate;
                                ProductDetailEvent event=new ProductDetailEvent();
                                event.setImage(image);
                                event.setTitle(title);
                                event.setCount(count);
                                EventBus.getDefault().postSticky(event);
                                String cityname= Constans.CITY;

                                //生成产品详细页的接口
                                ArrayList<ProductDetailBean> gson2= new ArrayList<>();
                                productDetailBean = new ProductDetailBean();
                                productDetailBean.setUserPhone(userphone);
                                productDetailBean.setType("h5");
                                productDetailBean.setId(userid+"");
                                productDetailBean.setStatusHeight("30.000");
                                productDetailBean.setCityName("");
                                gson2.add(productDetailBean);
                                String url2=new Gson().toJson(gson2);
                                String json2 =url2.substring(1,url2.length()-1);
                                Log.e("zlz",json2);
                                //加密json
                                String jiajson2= Base64.encode(json2.getBytes());
                                //生成产品详细页的接口
                                String jiekong2=Config.URL+"view/productDetails.html?bean="+jiajson2;
                                Constans.productDetails=jiekong2;

                                ArrayList<ProductDetailBean> gson= new ArrayList<>();
                                productDetailBean = new ProductDetailBean();
                                productDetailBean.setUserPhone(userphone);
                                productDetailBean.setType("app");
                                productDetailBean.setId(userid+"");
                                productDetailBean.setStatusHeight("30.000");
                                productDetailBean.setCityName(cityname);
                                gson.add(productDetailBean);
                                String url=new Gson().toJson(gson);
                                String json =url.substring(1,url.length()-1);
                                Log.e("zlz",json);
                                //加密json
                                String jiajson= Base64.encode(json.getBytes());
                                String jiekong= Config.URL+"view/productDetails.html?bean="+jiajson;
                                Log.e("zlz",jiekong);

                                if (!"".equals(jiekong2)) {
                                        WebViewActivity.open(new WebViewActivity.Builder()
                                                .setContext(mContext)
                                                .setAutoTitle(false)
                                                .setIsFwb(false)
                                                .setTitle("产品详情")
                                                .setNeedShare(false)
                                                .setUrl(jiekong));
                                }
                            }
                        });
                        productAdapter.bindToRecyclerView(rvProduct);
                        refreshLayout.setOnRefreshListener(ProductTypeFragment.this::onRefresh);
                        productAdapter.setOnApplyClickListener(ProductTypeFragment.this::onClick);
                        if (productBean.isIsSuccess()) {
                            if (productBean.getRecords() != null && productBean.getRecords().size() > 0) {
                                list.clear();
                                list.addAll(productBean.getRecords());
                                if (list.size() < 10) {
                                    productAdapter.disableLoadMoreIfNotFullPage();
                                }
                                productAdapter.notifyDataSetChanged();
                            }
                        }else {
                            toast(productBean.getMessage()+"");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.toString());
                        list = new ArrayList<>();
                        productAdapter = new ProductAdapter(list);
                        rvProduct.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                        rvProduct.setAdapter(productAdapter);
                        productAdapter.setEmptyView(R.layout.layout_empty, rvProduct);
                        productAdapter.setOnLoadMoreListener(ProductTypeFragment.this::onLoadMoreRequested);
                        productAdapter.bindToRecyclerView(rvProduct);
                        refreshLayout.setOnRefreshListener(ProductTypeFragment.this::onRefresh);
                        productAdapter.setOnApplyClickListener(ProductTypeFragment.this::onClick);
                    }
                }));
    }

    public static ProductTypeFragment newInstance(String title, String classifyId, String guarantee, String orderby, String orderway, String proFeature) {
        ProductTypeFragment productTypeFragment = new ProductTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("classifyId", classifyId);
        bundle.putString("guarantee", guarantee);
        bundle.putString("orderby", orderby);
        bundle.putString("orderway", orderway);
        bundle.putString("proFeature", proFeature);
        productTypeFragment.setArguments(bundle);
        Log.e("zlz", "instance" + classifyId);
        return productTypeFragment;
    }

    public void update(String title, String classifyId, String guarantee, String orderby, String orderway, String proFeature) {
        this.title = title;
        this.classifyId = classifyId;
        this.guarantee = guarantee;
        this.orderby = orderby;
        this.orderway = orderway;
        this.proFeature = proFeature;
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundle.putString("title", title);
            bundle.putString("classifyId", classifyId);
            bundle.putString("guarantee", guarantee);
            bundle.putString("orderby", orderby);
            bundle.putString("orderway", orderway);
            bundle.putString("proFeature", proFeature);
        }
        page = 1;
        Log.e("zlz", "update" + classifyId);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        page = 1;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("title", title);
        paramsMap.put("classifyId", classifyId);
        paramsMap.put("appid", Constans.APPID);
        paramsMap.put("loantype", "");
        paramsMap.put("guarantee", guarantee);
        paramsMap.put("cityName", Constans.CITY);
        paramsMap.put("orderby", orderby);
        paramsMap.put("orderway", orderway);
        paramsMap.put("proFeature", proFeature);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", 10);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getProductList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<ProductBean>(mContext, null) {
                    @Override
                    public void onNext(ProductBean productBean) {
//                        Log.e("zlz", "下啦" + new Gson().toJson(productBean));
                        if (productBean.isIsSuccess()) {
                            list.clear();
                            list.addAll(productBean.getRecords());
                            productAdapter.notifyDataSetChanged();
                            page = 1;
                            if (list.size() < 10) {
                                productAdapter.disableLoadMoreIfNotFullPage();
                            }
                            productAdapter.loadMoreComplete();
                        }else {
                            toast(productBean.getMessage()+"");
                        }
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        refreshLayout.setRefreshing(false);
                    }
                }));
    }

    /**
     * 上拉加載
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("title", title);
        paramsMap.put("classifyId", classifyId);
        paramsMap.put("appid", Constans.APPID);
        paramsMap.put("loantype", "");
        paramsMap.put("guarantee", guarantee);
        paramsMap.put("cityName", Constans.CITY);
        paramsMap.put("orderby", orderby);
        paramsMap.put("orderway", orderway);
        paramsMap.put("proFeature", proFeature);
        paramsMap.put("page", page);
        paramsMap.put("pageSize", 10);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getProductList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<ProductBean>(mContext, null) {
                    @Override
                    public void onNext(ProductBean productBean) {
                        Log.e("zlz", "上啦" + new Gson().toJson(productBean));
                        if (productBean.isIsSuccess()) {
                            if (productBean.getRecords() == null || productBean.getRecords().size() == 0) {
                                productAdapter.loadMoreEnd();
                            } else {
                                productAdapter.addData(productBean.getRecords());
//                            list.addAll();
                                productAdapter.notifyDataSetChanged();
                                productAdapter.loadMoreComplete();
                            }
                        } else {
                            productAdapter.loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        productAdapter.loadMoreFail();
                    }
                }));
    }


    @Override
    public void onClick(ProductBean.RecordsBean bean) {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getUserServiceInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<UserServiceBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<UserServiceBean> objectBaseData) {
                        Log.e("zlz", new Gson().toJson(objectBaseData));
                        if (objectBaseData.isSuccess()) {
                            if (objectBaseData.getCode() != 1) {
                                userServiceFragment = new UserServiceFragment(objectBaseData);
                                userServiceFragment.show(getFragmentManager(), "");
                            } else {
                                applyFragment = new ApplyFragment(bean);
                                applyFragment.show(getFragmentManager(), null);
                            }
                        } else {
                            toast(objectBaseData.getMessage());
                        }
                    }
                }));
//        applyFragment = new ApplyFragment(bean);
//        applyFragment.show(getFragmentManager(), null);
    }
}
