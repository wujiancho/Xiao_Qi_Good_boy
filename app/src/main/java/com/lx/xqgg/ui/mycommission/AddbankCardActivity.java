package com.lx.xqgg.ui.mycommission;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//添加银行卡
public class AddbankCardActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.account_name)
    EditText accountName;
    @BindView(R.id.bank_of_deposit)
    EditText bankOfDeposit;
    @BindView(R.id.bank_card_number)
    EditText bankCardNumber;
    @BindView(R.id.btn_addbankfinish)
    Button btnAddbankfinish;
    private Editable accountNameText;
    private Editable bankOfDepositText;
    private Editable bankCardNumberText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addbank_card;
    }

    @Override
    protected void initView() {
     toobarTitle.setText("添加银行卡");
    }

    @Override
    protected void initData() {
        accountNameText = accountName.getText();
        bankOfDepositText = bankOfDeposit.getText();
        bankCardNumberText = bankCardNumber.getText();
    }



    @OnClick({R.id.toobar_back, R.id.btn_addbankfinish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toobar_back:
                finish();
                break;
            case R.id.btn_addbankfinish:
                if ("".equals(accountNameText)){
                    toast("企业名称不能为空");
                }
               else if ("".equals(bankOfDepositText)){
                    toast("银行名称不能为空");
                }
               else if ("".equals(bankCardNumberText)){
                    toast("银行卡号不能为空");
                }
                else {
                    toast("银行卡添加成功");
                    finish();
                }
                break;
        }
    }
}
