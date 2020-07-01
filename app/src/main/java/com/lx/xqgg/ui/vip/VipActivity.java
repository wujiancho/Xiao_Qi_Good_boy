package com.lx.xqgg.ui.vip;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.explain.ExplainActivity;
import com.lx.xqgg.ui.login.bean.UserInfoBean;
import com.lx.xqgg.ui.vip.bean.AliParamBean;
import com.lx.xqgg.ui.vip.bean.NotifyBean;
import com.lx.xqgg.ui.vip.bean.PayListBean;
import com.lx.xqgg.ui.vip.bean.PayResult;
import com.lx.xqgg.ui.vip.bean.WechatParamBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class VipActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.tv_money_num)
    TextView tvMoneyNum;
    @BindView(R.id.btn_zf)
    Button btnZf;
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_vip_time)
    TextView tvVipTime;
    @BindView(R.id.checkbox)
    CheckBox checkBox;

    private String[] zfType = {"支付宝", "微信"};
    private String type = "";
    private List<String> name = new ArrayList<>();
    private List<String> values = new ArrayList<>();
    private List<Integer> ids = new ArrayList<>();

    private String payNum;

    private int payId = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip;
    }

    @Override
    protected void initView() {
        tvTitle.setText("我的VIP会员");
        EventBus.getDefault().register(this);
        getPayList();
        updateUserInfo();
    }

    @Override
    protected void initData() {

    }

    private void getPayList() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("group", "vipPrice");
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
                                for (PayListBean bean : listBaseData.getData()) {
                                    name.add(bean.getName() + "天");
                                    values.add("￥ " + bean.getValue());
                                    ids.add(bean.getId());
                                }
                                tvMoneyNum.setText(values.get(0));
                                payId = ids.get(0);
                                payNum = "￥ 0";
                                ArrayAdapter adpter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, name);
                                spinner.setAdapter(adpter);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        payNum = values.get(position);
                                        tvMoneyNum.setText(values.get(position));
                                        payId = ids.get(position);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                                return;
                            }
                        }
                        toast("获取配置失败！");
                        finish();
                    }
                }));
    }

    private void updateUserInfo() {
        ApiManage.getInstance().getMainApi().getUserInfo(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<UserInfoBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<UserInfoBean> userInfoBeanBaseData) {
                        Log.e("zlz", new Gson().toJson(userInfoBeanBaseData));
                        if (userInfoBeanBaseData.isSuccess()) {
                            SharedPrefManager.setUserInfo(userInfoBeanBaseData.getData());
                            tvVipTime.setText("会员到期时间：" + userInfoBeanBaseData.getData().getEndtime());
                            tvName.setText(SharedPrefManager.getUserInfo().getMobile());
                        }
                    }
                });
    }


    @OnClick({R.id.v_close, R.id.btn_zf, R.id.tv_hyxy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v_close:
                finish();
                break;
            case R.id.tv_hyxy:
                Intent intent = new Intent(mContext, ExplainActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_slient);
                break;
            case R.id.btn_zf:
                if (!checkBox.isChecked()) {
                    toast("请阅读并同意《会员协议》！");
                    return;
                }
                if (payNum.equals("￥ 0")) {
                    type = "auto";
                    getOrderParam();
                } else {
                    new AlertDialog.Builder(mContext)
                            .setTitle("请选择支付方式")
                            .setItems(zfType, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int position) {
                                    switch (position) {
                                        case 0:
                                            type = "alipay";
                                            break;
                                        case 1:
                                            type = "wechat";
                                            break;
                                    }
                                    getOrderParam();
                                }
                            })
                            .show();
                }
                break;
        }
    }

    /**
     * 获取参数
     */
    private void getOrderParam() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("pay_type", type);
        paramsMap.put("configId", payId);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getVipOrderParam(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData>(mContext, null) {
                    @Override
                    public void onNext(BaseData payOrderBeanBaseData) {
                        Log.e("zlz", new Gson().toJson(payOrderBeanBaseData));
                        if (payOrderBeanBaseData.isSuccess()) {
                            switch (type) {
                                case "alipay":
                                    AliParamBean aliParamBean = new Gson().fromJson(new Gson().toJson(payOrderBeanBaseData.getData()), new TypeToken<AliParamBean>() {
                                    }.getType());
                                    payByAli(aliParamBean.getAliResult());
                                    break;
                                case "wechat":
                                    WechatParamBean wechatParamBean = new Gson().fromJson(new Gson().toJson(payOrderBeanBaseData.getData()), new TypeToken<WechatParamBean>() {
                                    }.getType());
                                    payByWechat(wechatParamBean);
                                    break;
                                case "auto":
                                    toast("开通成功");
                                    setResult(RESULT_OK);
                                    finish();
                                    break;
                            }
                        } else {

                        }
                    }
                }));
    }

    /**
     * 支付宝支付
     */
    private void payByAli(String param) {
        final String orderInfo = param;   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {

            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            /**
             * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            switch (resultStatus) {
                case "9000":
                    toast("充值成功");
                    setResult(RESULT_OK);
                    finish();
                    //成功
                    break;
                case "4000":
                    showDialog("订单支付失败");
                    break;
                case "6001":
                    showDialog("取消支付");
                    break;
                default:
                    showDialog("支付失败");
                    break;
            }
//            if (TextUtils.equals(resultStatus, "9000")) {
//                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                Log.e("zlz", "succ");
//            } else {
//                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                Log.e("zlz", "else");
//            }
        }
    };

    /**
     * 微信支付
     */
    private void payByWechat(WechatParamBean beans) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(mContext, Constans.WECHAT_APPID);
        msgApi.registerApp(Constans.WECHAT_APPID);
        PayReq payReq = new PayReq();
        payReq.appId = beans.getSignMap().getAppid();
        payReq.partnerId = beans.getSignMap().getPartnerid();
        payReq.prepayId = beans.getSignMap().getPrepayid();
        payReq.packageValue = beans.getSignMap().getPackageX();
        payReq.nonceStr = beans.getSignMap().getNoncestr();
        payReq.timeStamp = beans.getSignMap().getTimestamp() + "";
        payReq.sign = beans.getSignMap().getSign();
        msgApi.sendReq(payReq);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(NotifyBean message) {
        Log.e("zlz", message.getMsg());
        if (message.getId() == 0) {
            toast("充值成功");
            setResult(RESULT_OK);
            finish();
        } else {
            showDialog("支付失败:errcode:" + message.getId());
        }
    }
}


