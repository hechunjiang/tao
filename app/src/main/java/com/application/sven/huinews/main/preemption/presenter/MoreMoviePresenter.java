package com.application.sven.huinews.main.preemption.presenter;

import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.MovieMoreListRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.MoreMovieListResponse;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.contract.MoreMovieContract;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public class MoreMoviePresenter extends MoreMovieContract.Presenter {
    @Override
    public void onMoreMovie() {
        final MovieMoreListRequest request = mView.getMovieMoreListRequest();
        mModel.getMoreMovie(request, new DataResponseCallback<MoreMovieListResponse.MoreMovieList>() {


            @Override
            public void onSucceed(MoreMovieListResponse.MoreMovieList moreMovieList) {
                mView.setMoreMovie(moreMovieList);
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
