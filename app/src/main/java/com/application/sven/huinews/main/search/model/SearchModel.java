package com.application.sven.huinews.main.search.model;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.entity.request.SearchRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.SearchHotResponse;
import com.application.sven.huinews.entity.response.SearchResponse;
import com.application.sven.huinews.main.search.contract.SearchContract;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public class SearchModel extends SearchContract.Model {
    @Override
    public void getSearch(final SearchRequest request, final DataResponseCallback callback) {
        getRetrofit().onSearch(request, new DataCallBack() {
            @Override
            public void onComplete() {
                callback.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                SearchResponse response = new Gson().fromJson(json, SearchResponse.class);
                callback.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callback.onFail(baseResponse);
            }
        });
    }


    @Override
    public void getSearchHot(final DataResponseCallback callBack) {
        getRetrofit().onSearchHot(new DataCallBack() {
            @Override
            public void onComplete() {
                callBack.onComplete();
            }

            @Override
            public void onSucceed(String json) {
                SearchHotResponse response = new Gson().fromJson(json, SearchHotResponse.class);
                callBack.onSucceed(response.getData());
            }

            @Override
            public void onFail(BaseResponse baseResponse) {
                callBack.onFail(baseResponse);
            }
        });
    }
}
