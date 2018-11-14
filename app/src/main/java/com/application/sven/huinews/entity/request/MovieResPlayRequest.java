package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/5/17
 * effect:
 */
public class MovieResPlayRequest extends BaseRequest {
    private int playId;
    private int aId;
    private int rId;

    public int getPlayId() {
        return playId;
    }

    public void setPlayId(int playId) {
        this.playId = playId;
    }


    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    @Override
    public String toString() {
        return "MovieResPlayRequest{" +
                "playId=" + playId +
                ", aId=" + aId +
                ", rId=" + rId +
                '}';
    }
}
