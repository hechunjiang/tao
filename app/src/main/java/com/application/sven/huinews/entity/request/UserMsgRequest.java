package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/5/18
 * effect:
 */
public class UserMsgRequest extends BaseRequest {
    private int user_id;
    private int type;


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
