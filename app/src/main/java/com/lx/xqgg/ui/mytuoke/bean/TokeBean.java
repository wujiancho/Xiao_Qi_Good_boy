package com.lx.xqgg.ui.mytuoke.bean;

import java.io.Serializable;

public class TokeBean implements Serializable {

    public TokeBean(String createtime, String mobile, String service_name, String verification) {
        this.createtime = createtime;
        this.mobile = mobile;
        this.service_name = service_name;
        this.verification = verification;
    }

    /**
     * createtime : 2020-05-06 12:07:09
     * id : 2987
     * mobile :
     * puid : 0
     * service_name : null
     * userServiceId : 0
     * username :
     * verification :
     */

    private String createtime;
    private int id;
    private String mobile;
    private int puid;
    private String service_name;
    private int userServiceId;
    private String username;
    private String verification;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getPuid() {
        return puid;
    }

    public void setPuid(int puid) {
        this.puid = puid;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public int getUserServiceId() {
        return userServiceId;
    }

    public void setUserServiceId(int userServiceId) {
        this.userServiceId = userServiceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }
}
