package com.application.sven.huinews.main.read.presenter;

import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.main.read.contract.BookListContract;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/10
 * effect:
 */
public class BookListPresenter extends BookListContract.Presenter {
    @Override
    public void onBookList() {
        BookListRequest request = mView.getBookListRequest();
        mModel.getBookList(request, new DataResponseCallback<List<BookList>>() {

            @Override
            public void onSucceed(List<BookList> bookLists) {
                mView.setBookList(bookLists);
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
