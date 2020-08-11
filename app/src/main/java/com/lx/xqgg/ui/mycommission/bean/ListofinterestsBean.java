package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class ListofinterestsBean  {

    /**
     * id : 1
     * image : /common/image?fileId=3b7973be3e2f456fad2f8a21f63e15ee.png
     * name : 测试
     * status : normal
     * value : null
     * weigh : 100
     */

    private int id;
    private String image;
    private String name;
    private String status;
    private Object value;
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getWeigh() {
        return weigh;
    }

    public void setWeigh(int weigh) {
        this.weigh = weigh;
    }
}
