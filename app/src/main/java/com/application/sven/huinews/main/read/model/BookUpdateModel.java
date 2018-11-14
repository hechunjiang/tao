package com.application.sven.huinews.main.read.model;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.BookUpdateResponse;
import com.application.sven.huinews.main.read.contract.BookUpdateContract;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookUpdateModel extends BookUpdateContract.Model {
    @Override
    public void getBookList(BookListRequest request, final DataResponseCallback callback) {
        getRetrofit().onBookList(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                BookUpdateResponse response = new Gson().fromJson(json, BookUpdateResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }
}
