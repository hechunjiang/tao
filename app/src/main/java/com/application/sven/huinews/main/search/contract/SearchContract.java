package com.application.sven.huinews.main.search.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.SearchRequest;
import com.application.sven.huinews.entity.response.SearchHotResponse;
import com.application.sven.huinews.entity.response.SearchResponse;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public interface SearchContract {
    abstract class Model extends BaseModel {
        public abstract void getSearch(SearchRequest request, DataResponseCallback callback);

        public abstract void getSearchHot(DataResponseCallback callBack);
    }

    interface View extends BaseView{
        SearchRequest getSearchRequest();

        void setHotSearch(List<SearchHotResponse.SearchList> hotSearch);

        void setSearchList(SearchResponse.Search mSearch);
    }

    abstract class Presenter extends BasePresenter<SearchContract.View, SearchContract.Model> {
        public abstract void onSearch();

        public abstract void onSearchHot();
    }
}
