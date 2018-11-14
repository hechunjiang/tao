package com.application.sven.huinews.entity.response;

import com.application.sven.huinews.entity.UserVideo;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/6/5
 * effect:
 */
public class UserVideoResponse extends BaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Serializable {
        private boolean is_more;
        private List<UserVideo> lists;

        public boolean isIs_more() {
            return is_more;
        }

        public void setIs_more(boolean is_more) {
            this.is_more = is_more;
        }

        public List<UserVideo> getLists() {
            return lists;
        }

        public void setLists(List<UserVideo> lists) {
            this.lists = lists;
        }
    }

}
