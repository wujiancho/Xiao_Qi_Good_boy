package com.lx.xqgg.util;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lx.xqgg.MainActivity;
import com.lx.xqgg.R;
import com.lx.xqgg.TranslucentActivity;
import com.lx.xqgg.ui.home.bean.AppVersionBean;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

/**
 * @author zlz
 * 2019/6/3
 */
public class UpdateUtil {
    public static File apk;
    public static final String apkName = "xiaoqiguaiguai.apk";//换新项目时需要改名
    private static NotificationCompat.Builder builder;
    private static NotificationManager notificationManager;
    private static MaterialDialog permissionDialog;
    private static boolean showNotification;

    public static void startUpdate(final Activity activity, final AppVersionBean versionBean, boolean showNotification) {
        UpdateUtil.showNotification = showNotification;
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_update, null);
        if(!TextUtils.isEmpty(versionBean.getVersionname())) {
            ((TextView) view.findViewById(R.id.tv_new_version)).setText(versionBean.getVersionname() + "");
        }
        if(!TextUtils.isEmpty(versionBean.getContent())) {
            ((TextView) view.findViewById(R.id.tv_update_content)).setText(versionBean.getContent().replaceAll(";", "\n"));
        }
        new MaterialDialog.Builder(activity)
                .title("更新提示")
                .icon(ContextCompat.getDrawable(activity, R.drawable.logo))
                .limitIconToDefaultSize()
                .customView(view, true)
//                .content("最新版本：" + versionBean.getVersionname() + "\n" + "更新内容：" + versionBean.getContent())
                .positiveText("更新")
                .negativeText("取消")
                .cancelable(false)
                .negativeColorRes(R.color.ctid_main_bg)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        ToastUtils.showShortToast(activity, "更新未完成");
                        Toast.makeText(activity,"更新未完成",Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AppExitUtil.exitAPP(activity);
                            }
                        }, 1500);
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        MaterialDialog progressDialog = new MaterialDialog.Builder(activity)
                                .progress(false, 100)
                                .progressIndeterminateStyle(true)
                                .content("正在下载中...")
                                .cancelable(false)
                                .build();
//                        if (getAndCheckDownloadApk(activity) != null) {
//                            installAPK(activity, getAndCheckDownloadApk(activity));
//                        } else {
//                            downloadAPK(activity, versionBean.getApk_url(), progressDialog);
//                        }
                        downloadAPK(activity, versionBean.getDownloadurl(), progressDialog);
                    }
                })
                .show();

    }

    public static File getAndCheckDownloadApk(Context context) {
        apk = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), apkName);
        return apk;
//        PackageInfo pkgInfo = getApkInfo(apk.getAbsolutePath(), context);
//        if (apk.exists()
//                && pkgInfo != null
//                && pkgInfo.versionCode > AppApplicationUtil.getVersionCode(context)
//                && pkgInfo.applicationInfo.packageName.equals(context.getPackageName())
//                && !pkgInfo.versionName.equals(AppApplicationUtil.getVersionName(context))
//        ) {
//            return apk;
//        } else {
//            if (apk.exists()) {
//                AppFileUtil.delFile(apk, true);
//            }
//            return null;
//        }

    }

    /**
     * 获取apk包的信息：版本号，名称，图标等
     *
     * @param absPath apk包的绝对路径
     * @param context
     */
    public static PackageInfo getApkInfo(String absPath, Context context) {

        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = pm.getPackageArchiveInfo(absPath, PackageManager.GET_ACTIVITIES);
        if (pkgInfo != null) {
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            /* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
            appInfo.sourceDir = absPath;
            appInfo.publicSourceDir = absPath;
            String appName = pm.getApplicationLabel(appInfo).toString();// 得到应用名
            String packageName = appInfo.packageName; // 得到包名
            String version = pkgInfo.versionName; // 得到版本信息
            /* icon1和icon2其实是一样的 */
            Drawable icon1 = pm.getApplicationIcon(appInfo);// 得到图标信息
            Drawable icon2 = appInfo.loadIcon(pm);
            String pkgInfoStr = String.format("PackageName:%s, Vesion: %s, AppName: %s", packageName, version, appName);
        }
        return pkgInfo;
    }

    public static void installAPK(final Activity activity, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean haveInstallPermission = activity.getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                //TODO  去开启权限
                Uri packageURI = Uri.parse("package:" + activity.getPackageName());
                //注意这个是8.0新API
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                activity.startActivityForResult(intent, MainActivity.UNKNOW_APP_SOURCE_CODE);
            } else {
                //TODO 安装APK
                installAPKFinal(activity,file);
            }
        }else {
            installAPKFinal(activity,file);
        }

    }

    public static void installAPKFinal(final Activity activity, File file){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileProvider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        activity.startActivityForResult(intent, MainActivity.ACTION_INSTALL);
    }


    public static void downloadAPK(final Activity activity, String mDownloadUrl, final MaterialDialog dialog) {
        apk = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), apkName);
        AppFileUtil.delFile(apk, true);
        if (showNotification) {
            showNotification(activity);
        }
        final RequestParams requestParams = new RequestParams(mDownloadUrl);
        // 为RequestParams设置文件下载后的保存路径
        requestParams.setSaveFilePath(apk.getPath());
        // 下载完成后自动为文件命名
        requestParams.setAutoRename(false);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (isDownloading && dialog != null) {
                    if (!dialog.isShowing()) {
                        dialog.show();
                    }
                    double progress = ((double) current / (double) total);
                    dialog.setProgress((int) (progress * 100));
                    if (notificationManager != null) {
                        builder.setProgress(100, (int) (progress * 100), false);
                        builder.setContentText("正在下载" + (int) (progress * 100) + "%");
                        notificationManager.notify(0, builder.build());
                    }

                }
            }

            @Override
            public void onSuccess(File result) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                Intent intent = new Intent(activity, TranslucentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(activity, 9999, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                if (builder != null) {
                    builder.setContentText("下载完毕，点击安装");
                    builder.setProgress(0, 0, false);
                    builder.setContentIntent(pendingIntent);
                    notificationManager.notify(0, builder.build());
                }
                Toast.makeText(activity,"下载成功",Toast.LENGTH_SHORT).show();
                installAPK(activity, result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                AppFileUtil.delFile(apk, true);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                AppFileUtil.delFile(apk, true);
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private static void showNotification(Activity activity) {
        String id = "update";
        String name = "小麒乖乖";
        notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(activity, "update")
                .setColor(Color.RED)
                .setContentTitle("小麒乖乖")
                .setContentText("正在下载")
                .setAutoCancel(true)
                .setOngoing(true)
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo))
                .setProgress(100, 0, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            builder.setChannelId(id);
        }
        notificationManager.notify(0, builder.build());

    }
}
