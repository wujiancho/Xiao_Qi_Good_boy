package com.lx.xqgg.util;

import android.widget.Toast;

import com.lx.xqgg.base.BaseApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 王阳
 * Class :1708A
 * @description:
 * @date :2020/8/10 14:08
 * @classname :JudgeUtil
 */
public class JudgeUtil {
    //正则判断
    //判断手机号
    public static boolean isPhone(String phone){
        Pattern compile = Pattern.compile("^1[0-9]{10}$");
        Matcher matcher = compile.matcher(phone);
        boolean matches = matcher.matches();
        if(matches){
            return matches;
        }else{
            Toast.makeText(BaseApplication.getInstance(),"手机号格式不正确",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    //判断密码
    public static boolean isPwd(String pwd){
        Pattern compile = Pattern.compile("^[a-zA-Z][a-zA-Z0-9.]{5,10}$");
        Matcher matcher = compile.matcher(pwd);
        boolean matches = matcher.matches();
        if(matches){
            return matches;
        }else{
            Toast.makeText(BaseApplication.getInstance(),"密码格式不正确",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
