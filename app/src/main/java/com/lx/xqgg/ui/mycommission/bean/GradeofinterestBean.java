package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class GradeofinterestBean implements Serializable {

    /**
     * configId : 24
     * configName : 青铜
     * id : 26
     * rightsId : 1
     * rightsName : 测试
     */

    private int configId;
    private String configName;
    private int id;
    private int rightsId;
    private String rightsName;

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRightsId() {
        return rightsId;
    }

    public void setRightsId(int rightsId) {
        this.rightsId = rightsId;
    }

    public String getRightsName() {
        return rightsName;
    }

    public void setRightsName(String rightsName) {
        this.rightsName = rightsName;
    }
}
