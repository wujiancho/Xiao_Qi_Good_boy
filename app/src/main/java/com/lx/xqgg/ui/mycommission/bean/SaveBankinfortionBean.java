package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class SaveBankinfortionBean implements Serializable {

    /**
     * bankName :
     * bankNo :
     * bankUser :
     */

    private String bankName;
    private String bankNo;
    private String bankUser;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankUser() {
        return bankUser;
    }

    public void setBankUser(String bankUser) {
        this.bankUser = bankUser;
    }
}
