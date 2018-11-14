package com.application.sven.huinews.main.read.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.entity.response.BookUpdateResponse;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public interface BookUpdateContract {


    abstract class Model extends BaseModel {
        public abstract void getBookList(BookListRequest request, DataResponseCallback callback);
    }

    interface View extends BaseView {
        BookListRequest getBookListRequest();

        void setBookList(BookUpdateResponse.BookUpdate mBookUpdate);
    }

    abstract class Presenter extends BasePresenter<BookUpdateContract.View, BookUpdateContract.Model> {
        public abstract void onBookList();
    }
}
