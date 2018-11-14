package com.application.sven.huinews.main.read.presenter;

import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookStoreRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.entity.response.BookStoreCate;
import com.application.sven.huinews.entity.response.BookStoreList;
import com.application.sven.huinews.main.read.contract.BookStoreContract;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookStorePresenter extends BookStoreContract.Presenter {
    @Override
    public void onBookStore() {
        BookStoreRequest request = mView.getBookStoreRequest();
        mModel.getBookStore(request, new DataResponseCallback<BookStoreList>() {


            @Override
            public void onSucceed(BookStoreList bookStoreList) {
                mView.setBookList(bookStoreList);
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
    public void onBookStoreCate() {
        mModel.getBookStoreCate(new DataResponseCallback<List<BookStoreCate>>() {


            @Override
            public void onSucceed(List<BookStoreCate> bookStoreCates) {
                mView.setCategroy(bookStoreCates);
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
