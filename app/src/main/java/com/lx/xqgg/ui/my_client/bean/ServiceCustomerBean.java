package com.lx.xqgg.ui.my_client.bean;

import java.util.List;

public class ServiceCustomerBean {

    /**
     * isSuccess : true
     * page : {"page":1,"size":1,"total":1,"totalPage":1}
     * records : [{"company":"","id":null,"isTop":null,"link_man":"张黎中","link_phone":"18626107621"}]
     * total : 1
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
         * size : 1
         * total : 1
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

    public static class RecordsBean {
        /**
         * company :
         * id : null
         * isTop : null
         * link_man : 张黎中
         * link_phone : 18626107621
         */

        private String company;
        private Object id;
        private Object isTop;
        private String link_man;
        private String link_phone;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getIsTop() {
            return isTop;
        }

        public void setIsTop(Object isTop) {
            this.isTop = isTop;
        }

        public String getLink_man() {
            return link_man;
        }

        public void setLink_man(String link_man) {
            this.link_man = link_man;
        }

        public String getLink_phone() {
            return link_phone;
        }

        public void setLink_phone(String link_phone) {
            this.link_phone = link_phone;
        }
    }
}
