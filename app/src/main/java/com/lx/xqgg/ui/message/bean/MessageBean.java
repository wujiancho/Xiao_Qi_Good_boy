package com.lx.xqgg.ui.message.bean;

import java.util.List;

public class MessageBean {

    /**
     * isSuccess : true
     * message : null
     * page : {"page":1,"size":10,"total":1,"totalPage":1}
     * records : [{"content":"您的VIP即将到期，为了不影响您的使用，请尽快续费。","correlation_id":0,"createtime":"2020-04-09 10:00:01","id":3497,"jump":"vip","msg_id":"29273457143734514","platform":"android,ios","push_type":"now","read":"0","receiver":"18913541567","sendno":1}]
     * total : 1
     */

    private boolean isSuccess;
    private Object message;
    private PageBean page;
    private int total;
    private List<RecordsBean> records;

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
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
         * size : 10
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
         * content : 您的VIP即将到期，为了不影响您的使用，请尽快续费。
         * correlation_id : 0
         * createtime : 2020-04-09 10:00:01
         * id : 3497
         * jump : vip
         * msg_id : 29273457143734514
         * platform : android,ios
         * push_type : now
         * read : 0
         * receiver : 18913541567
         * sendno : 1
         */

        private String content;
        private int correlation_id;
        private String createtime;
        private int id;
        private String jump;
        private String msg_id;
        private String platform;
        private String push_type;
        private String read;
        private String receiver;
        private int sendno;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCorrelation_id() {
            return correlation_id;
        }

        public void setCorrelation_id(int correlation_id) {
            this.correlation_id = correlation_id;
        }

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

        public String getJump() {
            return jump;
        }

        public void setJump(String jump) {
            this.jump = jump;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getPush_type() {
            return push_type;
        }

        public void setPush_type(String push_type) {
            this.push_type = push_type;
        }

        public String getRead() {
            return read;
        }

        public void setRead(String read) {
            this.read = read;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public int getSendno() {
            return sendno;
        }

        public void setSendno(int sendno) {
            this.sendno = sendno;
        }
    }
}
