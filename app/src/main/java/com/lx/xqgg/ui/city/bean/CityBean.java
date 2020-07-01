package com.lx.xqgg.ui.city.bean;

import java.util.List;

public class CityBean {

    /**
     * fisrt : C
     * listCity : [{"child":null,"code":"0519","first":"C","id":853,"lat":"31.772752","level":2,"lng":"119.946973","mergename":"中国,江苏省,常州市","name":"常州市","pid":820,"pinyin":"changzhou","shortname":"常州","zip":"213000"}]
     */

    private String first;
    private List<ListCityBean> listCity;

    public String getFisrt() {
        return first;
    }

    public void setFisrt(String fisrt) {
        this.first = fisrt;
    }

    public List<ListCityBean> getListCity() {
        return listCity;
    }

    public void setListCity(List<ListCityBean> listCity) {
        this.listCity = listCity;
    }

    public static class ListCityBean {
        /**
         * child : null
         * code : 0519
         * first : C
         * id : 853
         * lat : 31.772752
         * level : 2
         * lng : 119.946973
         * mergename : 中国,江苏省,常州市
         * name : 常州市
         * pid : 820
         * pinyin : changzhou
         * shortname : 常州
         * zip : 213000
         */

        private Object child;
        private String code;
        private String first;
        private int id;
        private String lat;
        private int level;
        private String lng;
        private String mergename;
        private String name;
        private int pid;
        private String pinyin;
        private String shortname;
        private String zip;

        public Object getChild() {
            return child;
        }

        public void setChild(Object child) {
            this.child = child;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getMergename() {
            return mergename;
        }

        public void setMergename(String mergename) {
            this.mergename = mergename;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getShortname() {
            return shortname;
        }

        public void setShortname(String shortname) {
            this.shortname = shortname;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }
    }
}
