package com.application.sven.huinews.config;

import java.io.Serializable;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class ChannelBean implements Serializable {
    private int channelId;
    private String channelTitle;
    private boolean isEdit;

    public ChannelBean(int channelId, String channelTitle) {
        this.channelId = channelId;
        this.channelTitle = channelTitle;
    }

    public ChannelBean(int channelId, String channelTitle, boolean isEdit) {
        this.channelId = channelId;
        this.channelTitle = channelTitle;
        this.isEdit = isEdit;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
}
