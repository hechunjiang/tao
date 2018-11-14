package com.application.sven.huinews.entity.response;

import com.application.sven.huinews.entity.MovieHistory;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public class MovieHistoryResponse extends BaseResponse {
    private MovieHistory data;

    public MovieHistory getData() {
        return data;
    }

    public void setData(MovieHistory data) {
        this.data = data;
    }
}
