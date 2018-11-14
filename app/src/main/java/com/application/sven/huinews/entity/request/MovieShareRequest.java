package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/6/13
 * effect:
 */
public class MovieShareRequest extends BaseRequest {
    private int movies_id;
    private int play_id;

    public int getMovies_id() {
        return movies_id;
    }

    public void setMovies_id(int movies_id) {
        this.movies_id = movies_id;
    }

    public int getPlay_id() {
        return play_id;
    }

    public void setPlay_id(int play_id) {
        this.play_id = play_id;
    }
}
