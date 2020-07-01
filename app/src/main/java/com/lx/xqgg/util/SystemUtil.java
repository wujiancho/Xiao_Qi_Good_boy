package com.lx.xqgg.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import com.lx.xqgg.base.BaseApplication;
import com.lx.xqgg.config.Config;
import com.lx.xqgg.helper.SharedPrefManager;
import com.lx.xqgg.ui.login.bean.UserBean;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;

/**
 * @author zlz
 * 2019/3/11
 */

public class SystemUtil {
    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     * @throws
     */
    public static String getTotalCacheSize(Context context) {
        long cacheSize = 0;
        try {
            cacheSize = getFolderSize(context.getCacheDir());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cacheSize += getFolderSize(context.getExternalCacheDir());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    /**
     * 清除Cookie
     */
    public static void clearAllCookie() {
        CookieSyncManager.createInstance(BaseApplication.getInstance());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }


    /**
     * 设置Cookie
     */
    public static void setUserInfoToCookie(UserBean userBean) {

        if (!SharedPrefManager.isLogin()) {
            return;
        }
        CookieSyncManager.createInstance(BaseApplication.getInstance());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        Gson gson = new Gson();
        cookieManager.setCookie(Config.URL, "user=" + gson.toJson(userBean));
        cookieManager.setCookie(Config.URL, "pt=A");
//        cookieManager.setCookie(Config.URL,"openId="+userBean.getWxOpenId());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync(); // forces sync manager to sync now
        }

    }

    public static void setType() {
        CookieSyncManager.createInstance(BaseApplication.getInstance());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(Config.URL, "pt=A");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync(); // forces sync manager to sync now
        }
    }


    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 获取大小
     *
     * @param file
     * @return
     * @throws
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 检验是否root
     *
     * @return
     */
    public static boolean isRooted() {
        String[] paths = {"/system/xbin/", "/system/bin/", "/system/sbin/", "/sbin/", "/vendor/bin/", "/su/bin/"};
        try {
            for (int i = 0; i < paths.length; i++) {
                String path = paths[i] + "su";
                if (new File(path).exists()) {
                    String execResult = exec(new String[]{"ls", "-l", path});
                    Log.d("cyb", "isRooted=" + execResult);
                    if (TextUtils.isEmpty(execResult) || execResult.indexOf("root") == execResult.lastIndexOf("root")) {
                        return false;
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String exec(String[] exec) {
        String ret = "";
        ProcessBuilder processBuilder = new ProcessBuilder(exec);
        try {
            Process process = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ret += line;
            }
            process.getInputStream().close();
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

}
