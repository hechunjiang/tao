package com.application.sven.huinews.main.home.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.UserMsgRequest;
import com.application.sven.huinews.entity.request.UserVideoRequest;
import com.application.sven.huinews.entity.response.UserInfoMsg;
import com.application.sven.huinews.entity.response.UserVideoResponse;

/**
 * auther: sunfuyi
 * data: 2018/5/18
 * effect:
 */
public interface UserMsgContract {
    abstract class Model extends BaseModel {
        public abstract void getUserMsg(UserMsgRequest request, DataResponseCallback callBack);

        public abstract void getUserVideo(UserVideoRequest request, DataResponseCallback callBack);

        public abstract void setFollowUser(UserMsgRequest request, DataResponseCallback callback);

        public abstract void setCancelFollowUser(UserMsgRequest request, DataResponseCallback callback);
    }

    interface View {
        UserMsgRequest getUserMsgRequest();

        UserVideoRequest getUserVideoRequest();

        void setUserInfo(UserInfoMsg.UserInfo userInfo);

        void setUserVideo(UserVideoResponse.Data userVideo);
    }

    abstract class Presenter extends BasePresenter<UserMsgContract.View, UserMsgContract.Model> {
        public abstract void onUserMsg();

        public abstract void onUserVideo();

        //关注用户
        public abstract void onFollowUser(int userId);

        //取消关注
        public abstract void onCancelFollowUser(int userId);
    }
}
