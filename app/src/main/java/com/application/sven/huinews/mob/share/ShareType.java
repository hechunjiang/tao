package com.application.sven.huinews.mob.share;

import java.util.ArrayList;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class ShareType {
    private int type; //分享类型 标记微信,qq,朋友圈，qq空间
    private String title; //分享标题
    private String content; //分享内容 描述 文本
    private String imgUrl; //图片地址
    private String url; //跳转路径
    private int wechatShareType; //微信的分享类型  图片 ，文本，网页等
    private ArrayList<String> imgArray; //图片数组
    private String activity_type;

    public ArrayList<String> getImgArray() {
        return imgArray;
    }

    public void setImgArray(ArrayList<String> imgArray) {
        this.imgArray = imgArray;
    }

    public int getWechatShareType() {
        return wechatShareType;
    }

    public void setWechatShareType(int wechatShareType) {
        this.wechatShareType = wechatShareType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    @Override
    public String toString() {
        return "JsShareType{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", url='" + url + '\'' +
                ", wechatShareType=" + wechatShareType +
                ", imgArray=" + imgArray +
                ", activity_type='" + activity_type + '\'' +
                '}';
    }
}
