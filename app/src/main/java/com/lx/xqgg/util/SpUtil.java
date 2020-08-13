package com.lx.xqgg.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.lx.xqgg.base.BaseApplication;

public class SpUtil  {
    //单例模式
    private static SpUtil instance;
    private final SharedPreferences user;
    private final SharedPreferences.Editor edit;

    private SpUtil() {
        user = BaseApplication.getInstance().getSharedPreferences("user", Context.MODE_PRIVATE);
        edit = user.edit();
    }

    public static SpUtil getInstance() {
        if(instance==null){
            synchronized (SpUtil.class){
                if(instance==null){
                    instance=new SpUtil();
                }
            }
        }
        return instance;
    }
    //缓存
    public void saveString(String key,String value){
        edit.putString(key, value);
        edit.commit();
    }
    public void saveInt(String key,int value){

        edit.putInt(key, value);
        edit.commit();
    }
    public void saveBoolean(String key,boolean value){
        edit.putBoolean(key, value);
        edit.commit();
    }
    //取缓存
    public String getSpString(String key){
        String string = user.getString(key, "");
        return string;
    }
    public int getSpInt(String key){
        int anInt = user.getInt(key, -1);
        return anInt;
    }
    public boolean getSpBoolean(String key){
        boolean aBoolean = user.getBoolean(key, false);
        return aBoolean;
    }
    //删除缓存
    public void removeKey(String key){
        edit.remove(key);
        edit.commit();
    }
    //用户退出，清理资料
    public void cloneUser(){
        edit.remove("userId");
        edit.remove("sessionId");
        edit.commit();
    }
}
