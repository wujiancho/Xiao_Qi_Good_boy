package com.lx.xqgg.ui.home.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ResultBean implements MultiItemEntity {

    /**
     * company :
     * id : 573
     * image : /uploads/20191112/1f8f438396437c98951173ce59be1537.png
     * name : 张家港农商银行 - 优企贷
     * phone :
     * quota : 2000000
     * rate : 0.26‰ - 0.36‰
     * type : 1
     */

    private String company;
    private int id;
    private String image;
    private String name;
    private String phone;
    private int quota;
    private String rate;
    private String type;
    private String isTop;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    @Override
    public int getItemType() {
        return Integer.parseInt(type);
    }
}
