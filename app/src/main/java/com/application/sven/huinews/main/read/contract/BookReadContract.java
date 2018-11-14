package com.application.sven.huinews.main.read.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookChapterPayRequest;
import com.application.sven.huinews.entity.request.BookChapterRequest;

import com.application.sven.huinews.entity.request.BookContentRequest;
import com.application.sven.huinews.entity.request.BookShareRequest;
import com.application.sven.huinews.entity.request.BookShelfAddRequest;
import com.application.sven.huinews.entity.request.BookisInStoreRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.response.BookChapterInfoResponse;
import com.application.sven.huinews.entity.response.BookChapterItem;
import com.application.sven.huinews.entity.response.BookChapters;
import com.application.sven.huinews.entity.response.BookShareResponse;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public interface BookReadContract {

    abstract class Model extends BaseModel {
        public abstract void getBookContent(BookContentRequest request, DataResponseCallback callback);

        public abstract void getBookChapter(BookChapterRequest request, DataResponseCallback callback);

        public abstract void bookChapterPay(BookChapterPayRequest request, DataResponseCallback callback);

        public abstract void isBookInStore(BookisInStoreRequest request, DataResponseCallback callBack);

        public abstract void addBookShelf(BookShelfAddRequest request, DataCallBack callBack);

        public abstract void bookShare(BookShareRequest request, DataResponseCallback callBack);

        public abstract void shareVisit(ShareVisitRequest request, DataCallBack callBack);

    }

    interface View extends BaseView {
        BookContentRequest getBookConentRequest();

        BookChapterRequest getBookChapterRequest();

        BookChapterPayRequest getBookChapterPayRequest();

        BookisInStoreRequest getBookisInStoreRequest();

        BookShelfAddRequest getBookShelfAddRequest();

        BookShareRequest getBookShareRequest();

        void setBookChapters(BookChapters mBookChapters);

        void setBookChapterInfo(BookChapterItem mBookChapterInfo, boolean isOpen);

        void setBookInStore(boolean isIn);

        void addBookOk();


        void setBookShare(BookShareResponse.BookShare mBookShare, int type);


    }

    abstract class Presenter extends BasePresenter<BookReadContract.View, BookReadContract.Model> {
        public abstract void onBookContent(boolean isOpen);

        public abstract void onBookChapter();

        public abstract void onBookChapterPay();

        public abstract void onBookIsInStore();

        public abstract void onAddBookShelf();

        public abstract void onBookShare(int type);

        public abstract void onShareVisit(String response, String videoType, int type);
    }
}
