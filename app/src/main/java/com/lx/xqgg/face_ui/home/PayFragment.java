package com.lx.xqgg.face_ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.vip.bean.AliParamBean;
import com.lx.xqgg.ui.vip.bean.NotifyBean;
import com.lx.xqgg.ui.vip.bean.PayListBean;
import com.lx.xqgg.ui.vip.bean.PayResult;
import com.lx.xqgg.ui.vip.bean.WechatParamBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PayFragment extends DialogFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.btn_apply)
    Button btnApply;

    private Unbinder mUnBinder;
    protected CompositeDisposable mCompositeDisposable;
    private List<PayListBean> list = new ArrayList<>();
    private PayAdapter payAdapter;
    private int selectedposition = -1;

    private String name;
    private String phone;
    private String companyName;
    private String zw;

    private String pay_type;

    private String[] zfType = {"支付宝", "微信"};

    public PayFragment(List<PayListBean> list, String name, String phone, String companyName, String zw) {
        this.list = list;
        this.name = name;
        this.phone = phone;
        this.companyName = companyName;
        this.zw = zw;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_pay, null);
        mUnBinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        payAdapter = new PayAdapter(list);
        recyclerView.setAdapter(payAdapter);
        payAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tvPrice.setText("合计：" + list.get(position).getValue());
                selectedposition = position;
                payAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        //设置背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 取消所有订阅
     */
    private void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
        unSubscribe();
    }

    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
        new AlertDialog.Builder(getActivity())
                .setTitle("请选择支付方式")
                .setItems(zfType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        switch (position) {
                            case 0:
                                pay_type = "alipay";
                                break;
                            case 1:
                                pay_type = "wechat";
                                break;
                        }
                        getOrderParam();
                    }
                })
                .show();

    }

    private void getOrderParam() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("pay_type", pay_type);
        paramsMap.put("configId", list.get(selectedposition).getId());
        paramsMap.put("username", name);
        paramsMap.put("company", companyName);
        paramsMap.put("position", zw);
        paramsMap.put("userphone", phone);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getFaceOrder(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData>(getActivity(), "") {
                    @Override
                    public void onNext(BaseData payOrderBeanBaseData) {
                        Log.e("zlz", new Gson().toJson(payOrderBeanBaseData));
                        if (payOrderBeanBaseData.isSuccess()) {
                            switch (pay_type) {
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
                PayTask alipay = new PayTask(getActivity());
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

    public void toast(String str) {
        Toast toast = Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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
                    dismiss();
                    //成功
                    break;
                case "4000":
                    toast("订单支付失败");
                    break;
                case "6001":
                    toast("取消支付");
                    break;
                default:
                    toast("支付失败");
                    break;
            }
        }
    };

    /**
     * 微信支付
     */
    private void payByWechat(WechatParamBean beans) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(getActivity(), Constans.WECHAT_APPID);
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

    public class PayAdapter extends BaseQuickAdapter<PayListBean, BaseViewHolder> {

        public PayAdapter(@Nullable List<PayListBean> data) {
            super(R.layout.item_pay, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PayListBean item) {
            Log.e("zlz", selectedposition + "");
            Log.e("zlz", helper.getLayoutPosition() + "");
            if (selectedposition == helper.getLayoutPosition()) {
                helper.setBackgroundColor(R.id.layout, getResources().getColor(R.color.gray_f0));
            } else {
                helper.setBackgroundColor(R.id.layout, getResources().getColor(R.color.white));
            }
            helper.setText(R.id.tv_name, item.getName());
            helper.setText(R.id.tv_price, "￥" + item.getValue());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(NotifyBean message) {
        Log.e("zlz", message.getMsg());
        if (message.getId() == 0) {
            toast("充值成功");
            dismiss();
        } else {
            toast("支付失败:errcode:" + message.getId());
        }
    }
}
