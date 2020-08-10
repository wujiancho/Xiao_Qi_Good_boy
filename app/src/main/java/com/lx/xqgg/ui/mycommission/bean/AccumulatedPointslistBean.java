package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class AccumulatedPointslistBean implements Serializable {
    private  String time;
    private  int vipimg;

    public AccumulatedPointslistBean(String time, int vipimg) {
        this.time = time;
        this.vipimg = vipimg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getVipimg() {
        return vipimg;
    }

    public void setVipimg(int vipimg) {
        this.vipimg = vipimg;
    }
}
