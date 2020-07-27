package com.lx.xqgg.ui.integral_query.bean;

import java.io.Serializable;

public class IntegralqueryhistoryBean implements Serializable {
    private String time;
    private  String integral;
    private  int status;

    public IntegralqueryhistoryBean(String time, String integral, int status) {
        this.time = time;
        this.integral = integral;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
