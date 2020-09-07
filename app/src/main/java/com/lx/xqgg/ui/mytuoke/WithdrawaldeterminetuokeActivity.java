package com.lx.xqgg.ui.mytuoke;

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

//提现确定
public class WithdrawaldeterminetuokeActivity extends BaseActivity {


    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.tx_moneyzfb)
    TextView txMoneyzfb;
    @BindView(R.id.tv_zfb_num)
    TextView tvZfbNum;
    @BindView(R.id.btn_txfinishafb)
    Button btnTxfinishafb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawaldeterminetuoke;
    }

    @Override
    protected void initView() {
    tvTitle.setText("提现确认");
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.v_close, R.id.btn_txfinishafb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_txfinishafb:
                finish();
                break;
        }
    }
}
