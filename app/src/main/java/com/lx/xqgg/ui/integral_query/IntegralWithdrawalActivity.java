package com.lx.xqgg.ui.integral_query;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//积分提现
public class IntegralWithdrawalActivity extends BaseActivity {

    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.integral_count)
    TextView integralCount;
    @BindView(R.id.integral_txke)
    TextView integralTxke;
    @BindView(R.id.integral_queryhistory)
    TextView integralQueryhistory;
    @BindView(R.id.srmoney)
    TextView srmoney;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.collection_accountno)
    TextView collectionAccountno;
    @BindView(R.id.collection_accountnosr)
    EditText collectionAccountnosr;
    @BindView(R.id.beneficiary_bank)
    TextView beneficiaryBank;
    @BindView(R.id.beneficiary_banksr)
    EditText beneficiaryBanksr;
    @BindView(R.id.collection_account)
    TextView collectionAccount;
    @BindView(R.id.collection_accountsr)
    EditText collectionAccountsr;
    @BindView(R.id.check_dui)
    CheckBox checkDui;
    @BindView(R.id.huize)
    TextView huize;
    @BindView(R.id.btn_tx)
    Button btnTx;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_withdrawal;
    }

    @Override
    protected void initView() {
        tvTitle.setText("积分提现");
    }

    @Override
    protected void initData() {

    }

    @OnClick()
    public void onViewClicked() {
        finish();
    }



    @OnClick({R.id.integral_queryhistory, R.id.huize, R.id.btn_tx,R.id.v_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.integral_queryhistory://历史记录
                Intent intenthistory = new Intent(IntegralWithdrawalActivity.this, Integral_queryhistoryActivity.class);
                startActivity(intenthistory);
                break;
            case R.id.huize://积分提现规则
                Intent intentrules = new Intent(IntegralWithdrawalActivity.this, IntegralwithdrawalrulesActivity.class);
                startActivity(intentrules);
                break;
            case R.id.btn_tx://提现确认信息
                Intent intentband = new Intent(IntegralWithdrawalActivity.this, BankinformationconfirmationActivity.class);
                startActivity(intentband);
                break;
            case R.id.v_close:
                finish();
                break;
        }
    }
}
