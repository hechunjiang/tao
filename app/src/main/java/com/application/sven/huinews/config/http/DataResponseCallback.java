package com.application.sven.huinews.config.http;

import com.application.sven.huinews.entity.response.BaseResponse;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public interface DataResponseCallback<T> {
    void onSucceed(T t);

    void onFail(BaseResponse response);

    void onComplete();
}
