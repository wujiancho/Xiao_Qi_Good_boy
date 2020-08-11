package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class DetailsofinterestBean implements Serializable {

    /**
     * id : 3
     * image : /common/image?fileId=d2cd768a5b874c6bac5c0ce0dfd473e1.png
     * name : 11
     * status : normal
     * value : <p>200</p>
     * weigh : 0
     */

    private int id;
    private String image;
    private String name;
    private String status;
    private String value;
    private int weigh;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getWeigh() {
        return weigh;
    }

    public void setWeigh(int weigh) {
        this.weigh = weigh;
    }
}
