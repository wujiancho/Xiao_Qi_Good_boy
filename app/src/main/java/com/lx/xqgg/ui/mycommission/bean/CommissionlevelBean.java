package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class CommissionlevelBean implements Serializable {

    /**
     * buy : true
     * currentCharge : 4917
     * currentLevel : 青铜
     * ico : /common/image?fileId=2b911773d31f4fb4b47a39b8010353d2.png
     * nextLevel : 准钻石
     * nextLevelMoney : 1
     * orderMoney : 44.7
     * picture : /common/image?fileId=90c66810d9f849c8b880942f2cd3cd7a.png
     */

    private boolean buy;
    private int currentCharge;
    private String currentLevel;
    private String ico;
    private String nextLevel;
    private int nextLevelMoney;
    private double orderMoney;
    private String picture;

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public int getCurrentCharge() {
        return currentCharge;
    }

    public void setCurrentCharge(int currentCharge) {
        this.currentCharge = currentCharge;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }

    public int getNextLevelMoney() {
        return nextLevelMoney;
    }

    public void setNextLevelMoney(int nextLevelMoney) {
        this.nextLevelMoney = nextLevelMoney;
    }

    public double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
