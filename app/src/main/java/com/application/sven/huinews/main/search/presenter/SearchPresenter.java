package com.application.sven.huinews.main.search.presenter;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.SearchRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.SearchHotResponse;
import com.application.sven.huinews.entity.response.SearchResponse;
import com.application.sven.huinews.main.search.contract.SearchContract;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public class SearchPresenter extends SearchContract.Presenter {
    @Override
    public void onSearch() {
        final SearchRequest request = mView.getSearchRequest();
        mModel.getSearch(request, new DataResponseCallback<SearchResponse.Search>() {

            @Override
            public void onSucceed(SearchResponse.Search search) {
                mView.setSearchList(search);
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
    public void onSearchHot() {
        mModel.getSearchHot(new DataResponseCallback<List<SearchHotResponse.SearchList>>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(List<SearchHotResponse.SearchList> searchLists) {
                mView.setHotSearch(searchLists);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }
}
