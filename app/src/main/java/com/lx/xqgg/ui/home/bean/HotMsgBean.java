package com.lx.xqgg.ui.home.bean;

import java.util.List;

public class HotMsgBean {

    /**
     * isSuccess : true
     * page : {"page":1,"size":10,"total":24,"totalPage":3}
     * records : [{"appCompanyName":"高辉科技","appid":1,"content":null,"create_time":"2020-01-16 11:41:40.0","deletetime":null,"id":83,"image":"/uploads/20190712/a4a2b265b6239901fb23ce3fe3f80413.jpg","read_num":1000,"status":"hidden","title":"善待=====自己------，健康无价","update_time":null}]
     * total : 24
     */

    private boolean isSuccess;
    private PageBean page;
    private int total;
    private List<RecordsBean> records;

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
         * size : 10
         * total : 24
         * totalPage : 3
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
         * appCompanyName : 高辉科技
         * appid : 1
         * content : null
         * create_time : 2020-01-16 11:41:40.0
         * deletetime : null
         * id : 83
         * image : /uploads/20190712/a4a2b265b6239901fb23ce3fe3f80413.jpg
         * read_num : 1000
         * status : hidden
         * title : 善待=====自己------，健康无价
         * update_time : null
         */

        private String appCompanyName;
        private int appid;
        private String content;
        private String create_time;
        private String deletetime;
        private int id;
        private String image;
        private int read_num;
        private String status;
        private String title;
        private String update_time;

        public String getAppCompanyName() {
            return appCompanyName;
        }

        public void setAppCompanyName(String appCompanyName) {
            this.appCompanyName = appCompanyName;
        }

        public int getAppid() {
            return appid;
        }

        public void setAppid(int appid) {
            this.appid = appid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDeletetime() {
            return deletetime;
        }

        public void setDeletetime(String deletetime) {
            this.deletetime = deletetime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getRead_num() {
            return read_num;
        }

        public void setRead_num(int read_num) {
            this.read_num = read_num;
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

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
