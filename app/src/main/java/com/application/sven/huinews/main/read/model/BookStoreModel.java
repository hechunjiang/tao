package com.application.sven.huinews.main.read.model;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BaseRequest;
import com.application.sven.huinews.entity.request.BookStoreRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.BookListResponse;
import com.application.sven.huinews.entity.response.BookStoreCategroyResponse;
import com.application.sven.huinews.entity.response.BookStoreListResponse;
import com.application.sven.huinews.main.read.contract.BookStoreContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;
import com.qq.e.comm.pi.CAI;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookStoreModel extends BookStoreContract.Model {
    @Override
    public void getBookStore(BookStoreRequest request, final DataResponseCallback callback) {
        getRetrofit().onBookStore(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showJson("getBookStore", json);
                BookStoreListResponse response = new Gson().fromJson(json, BookStoreListResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void getBookStoreCate(final DataResponseCallback callback) {
        getRetrofit().onBookCategroy(new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                BookStoreCategroyResponse response = new Gson().fromJson(json, BookStoreCategroyResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }
}
