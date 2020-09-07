package com.lx.xqgg.ui.mycommission;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.bean.BandinformationBean;
import com.lx.xqgg.ui.mycommission.bean.SaveBankinfortionBean;
import com.lx.xqgg.ui.person.bean.ProductDetailBean;
import com.lx.xqgg.util.SpUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
    private String accountNameText;
    private String bankOfDepositText;
    private String bankCardNumberText;

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        //回显
        String  data=   SpUtil.getInstance().getSpString("bankinfortion");
        if(!"".equals(data)){
            SaveBankinfortionBean saveBankinfortionBean=new Gson().fromJson(data, SaveBankinfortionBean.class);
            if (!"".equals(saveBankinfortionBean.getBankName())){
                accountName.setText(saveBankinfortionBean.getBankName());
            }
            if (!"".equals(saveBankinfortionBean.getBankNo())){
                bankCardNumber.setText(saveBankinfortionBean.getBankNo());
            }
            if (!"".equals(saveBankinfortionBean.getBankUser())){
                bankOfDeposit.setText(saveBankinfortionBean.getBankUser());
            }
        }
    }

    //添加银行卡信息保存
    private void savebankinfortion() {
        accountNameText = accountName.getText().toString();
        bankOfDepositText = bankOfDeposit.getText().toString();
        bankCardNumberText = bankCardNumber.getText().toString();
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("bankName", accountNameText);
        paramsMap.put("bankUser", bankOfDepositText);
        paramsMap.put("bankNo", bankCardNumberText);
        SpUtil.getInstance().saveString("bankinfortion", new Gson().toJson(paramsMap));
    }
    @OnClick({R.id.toobar_back, R.id.btn_addbankfinish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toobar_back:
                finish();
                break;
            case R.id.btn_addbankfinish:
                if (bankOfDeposit.getText().toString().length()<4){
                    toast("请输入正确的企业名称");
                    return;
                }
                if (accountName.getText().toString().length()<4){
                    toast("请输入正确的银行名称");
                    return;
                }
                if (bankCardNumber.getText().toString().length()<4){
                    toast("请输入正确的银行卡号");
                    return;
                }
                    toast("银行卡添加成功");
                    //添加银行卡信息保存
                    savebankinfortion();
                Intent intenaddbcd=new Intent(AddbankCardActivity.this,CashWithdrawalRebateActivity.class);
                intenaddbcd.putExtra("bankname",accountNameText);
                intenaddbcd.putExtra("bankno",bankCardNumberText);
                intenaddbcd.putExtra("bankUser",bankOfDepositText);
                setResult(1, intenaddbcd);
                finish();
                break;
        }
    }

}
