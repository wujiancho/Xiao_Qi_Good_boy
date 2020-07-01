package com.lx.xqgg.ui.order.bean;

public class OrderUserBean {

    /**
     * id : 94
     * name : 13862182099
     */

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderUserBean(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
