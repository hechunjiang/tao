package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/5/21
 * effect:
 */
public class VideoCollectionRequest extends BaseRequest {
    private int type;
    private long rId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getrId() {
        return rId;
    }

    public void setrId(long rId) {
        this.rId = rId;
    }
}
