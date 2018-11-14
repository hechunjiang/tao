package com.application.sven.huinews.main.my.presenter;

import android.content.Intent;
import android.net.Uri;

import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.MovieHistory;
import com.application.sven.huinews.entity.UserInfo;
import com.application.sven.huinews.entity.request.ActiavtePageRequest;
import com.application.sven.huinews.entity.request.MovieWatchHistoryRequest;
import com.application.sven.huinews.entity.response.ActiavtePageResponse;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.UserFlagResponse;
import com.application.sven.huinews.entity.response.UserHitsResponse;
import com.application.sven.huinews.main.my.contract.UserIndexContract;
import com.application.sven.huinews.utils.ToastUtils;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class UserIndexPresenter extends UserIndexContract.Presenter {
    @Override
    public void onUserInfo() {
        mModel.getUserInfo(new DataResponseCallback<UserInfo>() {


            @Override
            public void onSucceed(UserInfo userInfo) {
                mView.setUserInfo(userInfo);
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
    public void onUserFlag() {
        mModel.getUserFlag(new DataResponseCallback<UserFlagResponse.UserFlag>() {

            @Override
            public void onSucceed(UserFlagResponse.UserFlag userFlag) {
                mView.setUserFlag(userFlag);
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
    public void onMovieHistory() {
        MovieWatchHistoryRequest request = mView.getMovieWatchHistoryRequest();
        mModel.getMovieHistory(request, new DataResponseCallback<MovieHistory>() {


            @Override
            public void onSucceed(MovieHistory movieHistory) {
                mView.setMovieHistory(movieHistory);
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
    public void onActiavtePage() {
        ActiavtePageRequest request = new ActiavtePageRequest();
        request.setType(Constant.ACTIVATE_PAG);
        mModel.getActivePage(request, new DataResponseCallback<List<ActiavtePageResponse.Data>>() {

            @Override
            public void onSucceed(List<ActiavtePageResponse.Data> data) {
                mView.setActivatePage(data);
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
    public void toQQ() {
        int qq = UserSpCache.getInstance(mContext).getInt(UserSpCache.QQ);
        try {
            Uri uri = Uri.parse("mqqapi://card/show_pslcard?src_type=internal&version=1&card_type=group&uin=" + qq);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showLong(mContext, "请检查是否安装QQ客户端");
        }
    }

    @Override
    public void onDotInfo() {
        mModel.getDotInfo(new DataResponseCallback<UserHitsResponse>() {
            @Override
            public void onSucceed(UserHitsResponse userHitsResponse) {
                mView.setUserHits(userHitsResponse);
            }

            @Override
            public void onFail(BaseResponse response) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


}
