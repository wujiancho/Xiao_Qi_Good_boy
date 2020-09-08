package com.lx.xqgg.ui.mytuoke.bean;

import java.io.Serializable;

public class WithdrawAlrecordTuokelistBean implements Serializable {
    private String zfborbernum;
    private String money1;
    private String starttime;
    private String endtime;
    private String jif;
    private String money2;
    private String stuart;

    public WithdrawAlrecordTuokelistBean(String zfborbernum, String money1, String starttime, String endtime, String jif, String money2, String stuart) {
        this.zfborbernum = zfborbernum;
        this.money1 = money1;
        this.starttime = starttime;
        this.endtime = endtime;
        this.jif = jif;
        this.money2 = money2;
        this.stuart = stuart;
    }

    public String getZfborbernum() {
        return zfborbernum;
    }

    public void setZfborbernum(String zfborbernum) {
        this.zfborbernum = zfborbernum;
    }

    public String getMoney1() {
        return money1;
    }

    public void setMoney1(String money1) {
        this.money1 = money1;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getJif() {
        return jif;
    }

    public void setJif(String jif) {
        this.jif = jif;
    }

    public String getMoney2() {
        return money2;
    }

    public void setMoney2(String money2) {
        this.money2 = money2;
    }

    public String getStuart() {
        return stuart;
    }

    public void setStuart(String stuart) {
        this.stuart = stuart;
    }
}
