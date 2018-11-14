package com.application.sven.huinews.main.my.contract;

import com.application.sven.huinews.base.BaseModel;
import com.application.sven.huinews.base.BasePresenter;
import com.application.sven.huinews.base.BaseView;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.DataResponseCallback;
import com.application.sven.huinews.entity.CollectionList;
import com.application.sven.huinews.entity.request.CollectionRequest;
import com.application.sven.huinews.entity.request.VideoCollectionCancelRequest;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */

public interface CollectionContract {
    abstract class Model extends BaseModel {
        public abstract void getCollection(CollectionRequest request, DataResponseCallback callback);

        public abstract void delCollection(VideoCollectionCancelRequest request, DataCallBack callBack);

    }

    interface View extends BaseView {
        CollectionRequest getCollectionRequest();

        VideoCollectionCancelRequest getVideoCollectionCancelRequest();

        void setCollectionList(CollectionList mCollectionList);


        void delCollectionOk();
    }

    abstract class Presenter extends BasePresenter<CollectionContract.View, CollectionContract.Model> {
        public abstract void onCollection();

        public abstract void onDelCollection();
    }
}
