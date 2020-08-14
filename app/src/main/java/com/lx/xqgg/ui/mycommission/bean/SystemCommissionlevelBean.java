package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;
import java.util.List;

public class SystemCommissionlevelBean implements Serializable {


    /**
     * buy : true
     * delFlag : null
     * endMoney : 100
     * endTime : 2020-12-01
     * ico : /common/image?fileId=2b911773d31f4fb4b47a39b8010353d2.png
     * id : 24
     * month : 3
     * name : 青铜
     * picture : /common/image?fileId=90c66810d9f849c8b880942f2cd3cd7a.png
     * price : 300
     * rights : [{"configId":24,"configName":"青铜","id":26,"rightsId":1,"rightsName":"返佣权益","image":""}]
     * rightsNum : 1
     * startMoney : 1
     */

    private boolean buy;
    private Object delFlag;
    private int endMoney;
    private String endTime;
    private String ico;
    private int id;
    private int month;
    private String name;
    private String picture;
    private int price;
    private int rightsNum;
    private int startMoney;
    private List<RightsBean> rights;

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public Object getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Object delFlag) {
        this.delFlag = delFlag;
    }

    public int getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(int endMoney) {
        this.endMoney = endMoney;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRightsNum() {
        return rightsNum;
    }

    public void setRightsNum(int rightsNum) {
        this.rightsNum = rightsNum;
    }

    public int getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(int startMoney) {
        this.startMoney = startMoney;
    }

    public List<RightsBean> getRights() {
        return rights;
    }

    public void setRights(List<RightsBean> rights) {
        this.rights = rights;
    }

    public static class RightsBean {
        /**
         * configId : 24
         * configName : 青铜
         * id : 26
         * rightsId : 1
         * rightsName : 返佣权益
         * image :
         */

        private int configId;
        private String configName;
        private int id;
        private int rightsId;
        private String rightsName;
        private String image;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
