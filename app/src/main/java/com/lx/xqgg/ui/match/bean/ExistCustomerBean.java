package com.lx.xqgg.ui.match.bean;

import java.io.Serializable;

public class ExistCustomerBean implements Serializable {
    private String type;
    private String id;
    private String statusHeight;
    private String cityName;
    private String company;
    private String creditCode;
    private String id_card;
    private String id_card1;
    private String link_man;
    private String industry;
    private String link_phone;
    private String area;

    public String getType() {
        return type;
    }

    public String getId_card1() {
        return id_card1;
    }

    public void setId_card1(String id_card1) {
        this.id_card1 = id_card1;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getLink_man() {
        return link_man;
    }

    public void setLink_man(String link_man) {
        this.link_man = link_man;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLink_phone() {
        return link_phone;
    }

    public void setLink_phone(String link_phone) {
        this.link_phone = link_phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
