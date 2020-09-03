package com.lx.xqgg.util;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import com.lx.xqgg.base.BaseApplication;

public class ScreenUtil {
    private ScreenUtil() {
    }

    public static int getScreenWidth() {

        //context的方法，获取windowManager
        WindowManager windowManager = (WindowManager) BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕对象
        Display defaultDisplay = windowManager.getDefaultDisplay();
        //获取屏幕的宽、高，单位是像素
        int width = defaultDisplay.getWidth();
        int height = defaultDisplay.getHeight();

        return width;
    }

    public static int getScreenHeight() {

        //context的方法，获取windowManager
        WindowManager windowManager = (WindowManager)  BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕对象
        Display defaultDisplay = windowManager.getDefaultDisplay();
        //获取屏幕的宽、高，单位是像素
        int width = defaultDisplay.getWidth();
        int height = defaultDisplay.getHeight();
        return height;
    }


}
