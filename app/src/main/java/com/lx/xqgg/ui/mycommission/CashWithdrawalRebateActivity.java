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

import com.lx.xqgg.R;
import com.lx.xqgg.base.BaseActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        //判断一下用户是否绑定过银行卡
        addbankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenaddbcd=new Intent(CashWithdrawalRebateActivity.this,AddbankCardActivity.class);
                startActivity(intenaddbcd);

            }
        });

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
                checked = checkTx.isChecked();
                if (checked==true){
                    //获取系统的 日期
                    Calendar calendar= Calendar.getInstance();
                    //日
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    if (day>=25&&day<=29){
                        if (jifeng>=1000){
                            Intent intencwdcf=new Intent(CashWithdrawalRebateActivity.this,CashWithdrawalConfirmationActivity.class);
                            startActivity(intencwdcf);
                        }else {
                            toast("抱歉满1000积分才可以提现哦");
                        }
                    }else {
                        toast("提示：每月25日~29日可申请提现积分");
                    }
                }else {
                    toast("请先勾选小麒乖乖返佣规则");
                }


                break;
                //提现记录
            case R.id.btt_txjl:
                Intent intenwdr=new Intent(CashWithdrawalRebateActivity.this,WithdrawalRecordActivity.class);
                startActivity(intenwdr);
                break;
        }
    }
}
