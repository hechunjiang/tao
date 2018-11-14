package com.application.sven.huinews.main.read.model;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookChapterRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.BookChapterResponse;
import com.application.sven.huinews.main.read.contract.BookChapterContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookChapterModel extends BookChapterContract.Model {
    @Override
    public void getBookChapter(BookChapterRequest request, final DataResponseCallback callback) {
        getRetrofit().onBookChapters(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                BookChapterResponse response = new Gson().fromJson(json, BookChapterResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }
}
