package com.application.sven.huinews.mob.login;

import android.content.Context;

import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:  mob 微信登录
 */
public class WechatLogin {

    private Context mContext;

    public WechatLogin(Context mContext) {
        this.mContext = mContext;
    }

    public void wechatLogin(final WechatLoginCallBack callBack) {
        LogUtil.showLog("WechatLogin:JS调用微信登录");
        //微信登录


        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        if (platform.isAuthValid()) {
            platform.removeAccount(true);
        }

        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                hashMap.put("code", Constant.JS_RESPONSE_CODE_SUCCEED);
                WechatLoginResponse response = new Gson().fromJson(new Gson().toJson
                        (hashMap), WechatLoginResponse.class);
                callBack.onResponse(response);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("code", Constant.JS_RESPONSE_CODE_FAIL);
                WechatLoginResponse response = new Gson().fromJson(new Gson().toJson
                        (hashMap), WechatLoginResponse.class);
                callBack.onResponse(response);
                LogUtil.showLog("微信授权错误");

            }

            @Override
            public void onCancel(Platform platform, int i) {
                LogUtil.showLog("微信授权取消");
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("code", Constant.JS_RESPONSE_CODE_CANCEL);
                WechatLoginResponse response = new Gson().fromJson(new Gson().toJson
                        (hashMap), WechatLoginResponse.class);
                callBack.onResponse(response);
            }
        });
        platform.SSOSetting(true);
        platform.showUser(null);
    }
}
