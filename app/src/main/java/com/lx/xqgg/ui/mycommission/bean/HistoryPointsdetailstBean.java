package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class HistoryPointsdetailstBean implements Serializable {

    /**
     * chargeName : 王者
     * ico : null
     * month : 2020-07
     * picture : null
     * productCharge : 894
     * productCreditMoney : 447000
     */

    private String chargeName;
    private Object ico;
    private String month;
    private Object picture;
    private int productCharge;
    private int productCreditMoney;

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public Object getIco() {
        return ico;
    }

    public void setIco(Object ico) {
        this.ico = ico;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Object getPicture() {
        return picture;
    }

    public void setPicture(Object picture) {
        this.picture = picture;
    }

    public int getProductCharge() {
        return productCharge;
    }

    public void setProductCharge(int productCharge) {
        this.productCharge = productCharge;
    }

    public int getProductCreditMoney() {
        return productCreditMoney;
    }

    public void setProductCreditMoney(int productCreditMoney) {
        this.productCreditMoney = productCreditMoney;
    }
}
