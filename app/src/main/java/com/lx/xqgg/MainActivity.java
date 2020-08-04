package com.lx.xqgg;

import androidx.annotation.Nullable;
import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;

import com.google.gson.Gson;
import com.lx.xqgg.api.ApiManage;
import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.base.BaseActivityflase;
import com.lx.xqgg.base.BaseApplication;
import com.lx.xqgg.base.BaseData;
import com.lx.xqgg.base.BaseSubscriber;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.home.HomeFragment;
import com.lx.xqgg.ui.home.PicFragment;
import com.lx.xqgg.ui.home.UserServiceFragment;
import com.lx.xqgg.ui.home.bean.AdvertBean;
import com.lx.xqgg.ui.home.bean.AppVersionBean;
import com.lx.xqgg.ui.home.bean.UserServiceBean;
import com.lx.xqgg.ui.order.OrderFragment;
import com.lx.xqgg.ui.person.PersonFragment;
import com.lx.xqgg.ui.product.ProductFragment;
import com.lx.xqgg.util.AppApplicationUtil;
import com.lx.xqgg.util.AppExitUtil;
import com.lx.xqgg.util.JPushUtil;
import com.lx.xqgg.util.UpdateUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements OnTabSelectListener {
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private HomeFragment homeFragment;
    private ProductFragment productFragment;
    private OrderFragment orderFragment;
    private PersonFragment personFragment;
    private UserServiceFragment userServiceFragment;
    private PicFragment picFragment;

    public static final int ACTION_INSTALL = 10085;
    public static final int UNKNOW_APP_SOURCE_CODE = 10088;

    private String type;
    private  Boolean code=true;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }



    @Override
    protected void initView() {
        homeFragment = new HomeFragment();
        productFragment = new ProductFragment();
        orderFragment = new OrderFragment();
        personFragment = new PersonFragment();
        bottomBar.setOnTabSelectListener(this);
        loadMultipleRootFragment(R.id.contentContainer, 0, homeFragment, productFragment, orderFragment, personFragment);
      //  JPushUtil.initJPush(mContext, SharedPrefManager.getUser().getMobile(), null);
        try {
            type = getIntent().getStringExtra("type");
            Log.e("zlz", type + "");
            if ("order".equals(type)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bottomBar.selectTabAtPosition(2);
                        showHideFragment(orderFragment);
                    }
                }, 500);
            }
        } catch (Exception e) {
            Log.e("zlz", e.toString());
        }
    }


    @Override
    protected void initData() {
        checkUpdate(true);
       getUserServiceInfo();
      /* if(){

       }*/
        getAdvert();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            type = getIntent().getStringExtra("type");
            Log.e("zlz", type + "");
            if ("order".equals(type)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bottomBar.selectTabAtPosition(2);
                        showHideFragment(orderFragment);
                    }
                }, 500);
            }
        } catch (Exception e) {
            Log.e("zlz", e.toString());
        }
    }

    @Override
    public void onTabSelected(int tabId) {
        switch (tabId) {
            case R.id.tab_home:
                showHideFragment(homeFragment);
                break;
            case R.id.tab_stock:
                showHideFragment(productFragment);
                break;
            case R.id.tab_order:
                showHideFragment(orderFragment);
                break;
            case R.id.tab_person:
                showHideFragment(personFragment);
                break;
        }
    }

    /**
     * 获取服务商信息
     */
    private void getUserServiceInfo() {
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
                            if (objectBaseData.getCode() != 1) {
                                userServiceFragment = new UserServiceFragment(objectBaseData);
                                userServiceFragment.show(getSupportFragmentManager(), "");
                            }
                        }
                    }
                }));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return AppExitUtil.exitApp(this);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_INSTALL) {
            if (resultCode != RESULT_OK) {
                checkUpdate(true);
            }
        }
        if (requestCode == UNKNOW_APP_SOURCE_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (!haveInstallPermission) {//没有权限
                    //TODO  去开启权限
                    toast("更新未完成");
                } else {
                    //TODO 安装APK
                    UpdateUtil.installAPKFinal(this, UpdateUtil.apk);
                }
            } else {
                UpdateUtil.installAPKFinal(this, UpdateUtil.apk);
            }
        }
    }

    private void checkUpdate(final boolean isAuto) {
        UpdateUtil.getAndCheckDownloadApk(BaseApplication.getInstance());
        ApiManage.getInstance().getMainApi().checkUpdate(AppApplicationUtil.getVersionCode(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<AppVersionBean>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<AppVersionBean> responseData) {
                        Log.e("update", new Gson().toJson(responseData));
                        if (responseData.isSuccess()) {
                            if (null != responseData.getData()) {
                                final AppVersionBean appVersion = responseData.getData();
                                UpdateUtil.startUpdate(MainActivity.this, appVersion, true);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Log.e("zlz", t.toString());
                    }
                });
    }

    /**
     * 获取广告图
     */
    private void getAdvert() {
        ApiManage.getInstance().getMainApi().getAdvert(SharedPrefManager.getUser().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseSubscriber<BaseData<List<AdvertBean>>>(mContext, null) {
                    @Override
                    public void onNext(BaseData<List<AdvertBean>> listBaseData) {
                        Log.e("advert", new Gson().toJson(listBaseData));
                        if (listBaseData.isSuccess()) {
                            if (listBaseData.getData() != null && listBaseData.getData().size() > 0) {
                                AdvertBean advertBean = listBaseData.getData().get(0);
                                picFragment = new PicFragment(advertBean);
                                picFragment.show(getSupportFragmentManager(), "");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Log.e("zlz", t.toString());
                    }
                });
    }

}
