package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;
import java.util.List;

public class HistoryPointsdetailstBean implements Serializable {


    /**
     * code : 200
     * data : {"list":[{"chargeName":"王者","ico":null,"month":"2020-07","picture":null,"productCharge":894,"productCreditMoney":447000}],"money":5811}
     * message : null
     * success : true
     * timestamp : 1597132336453
     */

    private int code;
    private DataBean data;
    private Object message;
    private boolean success;
    private long timestamp;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static class DataBean {
        /**
         * list : [{"chargeName":"王者","ico":null,"month":"2020-07","picture":null,"productCharge":894,"productCreditMoney":447000}]
         * money : 5811
         */

        private int money;
        private List<ListBean> list;

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * chargeName : 王者
             * ico : null
             * month : 2020-07
             * picture : null
             * productCharge : 894
             * productCreditMoney : 447000
             */

            private String chargeName;
            private Object ico;
            private String month;
            private Object picture;
            private int productCharge;
            private int productCreditMoney;

            public String getChargeName() {
                return chargeName;
            }

            public void setChargeName(String chargeName) {
                this.chargeName = chargeName;
            }

            public Object getIco() {
                return ico;
            }

            public void setIco(Object ico) {
                this.ico = ico;
            }

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public Object getPicture() {
                return picture;
            }

            public void setPicture(Object picture) {
                this.picture = picture;
            }

            public int getProductCharge() {
                return productCharge;
            }

            public void setProductCharge(int productCharge) {
                this.productCharge = productCharge;
            }

            public int getProductCreditMoney() {
                return productCreditMoney;
            }

            public void setProductCreditMoney(int productCreditMoney) {
                this.productCreditMoney = productCreditMoney;
            }
        }
    }
}
