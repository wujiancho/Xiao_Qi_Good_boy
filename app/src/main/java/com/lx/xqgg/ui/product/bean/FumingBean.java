package com.lx.xqgg.ui.product.bean;

import java.io.Serializable;

public class FumingBean implements Serializable {

    private  FuMinTokenBusOutBody busOutBody;
    private String message;
    private String status;
    private String timestamp;
    public FuMinTokenBusOutBody getBusOutBody() {
        return busOutBody;
    }
    public void setBusOutBody(FuMinTokenBusOutBody busOutBody) {
        this.busOutBody = busOutBody;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = (message == null ? null : message.trim());
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = (status == null ? null : status.trim());
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = (timestamp == null ? null : timestamp.trim());
    }
}
