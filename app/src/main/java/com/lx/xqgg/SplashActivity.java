package com.lx.xqgg;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.home.bean.UserServiceBean;
import com.lx.xqgg.ui.login.LoginActivity;
import com.lx.xqgg.ui.login.bean.UserInfoBean;
import com.lx.xqgg.util.JPushUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SplashActivity extends AppCompatActivity {
    private MaterialDialog permissionDialog;
    private static int PERMISSION_CODE = 1000;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView( R.layout.spalsh);
        if (!this.isTaskRoot()) {
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        permissionDialog = new MaterialDialog.Builder(this)
                .title(R.string.permission_application)
                .content(R.string.permission_application_content)
                .cancelable(false)
                .positiveText(R.string.setting)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivityForResult(intent, PERMISSION_CODE);
                    }
                })
                .negativeText(R.string.no)
                .negativeColorRes(R.color.txt_normal)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .build();
        doCheckPermission();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @SuppressLint("CheckResult")
    private void doCheckPermission() {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission.request(Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.CAMERA
                , Manifest.permission.RECORD_AUDIO
                , Manifest.permission.READ_PHONE_STATE

        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            if (SharedPrefManager.isLogin()) {
                                //登录过刷新用户信息
                                ApiManage.getInstance().getMainApi().getUserInfo(SharedPrefManager.getUser().getToken())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeWith(new BaseSubscriber<BaseData<UserInfoBean>>(getApplicationContext(), null) {
                                            @Override
                                            public void onNext(BaseData<UserInfoBean> userInfoBeanBaseData) {
                                                Log.e("zlz", new Gson().toJson(userInfoBeanBaseData));
                                                if (userInfoBeanBaseData.isSuccess()) {
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            SharedPrefManager.setUserInfo(userInfoBeanBaseData.getData());
                                                            JPushUtil.initJPush(SplashActivity.this, SharedPrefManager.getUser().getMobile(), null);
                                                            HashMap<String, Object> paramsMap = new HashMap<>();
                                                            paramsMap.put("token", SharedPrefManager.getUser().getToken());
                                                            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsMap));
                                                            ApiManage.getInstance().getMainApi().getUserServiceInfo(body)
                                                                    .subscribeOn(Schedulers.io())
                                                                    .observeOn(AndroidSchedulers.mainThread())
                                                                    .subscribeWith(new BaseSubscriber<BaseData<UserServiceBean>>(SplashActivity.this, null) {
                                                                        @Override
                                                                        public void onNext(BaseData<UserServiceBean> objectBaseData) {
                                                                            Log.e("zlz", new Gson().toJson(objectBaseData));
                                                                            if (objectBaseData.isSuccess()) {
                                                                                if (0 == objectBaseData.getCode() ||
                                                                                        -1 == objectBaseData.getCode() ||
                                                                                        -2 == objectBaseData.getCode()) {
                                                                                    startActivity(new Intent(SplashActivity.this, FaceMainActivity.class));
                                                                                    finish();
                                                                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                                                    return;
                                                                                }
                                                                            }
                                                                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                                                            finish();
                                                                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                                        }

                                                                        @Override
                                                                        public void onError(Throwable t) {
                                                                            super.onError(t);
                                                                            startActivity(new Intent(SplashActivity.this, FaceMainActivity.class));
                                                                            finish();
                                                                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                                        }
                                                                    });
//                                                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                                                            finish();
//                                                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                        }
                                                    }, 1000);

                                                } else {
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast toast = Toast.makeText(SplashActivity.this, userInfoBeanBaseData.getMessage() + "", Toast.LENGTH_SHORT);
                                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                                            toast.show();
                                                            SharedPrefManager.clearLoginInfo();
                                                            JPushUtil.initJPush(SplashActivity.this, "", null);
                                                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                        }
                                                    }, 1000);

                                                }
                                            }

                                            @Override
                                            public void onError(Throwable t) {
                                                super.onError(t);
                                                Toast.makeText(getApplicationContext(), t.toString()+"请检查网络或者服务器网络异常", Toast.LENGTH_LONG).show();
                                                finish();
                                            }
                                        });

                            } else {
                                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }

                        } else {
                            if (!permissionDialog.isShowing()) {
                                permissionDialog.show();
                            }
                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_CODE) {
            doCheckPermission();
        }
    }
}
