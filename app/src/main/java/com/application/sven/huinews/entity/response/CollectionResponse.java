package com.application.sven.huinews.entity.response;

import com.application.sven.huinews.entity.CollectionList;

/**
 * auther: sunfuyi
 * data: 2018/5/24
 * effect:
 */
public class CollectionResponse extends BaseResponse {

    private CollectionList data;

    public CollectionList getData() {
        return data;
    }

    public void setData(CollectionList data) {
        this.data = data;
    }
}
