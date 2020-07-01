package com.lx.xqgg.ui.city.bean;

public class CityHistoryBean {
    private String province;
    private String city;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CityHistoryBean(String province, String city) {
        this.province = province;
        this.city = city;
    }
}
