package com.lx.xqgg.ui.home.bean;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

public class BannerBean extends SimpleBannerInfo {

    /**
     * appCompanyName : null
     * appid : 1
     * content :
     * createtime : 2019-03-26 21:16:59.0
     * id : 1
     * image : /uploads/20190718/e1bebda98419cd9f79879e32600b8aae.gif
     * name : indexfocus
     * status : normal
     * title : 03
     * type : 轮播图
     * updatetime : 2019-11-18 19:02:26.0
     * url :
     * weigh : 9
     */

    private String appCompanyName;
    private int appid;
    private String content;
    private String createtime;
    private int id;
    private String image;
    private String name;
    private String status;
    private String title;
    private String type;
    private String updatetime;
    private String url;
    private int weigh;

    public String getAppCompanyName() {
        return appCompanyName;
    }

    public void setAppCompanyName(String appCompanyName) {
        this.appCompanyName = appCompanyName;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWeigh() {
        return weigh;
    }

    public void setWeigh(int weigh) {
        this.weigh = weigh;
    }

    @Override
    public Object getXBannerUrl() {
        return null;
    }
}
