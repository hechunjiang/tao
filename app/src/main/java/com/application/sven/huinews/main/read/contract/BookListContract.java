package com.application.sven.huinews.main.read.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.request.MovieMoreListRequest;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.entity.response.MoreMovieListResponse;
import com.application.sven.huinews.main.preemption.contract.MoreMovieContract;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/10
 * effect:
 */
public interface BookListContract {
    abstract class Model extends BaseModel {
        public abstract void getBookList(BookListRequest request, DataResponseCallback callback);
    }

    interface View extends BaseView {
        BookListRequest getBookListRequest();

        void setBookList(List<BookList> mBookList);
    }

    abstract class Presenter extends BasePresenter<BookListContract.View, BookListContract.Model> {
        public abstract void onBookList();
    }
}
