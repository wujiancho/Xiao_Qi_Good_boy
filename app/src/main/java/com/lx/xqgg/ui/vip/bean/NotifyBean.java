package com.lx.xqgg.ui.vip.bean;

public class NotifyBean {
    private int id;
    private String msg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public NotifyBean(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }
}
