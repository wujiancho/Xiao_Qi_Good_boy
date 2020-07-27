package com.lx.xqgg.ui.integral_query;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BankinformationconfirmationActivity extends BaseActivity {


    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.bandname)
    TextView bandname;
    @BindView(R.id.userhumber)
    TextView userhumber;
    @BindView(R.id.txintegral)
    TextView txintegral;
    @BindView(R.id.txmoney)
    TextView txmoney;
    @BindView(R.id.btn_txno)
    Button btnTxno;
    @BindView(R.id.btn_txok)
    Button btnTxok;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bankinformationconfirmation;
    }

    @Override
    protected void initView() {
        tvTitle.setText("银行信息确认");
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.btn_txno, R.id.btn_txok,R.id.v_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_txno:
                finish();
                break;
            case R.id.btn_txok:
                finish();
                break;
            case R.id.v_close:
                finish();
                break;
        }
    }
}
