package com.lx.xqgg.ui.product.bean;

public class CateBean {

    /**
     * id : 50
     * image : /common/image?fileId=4116478de7ea44eb9a9abdf0a450ddfa.jpg
     * name : 12
     */

    private int id;
    private String image;
    private String name;

    public CateBean(int id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
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
}
