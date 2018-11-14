package com.application.sven.huinews.main.read.presenter;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.BookShelfDelRequest;
import com.application.sven.huinews.entity.request.BookShelfListRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.BookShelfList;
import com.application.sven.huinews.main.read.contract.BookShelfContract;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class BookShelfPresenter extends BookShelfContract.Presenter {
    @Override
    public void onBookShelfList() {
        BookShelfListRequest request = mView.getBookShelfListRequest();
        mModel.getBookShelfList(request, new DataResponseCallback<BookShelfList>() {


            @Override
            public void onSucceed(BookShelfList bookShelfList) {
                //BookShelfList.ListsBean listsBean = new BookShelfList.ListsBean();
                //listsBean.setTitle("addBook");
                // bookShelfList.getLists().add(bookShelfList.getLists().size(), listsBean);
                mView.setBookShelfList(bookShelfList);
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

    @Override
    public void onBookShelfDel() {
        BookShelfDelRequest request = mView.getBookShelfDelRequest();
        mModel.bookShlefDel(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                mView.bookShelfDelOk();
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                mView.showErrorTip(baseResponse.getCode(), baseResponse.getMsg());
            }
        });
    }
}
