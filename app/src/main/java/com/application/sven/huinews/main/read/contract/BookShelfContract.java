package com.application.sven.huinews.main.read.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.request.BookShelfDelRequest;
import com.application.sven.huinews.entity.request.BookShelfListRequest;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.entity.response.BookShelfList;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public interface BookShelfContract {
    abstract class Model extends BaseModel {
        public abstract void getBookShelfList(BookShelfListRequest request, DataResponseCallback callback);

        public abstract void bookShlefDel(BookShelfDelRequest request, DataCallBack callBack);
    }

    interface View extends BaseView {
        BookShelfListRequest getBookShelfListRequest();

        BookShelfDelRequest getBookShelfDelRequest();

        void setBookShelfList(BookShelfList bookShelfList);

        void bookShelfDelOk();
    }

    abstract class Presenter extends BasePresenter<BookShelfContract.View, BookShelfContract.Model> {
        public abstract void onBookShelfList();

        public abstract void onBookShelfDel();
    }
}
