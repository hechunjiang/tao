package com.application.sven.huinews.main.preemption.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.MovieMoreListRequest;
import com.application.sven.huinews.entity.response.MoreMovieListResponse;
import com.application.sven.huinews.entity.response.MovieListResponse;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public interface MoreMovieContract {
    abstract class Model extends BaseModel {
        public abstract void getMoreMovie(MovieMoreListRequest request, DataResponseCallback callback);
    }

    interface View  extends BaseView{
        MovieMoreListRequest getMovieMoreListRequest();

        void setMoreMovie(MoreMovieListResponse.MoreMovieList moreMovieList);
    }

    abstract class Presenter extends BasePresenter<MoreMovieContract.View, MoreMovieContract.Model> {
        public abstract void onMoreMovie();
    }
}
