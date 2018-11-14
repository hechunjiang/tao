package com.application.sven.huinews.main.my.presenter;

import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.request.PayInfoRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.PayInfoResponse;
import com.application.sven.huinews.entity.response.PayMsgResponse;
import com.application.sven.huinews.main.my.contract.PayMsgContract;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class PayMsgPresenter extends PayMsgContract.Presenter {
    @Override
    public void onPayMsg() {
        mModel.onPayMsg(new DataResponseCallback<PayMsgResponse.PayMsg>() {

            @Override
            public void onSucceed(PayMsgResponse.PayMsg payMsg) {
                mView.setPayMsg(payMsg);
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
    public void onPayInfo() {
        PayInfoRequest request = mView.getPayInfoRequest();
        mModel.onPayInfo(request, new DataResponseCallback<PayInfoResponse.PayInfo>() {

            @Override
            public void onSucceed(PayInfoResponse.PayInfo payInfo) {
                mView.setPayInfo(payInfo);
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
