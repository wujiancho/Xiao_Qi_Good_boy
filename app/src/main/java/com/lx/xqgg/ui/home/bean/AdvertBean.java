package com.lx.xqgg.ui.home.bean;

public class AdvertBean {

    /**
     * id : 11
     * image : /common/image?fileId=20200328193905.jpg
     * times : 1
     * url :
     */

    private int id;
    private String image;
    private int times;
    private String url;
    private String name;
    private int busId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
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

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
