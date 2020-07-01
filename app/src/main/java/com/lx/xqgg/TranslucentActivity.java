package com.lx.xqgg;

import android.content.Intent;
import android.graphics.Color;

import com.lx.xqgg.base.BaseActivity;
import com.lx.xqgg.util.StatusBarUtil;
import com.lx.xqgg.util.UpdateUtil;


public class TranslucentActivity extends BaseActivity {

    public static final int ANDROID_O_INSTALL_REQUEST = 10086;
    public static final int ANDROID_INSTALL = 10085;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        LogUtil.d("TranslucentActivity "+requestCode);
        switch (requestCode) {
            case ANDROID_O_INSTALL_REQUEST:
                if (resultCode == RESULT_OK) {
                    UpdateUtil.installAPK(mContext, UpdateUtil.apk);
                }
                finish();
                break;
            case ANDROID_INSTALL:
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_translucent;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setStatusBarColor(this, Color.TRANSPARENT);
        if (UpdateUtil.getAndCheckDownloadApk(this) != null) {
            UpdateUtil.installAPK(this, UpdateUtil.apk);
        }
    }

    @Override
    protected void initData() {

    }
}
