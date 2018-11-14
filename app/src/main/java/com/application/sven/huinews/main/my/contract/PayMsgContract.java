package com.application.sven.huinews.main.my.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.CollectionList;
import com.application.sven.huinews.entity.request.CollectionRequest;
import com.application.sven.huinews.entity.request.PayInfoRequest;
import com.application.sven.huinews.entity.request.VideoCollectionCancelRequest;
import com.application.sven.huinews.entity.response.PayInfoResponse;
import com.application.sven.huinews.entity.response.PayMsgResponse;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public interface PayMsgContract {
    abstract class Model extends BaseModel {
        public abstract void onPayMsg(DataResponseCallback callback);

        public abstract void onPayInfo(PayInfoRequest request, DataResponseCallback callback);
    }

    interface View extends BaseView {
        void setPayMsg(PayMsgResponse.PayMsg payMsg);

        PayInfoRequest getPayInfoRequest();

        void setPayInfo(PayInfoResponse.PayInfo payInfo);

    }

    abstract class Presenter extends BasePresenter<PayMsgContract.View, PayMsgContract.Model> {
        public abstract void onPayMsg();

        public abstract void onPayInfo();

    }
}
