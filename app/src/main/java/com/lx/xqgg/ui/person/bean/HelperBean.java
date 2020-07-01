package com.lx.xqgg.ui.person.bean;

import java.util.List;

public class HelperBean {

    /**
     * isSuccess : true
     * page : {"page":1,"size":10,"total":50,"totalPage":5}
     * records : [{"content":"<p style=\"text-align: center;\"><span style=\"font-size:18px\"><strong>图片双击可以放大缩小<\/strong><\/span><\/p><p style=\"text-align: center;\"><span style=\"font-size:18px\"><strong><img src=\"http://app.xhsqy.com/uploads/20191220/ad2f528a019170797c282bfbc95fe315.jpg\"/><\/strong><\/span><\/p>","createtime":"2019-12-20 16:49:42.0","id":144,"image":"","status":"normal","title":"富民银行 - 富税贷申请操作流程？","video":"","weigh":144}]
     * total : 50
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
         * total : 50
         * totalPage : 5
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
         * content : <p style="text-align: center;"><span style="font-size:18px"><strong>图片双击可以放大缩小</strong></span></p><p style="text-align: center;"><span style="font-size:18px"><strong><img src="http://app.xhsqy.com/uploads/20191220/ad2f528a019170797c282bfbc95fe315.jpg"/></strong></span></p>
         * createtime : 2019-12-20 16:49:42.0
         * id : 144
         * image :
         * status : normal
         * title : 富民银行 - 富税贷申请操作流程？
         * video :
         * weigh : 144
         */

        private String content;
        private String createtime;
        private int id;
        private String image;
        private String status;
        private String title;
        private String video;
        private int weigh;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public int getWeigh() {
            return weigh;
        }

        public void setWeigh(int weigh) {
            this.weigh = weigh;
        }
    }
}
