package com.lx.xqgg.ui.order.bean;

import java.io.Serializable;
import java.util.List;

public class OrderBean implements Serializable {

    /**
     * isSuccess : true
     * page : {"page":1,"size":22,"total":22,"totalPage":1}
     * records : [{"apply_company":"苏州工业园区源荣科创机电有限公司","apply_money":3000000,"city_id":null,"createtime":null,"credit_money":null,"id":12807,"link_man":"方鹏","link_mobile":"15106134491","meetAddress":null,"meetTime":null,"orderNo":null,"product_id":481,"status":"created","title":"微众银行 - 微业贷","user_id":94,"user_name":"13862182099"}]
     * total : 22
     */

    private boolean isSuccess;
    private PageBean page;
    private int total;
    private String message;
    private List<RecordsBean> records;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class PageBean {
        /**
         * page : 1
         * size : 22
         * total : 22
         * totalPage : 1
         */

        private int page;
        private int size;
        private int total;
        private int totalPage;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }
    }

    public static class RecordsBean implements Serializable{
        /**
         * apply_company : 苏州工业园区源荣科创机电有限公司
         * apply_money : 3000000
         * city_id : null
         * createtime : null
         * credit_money : null
         * id : 12807
         * link_man : 方鹏
         * link_mobile : 15106134491
         * meetAddress : null
         * meetTime : null
         * orderNo : null
         * product_id : 481
         * status : created
         * title : 微众银行 - 微业贷
         * user_id : 94
         * user_name : 13862182099
         */

        private String apply_company;
        private int apply_money;
        private String city_id;
        private String createtime;
        private String credit_money;
        private int id;
        private String link_man;
        private String link_mobile;
        private String meetAddress;
        private String meetTime;
        private String orderNo;
        private int product_id;
        private String status;
        private String title;
        private int user_id;
        private String user_name;
        private String rej_reason;
        private String real_money;
        private String creditAudit;
        private String pre_credit_money;
        private String appointment;

        public String getAppointment() {
            return appointment;
        }

        public void setAppointment(String appointment) {
            this.appointment = appointment;
        }

        public String getPre_credit_money() {
            return pre_credit_money;
        }

        public void setPre_credit_money(String pre_credit_money) {
            this.pre_credit_money = pre_credit_money;
        }

        public String getCreditAudit() {
            return creditAudit;
        }

        public void setCreditAudit(String creditAudit) {
            this.creditAudit = creditAudit;
        }

        public String getReal_money() {
            return real_money;
        }

        public void setReal_money(String real_money) {
            this.real_money = real_money;
        }

        public String getRej_reason() {
            return rej_reason;
        }

        public void setRej_reason(String rej_reason) {
            this.rej_reason = rej_reason;
        }

        public String getApply_company() {
            return apply_company;
        }

        public void setApply_company(String apply_company) {
            this.apply_company = apply_company;
        }

        public int getApply_money() {
            return apply_money;
        }

        public void setApply_money(int apply_money) {
            this.apply_money = apply_money;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getCredit_money() {
            return credit_money;
        }

        public void setCredit_money(String credit_money) {
            this.credit_money = credit_money;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink_man() {
            return link_man;
        }

        public void setLink_man(String link_man) {
            this.link_man = link_man;
        }

        public String getLink_mobile() {
            return link_mobile;
        }

        public void setLink_mobile(String link_mobile) {
            this.link_mobile = link_mobile;
        }

        public String getMeetAddress() {
            return meetAddress;
        }

        public void setMeetAddress(String meetAddress) {
            this.meetAddress = meetAddress;
        }

        public String getMeetTime() {
            return meetTime;
        }

        public void setMeetTime(String meetTime) {
            this.meetTime = meetTime;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}
