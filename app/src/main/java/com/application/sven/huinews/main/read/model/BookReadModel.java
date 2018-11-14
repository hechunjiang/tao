package com.application.sven.huinews.main.read.model;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookChapterPayRequest;
import com.application.sven.huinews.entity.request.BookChapterRequest;
import com.application.sven.huinews.entity.request.BookContentRequest;
import com.application.sven.huinews.entity.request.BookShareRequest;
import com.application.sven.huinews.entity.request.BookShelfAddRequest;
import com.application.sven.huinews.entity.request.BookisInStoreRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.BookChapterInfoResponse;
import com.application.sven.huinews.entity.response.BookChapterResponse;
import com.application.sven.huinews.entity.response.BookIsInStoreResponse;
import com.application.sven.huinews.entity.response.BookShareResponse;
import com.application.sven.huinews.main.read.contract.BookChapterContract;
import com.application.sven.huinews.main.read.contract.BookReadContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class BookReadModel extends BookReadContract.Model {

    @Override
    public void getBookContent(BookContentRequest request, final DataResponseCallback callback) {
        getRetrofit().onBookContent(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                BookChapterInfoResponse response = new Gson().fromJson(json, BookChapterInfoResponse.class);
                callback.onSucceed(response.getData());
                LogUtil.showJson("getBookContent", json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

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

    @Override
    public void bookChapterPay(BookChapterPayRequest request, final DataResponseCallback callback) {
        getRetrofit().onChapterPay(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                BookChapterInfoResponse response = new Gson().fromJson(json, BookChapterInfoResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }

    @Override
    public void isBookInStore(BookisInStoreRequest request, final DataResponseCallback callBack) {
        getRetrofit().onBookIsInStore(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                BookIsInStoreResponse response = new Gson().fromJson(json, BookIsInStoreResponse.class);
                callBack.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void addBookShelf(BookShelfAddRequest request, final DataCallBack callBack) {
        getRetrofit().onBookAdd(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void bookShare(BookShareRequest request, final DataResponseCallback callBack) {
        getRetrofit().onBookShare(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                BookShareResponse response = new Gson().fromJson(json, BookShareResponse.class);
                callBack.onSucceed(response);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

    @Override
    public void shareVisit(ShareVisitRequest request, final DataCallBack callBack) {
        getRetrofit().onShareVisit(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                callBack.onSucceed(json);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }

}
