package com.application.sven.huinews.main.my.presenter;

import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.CollectionList;
import com.application.sven.huinews.entity.request.CollectionRequest;
import com.application.sven.huinews.entity.request.VideoCollectionCancelRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.main.my.contract.CollectionContract;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class CollectionPresenter extends CollectionContract.Presenter {
    @Override
    public void onCollection() {
        final CollectionRequest request = mView.getCollectionRequest();
        mModel.getCollection(request, new DataResponseCallback<CollectionList>() {


            @Override
            public void onSucceed(CollectionList collectionList) {
                mView.setCollectionList(collectionList);
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
    public void onDelCollection() {
        VideoCollectionCancelRequest request = mView.getVideoCollectionCancelRequest();
        mModel.delCollection(request, new DataCallBack() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onSucceed(String json) {
                mView.delCollectionOk();
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }
}
