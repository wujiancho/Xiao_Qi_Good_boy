package com.lx.xqgg.ui.mycommission;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.lx.xqgg.ui.mycommission.bean.CommissionwithdrawalBean;
import com.lx.xqgg.ui.mycommission.bean.ReturningservantBean;
import com.lx.xqgg.util.SpUtil;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;

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
    int jifeng;
    private boolean checked;
    private String bankName;
   private  BandinformationBean  bandinformationBean;
    private String bankNo;
    private String bankUser;

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
        //获取可提现总积分
        String returningservantdata = SpUtil.getInstance().getSpString("returningservantdata");
        if (!"".equals(returningservantdata)){
            ReturningservantBean returningservantBean = new Gson().fromJson(returningservantdata, ReturningservantBean.class);
            int cashRebate = returningservantBean.getCashCharge();
            DecimalFormat df = new DecimalFormat("#,###");// 数字格式转换
            String cashRebatez= df.format(cashRebate);
            withdrawablePointsZong.setText(cashRebatez);
        }

        txCountsr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String count2=txCountsr.getText().toString().trim();
               if (!"".equals(count2)){
                   int jf= Integer.valueOf(count2);
                   txMoney.setText("￥"+(jf/10));
               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        String  bankinfortion = SpUtil.getInstance().getSpString("bankinfortion");
     /*  if (!"".equals(bankinfortion)){
           bandinformationBean=new Gson().fromJson(bankinfortion,BandinformationBean.class);
           bankName = bandinformationBean.getBankName();
       }
        if ("".equals(bankName)||addbankName.getText().toString().contains("添加银行卡")){
            //判断一下用户是否绑定过银行卡
            addbankName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intenaddbcd=new Intent(CashWithdrawalRebateActivity.this,AddbankCardActivity.class);
                    startActivity(intenaddbcd);

                }
            });
        }else {
            addbankName.setText(bankName);
        }*/

        withSettlement.setOnClickListener(new View.OnClickListener() {
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

    @OnClick({R.id.toobar_back,  R.id.but_alltx,  R.id.btt_txmoney, R.id.btt_txjl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toobar_back:
                finish();
                break;
            case R.id.but_alltx:
             String  withdrawalrebate=   withdrawalRebate.getText().toString().trim();
                 jifeng=Integer.valueOf(withdrawalrebate);
                 txCountsr.setText(jifeng+"");
                 String count=txCountsr.getText().toString().trim();
                 int jf= Integer.valueOf(count);
                 txMoney.setText("￥"+(jf/10));
                break;
            case R.id.btt_txmoney:
            /*    checked = checkTx.isChecked();
                if (checked==true){
                    //获取系统的 日期
                    Calendar calendar= Calendar.getInstance();
                    //日
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    if (day>=25&&day<=29){
                        if (jifeng>=1000){
                            bankNo = bandinformationBean.getBankNo();
                            bankUser = bandinformationBean.getBankUser();
                            String money1= txMoney.getText().toString();
                            String money=money1.substring(1,money1.length()-1);
                            HashMap<String, Object> paramsMap = new HashMap<>();
                            paramsMap.put("token", SharedPrefManager.getUser().getToken());
                         *//*   paramsMap.put("bankName", bankName);
                            paramsMap.put("bankNo", bankNo);
                            paramsMap.put("bankUser", bankUser);*//*
                            paramsMap.put("bankName", "工商银行");
                            paramsMap.put("bankNo", "1234567899875442278");
                            paramsMap.put("bankUser", bankUser);
                            paramsMap.put("money", money);
                            paramsMap.put("money", money);
                            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                            addSubscribe(ApiManage.getInstance().getMainApi().getCommissionwithdrawal(body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new BaseSubscriber<CommissionwithdrawalBean>(mContext, null
                            ) {
                             @Override
                             public void onNext(CommissionwithdrawalBean commissionwithdrawalBean) {
                             Intent intencwdcf=new Intent(CashWithdrawalRebateActivity.this,CashWithdrawalConfirmationActivity.class);
                           *//*  intencwdcf.putExtra("bankName",bankName);
                             intencwdcf.putExtra("bankNo",bankNo);*//*
                             intencwdcf.putExtra("bankName","工商银行");
                             intencwdcf.putExtra("bankNo","1234567899875442278");
                             intencwdcf.putExtra("money",money);
                             startActivity(intencwdcf);
                             }
                              }));
                        }else {
                            toast("抱歉满1000积分才可以提现哦");
                        }
                    }else {
                        toast("提示：每月25日~29日可申请提现积分");
                    }
                }else {
                    toast("请先勾选小麒乖乖返佣规则");
                }*/
                String money1= txMoney.getText().toString();
                String money=money1.substring(1,money1.length());
                Intent intencwdcf=new Intent(CashWithdrawalRebateActivity.this,CashWithdrawalConfirmationActivity.class);
                           /*  intencwdcf.putExtra("bankName",bankName);
                             intencwdcf.putExtra("bankNo",bankNo);*/
                intencwdcf.putExtra("bankName","工商银行");
                intencwdcf.putExtra("bankno","1234567899875442278");
                intencwdcf.putExtra("money",money);
                startActivity(intencwdcf);
                break;
                //提现记录
            case R.id.btt_txjl:
                Intent intenwdr=new Intent(CashWithdrawalRebateActivity.this,WithdrawalRecordActivity.class);
                startActivity(intenwdr);
                break;
        }
    }
}
