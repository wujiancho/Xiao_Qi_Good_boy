package com.lx.xqgg.ui.mytuoke;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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
    private String zfbnum;
    private String money;

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
        zfbnum = getIntent().getStringExtra("zfbnum");
        money = getIntent().getStringExtra("money");
        tvZfbNum.setText(zfbnum);
        txMoneyzfb.setText("￥"+money);
    }


    @OnClick({R.id.v_close, R.id.btn_txfinishafb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_txfinishafb:
                setResult(2);
                finish();
                break;
        }
    }
    //点击返回上一页面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            setResult(2, intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
