package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class PointsdetailsBean implements Serializable {
    private String product_name;
    private String usingletterstime;
    private  String order_num;
    private  String company_name;
    private  String kh_name;
    private  String phone;
    private  String yg_name;
    private  String status;
    private  String jiapoint;
    private  String credit_points;

    public PointsdetailsBean(String product_name, String usingletterstime, String order_num, String company_name, String kh_name, String phone, String yg_name, String status, String jiapoint, String credit_points) {
        this.product_name = product_name;
        this.usingletterstime = usingletterstime;
        this.order_num = order_num;
        this.company_name = company_name;
        this.kh_name = kh_name;
        this.phone = phone;
        this.yg_name = yg_name;
        this.status = status;
        this.jiapoint = jiapoint;
        this.credit_points = credit_points;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getUsingletterstime() {
        return usingletterstime;
    }

    public void setUsingletterstime(String usingletterstime) {
        this.usingletterstime = usingletterstime;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getKh_name() {
        return kh_name;
    }

    public void setKh_name(String kh_name) {
        this.kh_name = kh_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getYg_name() {
        return yg_name;
    }

    public void setYg_name(String yg_name) {
        this.yg_name = yg_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJiapoint() {
        return jiapoint;
    }

    public void setJiapoint(String jiapoint) {
        this.jiapoint = jiapoint;
    }

    public String getCredit_points() {
        return credit_points;
    }

    public void setCredit_points(String credit_points) {
        this.credit_points = credit_points;
    }
}
