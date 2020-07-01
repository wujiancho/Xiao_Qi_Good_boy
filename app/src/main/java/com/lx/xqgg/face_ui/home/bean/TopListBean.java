package com.lx.xqgg.face_ui.home.bean;

import java.io.Serializable;

public class TopListBean implements Serializable {
    private String name;
    private int img;
    private String introduction;//简介
    private String outline;//大纲
    private String reap;//收货

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getReap() {
        return reap;
    }

    public void setReap(String reap) {
        this.reap = reap;
    }

    public TopListBean(String name, int img, String introduction, String outline, String reap) {
        this.name = name;
        this.img = img;
        this.introduction = introduction;
        this.outline = outline;
        this.reap = reap;
    }
}
