package com.lx.xqgg.face_ui.home.bean;

import java.io.Serializable;
import java.util.List;

public class FaceProductBean implements Serializable{

    /**
     * id : 700
     * image : /common/image?fileId=baiwenbaida.jpg
     * product : [{"delFlag":null,"fpid":700,"id":701,"image":"/common/image?fileId=baiwenbaida.jpg","seriesImage":"/common/image?fileId=baiwenbaidaseries.jpg","seriesTitle":"【音频】激励员工最有效的六大方式？","seriesVideo":"/common/image?fileId=baiwenbaida2.mp3","title":"百问百答系列"}]
     * title : 百问百答系列
     */

    private int id;
    private String image;
    private String title;
    private List<ProductBean> product;

    public FaceProductBean(int id, String image, String title) {
        this.id = id;
        this.image = image;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public static class ProductBean implements Serializable {
        /**
         * delFlag : null
         * fpid : 700
         * id : 701
         * image : /common/image?fileId=baiwenbaida.jpg
         * seriesImage : /common/image?fileId=baiwenbaidaseries.jpg
         * seriesTitle : 【音频】激励员工最有效的六大方式？
         * seriesVideo : /common/image?fileId=baiwenbaida2.mp3
         * title : 百问百答系列
         */

        private Object delFlag;
        private int fpid;
        private int id;
        private String image;
        private String seriesImage;
        private String seriesTitle;
        private String seriesVideo;
        private String title;

        public Object getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(Object delFlag) {
            this.delFlag = delFlag;
        }

        public int getFpid() {
            return fpid;
        }

        public void setFpid(int fpid) {
            this.fpid = fpid;
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

        public String getSeriesImage() {
            return seriesImage;
        }

        public void setSeriesImage(String seriesImage) {
            this.seriesImage = seriesImage;
        }

        public String getSeriesTitle() {
            return seriesTitle;
        }

        public void setSeriesTitle(String seriesTitle) {
            this.seriesTitle = seriesTitle;
        }

        public String getSeriesVideo() {
            return seriesVideo;
        }

        public void setSeriesVideo(String seriesVideo) {
            this.seriesVideo = seriesVideo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
