package com.lx.xqgg.ui.match.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class MatchResultBean extends AbstractExpandableItem<MatchResultBean.ProductBean> implements MultiItemEntity {

    /**
     * cateId : 51
     * cateImage : /common/image?fileId=f58e1caa4168421fa2bf638ec75b9d73.png
     * cateName : 税贷
     * product : [{"adviseWeigh":999,"apply_num":273,"cityLink":null,"classifyId":",51,","classifyList":null,"classifyName":",税贷,","condition":null,"content":null,"createtime":"2020-03-20 14:36:56","featureId":null,"featureList":null,"featureName":null,"flowImage":null,"guarantee":"ben","id":682,"image":"/common/image?fileId=faf19afac5884b8c93fc9ce2966e6e97.png","limitMonth":null,"loantype":"2","mortgageOrpolicy":"-1","orderStatus":null,"quota":1000000,"rate":"(0.25-0.33)\u2030","status":"normal","title":"张农银行 - 优企贷","video":""},{"adviseWeigh":999,"apply_num":27,"cityLink":null,"classifyId":",51,","classifyList":null,"classifyName":",税贷,","condition":null,"content":null,"createtime":"2020-04-26 17:46:36","featureId":null,"featureList":null,"featureName":null,"flowImage":null,"guarantee":"ben","id":733,"image":"/common/image?fileId=3100829109a84d0bba83864a1a2b0589.png","limitMonth":null,"loantype":"2","mortgageOrpolicy":"-1","orderStatus":null,"quota":1000000,"rate":"(0.19-0.33)\u2030","status":"normal","title":"渤海银行 - 渤业贷","video":""},{"adviseWeigh":998,"apply_num":1079,"cityLink":null,"classifyId":",51,","classifyList":null,"classifyName":",税贷,","condition":null,"content":null,"createtime":"2020-03-20 17:25:43","featureId":null,"featureList":null,"featureName":null,"flowImage":null,"guarantee":"jin","id":685,"image":"/common/image?fileId=92e011fa92ff420dba997daaacf7f325.png","limitMonth":null,"loantype":"2","mortgageOrpolicy":"-1","orderStatus":"created","quota":2000000,"rate":"0.47\u2030","status":"normal","title":"苏宁银行 - 微商贷","video":""},{"adviseWeigh":996,"apply_num":246,"cityLink":null,"classifyId":",51,","classifyList":null,"classifyName":",税贷,","condition":null,"content":null,"createtime":"2020-03-20 15:47:21","featureId":null,"featureList":null,"featureName":null,"flowImage":null,"guarantee":"jin","id":684,"image":"/common/image?fileId=eac1c05ec0ef41598049bef597353b0b.png","limitMonth":null,"loantype":"2","mortgageOrpolicy":"-1","orderStatus":"created","quota":3000000,"rate":"(0.3-0.5)\u2030","status":"normal","title":"微众银行 - 微税贷","video":""},{"adviseWeigh":993,"apply_num":252,"cityLink":null,"classifyId":",51,","classifyList":null,"classifyName":",税贷,","condition":null,"content":null,"createtime":"2020-03-20 14:57:20","featureId":null,"featureList":null,"featureName":null,"flowImage":null,"guarantee":"xi","id":683,"image":"/common/image?fileId=fd0ae4856cd9493cb7cd7d396fef5e9b.png","limitMonth":null,"loantype":"2","mortgageOrpolicy":"-1","orderStatus":"nopass","quota":1000000,"rate":"(0.3-0.37)\u2030","status":"normal","title":"大数金融 - 掌柜贷","video":""},{"adviseWeigh":992,"apply_num":266,"cityLink":null,"classifyId":",51,","classifyList":null,"classifyName":",税贷,","condition":null,"content":null,"createtime":"2020-03-26 18:18:35","featureId":null,"featureList":null,"featureName":null,"flowImage":null,"guarantee":"jin,xi","id":724,"image":"/common/image?fileId=15c9f2bfbb0745fbaebd38040a11a3e4.png","limitMonth":null,"loantype":"2","mortgageOrpolicy":"-1","orderStatus":"created","quota":500000,"rate":"(0.3-0.36)\u2030","status":"normal","title":"平安银行 - 税金贷","video":""},{"adviseWeigh":885,"apply_num":158,"cityLink":null,"classifyId":",51,","classifyList":null,"classifyName":",税贷,","condition":null,"content":null,"createtime":"2020-03-24 18:38:55","featureId":null,"featureList":null,"featureName":null,"flowImage":null,"guarantee":"xi","id":707,"image":"/common/image?fileId=50753d34a2f047c0af5d9fc2cdf44b16.png","limitMonth":null,"loantype":"2","mortgageOrpolicy":null,"orderStatus":null,"quota":500000,"rate":"(0.3- 0.4)\u2030","status":"normal","title":"富民银行 - 富税贷","video":""},{"adviseWeigh":0,"apply_num":1,"cityLink":null,"classifyId":",51,","classifyList":null,"classifyName":",税贷,","condition":null,"content":null,"createtime":"2020-03-24 18:40:35","featureId":null,"featureList":null,"featureName":null,"flowImage":null,"guarantee":"jin,xi","id":708,"image":"/common/image?fileId=e68fcb42dcb64633947f9978707f2f74.png","limitMonth":null,"loantype":"2","mortgageOrpolicy":"0","orderStatus":null,"quota":500000,"rate":"0.33\u2030","status":"normal","title":"民生银行  -  网乐贷","video":""}]
     */

    private int cateId;
    private String cateImage;
    private String cateName;
    private List<ProductBean> product;

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getCateImage() {
        return cateImage;
    }

    public void setCateImage(String cateImage) {
        this.cateImage = cateImage;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
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

    public static class ProductBean implements MultiItemEntity{
        /**
         * adviseWeigh : 999
         * apply_num : 273
         * cityLink : null
         * classifyId : ,51,
         * classifyList : null
         * classifyName : ,税贷,
         * condition : null
         * content : null
         * createtime : 2020-03-20 14:36:56
         * featureId : null
         * featureList : null
         * featureName : null
         * flowImage : null
         * guarantee : ben
         * id : 682
         * image : /common/image?fileId=faf19afac5884b8c93fc9ce2966e6e97.png
         * limitMonth : null
         * loantype : 2
         * mortgageOrpolicy : -1
         * orderStatus : null
         * quota : 1000000
         * rate : (0.25-0.33)‰
         * status : normal
         * title : 张农银行 - 优企贷
         * video :
         */

        private int adviseWeigh;
        private int apply_num;
        private Object cityLink;
        private String classifyId;
        private Object classifyList;
        private String classifyName;
        private Object condition;
        private Object content;
        private String createtime;
        private Object featureId;
        private Object featureList;
        private Object featureName;
        private Object flowImage;
        private String guarantee;
        private int id;
        private String image;
        private Object limitMonth;
        private String loantype;
        private String mortgageOrpolicy;
        private String orderStatus;
        private int quota;
        private String rate;
        private String status;
        private String title;
        private String video;
        private int sortIndex;

        public int getSortIndex() {
            return sortIndex;
        }

        public void setSortIndex(int sortIndex) {
            this.sortIndex = sortIndex;
        }

        public int getAdviseWeigh() {
            return adviseWeigh;
        }

        public void setAdviseWeigh(int adviseWeigh) {
            this.adviseWeigh = adviseWeigh;
        }

        public int getApply_num() {
            return apply_num;
        }

        public void setApply_num(int apply_num) {
            this.apply_num = apply_num;
        }

        public Object getCityLink() {
            return cityLink;
        }

        public void setCityLink(Object cityLink) {
            this.cityLink = cityLink;
        }

        public String getClassifyId() {
            return classifyId;
        }

        public void setClassifyId(String classifyId) {
            this.classifyId = classifyId;
        }

        public Object getClassifyList() {
            return classifyList;
        }

        public void setClassifyList(Object classifyList) {
            this.classifyList = classifyList;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public Object getCondition() {
            return condition;
        }

        public void setCondition(Object condition) {
            this.condition = condition;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public Object getFeatureId() {
            return featureId;
        }

        public void setFeatureId(Object featureId) {
            this.featureId = featureId;
        }

        public Object getFeatureList() {
            return featureList;
        }

        public void setFeatureList(Object featureList) {
            this.featureList = featureList;
        }

        public Object getFeatureName() {
            return featureName;
        }

        public void setFeatureName(Object featureName) {
            this.featureName = featureName;
        }

        public Object getFlowImage() {
            return flowImage;
        }

        public void setFlowImage(Object flowImage) {
            this.flowImage = flowImage;
        }

        public String getGuarantee() {
            return guarantee;
        }

        public void setGuarantee(String guarantee) {
            this.guarantee = guarantee;
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

        public Object getLimitMonth() {
            return limitMonth;
        }

        public void setLimitMonth(Object limitMonth) {
            this.limitMonth = limitMonth;
        }

        public String getLoantype() {
            return loantype;
        }

        public void setLoantype(String loantype) {
            this.loantype = loantype;
        }

        public String getMortgageOrpolicy() {
            return mortgageOrpolicy;
        }

        public void setMortgageOrpolicy(String mortgageOrpolicy) {
            this.mortgageOrpolicy = mortgageOrpolicy;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
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

        @Override
        public int getItemType() {
            return 1;
        }
    }
}
