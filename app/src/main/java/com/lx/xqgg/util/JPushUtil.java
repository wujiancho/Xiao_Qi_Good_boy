package com.lx.xqgg.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import com.lx.xqgg.base.BaseApplication;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 极光推送相关的工具集合
 *
 * @author huangxz
 */
public class JPushUtil {

    private static final int MSG_SET_ALIAS = 1001;
    private final static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d("flo", "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    PushMsg pMsg = (PushMsg) msg.obj;
                    if (pMsg != null)
                        JPushInterface.setAliasAndTags(BaseApplication.getInstance().getApplicationContext(), pMsg.alias, pMsg.tags, mAliasCallback);
                    break;
                default:
                    Log.i("flo", "Unhandled msg - " + msg.what);
            }
        }
    };

    public static void initJPush(Context context) {
        JPushInterface.init(context);
        Log.d("flo", "init JPush");
    }

    /**
     * 初始化推送服务，并且设置设备的别名和组别
     *
     * @param alias 别名
     * @param tag   组别
     */
    public static void initJPush(final Context context, String alias, HashSet<String> tag) {
        String mAlias = "";
        if (!stringIsNullOrEmpty(alias)) {
            mAlias = alias;
        }
        initJPush(context);
        JPushInterface.setAliasAndTags(context, mAlias, tag, mAliasCallback);
//		JPushInterface.setAlias(context,1,alias);
    }


    private final static TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            Log.e("flo", "call back executing....");
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("flo", logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 30s.";
                    Log.i("flo", logs);
                    PushMsg msg = new PushMsg(alias, tags);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, msg), 1000 * 30);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("flo", logs);
            }
        }
    };

    public static void stopJPush(Context context) {
        JPushInterface.stopPush(context);
        Log.d("flo", "stop JPush");
    }

    public static void resumeJPush(Context context) {
        JPushInterface.resumePush(context);
        Log.d("flo", "resume JPush");
    }

    public static boolean stringIsNullOrEmpty(String string) {
        return string == null || string.trim().length() < 1 || "null".equals(string);
    }

    @SuppressWarnings("deprecation")
    public static String getFormatedDate(String str) {
        if (stringIsNullOrEmpty(str)) {
            return "时间获取错误";
        }
        // 对数据库或缓存里面聊天记录时间做的处理
        String strTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date mydate = null;
        String date = "";
        int year = 0;
        int month = 0;
        int day = 0;
        int hour = 0;
        int minute = 0;
        String hours = "";
        String minutes = "";
        @SuppressWarnings("unused")
        int second = 0;
        try {
            mydate = sdf.parse(str);
            date = sdf.format(mydate);
            year = mydate.getYear() + 1900;
            month = mydate.getMonth() + 1;
            day = mydate.getDate();
            hour = mydate.getHours();
            minute = mydate.getMinutes();
            second = mydate.getSeconds();
            if (hour >= 10) {
                hours = hour + "";
            } else {
                hours = "0" + hour;
            }
            if (minute >= 10) {
                minutes = minute + "";
            } else {
                minutes = "0" + minute;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 获取系统当前时间作为比较标准
        Calendar calendar = Calendar.getInstance();
        int dayBase = calendar.get(Calendar.DAY_OF_MONTH);
        int monthBase = calendar.get(Calendar.MONTH) + 1;
        int yearBase = calendar.get(Calendar.YEAR);
        String DayStr = getWeek(new Date());
        if (year < yearBase) {
            strTime = date.substring(0, 10).replace("-", "/");
        } else if (year == yearBase) {
            if (month < monthBase) {
                strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
            } else if (month == monthBase) {
                if ((dayBase - day) == 0) {
                    strTime = strTime + hours + ":" + minutes;
                } else if ((dayBase - day) == 1) {
                    strTime = strTime + "昨天";
                } else if ((dayBase - day) > 1) {
                    if (DayStr.equals("星期一")) {
                        strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
                    }
                    if (DayStr.equals("星期二")) {
                        strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
                    }
                    if (DayStr.equals("星期三")) {
                        if ((dayBase - day) < 3) {
                            strTime = getWeek(str) + " " + hours + ":" + minutes;
                        } else {
                            strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
                        }
                    }
                    if (DayStr.equals("星期四")) {
                        if ((dayBase - day) < 4) {
                            strTime = getWeek(str) + " " + hours + ":" + minutes;
                        } else {
                            strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
                        }
                    }
                    if (DayStr.equals("星期五")) {
                        if ((dayBase - day) < 5) {
                            strTime = getWeek(str) + " " + hours + ":" + minutes;
                        } else {
                            strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
                        }
                    }
                    if (DayStr.equals("星期六")) {
                        if ((dayBase - day) < 6) {
                            strTime = getWeek(str) + " " + hours + ":" + minutes;
                        } else {
                            strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
                        }
                    }
                    if (DayStr.equals("星期天")) {
                        if ((dayBase - day) < 7) {
                            strTime = getWeek(str) + " " + hours + ":" + minutes;
                        } else {
                            strTime = date.substring(5, 10).replace("-", "/") + " " + hours + ":" + minutes;
                        }
                    }
                } else if ((dayBase - day) < 0) {
                    System.out.println("此条数据为无效数据！");
                    return "";
                }
            }
        } else if (year > yearBase) {
            System.out.println("此条数据为无效数据！");
            return "";
        }
        return strTime;
    }

    public static String getWeek(String str) {
        SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date dates = null;
        try {
            dates = sdfs.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.CHINA);
        String week = sdf.format(dates);
        return week;
    }

    public static String getWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.CHINA);
        String week = sdf.format(date);
        return week;
    }

    public static String toParamFromBean(Object message) {
        String result = "";
        try {
            Field[] fields = message.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (!field.getName().equals("serialVersionUID")) {
                    result = result + "&" + field.getName() + "=" + getFieldValue(field, message);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return (result != null) && (result.length() > 0) ? result.substring(1) : "";
    }

    private static String getFieldValue(Field field, Object obj) {
        try {
            Object value = field.get(obj);

            return value.toString();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static Bitmap getBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            return null;
        }
        return bitmap;
    }

    static class PushMsg {
        public String alias;
        public Set<String> tags;

        public PushMsg(String alias, Set<String> tags) {
            this.alias = alias;
            this.tags = tags;
        }
    }

}