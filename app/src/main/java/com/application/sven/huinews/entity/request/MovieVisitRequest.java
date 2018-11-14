package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public class MovieVisitRequest extends BaseRequest {
    private int movies_id;
    private int play_id;
    private int play_time;

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

    public int getPlay_time() {
        return play_time;
    }

    public void setPlay_time(int play_time) {
        this.play_time = play_time;
    }

    @Override
    public String toString() {
        return "MovieVisitRequest{" +
                "movies_id=" + movies_id +
                ", play_id=" + play_id +
                ", play_time=" + play_time +
                '}';
    }
}
