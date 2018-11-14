package com.application.sven.huinews.main.read.presenter;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.jspush.JsShareType;
import com.application.sven.huinews.entity.request.BookChapterPayRequest;
import com.application.sven.huinews.entity.request.BookChapterRequest;
import com.application.sven.huinews.entity.request.BookContentRequest;
import com.application.sven.huinews.entity.request.BookShareRequest;
import com.application.sven.huinews.entity.request.BookShelfAddRequest;
import com.application.sven.huinews.entity.request.BookisInStoreRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.BookChapterInfoResponse;
import com.application.sven.huinews.entity.response.BookChapterItem;
import com.application.sven.huinews.entity.response.BookChapters;
import com.application.sven.huinews.entity.response.BookIsInStoreResponse;
import com.application.sven.huinews.entity.response.BookShareResponse;
import com.application.sven.huinews.main.read.contract.BookReadContract;
import com.application.sven.huinews.mob.share.ShareResponse;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.google.gson.Gson;

import cn.sharesdk.wechat.friends.Wechat;

/**
 * auther: sunfuyi
 * data: 2018/7/14
 * effect:
 */
public class BookReadPresenter extends BookReadContract.Presenter {
    @Override
    public void onBookContent(final boolean isOpenBook) {
        BookContentRequest request = mView.getBookConentRequest();
        mModel.getBookContent(request, new DataResponseCallback<BookChapterItem>() {


            @Override
            public void onSucceed(BookChapterItem bookChapterInfo) {
                mView.setBookChapterInfo(bookChapterInfo, isOpenBook);
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
    public void onBookChapter() {
        BookChapterRequest request = mView.getBookChapterRequest();
        mModel.getBookChapter(request, new DataResponseCallback<BookChapters>() {


            @Override
            public void onSucceed(BookChapters bookChapters) {
                mView.setBookChapters(bookChapters);
            }

            @Override
            public void onFail(BaseResponse response) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onBookChapterPay() {
        BookChapterPayRequest request = mView.getBookChapterPayRequest();
        LogUtil.showLog("msg----onBookChapterPay:" + request.toString());
        mModel.bookChapterPay(request, new DataResponseCallback<BookChapterItem>() {


            @Override
            public void onSucceed(BookChapterItem bookChapterItem) {
                // ToastUtils.show(mContext, "购买成功", 1);
                mView.setBookChapterInfo(bookChapterItem, true);
            }

            @Override
            public void onFail(BaseResponse response) {

            }

            @Override
            public void onComplete() {

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
