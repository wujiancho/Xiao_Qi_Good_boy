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
import com.lx.xqgg.util.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private String token;

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
        //获取用户token
        token = SharedPrefManager.getUser().getToken();
        accountNameText = accountName.getText();
        bankOfDepositText = bankOfDeposit.getText();
        bankCardNumberText = bankCardNumber.getText();

        //佣金提现获取银行信息
      //  Accesstobankinformation();
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
                    finish();
                }
                break;
        }
    }

    //佣金提现获取银行信息
    private void Accesstobankinformation() {
        addSubscribe(ApiManage.getInstance().getMainApi().getBandinformation(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<BandinformationBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<BandinformationBean> bandinformationBeanBaseData) {
                        BandinformationBean data=bandinformationBeanBaseData.getData();
                        accountName.setText(data.getBankName());
                        bankOfDeposit.setText(data.getBankUser());
                        bankCardNumber.setText(data.getBankNo());
                        SpUtil.getInstance().saveString("bankinfortion",new Gson().toJson(data));
                       /* ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建普通字符型ClipData
                        ClipData mClipData = ClipData.newPlainText("Label", data.getBankName() + "");
                        // 将ClipData内容放到系统剪贴板里。
                        cm.setPrimaryClip(mClipData);
                        toast("账户名称已复制到剪切板");*/
                    }
                }));
    }
}
