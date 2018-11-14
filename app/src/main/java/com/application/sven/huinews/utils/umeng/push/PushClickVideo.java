package com.application.sven.huinews.utils.umeng.push;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/7 0007.
 */

public class PushClickVideo implements Serializable {
    private String video_id;

    private boolean is_add_gold;

    public boolean isIs_add_gold() {
        return is_add_gold;
    }

    public void setIs_add_gold(boolean is_add_gold) {
        this.is_add_gold = is_add_gold;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    @Override
    public String toString() {
        return "PushClickVideo{" +
                "video_id='" + video_id + '\'' +
                '}';
    }
}
