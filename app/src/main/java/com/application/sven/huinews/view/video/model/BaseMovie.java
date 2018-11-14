package com.application.sven.huinews.view.video.model;

import com.dueeeke.videoplayer.controller.BaseVideoController;

/**
 * auther: sunfuyi
 * data: 2018/5/18
 * effect:
 */
public class BaseMovie {
    private String movieUrl;

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public BaseMovie(String url, BaseVideoController controller) {
        this.movieUrl = url;

        this.controller = controller;
    }

    public BaseVideoController controller;
}
