package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;
import java.util.List;

public class HistoryCommissionwithdrawalBean implements Serializable {

    /**
     * code : 200
     * data : {"list":[{"applyTime":"2020-08-31 09:19:09","applyTimeStr":"2020-08-31","approveTime":null,"bankName":"工商银行","bankNo":"1234567890","bankUser":"苏州工商银行","id":4,"money":100,"service_id":301,"status":"未完成","user_id":3008}],"money":0}
     * message : null
     * success : true
     * timestamp : 1598837043581
     */

    private int code;
    private DataBean data;
    private Object message;
    private boolean success;
    private long timestamp;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static class DataBean {
        /**
         * list : [{"applyTime":"2020-08-31 09:19:09","applyTimeStr":"2020-08-31","approveTime":null,"bankName":"工商银行","bankNo":"1234567890","bankUser":"苏州工商银行","id":4,"money":100,"service_id":301,"status":"未完成","user_id":3008}]
         * money : 0
         */

        private int money;
        private List<ListBean> list;

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * applyTime : 2020-08-31 09:19:09
             * applyTimeStr : 2020-08-31
             * approveTime : null
             * bankName : 工商银行
             * bankNo : 1234567890
             * bankUser : 苏州工商银行
             * id : 4
             * money : 100
             * service_id : 301
             * status : 未完成
             * user_id : 3008
             */

            private String applyTime;
            private String applyTimeStr;
            private Object approveTime;
            private String bankName;
            private String bankNo;
            private String bankUser;
            private int id;
            private int money;
            private int service_id;
            private String status;
            private int user_id;

            public String getApplyTime() {
                return applyTime;
            }

            public void setApplyTime(String applyTime) {
                this.applyTime = applyTime;
            }

            public String getApplyTimeStr() {
                return applyTimeStr;
            }

            public void setApplyTimeStr(String applyTimeStr) {
                this.applyTimeStr = applyTimeStr;
            }

            public Object getApproveTime() {
                return approveTime;
            }

            public void setApproveTime(Object approveTime) {
                this.approveTime = approveTime;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getBankNo() {
                return bankNo;
            }

            public void setBankNo(String bankNo) {
                this.bankNo = bankNo;
            }

            public String getBankUser() {
                return bankUser;
            }

            public void setBankUser(String bankUser) {
                this.bankUser = bankUser;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getMoney() {
                return money;
            }

            public void setMoney(int money) {
                this.money = money;
            }

            public int getService_id() {
                return service_id;
            }

            public void setService_id(int service_id) {
                this.service_id = service_id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }
        }
    }
}
