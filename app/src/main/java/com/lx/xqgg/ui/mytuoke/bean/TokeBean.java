package com.lx.xqgg.ui.mytuoke.bean;

import java.io.Serializable;

public class TokeBean implements Serializable {
    private String time;
    private String Serviceprovider;
    private String phone;
    private String status;

    public TokeBean(String time, String serviceprovider, String phone, String status) {
        this.time = time;
        Serviceprovider = serviceprovider;
        this.phone = phone;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getServiceprovider() {
        return Serviceprovider;
    }

    public void setServiceprovider(String serviceprovider) {
        Serviceprovider = serviceprovider;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
