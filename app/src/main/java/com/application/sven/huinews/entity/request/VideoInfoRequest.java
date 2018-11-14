package com.application.sven.huinews.entity.request;

import com.application.sven.huinews.entity.response.VideoListResponse;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/5/22
 * effect:
 */
public class VideoInfoRequest implements Serializable {
    private VideoListResponse.VideoList.ListBean mVideoInfo;
    private int position;
    private int channel_id;

    public VideoListResponse.VideoList.ListBean getmVideoInfo() {
        return mVideoInfo;
    }

    public void setmVideoInfo(VideoListResponse.VideoList.ListBean mVideoInfo) {
        this.mVideoInfo = mVideoInfo;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }
}
