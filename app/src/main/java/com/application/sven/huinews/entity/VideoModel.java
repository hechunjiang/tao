package com.application.sven.huinews.entity;

import com.dueeeke.videoplayer.controller.BaseVideoController;

/**
 * auther: sunfuyi
 * data: 2018/6/5
 * effect:
 */
public class VideoModel {

    public String url;
    public String title;

    public VideoModel(String url, String title, BaseVideoController controller) {
        this.url = url;
        this.title = title;
        this.controller = controller;
    }

    public BaseVideoController controller;
}
