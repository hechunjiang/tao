package com.application.sven.huinews.view.video;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


import com.application.sven.huinews.entity.VideoModel;
import com.application.sven.huinews.view.video.interf.ListMediaPlayerControl;
import com.application.sven.huinews.view.video.model.BaseMovie;
import com.dueeeke.videoplayer.player.IjkVideoView;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/18
 * effect:
 */
public class ListIjkVideoView extends IjkVideoView implements ListMediaPlayerControl {

    protected List<VideoModel> mVideoModels;//列表播放数据
    protected int mCurrentVideoPosition = 0;//列表播放时当前播放视频的在List中的位置

    public ListIjkVideoView(@NonNull Context context) {
        super(context);
    }

    public ListIjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ListIjkVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onCompletion() {
        super.onCompletion();
        mCurrentVideoPosition++;
        if (mVideoModels != null && mVideoModels.size() > 1) {
            if (mCurrentVideoPosition >= mVideoModels.size()) {
                return;
            }
            playNext();
            addDisplay();
            startPrepare(true);
        }
    }

    /**
     * 播放下一条视频
     */
    private void playNext() {
        VideoModel videoModel = mVideoModels.get(mCurrentVideoPosition);
        if (videoModel != null) {
            mCurrentUrl = videoModel.url;
           // mCurrentPosition = 0;
            setVideoController(videoModel.controller);
        }
    }

    /**
     * 设置一个列表的视频
     */
    public void setVideos(List<VideoModel> videoModels) {
        this.mVideoModels = videoModels;
        playNext();
    }

    /**
     * 播放下一条视频，可用于跳过广告
     */
    @Override
    public void skipToNext() {
        mCurrentVideoPosition++;
        if (mVideoModels != null && mVideoModels.size() > 1) {
            if (mCurrentVideoPosition >= mVideoModels.size()) return;
            playNext();
            addDisplay();
            startPrepare(true);
        }
    }


}
