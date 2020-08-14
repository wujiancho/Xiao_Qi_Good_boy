package com.lx.xqgg.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 王阳
 * Class :1708A
 * @description:
 * @date :2020/7/10 8:51
 * @classname :TimeStampUtil
 */
public class TimeStampUtil {
    //时间戳工具类
    public static String getTime(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        String format = simpleDateFormat.format(new Date(time));
        return format;
    }
    //时间戳工具类2
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt =new Long(s);
        Date date =new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
