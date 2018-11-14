package com.application.sven.huinews.entity.response;

import com.application.sven.huinews.entity.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/6/1
 * effect:
 */
public class CommentReponse extends BaseResponse {
    private List<Comment> data;

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }
}
