package com.lx.xqgg.ui.match.bean;

public class SaveRequestBean {
    private String token;
    private String productId;
    private MatchRequestBean condition;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public MatchRequestBean getCondition() {
        return condition;
    }

    public void setCondition(MatchRequestBean condition) {
        this.condition = condition;
    }
}
