package com.lx.xqgg.ui.order.bean;

import java.io.Serializable;

public class TjBean implements Serializable {

    /**
     * averageMoney : 0
     * createdOrderNum : 23
     * id : -1
     * name : 全部
     * passNum : 0
     * passRate : .00
     * preCreditOrderNum : 0
     * totalOrderNum : 23
     * useCreditOrderNum : 0
     * userCreditMoney : 0
     * userCreditRate : .00
     */

    private String averageMoney;
    private int createdOrderNum;
    private String id;
    private String name;
    private int passNum;
    private String passRate;
    private int preCreditOrderNum;
    private int totalOrderNum;
    private int useCreditOrderNum;
    private String userCreditMoney;
    private String userCreditRate;



    public String getAverageMoney() {
        return averageMoney;
    }

    public void setAverageMoney(String averageMoney) {
        this.averageMoney = averageMoney;
    }

    public int getCreatedOrderNum() {
        return createdOrderNum;
    }

    public void setCreatedOrderNum(int createdOrderNum) {
        this.createdOrderNum = createdOrderNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassNum() {
        return passNum;
    }

    public void setPassNum(int passNum) {
        this.passNum = passNum;
    }

    public String getPassRate() {
        return passRate;
    }

    public void setPassRate(String passRate) {
        this.passRate = passRate;
    }

    public int getPreCreditOrderNum() {
        return preCreditOrderNum;
    }

    public void setPreCreditOrderNum(int preCreditOrderNum) {
        this.preCreditOrderNum = preCreditOrderNum;
    }

    public int getTotalOrderNum() {
        return totalOrderNum;
    }

    public void setTotalOrderNum(int totalOrderNum) {
        this.totalOrderNum = totalOrderNum;
    }

    public int getUseCreditOrderNum() {
        return useCreditOrderNum;
    }

    public void setUseCreditOrderNum(int useCreditOrderNum) {
        this.useCreditOrderNum = useCreditOrderNum;
    }

    public String getUserCreditMoney() {
        return userCreditMoney;
    }

    public void setUserCreditMoney(String userCreditMoney) {
        this.userCreditMoney = userCreditMoney;
    }

    public String getUserCreditRate() {
        return userCreditRate;
    }

    public void setUserCreditRate(String userCreditRate) {
        this.userCreditRate = userCreditRate;
    }
}
