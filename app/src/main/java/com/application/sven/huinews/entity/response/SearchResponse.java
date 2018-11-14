package com.application.sven.huinews.entity.response;

import com.application.sven.huinews.entity.SearchList;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/28
 * effect:
 */
public class SearchResponse extends BaseResponse {

    private Search data;

    public Search getData() {
        return data;
    }

    public void setData(Search data) {
        this.data = data;
    }

    public static class Search implements Serializable{
        boolean is_more;
        private List<SearchList> lists;

        public boolean isIs_more() {
            return is_more;
        }

        public void setIs_more(boolean is_more) {
            this.is_more = is_more;
        }

        public List<SearchList> getLists() {
            return lists;
        }

        public void setLists(List<SearchList> lists) {
            this.lists = lists;
        }
    }


}
