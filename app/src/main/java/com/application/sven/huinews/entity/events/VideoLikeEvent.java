package com.application.sven.huinews.entity.events;

import com.application.sven.huinews.entity.response.VideoListResponse;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public class VideoLikeEvent {
    private VideoListResponse.VideoList.ListBean mData;

    public VideoLikeEvent(VideoListResponse.VideoList.ListBean mData) {
        this.mData = mData;
    }

    public VideoListResponse.VideoList.ListBean getmData() {
        return mData;
    }

    public void setmData(VideoListResponse.VideoList.ListBean mData) {
        this.mData = mData;
    }
}

