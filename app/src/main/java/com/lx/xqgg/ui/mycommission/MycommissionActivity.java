package com.lx.xqgg.ui.mycommission;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MycommissionActivity extends BaseActivity {


    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mycommission;
    }

    @Override
    protected void initView() {
     tvTitle.setText("我的佣金");
    }

    @Override
    protected void initData() {

    }



    @OnClick(R.id.v_close)
    public void onViewClicked() {
        finish();
    }
}
