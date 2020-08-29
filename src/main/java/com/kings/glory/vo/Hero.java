package com.kings.glory.vo;

public class Hero {

    /**
     * 英雄名称
     */
    private String hero;

    /**
     * 英雄图片
     */
    private String picture;

    /**
     * 英雄详情页
     */
    private String detail;

    /**
     * 英雄的荣耀详情
     */
    private String glory;

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "[\"hero\":" + this.hero + ",\"picture\":" + this.picture
                + ",\"detail\":" + this.detail + ",\"glory\":" + this.glory + "]";
    }
}
