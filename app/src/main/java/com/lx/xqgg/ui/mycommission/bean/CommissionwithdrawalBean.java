package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class CommissionwithdrawalBean implements Serializable {

    /**
     * code : null
     * data : null
     * message : null
     * success : true
     * timestamp : 1594360453770
     */

    private Object code;
    private Object data;
    private Object message;
    private boolean success;
    private long timestamp;

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
}
