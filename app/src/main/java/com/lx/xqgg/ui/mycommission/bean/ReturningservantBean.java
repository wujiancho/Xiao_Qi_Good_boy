package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class ReturningservantBean implements Serializable {
    /**
     * allCharge : 5811
     * cashCharge : 891
     * currentMonthCharge : 4917
     * createdMoney :
     */

    private int allCharge;
    private int cashCharge;
    private int currentMonthCharge;
    private String createdMoney;

    public int getAllCharge() {
        return allCharge;
    }

    public void setAllCharge(int allCharge) {
        this.allCharge = allCharge;
    }

    public int getCashCharge() {
        return cashCharge;
    }

    public void setCashCharge(int cashCharge) {
        this.cashCharge = cashCharge;
    }

    public int getCurrentMonthCharge() {
        return currentMonthCharge;
    }

    public void setCurrentMonthCharge(int currentMonthCharge) {
        this.currentMonthCharge = currentMonthCharge;
    }

    public String getCreatedMoney() {
        return createdMoney;
    }

    public void setCreatedMoney(String createdMoney) {
        this.createdMoney = createdMoney;
    }



}
