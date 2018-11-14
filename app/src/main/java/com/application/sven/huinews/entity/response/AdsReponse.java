package com.application.sven.huinews.entity.response;

import com.application.sven.huinews.entity.AdsList;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/6/4
 * effect:
 */
public class AdsReponse extends BaseResponse {
    private List<AdsList> data;

    public List<AdsList> getData() {
        return data;
    }

    public void setData(List<AdsList> data) {
        this.data = data;
    }
}
