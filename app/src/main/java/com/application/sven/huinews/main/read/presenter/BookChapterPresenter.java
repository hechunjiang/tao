package com.application.sven.huinews.main.read.presenter;

import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookChapterRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.BookChapterResponse;
import com.application.sven.huinews.entity.response.BookChapters;
import com.application.sven.huinews.main.read.contract.BookChapterContract;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookChapterPresenter extends BookChapterContract.Presenter {
    @Override
    public void onBookChapters() {
        BookChapterRequest request = mView.getBookChapterRequest();
        mModel.getBookChapter(request, new DataResponseCallback<BookChapters>() {


            @Override
            public void onSucceed(BookChapters bookChapters) {
                mView.setBookChapters(bookChapters);
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
}
