package com.application.sven.huinews.entity.response;

import com.application.sven.huinews.entity.User;
import com.google.gson.annotations.SerializedName;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class LoginResponse extends BaseResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("ticket")
        private String loginTicket;
        @SerializedName("user_info")
        private User user;

        @SerializedName("login_flag")
        private boolean isUserLogin;//是否是用户登录

        public String getLoginTicket() {
            return loginTicket;
        }

        public void setLoginTicket(String loginTicket) {
            this.loginTicket = loginTicket;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public boolean isUserLogin() {
            return isUserLogin;
        }

        public void setUserLogin(boolean userLogin) {
            isUserLogin = userLogin;
        }
    }
}
