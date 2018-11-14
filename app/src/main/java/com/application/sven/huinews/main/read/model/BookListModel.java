package com.application.sven.huinews.main.read.model;

import android.util.Log;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.entity.response.BookListResponse;
import com.application.sven.huinews.main.read.contract.BookListContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/7/10
 * effect:
 */
public class BookListModel extends BookListContract.Model {
    @Override
    public void getBookList(BookListRequest request, final DataResponseCallback callback) {
        getRetrofit().onBookList(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getBookList", json);
                BookListResponse response = new Gson().fromJson(json, BookListResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }
}
