package com.application.sven.huinews.main.read.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookDetailsRequest;
import com.application.sven.huinews.entity.request.BookStoreRequest;
import com.application.sven.huinews.entity.response.BookDetailsResponse;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.entity.response.BookStoreCate;
import com.application.sven.huinews.entity.response.BookStoreCategroyResponse;
import com.application.sven.huinews.entity.response.BookStoreList;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public interface BookStoreContract {
    abstract class Model extends BaseModel {
        public abstract void getBookStore(BookStoreRequest request, DataResponseCallback callback);

        public abstract void getBookStoreCate(DataResponseCallback callback);
    }

    interface View extends BaseView {

        BookStoreRequest getBookStoreRequest();

        void setCategroy(List<BookStoreCate> mBookStoreCates);

        void setBookList(BookStoreList bookStoreList);
    }

    abstract class Presenter extends BasePresenter<BookStoreContract.View, BookStoreContract.Model> {
        public abstract void onBookStore();

        public abstract void onBookStoreCate();
    }
}
