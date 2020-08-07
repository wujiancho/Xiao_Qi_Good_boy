package com.lx.xqgg.ui.match.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class MatchSavedBean extends AbstractExpandableItem<MatchSavedBean.ProductBean> implements MultiItemEntity {

    /**
     * company : null
     * createtime : 2020-03-19 14:05:20.0
     * id : 680
     * link_man : test
     * link_phone : null
     * product : [{"id":659,"image":"4","quota":1000000,"rate":"1.2\u2031","title":"测试商店"}]
     * productId : 659
     */

    private String company;
    private String createtime;
    private int id;
    private String link_man;
    private String link_phone;
    private String productId;
    private List<ProductBean> product;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public static class ProductBean implements MultiItemEntity {
        /**
         * id : 659
         * image : 4
         * quota : 1000000
         * rate : 1.2‱
         * title : 测试商店
         */

        private int id;
        private String image;
        private int quota;
        private String rate;
        private String title;

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

        public int getQuota() {
            return quota;
        }

        public void setQuota(int quota) {
            this.quota = quota;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public int getItemType() {
            return 1;
        }
    }
}
