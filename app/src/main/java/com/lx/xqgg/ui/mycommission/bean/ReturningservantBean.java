package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class ReturningservantBean implements Serializable {

    /**可提返佣-本月返佣-累计返佣
     * allCharge : 5811
     * cashCharge : 891
     * currentMonthCharge : 4917
     */

    private int allCharge;
    private int cashCharge;
    private int currentMonthCharge;

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
}
