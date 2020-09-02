package com.lx.xqgg.ui.mycommission;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.bean.BandinformationBean;
import com.lx.xqgg.ui.mycommission.bean.CommissionwithdrawalBean;
import com.lx.xqgg.ui.mycommission.bean.ReturningservantBean;
import com.lx.xqgg.ui.mycommission.bean.SaveBankinfortionBean;
import com.lx.xqgg.ui.vip.bean.PayListBean;
import com.lx.xqgg.util.SpUtil;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

//可提返佣
public class CashWithdrawalRebateActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.withdrawable_points_zong)
    TextView withdrawablePointsZong;
    @BindView(R.id.withdrawal_rebate)
    TextView withdrawalRebate;
    @BindView(R.id.with_settlement)
    TextView withSettlement;
    @BindView(R.id.addbank_name)
    TextView addbankName;
    @BindView(R.id.tx_countsr)
    EditText txCountsr;
    @BindView(R.id.but_alltx)
    TextView butAlltx;
    @BindView(R.id.tx_money)
    TextView txMoney;
    @BindView(R.id.btt_txmoney)
    Button bttTxmoney;
    @BindView(R.id.btt_txjl)
    Button bttTxjl;
    @BindView(R.id.check_tx)
    CheckBox checkTx;
    @BindView(R.id.riqitix)
    TextView riqitix;
    @BindView(R.id.yihuo)
    LinearLayout yihuo;
    @BindView(R.id.fanyongguiz)
    TextView fanyongguiz;
    private boolean checked;
    private String bankName;
    private String bankName2;
    private String bankNo;
    private String bankNo2;
    private String bankUser;
    private String bankUser2;
    private String token;
    private int cashRebate;
    private String riyuejie;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cash_withdrawal_rebate;
    }

    @Override
    protected void initView() {
        toobarTitle.setText("可提积分");
    }

    @Override
    protected void initData() {
        //获取用户token
        token = SharedPrefManager.getUser().getToken();
        riyuejie = getIntent().getStringExtra("riyuejie");
        //获取可提现总积分
        if ("日结".equals(riyuejie)){
            riqitix.setText("非常抱歉您为日结用户只能支持每天线下日结,暂不支持该提现功能");
        }else {
            riqitix.setText("提示：每月25日~29日可申请提现积分");
        }

      /*  String returningservantdata = SpUtil.getInstance().getSpString("returningservantdata");
        if (!"".equals(returningservantdata)) {
            ReturningservantBean returningservantBean = new Gson().fromJson(returningservantdata, ReturningservantBean.class);
            cashCharge = returningservantBean.getCashCharge();
            String createdMoney = returningservantBean.getCreatedMoney();
            int ccc = Integer.valueOf(createdMoney.substring(0, createdMoney.indexOf(".")));
            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
            String cashRebatez = df.format(cashCharge);
            withdrawablePointsZong.setText((cashCharge + ccc) + "");
            withSettlement.setText(createdMoney);
            withdrawalRebate.setText(cashRebatez);
        }*/
        //返佣方法
      Returningaservant();
        //佣金提现获取银行信息
      Accesstobankinformation();
            String data = SpUtil.getInstance().getSpString("bankinfortion");
            SaveBankinfortionBean saveBankinfortionBean = new Gson().fromJson(data, SaveBankinfortionBean.class);
            if (!"".equals(data)) {
                bankName = saveBankinfortionBean.getBankName();
                bankNo = saveBankinfortionBean.getBankNo();
                bankUser = saveBankinfortionBean.getBankUser();
                addbankName.setText(bankName.substring(bankName.length() - 4, bankName.length()) + "(" + bankNo.substring(bankNo.length() - 5, bankNo.length()) + ")");
        }
        addbankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenaddbcd = new Intent(CashWithdrawalRebateActivity.this, AddbankCardActivity.class);
                intenaddbcd.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intenaddbcd, 2);
            }
        });
        txCountsr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String count2 = txCountsr.getText().toString().trim();
                if (!"".equals(count2)) {
                    int jf = Integer.valueOf(count2);
                    if (jf>cashRebate){
                        toast("输入积分不能大于已有积分");
                        return;
                    }
                    txMoney.setText("¥" + (jf / 10));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        yihuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(CashWithdrawalRebateActivity.this);
                builder1.setMessage("已申请提现，小麒乖乖处理中");
                builder1.setTitle("温馨提示");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.show();
            }
        });

    }

    @OnClick({R.id.toobar_back, R.id.but_alltx, R.id.btt_txmoney, R.id.btt_txjl,R.id.fanyongguiz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toobar_back:
                setResult(2);
                finish();
                break;
            case R.id.but_alltx:
                txCountsr.setText(cashRebate + "");
                txMoney.setText("￥" + (cashRebate / 10));
                break;
            case R.id.btt_txmoney:
                checked = checkTx.isChecked();
                //提现方法
                tixian(bankName, bankNo, bankUser);
                if (!"".equals(bankName2)||!"".equals(bankNo2)||!"".equals(bankUser2)){
                    tixian(bankName2, bankNo2, bankUser2);
                }
                break;
            //提现记录
            case R.id.btt_txjl:
                Intent intenwdr = new Intent(CashWithdrawalRebateActivity.this, WithdrawalRecordActivity.class);
                startActivity(intenwdr);
                break;

            case R.id.fanyongguiz:
                Intent intenxieyi = new Intent(CashWithdrawalRebateActivity.this, XieYiActivity.class);
                intenxieyi.putExtra("group","rakebackTackOutRule");
                startActivity(intenxieyi);
                break;
        }
    }



    //提现方法
    private void tixian(String bankName, String bankNo, String bankUser) {
        String count2 = txCountsr.getText().toString().trim();
        if("".equals(count2)){
            toast("提现积分不能为空");
            return;
        }
        int jf = Integer.valueOf(count2);
        if (checked == false) {
        toast("请先勾选小麒乖乖返佣规则");
        return;
        }
        if ("添加银行卡".equals(addbankName.getText().toString().trim())){
                    toast("请先绑定银行卡");
                    return;
                }
             if ("日结".equals(riyuejie)) {
                   toast("您是日结用户，已完成提现，请转为月结，方可在返佣系统内提现");
                   return;
               }
                if(jf<0||count2.length()<4){
                    toast("提现积分不能少于4位");
                    return;
                }
                String money1 = txMoney.getText().toString();
                String money = money1.substring(1, money1.length());
                if (money.length() < 3) {
                    toast("提现金额不能小于3位");
                    return;
                }
        //day>=25&&day<=29
       /* //获取系统的 日期
        Calendar calendar = Calendar.getInstance();
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day<=25||day>=29) {
            toast("提示：每月25日~29日可申请提现积分");
            return;
        }*/

        HashMap<String, Object> paramsMap = new HashMap<>();
        if (!"".equals(bankName2)&&!"".equals(bankNo2)&&!"".equals(bankUser2)){
            paramsMap.put("token", SharedPrefManager.getUser().getToken());
            paramsMap.put("bankName", bankName2);
            paramsMap.put("bankNo", bankNo2);
            paramsMap.put("bankUser", bankUser2);
            paramsMap.put("money", money);
             if (bankName.equals(bankName2)&&bankNo.equals(bankNo2)&&bankUser.equals(bankUser2)&&!"".equals(bankName)&&!"".equals(bankNo)&&!"".equals(bankUser)&&bankName!=null&&bankNo!=null&&bankUser!=null){
                paramsMap.put("token", SharedPrefManager.getUser().getToken());
                paramsMap.put("bankName", bankName2);
                paramsMap.put("bankNo", bankNo2);
                paramsMap.put("bankUser", bankUser2);
                paramsMap.put("money", money);
            }else {
                 paramsMap.put("token", SharedPrefManager.getUser().getToken());
                 paramsMap.put("bankName", bankName);
                 paramsMap.put("bankNo", bankNo);
                 paramsMap.put("bankUser", bankUser);
                 paramsMap.put("money", money);
             }
        }
        else {
            paramsMap.put("token", SharedPrefManager.getUser().getToken());
            paramsMap.put("bankName", bankName);
            paramsMap.put("bankNo", bankNo);
            paramsMap.put("bankUser", bankUser);
            paramsMap.put("money", money);
        }
    /*    paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("bankName", bankName);
        paramsMap.put("bankNo", bankNo);
        paramsMap.put("bankUser", bankUser);
        paramsMap.put("money", money);*/
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                addSubscribe(ApiManage.getInstance().getMainApi().getCommissionwithdrawal(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseSubscriber<CommissionwithdrawalBean>(mContext, null
                        ) {
                            @Override
                            public void onNext(CommissionwithdrawalBean commissionwithdrawalBean) {
                                if (commissionwithdrawalBean.isSuccess()) {
                                    Intent intencwdcf = new Intent(CashWithdrawalRebateActivity.this, CashWithdrawalConfirmationActivity.class);
                                    intencwdcf.putExtra("bankName", bankName);
                                    intencwdcf.putExtra("bankNo", bankNo);
                                    intencwdcf.putExtra("money", money);
                                    startActivityForResult(intencwdcf,2);
                                } else {
                                    toast(commissionwithdrawalBean.getMessage().toString());
                                }
                            }
                        }));
              /*  Intent intencwdcf = new Intent(CashWithdrawalRebateActivity.this, CashWithdrawalConfirmationActivity.class);
                intencwdcf.putExtra("bankName", bankName);
                intencwdcf.putExtra("bankNo", bankNo);
                intencwdcf.putExtra("money", money);
                startActivity(intencwdcf);*/
        }

    //返佣方法
    public void Returningaservant() {
        addSubscribe(ApiManage.getInstance().getMainApi().getReturningservant(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<ReturningservantBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<ReturningservantBean> returningservantBeanBaseData) {
                        if (returningservantBeanBaseData.isSuccess()) {
                            ReturningservantBean data = returningservantBeanBaseData.getData();
                            int allRebate = data.getAllCharge();
                             cashRebate = data.getCashCharge();
                            String createdMoney = data.getCreatedMoney();
                            int ccc = Integer.valueOf(createdMoney.substring(0, createdMoney.indexOf(".")));
                            int thismothRebate = data.getCurrentMonthCharge();
                            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
                            String allRebatez = df.format(allRebate);//累计返佣
                            String cashRebatez = df.format(cashRebate);//可提返佣
                            String thismothRebatez = df.format(thismothRebate);//本月返佣
                            String ccccreatedMoney = df.format(ccc);//带结算
                            withdrawablePointsZong.setText((allRebatez));
                            withSettlement.setText(ccccreatedMoney);
                            withdrawalRebate.setText(cashRebatez);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
                    }
                }));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==1||data!=null){
            bankName = data.getStringExtra("bankname");
            bankNo = data.getStringExtra("bankno");
            bankUser = data.getStringExtra("bankUser");
            addbankName.setText(bankName.substring(bankName.length() - 4, bankName.length()) + "(" + bankNo.substring(bankNo.length() - 5, bankNo.length()) + ")");
        }
        if (requestCode == 2) {
            if (resultCode == 2) {
                Returningaservant();
            }
        }
    }
    //佣金提现获取银行信息
    private void Accesstobankinformation() {
        addSubscribe(ApiManage.getInstance().getMainApi().getBandinformation(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<BandinformationBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<BandinformationBean> bandinformationBeanBaseData) {
                        if (bandinformationBeanBaseData.isSuccess()){
                            BandinformationBean data = bandinformationBeanBaseData.getData();
                            if (!"".equals(data)&&data!=null){
                                bankName2 = data.getBankName();
                                bankNo2 = data.getBankNo();
                                bankUser2 = data.getBankUser();
                                //获取银行卡信息
                                if (!"".equals(bankName2)&&!"".equals(bankNo2)&&!"".equals(bankUser2)&&bankName2!=null&&bankNo2!=null&&bankUser2!=null){
                                    addbankName.setText(bankName2.substring(bankName2.length() - 4, bankName2.length()) + "(" + bankNo2.substring(bankNo2.length() - 5, bankNo2.length()) + ")");
                                    if (bankName.equals(bankName2)&&bankNo.equals(bankNo2)&&bankUser.equals(bankUser2)&&!"".equals(bankName)&&!"".equals(bankNo)&&!"".equals(bankUser)&&bankName!=null&&bankNo!=null&&bankUser!=null){
                                        addbankName.setText(bankName2.substring(bankName2.length() - 4, bankName2.length()) + "(" + bankNo2.substring(bankNo2.length() - 5, bankNo2.length()) + ")");
                                    }
                                    else {
                                        String spdata = SpUtil.getInstance().getSpString("bankinfortion");
                                        SaveBankinfortionBean saveBankinfortionBean = new Gson().fromJson(spdata, SaveBankinfortionBean.class);
                                        if (!"".equals(spdata)) {
                                            bankName = saveBankinfortionBean.getBankName();
                                            bankNo = saveBankinfortionBean.getBankNo();
                                            bankUser = saveBankinfortionBean.getBankUser();
                                            addbankName.setText(bankName.substring(bankName.length() - 4, bankName.length()) + "(" + bankNo.substring(bankNo.length() - 5, bankNo.length()) + ")");
                                        }
                                    }
                                }
                                else {
                                    String spdata = SpUtil.getInstance().getSpString("bankinfortion");
                                    SaveBankinfortionBean saveBankinfortionBean = new Gson().fromJson(spdata, SaveBankinfortionBean.class);
                                    if (!"".equals(spdata)) {
                                        bankName = saveBankinfortionBean.getBankName();
                                        bankNo = saveBankinfortionBean.getBankNo();
                                        bankUser = saveBankinfortionBean.getBankUser();
                                        addbankName.setText(bankName.substring(bankName.length() - 4, bankName.length()) + "(" + bankNo.substring(bankNo.length() - 5, bankNo.length()) + ")");
                                    }
                                }
                            }
                          else {
                                String spdata = SpUtil.getInstance().getSpString("bankinfortion");
                                SaveBankinfortionBean saveBankinfortionBean = new Gson().fromJson(spdata, SaveBankinfortionBean.class);
                                if (!"".equals(spdata)) {
                                    bankName = saveBankinfortionBean.getBankName();
                                    bankNo = saveBankinfortionBean.getBankNo();
                                    bankUser = saveBankinfortionBean.getBankUser();
                                    addbankName.setText(bankName.substring(bankName.length() - 4, bankName.length()) + "(" + bankNo.substring(bankNo.length() - 5, bankNo.length()) + ")");
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
                    }
                }));
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
