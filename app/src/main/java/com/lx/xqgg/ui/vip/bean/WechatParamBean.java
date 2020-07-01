package com.lx.xqgg.ui.vip.bean;

import com.google.gson.annotations.SerializedName;

public class WechatParamBean {

    /**
     * signMap : {"appid":"wxc8d5861b096a0dae","noncestr":"uVp6pLMUwhCHzvKt","orderNo":"20200319171956568000","package":"Sign=WXPay","partnerid":"1536722821","prepayid":null,"sign":"436C35C967372D4F80A098D22140C597","timestamp":1584609596}
     */

    private SignMapBean signMap;

    public SignMapBean getSignMap() {
        return signMap;
    }

    public void setSignMap(SignMapBean signMap) {
        this.signMap = signMap;
    }

    public static class SignMapBean {
        /**
         * appid : wxc8d5861b096a0dae
         * noncestr : uVp6pLMUwhCHzvKt
         * orderNo : 20200319171956568000
         * package : Sign=WXPay
         * partnerid : 1536722821
         * prepayid : null
         * sign : 436C35C967372D4F80A098D22140C597
         * timestamp : 1584609596
         */

        private String appid;
        private String noncestr;
        private String orderNo;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private int timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }
    }
}
