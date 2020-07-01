package com.lx.xqgg.ui.company_auth;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

public class CompanyAuthSuccActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_auth_succ;
    }

    @Override
    protected void initView() {
        tvTitle.setText("企业认证");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.v_close, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_confirm:
                finish();
                break;
        }
    }
}
