package com.lx.xqgg.ui.match;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.ui.match.adapter.MatchResultAdapter;
import com.lx.xqgg.ui.match.bean.MatchResultBean;
import com.lx.xqgg.ui.product.ProductDetailActivity;
import com.lx.xqgg.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MatchSavedDetailActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private MatchResultAdapter matchResultAdapter;
    private Integer ids;

    private List<MatchResultBean> listProducts = new ArrayList<>();
    private List<MultiItemEntity> list1 = new ArrayList<>();
    private String name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initView() {
        tvTitle.setText(getIntent().getStringExtra("company"));
        ids = getIntent().getIntExtra("ids", 0);
        name = getIntent().getStringExtra("name");
        Log.d("id---", "initView: "+ids);
        SpUtil.getInstance().saveString("companymach",getIntent().getStringExtra("company"));
        SpUtil.getInstance().saveString("namemach",getIntent().getStringExtra("name"));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        matchResultAdapter = new MatchResultAdapter(list1, true);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(matchResultAdapter);

    }

    @Override
    protected void initData() {
        addSubscribe(ApiManage.getInstance().getMainApi().getAutoDetail(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<MatchResultBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<MatchResultBean>> listBaseData) {
                        Log.e("zlz", new Gson().toJson(listBaseData));
                        if (!listBaseData.isSuccess()) {
                            toast(listBaseData.getMessage());
                        } else {
                            if (listBaseData.getData() != null && listBaseData.getData().size() > 0) {
                                listProducts = listBaseData.getData();
                                list1.clear();

                                for (MatchResultBean matchSavedBean : listProducts) {
                                    List<MatchResultBean.ProductBean> productList = matchSavedBean.getProduct();
                                    for (MatchResultBean.ProductBean productBean1 : productList) {
                                        matchSavedBean.addSubItem(productBean1);
                                    }
                                    list1.add(matchSavedBean);
                                }

                                matchResultAdapter.setNewData(list1);
//                                listProducts.addAll(productBean.getData());
                                matchResultAdapter.expandAll();
                                matchResultAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }));
    }


    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }
}
