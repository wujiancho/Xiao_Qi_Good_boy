package com.lx.xqgg.ui.mytuoke;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.CashWithdrawalRebateActivity;
import com.lx.xqgg.ui.vip.bean.PayListBean;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

//可提现积分--拓客
public class DerivableintegralTuokeActivity extends BaseActivity {


    @BindView(R.id.toobar_back)
    View toobarBack;
    @BindView(R.id.toobar_title)
    TextView toobarTitle;
    @BindView(R.id.withdrawable_points_zongtuoke)
    TextView withdrawablePointsZongtuoke;
    @BindView(R.id.riqitixtuoke)
    TextView riqitixtuoke;
    @BindView(R.id.withdrawal_rebatetuoke)
    TextView withdrawalRebatetuoke;
    @BindView(R.id.with_settlementtuoke)
    TextView withSettlementtuoke;
    @BindView(R.id.yihuo)
    TextView yihuo;
    @BindView(R.id.addzfb_name)
    TextView addzfbName;
    @BindView(R.id.tx_countsrtuoke)
    EditText txCountsrtuoke;
    @BindView(R.id.but_alltxtuoke)
    TextView butAlltxtuoke;
    @BindView(R.id.tx_moneytuoke)
    TextView txMoneytuoke;
    @BindView(R.id.btt_txmoneytuoke)
    Button bttTxmoneytuoke;
    @BindView(R.id.btt_txjltuoke)
    Button bttTxjltuoke;
    @BindView(R.id.check_txtuoke)
    CheckBox checkTxtuoke;
    @BindView(R.id.fanyongguiztuoke)
    TextView fanyongguiztuoke;
    @BindView(R.id.addzfb)
    ImageView addzfb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_derivableintegral_tuoke;
    }

    @Override
    protected void initView() {
        toobarTitle.setText("可提积分");
    }

    @Override
    protected void initData() {
        txCountsrtuoke.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        String count2= txCountsrtuoke.getText().toString().trim();
        if(!"".equals(count2)){
            int jf=Integer.valueOf(count2);
            txMoneytuoke.setText("¥"+(jf/10));
        }
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    });
    }


    @OnClick({R.id.addzfb,R.id.toobar_back, R.id.yihuo, R.id.addzfb_name, R.id.but_alltxtuoke, R.id.btt_txmoneytuoke, R.id.btt_txjltuoke, R.id.fanyongguiztuoke})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toobar_back:
                finish();
                break;
            case R.id.yihuo:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(DerivableintegralTuokeActivity.this);
                builder1.setMessage("已申请提现，小麒乖乖处理中");
                builder1.setTitle("温馨提示");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.show();
                break;
            //绑定支付宝
            case R.id.addzfb:
                toast("功能待定");
                break;
            //全部提现
            case R.id.but_alltxtuoke:
                txCountsrtuoke.setText(withdrawalRebatetuoke.getText().toString().trim());
                String count= txCountsrtuoke.getText().toString().trim();
                if(!"".equals(count)){
                    int jff = Integer.valueOf(count);
                    txCountsrtuoke.setText(jff + "");
                    txMoneytuoke.setText("¥"+(jff/10));
                }
                break;
            //提现
            case R.id.btt_txmoneytuoke:
                toast("提现成功");
                break;
            //提现记录
            case R.id.btt_txjltuoke:
                Intent intentwltuoke = new Intent(DerivableintegralTuokeActivity.this, WithdrawalrecordTuokeActivity.class);
                startActivity(intentwltuoke);
                break;
            //规则权益
            case R.id.fanyongguiztuoke:
                initCharacter();
                break;

        }
    }

    private void initCharacter() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("group", "buyStrategy");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getPayList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<PayListBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<PayListBean>> listBaseData) {
                        Log.e("zlz", new Gson().toJson(listBaseData));
                        if (listBaseData.isSuccess()) {
                            if (listBaseData.getData() != null) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(DerivableintegralTuokeActivity.this);
                                builder1.setMessage(listBaseData.getData().get(0).getValue());
                                builder1.setTitle(listBaseData.getData().get(0).getName());
                                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder1.show();
                            }
                        }
                    }
                }));
    }
}
