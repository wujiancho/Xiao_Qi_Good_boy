package com.lx.xqgg.ui.product.bean;

import java.util.List;

public class CaseInfoBean {

    /**
     * OrderNumber : COURTANNO2020032114425128908086
     * Paging : {"PageSize":10,"PageIndex":1,"TotalRecords":15}
     * Result : [{"Defendantlist":"黄建辉\t广州盈合广告有限公司","Executegov":"上海市宝山法院","Prosecutorlist":"上海微盟企业发展有限公司","LianDate":"2020-04-14 09:00:00","CaseReason":"中外合作经营企业合同纠纷","Id":"f36c5adf6b54ce7d66ddb4c85ef8b9315","CaseNo":"（2019）沪0113民初18075号"},{"Defendantlist":"黄建辉\t广州盈合广告有限公司","Executegov":"上海市宝山法院","Prosecutorlist":"上海微盟企业发展有限公司","LianDate":"2019-12-19 14:00:00","CaseReason":"中外合作经营企业合同纠纷","Id":"88439aee433fa4f7c8d5910d6fe9efe95","CaseNo":"（2019）沪0113民初18075号"},{"Defendantlist":"广州盈合广告有限公司","Executegov":"上海市宝山法院","Prosecutorlist":"上海微盟企业发展有限公司","LianDate":"2019-10-21 09:00:00","CaseReason":"中外合作经营企业合同纠纷","Id":"1f34fc2bda26fcd9efda9ae85cd83fbb5","CaseNo":"（2019）沪0113民初18075号"},{"Defendantlist":"广州盈合广告有限公司","Executegov":"上海市宝山法院","Prosecutorlist":"上海微盟企业发展有限公司","LianDate":"2019-10-16 14:00:00","CaseReason":"中外合作经营企业合同纠纷","Id":"7450552d4650946914fa8af918b68f115","CaseNo":"（2019）沪0113民初18075号"},{"Defendantlist":"广州盈合广告有限公司","Executegov":"深圳市中级人民法院","Prosecutorlist":"北京腾讯文化传媒有限公司","LianDate":"2019-02-25 16:00:00","CaseReason":"广告合同纠纷","Id":"45ad61f038062a87cb634389dee88fd65","CaseNo":"（2018）粤03民终17607号"},{"Defendantlist":"广州盈合广告有限公司","Executegov":"上海市浦东法院","Prosecutorlist":"上海银赛计算机科技有限公司","LianDate":"2019-01-22 14:45:00","CaseReason":"广告合同纠纷","Id":"61b5c306dac6e6f83079f02cf6afe99e5","CaseNo":"（2018）沪0115民初44992号"},{"Defendantlist":"广州盈合广告有限公司","Executegov":"上海市浦东法院","Prosecutorlist":"上海银赛计算机科技有限公司","LianDate":"2018-12-11 13:45:00","CaseReason":"广告合同纠纷","Id":"d70866dfb92e9f53ad63917632a819eb5","CaseNo":"（2018）沪0115民初44992号"},{"Defendantlist":"广州盈合广告有限公司","Executegov":"上海市杨浦法院","Prosecutorlist":"自贡诺亚金融服务有限公司","LianDate":"2018-11-27 15:30:00","CaseReason":"广告合同纠纷","Id":"8b7512c7f7c02fb87e6aba2b6efbf5995","CaseNo":"（2018）沪0110民初22178号"},{"Defendantlist":"广州盈合广告有限公司","Executegov":"上海市杨浦法院","Prosecutorlist":"自贡诺亚金融服务有限公司","LianDate":"2018-11-11 09:30:00","CaseReason":"广告合同纠纷","Id":"485ca807f87f5e3962031330cefd28d65","CaseNo":"（2018）沪0110民初22178号"},{"Defendantlist":"广州盈合广告有限公司","Executegov":"上海市宝山法院","Prosecutorlist":"上海微盟企业发展有限公司","LianDate":"2018-11-01 13:45:00","CaseReason":"广告合同纠纷","Id":"3371097f07602a018fea2c2aa8c26d1a5","CaseNo":"（2018）沪0113民初4510号"}]
     * Status : 200
     * Message : 查询成功
     */

    private String OrderNumber;
    private PagingBean Paging;
    private String Status;
    private String Message;
    private List<ResultBean> Result;

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String OrderNumber) {
        this.OrderNumber = OrderNumber;
    }

    public PagingBean getPaging() {
        return Paging;
    }

    public void setPaging(PagingBean Paging) {
        this.Paging = Paging;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<ResultBean> getResult() {
        return Result;
    }

    public void setResult(List<ResultBean> Result) {
        this.Result = Result;
    }

    public static class PagingBean {
        /**
         * PageSize : 10
         * PageIndex : 1
         * TotalRecords : 15
         */

        private int PageSize;
        private int PageIndex;
        private int TotalRecords;

        public int getPageSize() {
            return PageSize;
        }

        public void setPageSize(int PageSize) {
            this.PageSize = PageSize;
        }

        public int getPageIndex() {
            return PageIndex;
        }

        public void setPageIndex(int PageIndex) {
            this.PageIndex = PageIndex;
        }

        public int getTotalRecords() {
            return TotalRecords;
        }

        public void setTotalRecords(int TotalRecords) {
            this.TotalRecords = TotalRecords;
        }
    }

    public static class ResultBean {
        /**
         * Defendantlist : 黄建辉	广州盈合广告有限公司
         * Executegov : 上海市宝山法院
         * Prosecutorlist : 上海微盟企业发展有限公司
         * LianDate : 2020-04-14 09:00:00
         * CaseReason : 中外合作经营企业合同纠纷
         * Id : f36c5adf6b54ce7d66ddb4c85ef8b9315
         * CaseNo : （2019）沪0113民初18075号
         */

        private String Defendantlist;
        private String Executegov;
        private String Prosecutorlist;
        private String LianDate;
        private String CaseReason;
        private String Id;
        private String CaseNo;

        public String getDefendantlist() {
            return Defendantlist;
        }

        public void setDefendantlist(String Defendantlist) {
            this.Defendantlist = Defendantlist;
        }

        public String getExecutegov() {
            return Executegov;
        }

        public void setExecutegov(String Executegov) {
            this.Executegov = Executegov;
        }

        public String getProsecutorlist() {
            return Prosecutorlist;
        }

        public void setProsecutorlist(String Prosecutorlist) {
            this.Prosecutorlist = Prosecutorlist;
        }

        public String getLianDate() {
            return LianDate;
        }

        public void setLianDate(String LianDate) {
            this.LianDate = LianDate;
        }

        public String getCaseReason() {
            return CaseReason;
        }

        public void setCaseReason(String CaseReason) {
            this.CaseReason = CaseReason;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getCaseNo() {
            return CaseNo;
        }

        public void setCaseNo(String CaseNo) {
            this.CaseNo = CaseNo;
        }
    }
}
