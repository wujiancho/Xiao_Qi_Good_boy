package com.lx.xqgg.ui.qcc.bean;

import java.io.Serializable;
import java.util.List;

public class FuzzyqueryQccBean implements Serializable {


    /**
     * Paging : {"PageSize":10,"PageIndex":1,"TotalRecords":393}
     * Result : [{"KeyNo":"43deb5e9ccfb6328eb1771af5118f287","Name":"上海蘑菇云平台经济发展有限公司","OperName":"吴家翔","StartDate":"2016-02-19 00:00:00","Status":"存续","No":"310141000272388","CreditCode":"91310000MA1K382LX1"},{"KeyNo":"f772e4a6c7639bc62fbc036516903be3","Name":"合肥蘑菇云信息科技有限公司","OperName":"徐琳","StartDate":"2019-04-30 00:00:00","Status":"存续","No":"","CreditCode":"91340102MA2TNMN68T"},{"KeyNo":"e1afb1188493ee85c7eb1876e43854b0","Name":"苏州蘑菇云网络科技有限公司","OperName":"崔茂","StartDate":"2016-12-13 00:00:00","Status":"存续","No":"320508000332537","CreditCode":"91320508MA1N3B129R"},{"KeyNo":"bc478049f4ee8b26796782e623003282","Name":"深圳市蘑菇财富技术有限公司","OperName":"宋洪强","StartDate":"2014-04-17 00:00:00","Status":"存续","No":"440306109178395","CreditCode":"914403000940931047"},{"KeyNo":"45867b46715515ab00e7a0e81d630d3f","Name":"成都维怿科技有限公司","OperName":"李兆亮","StartDate":"2013-09-23 00:00:00","Status":"存续","No":"510109000393051","CreditCode":"915101000776869017"},{"KeyNo":"80cfeb37bb7bf0e6eab9c6962b8c4e40","Name":"杭州蘑菇云保科技有限公司","OperName":"马桂琴","StartDate":"2018-12-24 00:00:00","Status":"注销","No":"330106000795888","CreditCode":"91330106MA2CGCB87D"},{"KeyNo":"77db682a5901200000bfaf0ff0a0eb8b","Name":"佛山市蘑菇云门业有限公司","OperName":"林延培","StartDate":"2016-01-20 00:00:00","Status":"在业","No":"440682000702648","CreditCode":"91440605MA4ULML77Q"},{"KeyNo":"h0ba47300217a24752f8480d231480c6","Name":"香港蘑菇云科技有限公司","OperName":"","StartDate":"2014-03-26 00:00:00","Status":"已告解散","No":"2066309","CreditCode":""},{"KeyNo":"21941823792317df50c194b609217865","Name":"深圳市蘑菇云网络有限公司","OperName":"莫永辉","StartDate":"2016-03-30 00:00:00","Status":"存续","No":"440301115555547","CreditCode":"91440300MA5D9M9M4L"},{"KeyNo":"f60947c8dac3d6c4f8b8bd4e23762578","Name":"贵州蘑菇云科技有限公司","OperName":"张文明","StartDate":"2020-04-09 00:00:00","Status":"存续","No":"","CreditCode":"91520112MA6JCG2L92"}]
     * Status : 200
     * Message : 查询成功
     * OrderNumber : ECI2020072014443230208182
     */

    private String Status;
    private String Message;
    private String OrderNumber;
    private List<ResultBean> Result;
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

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String OrderNumber) {
        this.OrderNumber = OrderNumber;
    }

    public List<ResultBean> getResult() {
        return Result;
    }

    public void setResult(List<ResultBean> Result) {
        this.Result = Result;
    }

    public static class ResultBean implements Serializable{
        /**
         * KeyNo : 43deb5e9ccfb6328eb1771af5118f287
         * Name : 上海蘑菇云平台经济发展有限公司
         * OperName : 吴家翔
         * StartDate : 2016-02-19 00:00:00
         * Status : 存续
         * No : 310141000272388
         * CreditCode : 91310000MA1K382LX1
         */

        private String KeyNo;
        private String Name;
        private String OperName;
        private String StartDate;
        private String Status;
        private String No;
        private String CreditCode;

        public String getKeyNo() {
            return KeyNo;
        }

        public void setKeyNo(String KeyNo) {
            this.KeyNo = KeyNo;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getOperName() {
            return OperName;
        }

        public void setOperName(String OperName) {
            this.OperName = OperName;
        }

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String StartDate) {
            this.StartDate = StartDate;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public String getNo() {
            return No;
        }

        public void setNo(String No) {
            this.No = No;
        }

        public String getCreditCode() {
            return CreditCode;
        }

        public void setCreditCode(String CreditCode) {
            this.CreditCode = CreditCode;
        }
    }
}
