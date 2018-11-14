package com.application.sven.huinews.entity.request;

import com.google.gson.annotations.SerializedName;

/**
 * auther: sunfuyi
 * data: 2018/7/10
 * effect:
 */
public class BookListRequest extends BaseRequest {
    private int typeId;
    private int wtime;

    public int getWtime() {
        return wtime;
    }

    public void setWtime(int wtime) {
        this.wtime = wtime;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
