package com.lx.xqgg.ui.share.bean;

import com.lx.xqgg.ui.login.bean.UserBean;

import java.io.Serializable;

public class Regmg implements Serializable {
    private String name;
    private String name1;
    private  String mobile ;
    private String mobile1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }
}


