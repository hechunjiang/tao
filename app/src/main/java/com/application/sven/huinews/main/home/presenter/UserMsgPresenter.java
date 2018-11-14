package com.application.sven.huinews.main.home.presenter;

import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.UserMsgRequest;
import com.application.sven.huinews.entity.request.UserVideoRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.UserInfoMsg;
import com.application.sven.huinews.entity.response.UserVideoResponse;
import com.application.sven.huinews.main.home.contract.UserMsgContract;

/**
 * auther: sunfuyi
 * data: 2018/5/18
 * effect:
 */
public class UserMsgPresenter extends UserMsgContract.Presenter {
    @Override
    public void onUserMsg() {
        UserMsgRequest request = mView.getUserMsgRequest();
        mModel.getUserMsg(request, new DataResponseCallback<UserInfoMsg.UserInfo>() {


            @Override
            public void onSucceed(UserInfoMsg.UserInfo userInfo) {
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
    public void onUserVideo() {
        UserVideoRequest request = mView.getUserVideoRequest();
        mModel.getUserVideo(request, new DataResponseCallback<UserVideoResponse.Data>() {


            @Override
            public void onSucceed(UserVideoResponse.Data data) {
                mView.setUserVideo(data);
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
    public void onFollowUser(int userId) {
        UserMsgRequest request = new UserMsgRequest();
        request.setUser_id(userId);
        mModel.setFollowUser(request, new DataResponseCallback() {
            @Override
            public void onSucceed(Object o) {

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
    public void onCancelFollowUser(int userId) {
        UserMsgRequest request = new UserMsgRequest();
        request.setUser_id(userId);
        mModel.setCancelFollowUser(request, new DataResponseCallback() {
            @Override
            public void onSucceed(Object o) {

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
