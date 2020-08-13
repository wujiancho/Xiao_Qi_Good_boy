package com.lx.xqgg.event;

public class ServiseridurlEvent {
    private int id;
    private String imgurl;
    private String vipname;

    public ServiseridurlEvent(int id, String imgurl, String vipname) {
        this.id = id;
        this.imgurl = imgurl;
        this.vipname = vipname;
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
