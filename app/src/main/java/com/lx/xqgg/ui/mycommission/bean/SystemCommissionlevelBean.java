package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class SystemCommissionlevelBean implements Serializable {


    /**
     * buy : false
     * delFlag : null
     * endMoney : 100
     * endTime : null
     * ico : /common/image?fileId=2b911773d31f4fb4b47a39b8010353d2.png
     * id : 24
     * month : 3
     * name : 青铜
     * picture : /common/image?fileId=90c66810d9f849c8b880942f2cd3cd7a.png
     * price : 300
     * startMoney : 1
     */

    private boolean buy;
    private Object delFlag;
    private int endMoney;
    private Object endTime;
    private String ico;
    private int id;
    private int month;
    private String name;
    private String picture;
    private int price;
    private int startMoney;
    private boolean selectbuy;
    public boolean isBuy() {
        return buy;
    }

    public boolean isSelectbuy() {
        return selectbuy;
    }

    public void setSelectbuy(boolean selectbuy) {
        this.selectbuy = selectbuy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public Object getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Object delFlag) {
        this.delFlag = delFlag;
    }

    public int getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(int endMoney) {
        this.endMoney = endMoney;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(int startMoney) {
        this.startMoney = startMoney;
    }
}
