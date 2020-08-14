package com.lx.xqgg.event;

public class ServiseridurlEvent {
    private int id;
    private String imgurl;
    private String vipname;
    private String endTime;
    private int rightsNum;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRightsNum() {
        return rightsNum;
    }

    public void setRightsNum(int rightsNum) {
        this.rightsNum = rightsNum;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getVipname() {
        return vipname;
    }

    public void setVipname(String vipname) {
        this.vipname = vipname;
    }
}
