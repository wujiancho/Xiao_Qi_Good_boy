package com.lx.xqgg.ui.mycommission;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import com.lx.xqgg.util.SpUtil;

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
        //回显
        String  data=   SpUtil.getInstance().getSpString("bankinfortion");
        SaveBankinfortionBean saveBankinfortionBean=new Gson().fromJson(data, SaveBankinfortionBean.class);
        String bankNamesave=saveBankinfortionBean.getBankName();
        String bankNosvae=saveBankinfortionBean.getBankNo();
        String bankUsersave=saveBankinfortionBean.getBankUser();
        if (!"".equals(bankNamesave)){
            accountName.setText(bankNamesave);
        }
        if (!"".equals(bankNosvae)){
            bankCardNumber.setText(bankNosvae);
        }
        if (!"".equals(bankUsersave)){
            bankOfDeposit.setText(bankUsersave);
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
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        SpUtil.getInstance().saveString("bankinfortion",new Gson().toJson(body));
    }
    @OnClick({R.id.toobar_back, R.id.btn_addbankfinish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toobar_back:
                finish();
                break;
            case R.id.btn_addbankfinish:
                if (TextUtils.isEmpty(accountNameText)){
                    toast("企业名称不能为空");
                }
               else if (TextUtils.isEmpty(bankOfDepositText)){
                    toast("银行名称不能为空");
                }
               else if (TextUtils.isEmpty(bankCardNumberText)){
                    toast("银行卡号不能为空");
                }
                else {
                    toast("银行卡添加成功");
                    //添加银行卡信息保存
                    savebankinfortion();
                    finish();
                }
                break;
        }
    }

}
