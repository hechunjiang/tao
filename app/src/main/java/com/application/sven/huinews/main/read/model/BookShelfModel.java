package com.application.sven.huinews.main.read.model;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.request.BookShelfDelRequest;
import com.application.sven.huinews.entity.request.BookShelfListRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.BookShelfResponse;
import com.application.sven.huinews.main.read.contract.BookShelfContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;
import com.qq.e.comm.pi.CAI;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class BookShelfModel extends BookShelfContract.Model {
    @Override
    public void getBookShelfList(BookShelfListRequest request, final DataResponseCallback callback) {
        getRetrofit().onBookShelfList(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                BookShelfResponse response = new Gson().fromJson(json, BookShelfResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void bookShlefDel(BookShelfDelRequest request, final DataCallBack callBack) {
        getRetrofit().onBookShelfDel(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                callBack.onSucceed(json);
                LogUtil.showJson("bookShlefDel", json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }
}
