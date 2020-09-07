package com.lx.xqgg.ui.mycommission.bean;

import java.io.Serializable;

public class BuynowBean implements Serializable {

    /**
     * code : null
     * data : null
     * message : null
     * success : true
     * timestamp : 1597116162202
     */

    private int code;
    private String data;
    private String message;
    private boolean success;
    private long timestamp;

    public Object getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
}
