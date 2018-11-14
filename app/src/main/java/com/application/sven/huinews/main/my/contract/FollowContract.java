package com.application.sven.huinews.main.my.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.FollowList;
import com.application.sven.huinews.entity.request.FollowListRequest;
import com.application.sven.huinews.entity.request.UserMsgRequest;
import com.application.sven.huinews.entity.response.FollowListResponse;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/6/1
 * effect:
 */
public interface FollowContract {
    abstract class Model extends BaseModel {
        public abstract void getFollowList(FollowListRequest request, DataResponseCallback callback);

        public abstract void setFollowUser(UserMsgRequest request, DataResponseCallback callback);

        public abstract void setCancelFollowUser(UserMsgRequest request, DataResponseCallback callback);
    }

    interface View extends BaseView {
        FollowListRequest getFollowListRequest();

        void setFollowData(List<FollowList> mDatas);

        void setFollowNoData();

        void setCancelFollow();
    }

    abstract class Presenter extends BasePresenter<FollowContract.View, FollowContract.Model> {
        public abstract void onFollowList();

        //关注用户
        public abstract void onFollowUser(int userId);

        //取消关注
        public abstract void onCancelFollowUser(int userId);
    }
}
