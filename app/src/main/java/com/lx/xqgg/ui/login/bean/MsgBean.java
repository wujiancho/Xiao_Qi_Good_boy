package com.lx.xqgg.ui.login.bean;

public class MsgBean {

    /**
     * code : null
     * data : {}
     * message : 发送成功
     * success : true
     * timestamp : 1583889866235
     */

    private Object code;
    private DataBean data;
    private String message;
    private boolean success;
    private long timestamp;

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
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
    }
}
