package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class ViplistBean implements Serializable {
    private int imgid;
    private String name;

    public ViplistBean(int imgid, String name) {
        this.imgid = imgid;
        this.name = name;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
