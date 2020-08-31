package com.lx.xqgg.ui.mycommission;

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

//提现确认
public class CashWithdrawalConfirmationActivity extends BaseActivity {


    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.tx_money)
    TextView txMoney;
    @BindView(R.id.bandname_id)
    TextView bandnameId;
    @BindView(R.id.btn_txfinish)
    Button btnTxfinish;
    private String bankName;
    private String bankno;
    private String money;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cash_withdrawal_confirmation;
    }

    @Override
    protected void initView() {
        tvTitle.setText("提现确认");
    }

    @Override
    protected void initData() {
        bankName = getIntent().getStringExtra("bankName");
        bankno = getIntent().getStringExtra("bankNo");
        money = getIntent().getStringExtra("money");
        if (!"".equals(money)){
            txMoney.setText("¥"+money);
        }
        if (!"".equals(bankno)||!"".equals(bankName)){
           String Hou4bankNo= bankno.substring(bankno.length()-4,bankno.length());
            bandnameId.setText(bankName+" 尾号"+Hou4bankNo);
        }
    }
    @OnClick({R.id.v_close, R.id.btn_txfinish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.btn_txfinish:
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
