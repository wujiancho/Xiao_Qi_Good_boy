package com.lx.xqgg.ui.person.bean;

import java.io.Serializable;

public class ProductDetailBean implements Serializable {
    private String userPhone;
    private String type;
    private String id;
    private String statusHeight;
    private String cityName;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatusHeight() {
        return statusHeight;
    }

    public void setStatusHeight(String statusHeight) {
        this.statusHeight = statusHeight;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
