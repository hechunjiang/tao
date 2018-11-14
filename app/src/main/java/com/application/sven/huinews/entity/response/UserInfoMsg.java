package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/6/5
 * effect:
 */
public class UserInfoMsg extends BaseResponse {


    private UserInfo data;

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

    public static class UserInfo implements Serializable {


        /**
         * user_id : 2138
         * nickname : 鲜生果园
         * headimg : http://thirdwx.qlogo.cn/mmopen/vi_32/GbonzdooBz7E0LMwibwFMxGJsxBDSLZibXsLl2gAeg3UrXPGEmXib8zchd4OalFlTpGDcia4LLsyefeiaVwt8F6uaoQ/132
         * is_follow : false
         */

        private int user_id;
        private String nickname;
        private String headimg;
        private boolean is_follow;

        public static UserInfo objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), UserInfo.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public boolean isIs_follow() {
            return is_follow;
        }

        public void setIs_follow(boolean is_follow) {
            this.is_follow = is_follow;
        }
    }
}
