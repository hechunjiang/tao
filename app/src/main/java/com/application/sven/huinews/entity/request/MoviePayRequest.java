package com.application.sven.huinews.entity.request;

import com.application.sven.huinews.entity.response.BaseResponse;

/**
 * auther: sunfuyi
 * data: 2018/7/24
 * effect:
 */
public class MoviePayRequest extends BaseRequest {
    private int moviesId;
    private int playId;

    public int getMoviesId() {
        return moviesId;
    }

    public void setMoviesId(int moviesId) {
        this.moviesId = moviesId;
    }

    public int getPlayId() {
        return playId;
    }

    public void setPlayId(int playId) {
        this.playId = playId;
    }
}
