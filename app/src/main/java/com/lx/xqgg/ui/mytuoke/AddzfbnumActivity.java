package com.lx.xqgg.ui.mytuoke;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.lx.xqgg.ui.login.bean.MsgBean;
import com.lx.xqgg.ui.mytuoke.bean.InsertOrUpdateZfbBean;
import com.lx.xqgg.ui.mytuoke.bean.getZfbBean;
import com.lx.xqgg.util.CountDownTimerUtils;
import com.lx.xqgg.util.FastClickUtil;

import java.util.HashMap;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddzfbnumActivity extends BaseActivity {


    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.zfb_name)
    EditText zfbName;
    @BindView(R.id.zfb_num)
    EditText zfbNum;
    @BindView(R.id.zfb_phone)
    EditText zfbPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_getCode)
    Button btnGetCode;
    @BindView(R.id.btn_addzfbfinish)
    Button btnAddzfbfinish;
    private CountDownTimerUtils countDownTimer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addzfbnum;
    }

    @Override
    protected void initView() {
        tvTitle.setText("修改支付宝");
    }

    @Override
    protected void initData() {
        zfbmassage();
        countDownTimer = new CountDownTimerUtils(btnGetCode, 60000, 1000, handler);

    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (countDownTimer != null) {
                countDownTimer.onFinish();
                countDownTimer.cancel();
            }
        }
    };

    @OnClick()
    public void onViewClicked() {
        finish();
    }


    @OnClick({R.id.btn_getCode, R.id.btn_addzfbfinish,R.id.v_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_getCode:
                if (zfbPhone.getText().toString().length() < 11) {
                    toast("请输入正确的手机号码");
                    return;
                }
                if (FastClickUtil.isFastClick()) {
                    return;
                }
                addSubscribe(ApiManage.getInstance().getMainApi().getMsg(zfbPhone.getText().toString(), "zfb",null,null)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseSubscriber<MsgBean>(mContext, null) {
                            @Override
                            public void onNext(MsgBean msgBean) {
                                Log.e("zlz", new Gson().toJson(msgBean));

                                if (msgBean.isSuccess()) {
                                    countDownTimer.start();
                                    toast(msgBean.getMessage());
                                } else {
                                    toast(msgBean.getMessage());
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                super.onError(t);
                                toast(t.getMessage());
                            }
                        }));
                break;
            case R.id.btn_addzfbfinish:
                if (zfbName.getText().toString().length()<2){
                    toast("请输入支付宝名称");
                    return;
                }
                if (zfbNum.getText().toString().length()<10){
                    toast("请输入正确的支付宝账号");
                    return;
                }
                if (zfbPhone.getText().toString().length()<11){
                    toast("请输入正确的支付宝绑定的手机号");
                    return;
                }
                if (etCode.getText().toString().length() < 4) {
                    toast("请输入正确的短信验证码");
                    return;
                }
                addzfbmassage();
                break;
            case R.id.v_close:
                finish();
                break;
        }
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
                        if (data!=null){
                           zfbName.setText(data.getZfb_name());
                           zfbNum.setText(data.getZfb_account());
                           zfbPhone.setText(data.getUser_phone());
                        }else{
                            zfbName.setText("");
                            zfbNum.setText("");
                            zfbPhone.setText("");
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
                    }
                }));
    }

    //绑定用户支付宝信息
    private void addzfbmassage() {
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("token", SharedPrefManager.getUser().getToken());
        paramsMap.put("zfb_name", zfbName.getText().toString());
        paramsMap.put("zfb_account", zfbNum.getText().toString());
        paramsMap.put("user_phone", zfbPhone.getText().toString());
        paramsMap.put("captcha", etCode.getText().toString());
        paramsMap.put("id", "");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
        addSubscribe(ApiManage.getInstance().getMainApi().getInsertOrUpdateZfb(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<InsertOrUpdateZfbBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<InsertOrUpdateZfbBean> insertOrUpdateZfbBeanBaseData) {
                            toast("支付宝绑定成功");
                            finish();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        toast(t.getMessage());
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
