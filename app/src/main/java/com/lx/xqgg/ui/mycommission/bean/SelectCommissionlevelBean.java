package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class SelectCommissionlevelBean implements Serializable {

    /**
     * newCharge : 1341
     * oldCharge : 4917
     * promote : -0.73
     * rightsNum : 0
     */

    private int newCharge;
    private int oldCharge;
    private String promote;
    private int rightsNum;

    public int getNewCharge() {
        return newCharge;
    }

    public void setNewCharge(int newCharge) {
        this.newCharge = newCharge;
    }

    public int getOldCharge() {
        return oldCharge;
    }

    public void setOldCharge(int oldCharge) {
        this.oldCharge = oldCharge;
    }

    public String getPromote() {
        return promote;
    }

    public void setPromote(String promote) {
        this.promote = promote;
    }

    public int getRightsNum() {
        return rightsNum;
    }

    public void setRightsNum(int rightsNum) {
        this.rightsNum = rightsNum;
    }
}
