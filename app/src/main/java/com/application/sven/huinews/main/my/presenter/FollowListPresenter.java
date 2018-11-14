package com.application.sven.huinews.main.my.presenter;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.FollowListRequest;
import com.application.sven.huinews.entity.request.UserMsgRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.FollowListResponse;
import com.application.sven.huinews.main.my.contract.FollowContract;
import com.application.sven.huinews.utils.ToastUtils;

/**
 * auther: sunfuyi
 * data: 2018/6/1
 * effect:
 */
public class FollowListPresenter extends FollowContract.Presenter {
    @Override
    public void onFollowList() {
        FollowListRequest request = mView.getFollowListRequest();
        mModel.getFollowList(request, new DataResponseCallback<FollowListResponse.Data>() {
            @Override
            public void onSucceed(FollowListResponse.Data data) {
                if (data.getLists().size() > 0) {
                    mView.setFollowData(data.getLists());
                } else {
                    mView.setFollowNoData();
                }
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
                mView.setCancelFollow();
            }

            @Override
            public void onFail(BaseResponse response) {
                ToastUtils.showLong(mContext, response.getMsg());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
