package com.lx.xqgg.ui.login.bean;

import java.io.Serializable;

public class UserInfoBean implements Serializable {


        /**
         * avatar : ''
         * crmUser : true
         * email :
         * endtime : 2020-12-31 00:00:00
         * id : 192
         * isVip : 1
         * isenter : 1
         * legal : false
         * level : 0
         * mobile : 17312615026
         * nickname : 陈玲
         * username : 陈玲
         */

        private String avatar;
        private boolean crmUser;
        private String email;
        private String endtime;
        private int id;
        private int isVip;
        private String isenter;
        private boolean legal;
        private int level;
        private String mobile;
        private String nickname;
        private String username;
        private String charge_type;

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }

    public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public boolean isCrmUser() {
            return crmUser;
        }

        public void setCrmUser(boolean crmUser) {
            this.crmUser = crmUser;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsVip() {
            return isVip;
        }

        public void setIsVip(int isVip) {
            this.isVip = isVip;
        }

        public String getIsenter() {
            return isenter;
        }

        public void setIsenter(String isenter) {
            this.isenter = isenter;
        }

        public boolean isLegal() {
            return legal;
        }

        public void setLegal(boolean legal) {
            this.legal = legal;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

}
