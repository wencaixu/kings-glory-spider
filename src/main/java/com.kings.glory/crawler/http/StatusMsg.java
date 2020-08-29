package com.kings.glory.crawler.http;

/**
 * 请求状态
 */
public enum  StatusMsg {

    http_error("请求失败");

    private String msg;

    public String getMsg(){
        return msg;
    }

    StatusMsg(String msg) {
        this.msg = msg;
    }
}
