package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class WithdrawalrecordBean implements Serializable {
    private String time;
    private  String bandlastnumber;
    private  String money;
    private  int status;

    public WithdrawalrecordBean(String time, String bandlastnumber, String money, int status) {
        this.time = time;
        this.bandlastnumber = bandlastnumber;
        this.money = money;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBandlastnumber() {
        return bandlastnumber;
    }

    public void setBandlastnumber(String bandlastnumber) {
        this.bandlastnumber = bandlastnumber;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
