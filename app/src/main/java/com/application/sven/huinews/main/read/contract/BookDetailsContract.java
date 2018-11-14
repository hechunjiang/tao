package com.application.sven.huinews.main.read.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookDetailsRequest;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.request.BookShareRequest;
import com.application.sven.huinews.entity.request.BookShelfAddRequest;
import com.application.sven.huinews.entity.request.BookisInStoreRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.response.BookDetailsResponse;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.entity.response.BookShareResponse;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public interface BookDetailsContract {
    abstract class Model extends BaseModel {
        public abstract void getBookDetails(BookDetailsRequest request, DataResponseCallback callback);

        public abstract void addBookShelf(BookShelfAddRequest request, DataCallBack callBack);

        public abstract void isBookInStore(BookisInStoreRequest request, DataResponseCallback callBack);

        public abstract void bookShare(BookShareRequest request, DataResponseCallback callBack);

        public abstract void shareVisit(ShareVisitRequest request, DataCallBack callBack);

    }

    interface View extends BaseView {
        BookDetailsRequest getBookDetailsRequest();

        BookShelfAddRequest getBookShelfAddRequest();

        BookisInStoreRequest getBookisInStoreRequest();

        BookShareRequest getBookShareRequest();

        void setBookDetails(BookDetailsResponse.BookDetails mBookInfo);

        void addBookOk();

        void setBookInStore(boolean isIn);

        void setBookShare(BookShareResponse.BookShare mBookShare, int type);
    }

    abstract class Presenter extends BasePresenter<BookDetailsContract.View, BookDetailsContract.Model> {
        public abstract void onBookDetails();

        public abstract void onAddBookShelf();

        public abstract void onBookIsInStore();

        public abstract void onBookShare(int type);

        public abstract void onShareVisit(String response, String videoType, int type);
    }
}
