package com.application.sven.huinews.main.read.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookChapterRequest;
import com.application.sven.huinews.entity.request.BookDetailsRequest;
import com.application.sven.huinews.entity.response.BookChapters;
import com.application.sven.huinews.entity.response.BookDetailsResponse;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public interface BookChapterContract {
    abstract class Model extends BaseModel {
        public abstract void getBookChapter(BookChapterRequest request, DataResponseCallback callback);
    }

    interface View extends BaseView {
        BookChapterRequest getBookChapterRequest();

        void setBookChapters(BookChapters mBookChapters);
    }

    abstract class Presenter extends BasePresenter<BookChapterContract.View, BookChapterContract.Model> {
        public abstract void onBookChapters();
    }
}
