package com.application.sven.huinews.main.read.presenter;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.jspush.JsShareType;
import com.application.sven.huinews.entity.request.BookDetailsRequest;
import com.application.sven.huinews.entity.request.BookShareRequest;
import com.application.sven.huinews.entity.request.BookShelfAddRequest;
import com.application.sven.huinews.entity.request.BookisInStoreRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.BookDetailsResponse;
import com.application.sven.huinews.entity.response.BookIsInStoreResponse;
import com.application.sven.huinews.entity.response.BookShareResponse;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.main.read.contract.BookDetailsContract;
import com.application.sven.huinews.mob.share.ShareResponse;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

import cn.sharesdk.wechat.friends.Wechat;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookDetailsPresenter extends BookDetailsContract.Presenter {
    @Override
    public void onBookDetails() {
        BookDetailsRequest request = mView.getBookDetailsRequest();
        mModel.getBookDetails(request, new DataResponseCallback<BookDetailsResponse.BookDetails>() {


            @Override
            public void onSucceed(BookDetailsResponse.BookDetails bookDetails) {
                mView.setBookDetails(bookDetails);
            }

            @Override
            public void onFail(BaseResponse response) {
                mView.showErrorTip(response.getCode(), response.getMsg());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onAddBookShelf() {
        BookShelfAddRequest request = mView.getBookShelfAddRequest();
        mModel.addBookShelf(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                mView.addBookOk();
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                mView.showErrorTip(baseResponse.getCode(), baseResponse.getMsg());

            }
        });
    }

    @Override
    public void onBookIsInStore() {
        BookisInStoreRequest request = mView.getBookisInStoreRequest();
        mModel.isBookInStore(request, new DataResponseCallback<BookIsInStoreResponse.BookIsInStore>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(BookIsInStoreResponse.BookIsInStore bookIsInStore) {
                mView.setBookInStore(bookIsInStore.isStatus());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    @Override
    public void onBookShare(final int type) {
        final BookShareRequest request = mView.getBookShareRequest();
        mModel.bookShare(request, new DataResponseCallback<BookShareResponse>() {

            @Override
            public void onSucceed(BookShareResponse bookShareResponse) {
                mView.setBookShare(bookShareResponse.getData(), type);
            }

            @Override
            public void onFail(BaseResponse response) {
                mView.showErrorTip(response.getCode(), response.getMsg());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onShareVisit(String response, String videoType, int type) {
        ShareResponse mJsShareResponse = new Gson().fromJson(response, ShareResponse.class);
        ShareVisitRequest request = new ShareVisitRequest();
        request.setActivityType(videoType);
        request.setCode(mJsShareResponse.getCode() + "");
        request.setShareChannel(type + "");
        mModel.shareVisit(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {

            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    /**
     * 视频分享
     *
     * @param mData
     * @param type
     * @return
     */
    public String getShareJson(BookShareResponse.BookShare mData, int type) {
        JsShareType jsShareType = new JsShareType();
        jsShareType.setType(type);
        jsShareType.setWechatShareType(Wechat.SHARE_WEBPAGE);
        jsShareType.setTitle(mData.getTitle());
        jsShareType.setUrl(mData.getUrl());
        jsShareType.setContent(mData.getContent());
        jsShareType.setImgUrl(mData.getImg());
        return new Gson().toJson(jsShareType);
    }
}
