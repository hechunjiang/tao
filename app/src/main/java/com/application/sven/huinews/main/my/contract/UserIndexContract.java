package com.application.sven.huinews.main.my.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.MovieHistory;
import com.application.sven.huinews.entity.UserInfo;
import com.application.sven.huinews.entity.request.ActiavtePageRequest;
import com.application.sven.huinews.entity.request.MovieWatchHistoryRequest;
import com.application.sven.huinews.entity.response.ActiavtePageResponse;
import com.application.sven.huinews.entity.response.UserFlagResponse;
import com.application.sven.huinews.entity.response.UserHitsResponse;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public interface UserIndexContract {

    abstract class Model extends BaseModel {
        public abstract void getUserInfo(DataResponseCallback callback);

        public abstract void getUserFlag(DataResponseCallback callback);

        public abstract void getMovieHistory(MovieWatchHistoryRequest request, DataResponseCallback callback);

        public abstract void getActivePage(ActiavtePageRequest request, DataResponseCallback callback);

        public abstract void getDotInfo(DataResponseCallback callback);
    }

    interface View {
        void setUserInfo(UserInfo mUserInfo);

        void setUserFlag(UserFlagResponse.UserFlag mUserFlag);

        MovieWatchHistoryRequest getMovieWatchHistoryRequest();

        void setMovieHistory(MovieHistory movieHistory);

        void setActivatePage(List<ActiavtePageResponse.Data> mActivates);

        void setUserHits(UserHitsResponse response);
    }

    abstract class Presenter extends BasePresenter<UserIndexContract.View, UserIndexContract.Model> {
        public abstract void onUserInfo();

        public abstract void onUserFlag();

        public abstract void onMovieHistory();

        public abstract void onActiavtePage();

        public abstract void toQQ();

        public abstract void onDotInfo();
    }
}
