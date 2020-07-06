package com.lx.xqgg.ui.product.bean;

import java.io.Serializable;

public class FuMinTokenBusOutBody implements Serializable {
    private String caFlag;
    private String SEC_DATE;
    private String token;
    public String getCaFlag() {
        return caFlag;
    }
    public void setCaFlag(String caFlag) {
        this.caFlag = (caFlag == null ? null : caFlag.trim());
    }
    public String getSEC_DATE() {
        return SEC_DATE;
    }
    public void setSEC_DATE(String sEC_DATE) {
        SEC_DATE = (sEC_DATE == null ? null : sEC_DATE.trim());
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = (token == null ? null : token.trim());
    }
}
