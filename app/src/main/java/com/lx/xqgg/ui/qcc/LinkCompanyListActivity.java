package com.lx.xqgg.ui.qcc;

import android.content.Intent;
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
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.product.bean.QccBean;
import com.lx.xqgg.ui.qcc.adapter.LinkCompanyAdapter;
import com.lx.xqgg.ui.qcc.bean.QccPersonBean;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LinkCompanyListActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private LinkCompanyAdapter linkCompanyAdapter;
    private QccPersonBean qccPersonBean;
    private String name;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_link_company;
    }

    @Override
    protected void initView() {
        tvTitle.setText("相关公司");
        name = getIntent().getStringExtra("name");
        qccPersonBean = (QccPersonBean) getIntent().getSerializableExtra("data");
        tvFirst.setText(name.substring(0, 1));
        tvName.setText(name);

        linkCompanyAdapter=new LinkCompanyAdapter(qccPersonBean.getResult());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(linkCompanyAdapter);

        linkCompanyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String compnay=qccPersonBean.getResult().get(position).getName();
                addSubscribe(ApiManage.getInstance().getMainApi().getQiCcInfo(compnay)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseSubscriber<BaseData<String>>(mContext, null) {
                            @Override
                            public void onNext(BaseData<String> s) {
                                Log.e("zlz",new Gson().toJson(s));
                                if (s.isSuccess()) {
                                    QccBean qccBean = new Gson().fromJson(s.getData(), QccBean.class);
//                            Log.e("zlz", new Gson().toJson(qccBean));
                                    if(qccBean==null){
                                        return;
                                    }
                                    if ("查询无结果".equals(qccBean.getMessage())) {
                                        toast("未查询相关信息，请确认企业名称输入无误");
                                        return;
                                    }
                                    Intent intent=new Intent(mContext,QccCompanyResultMainActivity.class);
                                    intent.putExtra("data",qccBean);
                                    startActivity(intent);
                                }else {
                                    toast(s.getData());
                                }
                            }
                        }));
            }
        });
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }
}
