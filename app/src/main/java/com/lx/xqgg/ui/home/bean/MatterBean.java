package com.lx.xqgg.ui.home.bean;

public class MatterBean {
    private String name;
    private String action;
    private int imgRes;

    public MatterBean(String name, String action, int imgRes) {
        this.name = name;
        this.action = action;
        this.imgRes = imgRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
