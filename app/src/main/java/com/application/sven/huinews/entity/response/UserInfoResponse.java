package com.application.sven.huinews.entity.response;

import com.application.sven.huinews.entity.UserInfo;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class UserInfoResponse extends BaseResponse {

    private UserInfo data;

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }
}
