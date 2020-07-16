package com.lx.xqgg.base;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.lx.xqgg.loader.GlideImageLoader;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
//import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFOptions;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zxy.tiny.Tiny;

import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import cn.jpush.android.api.JPushInterface;

public class BaseApplication extends Application {
    private static BaseApplication instance;

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        x.Ext.init(this);
        Tiny.getInstance().init(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        //初始化二维码工具类
        ZXingLibrary.initDisplayOpinion(this);
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, "5e8d2ea10cafb2461700002b", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//        MobclickAgent.set
        UMConfigure.setLogEnabled(true);
        MobclickAgent.setDebugMode(true);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        PlatformConfig.setWeixin("wxc8d5861b096a0dae", "478f5d767e6a9689358f3221e496ce82");//微信APPID和AppSecret
        PlatformConfig.setQQZone("1110290771", "FvyRCohtmHIdgfGI");

        if (inMainProcess(this)) {
            Tiny.getInstance().init(this);
            CrashReport.initCrashReport(getApplicationContext(), "ca898d5446", false);
            QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

                @Override
                public void onViewInitFinished(boolean arg0) {
                    // TODO Auto-generated method stub
                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                    Log.d("app", " onViewInitFinished is " + arg0);
                }

                @Override
                public void onCoreInitFinished() {
                    // TODO Auto-generated method stub
                }
            };
            //x5内核初始化接口
            QbSdk.initX5Environment(getApplicationContext(), cb);
        }
        Unicorn.init(this, "946df56a8b1b01f0c7628b8bcc353567", options(), new GlideImageLoader(this));
    }


    public static boolean inMainProcess(Context context) {
        String mainProcessName = context.getApplicationInfo().processName;
        String processName = getProcessName();
        return TextUtils.equals(mainProcessName, processName);
    }

    /**
     * 获取当前进程名
     */
    public static String getProcessName() {
        BufferedReader reader = null;
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            reader = new BufferedReader(new FileReader(file));
            return reader.readLine().trim();
        } catch (IOException e) {
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        return options;
    }
}
