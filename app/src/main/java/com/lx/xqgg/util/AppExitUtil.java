package com.lx.xqgg.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.login.LoginActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * APP应用退出(按两次)
 * Created by xiarh on 2017/9/13.
 */

public class AppExitUtil {

    private static Boolean isExit = false;

    /**
     * 退出App程序应用
     *
     * @param context 上下文
     * @return boolean True退出|False提示
     */
    public static boolean exitApp(Context context) {
        Timer tExit;
        if (isExit == false) {
            isExit = true;
            Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            //创建定时器
            tExit = new Timer();
            //如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    //取消退出
                    isExit = false;
                }
            }, 2000);
        } else {
            Log.d("zlz", "exit");

            AppActivityTaskManager.getInstance().removeAllActivity();
            //创建ACTION_MAIN
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //启动ACTION_MAIN，直接回到桌面
            context.startActivity(intent);
            MobclickAgent.onKillProcess(context);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        return isExit;
    }

    /**
     * 直接退出
     *
     * @param context
     */
    public static void exitAPP(Context context) {
        AppActivityTaskManager.getInstance().removeAllActivity();
        //创建ACTION_MAIN
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //启动ACTION_MAIN，直接回到桌面
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 账号被顶
     *
     * @param context
     */
    public static void logout(Context context) {
        SharedPrefManager.clearLoginInfo();
        JPushUtil.initJPush(context, "", null);
        Intent intent_login = new Intent();
        intent_login.setClass(context, LoginActivity.class);
        intent_login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //关键的一句，将新的activity置为栈顶
        context.startActivity(intent_login);
        AppActivityTaskManager.getInstance().removeAllActivity();
    }
}
