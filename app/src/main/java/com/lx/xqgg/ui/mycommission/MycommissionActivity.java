package com.lx.xqgg.ui.mycommission;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;
import com.stx.xhb.xbanner.XBanner;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MycommissionActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.xbanner_vip)
    XBanner xbannerVip;
    @BindView(R.id.vip_RecyclerView)
    RecyclerView vipRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mycommission;
    }

    @Override
    protected void initView() {
        toobarTitle.setText("业绩返佣服务包");
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.toobar_back)
    public void onViewClicked() {
        finish();
    }



}
