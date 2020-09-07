package com.lx.xqgg.ui.mytuoke.bean;

import java.io.Serializable;

public class getZfbBean implements Serializable {

    /**
     * createtime : 2020-09-03 14:39:10
     * id : 3
     * user_id : 2
     * user_phone : 18913541567
     * zfb_account : 213213
     * zfb_name : asd
     */

    private String createtime;
    private int id;
    private int user_id;
    private String user_phone;
    private String zfb_account;
    private String zfb_name;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getZfb_account() {
        return zfb_account;
    }

    public void setZfb_account(String zfb_account) {
        this.zfb_account = zfb_account;
    }

    public String getZfb_name() {
        return zfb_name;
    }

    public void setZfb_name(String zfb_name) {
        this.zfb_name = zfb_name;
    }
}
