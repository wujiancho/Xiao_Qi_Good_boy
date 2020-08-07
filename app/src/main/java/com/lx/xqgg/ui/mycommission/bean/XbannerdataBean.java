package com.lx.xqgg.ui.mycommission.bean;
import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.io.Serializable;

public  class XbannerdataBean implements Serializable {
    private  String vipname;
    private  String  jurisdiction;

    public XbannerdataBean(String vipname, String jurisdiction) {
        this.vipname = vipname;
        this.jurisdiction = jurisdiction;
    }

    public String getVipname() {
        return vipname;
    }

    public void setVipname(String vipname) {
        this.vipname = vipname;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }



}
