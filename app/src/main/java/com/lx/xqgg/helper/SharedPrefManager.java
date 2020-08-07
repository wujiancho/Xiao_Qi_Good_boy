package com.lx.xqgg.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.xqgg.api.ImitationexaminationBean;
import com.lx.xqgg.base.BaseApplication;
import com.lx.xqgg.ui.city.bean.CityBean;
import com.lx.xqgg.ui.city.bean.CityHistoryBean;
import com.lx.xqgg.ui.login.bean.UserBean;
import com.lx.xqgg.ui.login.bean.UserInfoBean;
import com.lx.xqgg.ui.product.bean.CateBean;
import com.lx.xqgg.ui.vip.bean.PayListBean;

import java.util.ArrayList;
import java.util.List;

public class SharedPrefManager {
    /**
     * 向导信息
     **/
    private static final String GUIDE_SHARED = "guid_shared";
    /**
     * 账号信息
     **/
    private static final String ACCOUNT_SHARED = "account_shared";
    // 系统设置信息
    private static final String KEY_SETTING_SHARED = "setting_shared";

    private static SharedPreferences GUIDE = BaseApplication.getInstance().getSharedPreferences(GUIDE_SHARED, Context.MODE_PRIVATE);

    private static SharedPreferences ACCOUNT = BaseApplication.getInstance().getSharedPreferences(ACCOUNT_SHARED, Context.MODE_PRIVATE);

    private static SharedPreferences SETTING = BaseApplication.getInstance().getSharedPreferences(KEY_SETTING_SHARED, Context.MODE_PRIVATE);

    public static boolean isFirstLogin() {
        return GUIDE.getBoolean("is_first_login", true);
    }

    public static void setFirstLogin(boolean isFirstLogin) {
        GUIDE.edit().putBoolean("is_first_login", isFirstLogin).commit();
    }

    /**
     * 登录接口返回的信息
     *
     * @param user
     */
    public static void setUser(UserBean user) {
        setLogin(true);
        ACCOUNT.edit().putString("user_info", new Gson().toJson(user)).commit();
    }

    public static UserBean getUser() {
        UserBean userBean = new Gson().fromJson(ACCOUNT.getString("user_info", ""), UserBean.class);
        return userBean;
    }

    /**
     * 仿检查接口返回的信息
     *
     * @param
     */
    public static void setImitationexamination(ImitationexaminationBean imitationexamination) {
        ACCOUNT.edit().putString("imitation_examination", new Gson().toJson(imitationexamination)).commit();
    }

    public static ImitationexaminationBean getImitationexamination() {
        ImitationexaminationBean imitationexaminationBean = new Gson().fromJson(ACCOUNT.getString("imitation_examination", ""), ImitationexaminationBean.class);
        return imitationexaminationBean;
    }

    /**
     * 用户详细信息
     *
     * @param user
     */
    public static void setUserInfo(UserInfoBean user) {
        ACCOUNT.edit().putString("user_info_detail", new Gson().toJson(user)).commit();

    }

    public static UserInfoBean getUserInfo() {
        UserInfoBean userBean = new Gson().fromJson(ACCOUNT.getString("user_info_detail", ""), UserInfoBean.class);
        return userBean;
    }

//    /**
//     * 服务商信息
//     *
//     * @param user
//     */
//    public static void setServiceInfo(BaseData<UserServiceBean> user) {
//        setLogin(true);
//        ACCOUNT.edit().putString("service_info_detail", new Gson().toJson(user)).commit();
//    }
//
//    public static BaseData<UserServiceBean> getServiceInfo() {
//        BaseData<UserServiceBean> userServiceBean = new Gson().fromJson(ACCOUNT.getString("service_info_detail", ""), new TypeToken<BaseData<UserServiceBean>>() {
//        }.getType());
//        return userServiceBean;
//    }


    public static void setCate(List<CateBean> list) {
        ACCOUNT.edit().putString("cate_list", new Gson().toJson(list)).commit();
    }

    public static List<CateBean> getCate() {
        List<CateBean> cateBeans = new ArrayList<>();
        cateBeans = new Gson().fromJson(ACCOUNT.getString("cate_list", ""), new TypeToken<List<CateBean>>() {
        }.getType());
        return cateBeans;
    }

    public static void setSearchHistory(List<String> list) {
        ACCOUNT.edit().putString("search_history_list", new Gson().toJson(list)).commit();
    }

    public static List<String> getSearchHistory() {
        List<String> cateBeans = new ArrayList<>();
        cateBeans = new Gson().fromJson(ACCOUNT.getString("search_history_list", ""), new TypeToken<List<String>>() {
        }.getType());
        return cateBeans;
    }


    public static void setCityHistory(List<CityHistoryBean> list) {
        ACCOUNT.edit().putString("city_history_list", new Gson().toJson(list)).commit();
    }

    public static List<CityHistoryBean> getCityHistory() {
        List<CityHistoryBean> cateBeans = new ArrayList<>();
        cateBeans = new Gson().fromJson(ACCOUNT.getString("city_history_list", ""), new TypeToken<List<CityHistoryBean>>() {
        }.getType());
        return cateBeans;
    }

    public static void setCharacter(List<PayListBean> list) {
        ACCOUNT.edit().putString("character_list", new Gson().toJson(list)).commit();
    }

    public static List<PayListBean> getCharacter() {
        List<PayListBean> cateBeans = new ArrayList<>();
        cateBeans = new Gson().fromJson(ACCOUNT.getString("character_list", ""), new TypeToken<List<PayListBean>>() {
        }.getType());
        return cateBeans;
    }

    public static void setCityList(List<CityBean> list) {
        ACCOUNT.edit().putString("city_list", new Gson().toJson(list)).commit();
    }

    public static List<CityBean> getCityList() {
        List<CityBean> cateBeans = new ArrayList<>();
        cateBeans = new Gson().fromJson(ACCOUNT.getString("city_list", ""), new TypeToken<List<CityBean>>() {
        }.getType());
        return cateBeans;
    }

    public static boolean isLogin() {
        return GUIDE.getBoolean("is_login", false);
    }

    public static void setLogin(boolean isLogin) {
        Log.e("zlz", "setLogin" + isLogin);
        GUIDE.edit().putBoolean("is_login", isLogin).commit();
    }

    /**
     * 三证合一保存信息
     */
    //身份证
    public static void setIdNum(String idNUm) {
        ACCOUNT.edit().putString("user_sz_id_num", idNUm).commit();
    }

    public static String getIdNum() {
        return ACCOUNT.getString("user_sz_id_num", "");
    }

    //姓名
    public static void setRealName(String realName) {
        ACCOUNT.edit().putString("user_sz_real_name", realName).commit();
    }

    public static String getRealName() {
        return ACCOUNT.getString("user_sz_real_name", "");
    }

    //手机号
    public static void setPhone(String phone) {
        ACCOUNT.edit().putString("user_sz_phone", phone).commit();
    }

    public static String getPhone() {
        return ACCOUNT.getString("user_sz_phone", "");
    }


    /**
     * 退出登录
     **/
    public static void clearLoginInfo() {
        setUser(null);
        setUserInfo(null);
        setLogin(false);
    }
}
