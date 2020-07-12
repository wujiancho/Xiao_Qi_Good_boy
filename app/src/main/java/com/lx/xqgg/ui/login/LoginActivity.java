package com.lx.xqgg.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lx.xqgg.FaceMainActivity;
import com.lx.xqgg.MainActivity;
import com.lx.xqgg.R;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.base.Constans;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.explain.ExplainActivity;
import com.lx.xqgg.ui.home.bean.UserServiceBean;
import com.lx.xqgg.ui.login.bean.LoginBean;
import com.lx.xqgg.ui.login.bean.MsgBean;
import com.lx.xqgg.ui.login.bean.UserBean;
import com.lx.xqgg.util.AppApplicationUtil;
import com.lx.xqgg.util.CountDownTimerUtils;
import com.lx.xqgg.util.FastClickUtil;
import com.lx.xqgg.util.JPushUtil;

import java.util.HashMap;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.v_close)
    View vClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toobar)
    ConstraintLayout toobar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_getCode)
    Button btnGetCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.checkbox)
    CheckBox checkBox;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    private CountDownTimerUtils countDownTimer;

    private long lastClickTime = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        vClose.setVisibility(View.INVISIBLE);
        tvTitle.setText("登录");
        tvVersion.setText("V" + AppApplicationUtil.getVersionName(mContext));
    }

    @Override
    protected void initData() {
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

    @OnClick({R.id.btn_getCode, R.id.btn_login, R.id.tv_announce, R.id.tv_user_announce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_getCode:
                if (etPhone.getText().toString().length() < 11) {
                    toast("请输入正确的手机号码");
                    return;
                }
                if (FastClickUtil.isFastClick()) {
                    return;
                }
                addSubscribe(ApiManage.getInstance().getMainApi().getMsg(etPhone.getText().toString(), "mobilelogin",null,null)
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
                        }));
                break;
            case R.id.btn_login:
                if (etPhone.getText().toString().length() < 11) {
                    toast("请输入正确的手机号码");
                    return;
                }
                if (etCode.getText().toString().length() < 4) {
                    toast("请输入正确的短信验证码");
                    return;
                }
                if (!checkBox.isChecked()) {
                    toast("请阅读并同意声明");
                    return;
                }
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("mobile", etPhone.getText().toString());
                paramsMap.put("captcha", etCode.getText().toString());
                paramsMap.put("jpushId", etPhone.getText().toString());
                paramsMap.put("appId", Constans.APPID);
                paramsMap.put("identity", "android");
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                addSubscribe(ApiManage.getInstance().getMainApi().login(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new BaseSubscriber<LoginBean>(mContext, null) {
                            @Override
                            public void onNext(LoginBean loginBean) {
                                if (loginBean.isSuccess()) {
                                    //登录成功
                                    if (loginBean.getData() != null) {
                                        UserBean userBean = loginBean.getData();
                                        SharedPrefManager.setUser(userBean);
                                        JPushUtil.initJPush(mContext, etPhone.getText().toString(), null);
                                        HashMap<String, Object> paramsMap = new HashMap<>();
                                        paramsMap.put("token", SharedPrefManager.getUser().getToken());
                                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                                        addSubscribe(ApiManage.getInstance().getMainApi().getUserServiceInfo(body)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribeWith(new BaseSubscriber<BaseData<UserServiceBean>>(mContext, null) {
                                                    @Override
                                                    public void onNext(BaseData<UserServiceBean> objectBaseData) {
                                                        Log.e("zlz", new Gson().toJson(objectBaseData));
                                                        if (objectBaseData.isSuccess()) {
                                                            if (0 == objectBaseData.getCode() ||
                                                                    -1 == objectBaseData.getCode() ||
                                                                    -2 == objectBaseData.getCode()) {
                                                                startActivity(new Intent(LoginActivity.this, FaceMainActivity.class));
                                                                finish();
                                                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                                return;
                                                            }
                                                        }
                                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                        finish();
                                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                    }

                                                    @Override
                                                    public void onError(Throwable t) {
                                                        super.onError(t);
                                                        toast(t.toString());
                                                        startActivity(new Intent(LoginActivity.this, FaceMainActivity.class));
                                                        finish();
                                                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                    }
                                                }));


                                    }
                                } else {
                                    showDialog(loginBean.getMessage());
                                }
                            }
                        }));
                break;
            case R.id.tv_announce:
                Intent intent = new Intent(mContext, ExplainActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_slient);
                break;
            case R.id.tv_user_announce:
                Intent intent1 = new Intent(mContext, ExplainActivity.class);
                intent1.putExtra("type", 0);
                startActivity(intent1);
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_slient);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
