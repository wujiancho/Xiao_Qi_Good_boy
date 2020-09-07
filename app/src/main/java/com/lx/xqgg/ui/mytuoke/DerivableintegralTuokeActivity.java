package com.lx.xqgg.ui.mytuoke;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.mycommission.XieYiActivity;
import com.lx.xqgg.ui.mytuoke.bean.getZfbBean;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private boolean checked;
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
        //获取用户支付宝信息
        zfbmassage();
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
                Intent intenaddzfbtuoke = new Intent(DerivableintegralTuokeActivity.this, AddzfbnumActivity.class);
                startActivity(intenaddzfbtuoke);
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
                checked = checkTxtuoke.isChecked();
                if ("支付宝绑定".equals(addzfbName.getText().toString().trim())){
                    toast("请先绑定支付宝");
                    return;
                }
                tixian();
                break;
            //提现记录
            case R.id.btt_txjltuoke:
                Intent intentwltuoke = new Intent(DerivableintegralTuokeActivity.this, WithdrawalrecordTuokeActivity.class);
                startActivity(intentwltuoke);
                break;
            //规则权益
            case R.id.fanyongguiztuoke:
                Intent intenxieyi = new Intent(DerivableintegralTuokeActivity.this, XieYiActivity.class);
                intenxieyi.putExtra("group","rakebackTackOutRule");
                startActivity(intenxieyi);
                break;

        }
    }
   //提现
    private  void tixian(){
        String count2 = txCountsrtuoke.getText().toString().trim();
        if (checked == false) {
            toast("请先勾选小麒乖乖返佣规则");
            return;
        }
        int jftuoke = Integer.valueOf(count2);
        if("".equals(count2)){
            toast("提现积分不能为空");
            return;
        }
        if(jftuoke<0||count2.length()<4){
            toast("提现积分不能少于4位");
            return;
        }
        String money1 = txMoneytuoke.getText().toString();
        String money = money1.substring(1, money1.length());
        if (money.length() < 3) {
            toast("提现金额不能小于3位");
            return;
        }
        Intent intenddtuoke = new Intent(DerivableintegralTuokeActivity.this, WithdrawaldeterminetuokeActivity.class);
        startActivity(intenddtuoke);
    }


    //获取用户支付宝信息
    private void zfbmassage() {
        addSubscribe(ApiManage.getInstance().getMainApi().getgetZfb(SharedPrefManager.getUser().getToken())
         .subscribeOn(Schedulers.io())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribeWith(new BaseSubscriber<BaseData<getZfbBean>>(mContext, null) {
             @Override
             public void onNext(BaseData<getZfbBean> getZfbBeanBaseData) {
                 getZfbBean data= getZfbBeanBaseData.getData();
                 if (data!=null&&"".equals(data)){
                   addzfbName.setText(data.getZfb_account()+data.getZfb_name());
                 }else{
                     addzfbName.setText("支付宝绑定");
                 }

             }

             @Override
             public void onError(Throwable t) {
                 super.onError(t);
                 toast(t.getMessage());
             }
         }));
    }

    //回显刷新数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
