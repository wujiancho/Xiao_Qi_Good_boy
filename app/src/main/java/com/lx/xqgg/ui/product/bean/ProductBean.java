package com.lx.xqgg.ui.product.bean;

import java.io.Serializable;
import java.util.List;

public class ProductBean implements Serializable{

    /**
     * isSuccess : true
     * page : {"page":1,"size":10,"total":120,"totalPage":12}
     * records : [{"apply_num":1,"cityLink":null,"classifyId":"","classifyList":[{"id":"15","name":""},{"id":"21","name":"=="}],"classifyName":",,","condition":null,"content":null,"createtime":"2020-02-27 14:21:23","featureList":null,"guarantee":"","id":656,"image":"","loantype":"1,2","mortgageOrpolicy":null,"quota":1000000,"rate":"1.2\u2031","title":"","video":""}]
     * total : 120
     */

    private boolean isSuccess;
    private String message;
    private PageBean page;
    private int total;
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

    public static class PageBean implements Serializable{
        /**
         * page : 1
         * size : 10
         * total : 120
         * totalPage : 12
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

    public static class RecordsBean implements Serializable {

        /**
         * apply_num : 0
         * cityLink : https://dbank.zrcbank.com:8443/samples/#/YQGetLimit?tellerId=00000018&perrandom=Pxj9b4dmY9LQnlRch230t2Z9rxMzy1
         * classifyId : ,51,
         * classifyList : [{"id":"51","name":""}]
         * classifyName : ,,
         * condition : {"ageEnd":60,"ageStart":18,"changeMonth":6,"companyType":"有限公司","delFlag":null,"foundTime":24,"id":683,"loanBalance":-1,"loanTimes":5,"mortgage":"-1","mortgageOrpolicy":null,"overdue":"0","overdueTimes":10,"position":"","productId":682,"shareHolding":20,"sixMonthLoanSearchTImes":-1,"sixMonthOverdueTimes":-1,"spouseInformed":"-1","taxLevel":"C","taxOwed":"0","twoMonthLoanSearchTimes":-1,"twoYearOverdueFourTimes":-1,"twoYearPolicy":"-1","yearInvoice":-1,"yearOverdueTimesThreesTimes":-1,"yeatTax":5000}
         * content :
         * createtime : 2020-03-20 14:36:56
         * featureList : null
         * flowImage : /common/image?fileId=058d98f38b3a465d89626a470002dd41.jpg
         * guarantee : ben
         * id : 682
         * image : /common/image?fileId=1fee76a1fb174dc0b110e9ed2395c1dc.png
         * limitMonth : 12
         * loantype : 3
         * mortgageOrpolicy : null
         * quota : 1000000
         * rate : 0.26‰ - 0.36‰
         * title :
         * video :
         */

        private int apply_num;
        private String cityLink;
        private String classifyId;
        private String classifyName;
        private ConditionBean condition;
        private String content;
        private String createtime;
        private List<ClassifyListBean> featureList;
        private String flowImage;
        private String guarantee;
        private int id;
        private String image;
        private String limitMonth;
        private String loantype;
        private Object mortgageOrpolicy;
        private int quota;
        private String rate;
        private String title;
        private String video;
        private List<ClassifyListBean> classifyList;

        public int getApply_num() {
            return apply_num;
        }

        public void setApply_num(int apply_num) {
            this.apply_num = apply_num;
        }

        public String getCityLink() {
            return cityLink;
        }

        public void setCityLink(String cityLink) {
            this.cityLink = cityLink;
        }

        public String getClassifyId() {
            return classifyId;
        }

        public void setClassifyId(String classifyId) {
            this.classifyId = classifyId;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public ConditionBean getCondition() {
            return condition;
        }

        public void setCondition(ConditionBean condition) {
            this.condition = condition;
        }

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

        public List<ClassifyListBean> getFeatureList() {
            return featureList;
        }

        public void setFeatureList(List<ClassifyListBean> featureList) {
            this.featureList = featureList;
        }

        public String getFlowImage() {
            return flowImage;
        }

        public void setFlowImage(String flowImage) {
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

        public String getLimitMonth() {
            return limitMonth;
        }

        public void setLimitMonth(String limitMonth) {
            this.limitMonth = limitMonth;
        }

        public String getLoantype() {
            return loantype;
        }

        public void setLoantype(String loantype) {
            this.loantype = loantype;
        }

        public Object getMortgageOrpolicy() {
            return mortgageOrpolicy;
        }

        public void setMortgageOrpolicy(Object mortgageOrpolicy) {
            this.mortgageOrpolicy = mortgageOrpolicy;
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

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public List<ClassifyListBean> getClassifyList() {
            return classifyList;
        }

        public void setClassifyList(List<ClassifyListBean> classifyList) {
            this.classifyList = classifyList;
        }

        public static class ConditionBean implements Serializable{
            /**
             * ageEnd : 60
             * ageStart : 18
             * changeMonth : 6
             * companyType : 有限公司
             * delFlag : null
             * foundTime : 24
             * id : 683
             * loanBalance : -1
             * loanTimes : 5
             * mortgage : -1
             * mortgageOrpolicy : null
             * overdue : 0
             * overdueTimes : 10
             * position :
             * productId : 682
             * shareHolding : 20
             * sixMonthLoanSearchTImes : -1
             * sixMonthOverdueTimes : -1
             * spouseInformed : -1
             * taxLevel : C
             * taxOwed : 0
             * twoMonthLoanSearchTimes : -1
             * twoYearOverdueFourTimes : -1
             * twoYearPolicy : -1
             * yearInvoice : -1
             * yearOverdueTimesThreesTimes : -1
             * yeatTax : 5000
             */

            private int ageEnd;
            private int ageStart;
            private int changeMonth;
            private String companyType;
            private Object delFlag;
            private int foundTime;
            private int id;
            private int loanBalance;
            private int loanTimes;
            private String mortgage;
            private Object mortgageOrpolicy;
            private String overdue;
            private int overdueTimes;
            private String position;
            private int productId;
            private int shareHolding;
            private int sixMonthLoanSearchTImes;
            private int sixMonthOverdueTimes;
            private String spouseInformed;
            private String taxLevel;
            private String taxOwed;
            private int twoMonthLoanSearchTimes;
            private int twoYearOverdueFourTimes;
            private String twoYearPolicy;
            private int yearInvoice;
            private int yearOverdueTimesThreesTimes;
            private int yeatTax;

            public int getAgeEnd() {
                return ageEnd;
            }

            public void setAgeEnd(int ageEnd) {
                this.ageEnd = ageEnd;
            }

            public int getAgeStart() {
                return ageStart;
            }

            public void setAgeStart(int ageStart) {
                this.ageStart = ageStart;
            }

            public int getChangeMonth() {
                return changeMonth;
            }

            public void setChangeMonth(int changeMonth) {
                this.changeMonth = changeMonth;
            }

            public String getCompanyType() {
                return companyType;
            }

            public void setCompanyType(String companyType) {
                this.companyType = companyType;
            }

            public Object getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(Object delFlag) {
                this.delFlag = delFlag;
            }

            public int getFoundTime() {
                return foundTime;
            }

            public void setFoundTime(int foundTime) {
                this.foundTime = foundTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLoanBalance() {
                return loanBalance;
            }

            public void setLoanBalance(int loanBalance) {
                this.loanBalance = loanBalance;
            }

            public int getLoanTimes() {
                return loanTimes;
            }

            public void setLoanTimes(int loanTimes) {
                this.loanTimes = loanTimes;
            }

            public String getMortgage() {
                return mortgage;
            }

            public void setMortgage(String mortgage) {
                this.mortgage = mortgage;
            }

            public Object getMortgageOrpolicy() {
                return mortgageOrpolicy;
            }

            public void setMortgageOrpolicy(Object mortgageOrpolicy) {
                this.mortgageOrpolicy = mortgageOrpolicy;
            }

            public String getOverdue() {
                return overdue;
            }

            public void setOverdue(String overdue) {
                this.overdue = overdue;
            }

            public int getOverdueTimes() {
                return overdueTimes;
            }

            public void setOverdueTimes(int overdueTimes) {
                this.overdueTimes = overdueTimes;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public int getShareHolding() {
                return shareHolding;
            }

            public void setShareHolding(int shareHolding) {
                this.shareHolding = shareHolding;
            }

            public int getSixMonthLoanSearchTImes() {
                return sixMonthLoanSearchTImes;
            }

            public void setSixMonthLoanSearchTImes(int sixMonthLoanSearchTImes) {
                this.sixMonthLoanSearchTImes = sixMonthLoanSearchTImes;
            }

            public int getSixMonthOverdueTimes() {
                return sixMonthOverdueTimes;
            }

            public void setSixMonthOverdueTimes(int sixMonthOverdueTimes) {
                this.sixMonthOverdueTimes = sixMonthOverdueTimes;
            }

            public String getSpouseInformed() {
                return spouseInformed;
            }

            public void setSpouseInformed(String spouseInformed) {
                this.spouseInformed = spouseInformed;
            }

            public String getTaxLevel() {
                return taxLevel;
            }

            public void setTaxLevel(String taxLevel) {
                this.taxLevel = taxLevel;
            }

            public String getTaxOwed() {
                return taxOwed;
            }

            public void setTaxOwed(String taxOwed) {
                this.taxOwed = taxOwed;
            }

            public int getTwoMonthLoanSearchTimes() {
                return twoMonthLoanSearchTimes;
            }

            public void setTwoMonthLoanSearchTimes(int twoMonthLoanSearchTimes) {
                this.twoMonthLoanSearchTimes = twoMonthLoanSearchTimes;
            }

            public int getTwoYearOverdueFourTimes() {
                return twoYearOverdueFourTimes;
            }

            public void setTwoYearOverdueFourTimes(int twoYearOverdueFourTimes) {
                this.twoYearOverdueFourTimes = twoYearOverdueFourTimes;
            }

            public String getTwoYearPolicy() {
                return twoYearPolicy;
            }

            public void setTwoYearPolicy(String twoYearPolicy) {
                this.twoYearPolicy = twoYearPolicy;
            }

            public int getYearInvoice() {
                return yearInvoice;
            }

            public void setYearInvoice(int yearInvoice) {
                this.yearInvoice = yearInvoice;
            }

            public int getYearOverdueTimesThreesTimes() {
                return yearOverdueTimesThreesTimes;
            }

            public void setYearOverdueTimesThreesTimes(int yearOverdueTimesThreesTimes) {
                this.yearOverdueTimesThreesTimes = yearOverdueTimesThreesTimes;
            }

            public int getYeatTax() {
                return yeatTax;
            }

            public void setYeatTax(int yeatTax) {
                this.yeatTax = yeatTax;
            }
        }

        public static class ClassifyListBean {
            /**
             * id : 51
             * name : sd
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
