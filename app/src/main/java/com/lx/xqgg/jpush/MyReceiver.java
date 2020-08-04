package com.lx.xqgg.jpush;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.lx.xqgg.MainActivity;
import com.lx.xqgg.ui.order.OrderFragment;
import com.lx.xqgg.ui.vip.VipActivity;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        JpushBean jpushBean = new JpushBean();
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.e("zlz", "接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            Log.e("zlz", "接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            jpushBean = new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA), JpushBean.class);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d("zlz", "接收到推送下来的通知");
            int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d("zlz", "接收到推送下来的通知的ID: " + notificationId);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            if (isAppAlive(context, "com.lx.xqgg")) {
                if ("order".equals(jpushBean.getUrl())) {
                    Intent intent1 = new Intent(context, MainActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    intent1.putExtra("type", "order");
                    context.startActivity(intent1);
                 }else {
                    Intent intent1 = new Intent(context, VipActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    context.startActivity(intent1);
                }

            } else {
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.lx.xqgg");
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                Bundle args = new Bundle();
                launchIntent.putExtra("launchargs", args);
                context.startActivity(launchIntent);
            }
        }
    }

    /**
     * 判断APP进程是否存活
     *
     * @param context
     * @param packageName
     * @return
     */
    private boolean isAppAlive(Context context, String packageName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> progressInfos = manager.getRunningAppProcesses();
        for (int i = 0; i < progressInfos.size(); i++) {
            if (progressInfos.get(i).processName.equals(packageName))
                return true;
        }
        return false;
    }
}
