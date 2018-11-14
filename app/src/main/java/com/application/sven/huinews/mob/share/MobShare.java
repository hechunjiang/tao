package com.application.sven.huinews.mob.share;

import android.content.Context;
import android.text.TextUtils;

import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect: mob  分享
 */
public class MobShare {
    private Context mContext;

    public MobShare(Context mContext) {
        this.mContext = mContext;
    }

    public void shareToOne(Context context, String shareJson, final ShareCallBack callBack) {
        LogUtil.showLog("MobShare:分享到制定平台");
        //返回给js的
        final ShareResponse response = new ShareResponse();
        //js传来的实体
        ShareType jsShareType = new Gson().fromJson(shareJson, ShareType.class);
        Platform.ShareParams shareParams = null;
        String type = Wechat.NAME;

        switch (jsShareType.getType()) {
            case Constant.SHARE_TYPE_WECHAT:
                shareParams = new Wechat.ShareParams();
                type = Wechat.NAME;
                setWechatShareParams(context, shareParams, jsShareType);
                break;
            case Constant.SHARE_TYPE_WECHAT_ZOOM:
                shareParams = new WechatMoments.ShareParams();
                shareParams.setShareType(Wechat.SHARE_WEBPAGE);
                type = WechatMoments.NAME;
                setWechatShareParams(context, shareParams, jsShareType);
                break;
            case Constant.SHARE_TYPE_QQ:
                shareParams = new QQ.ShareParams();
                type = QQ.NAME;
                setQQShareParams(context, shareParams, jsShareType);
                break;
            case Constant.SHARE_TYPE_QQ_ZOOM:
                shareParams = new QZone.ShareParams();
                type = QZone.NAME;
                setQQShareParams(context, shareParams, jsShareType);
                break;
        }
        Platform platform = ShareSDK.getPlatform(type);
        if (jsShareType.getType() == Constant.SHARE_TYPE_WECHAT_ZOOM) {
            //多图片设置绕过分享
            if (jsShareType.getImgArray() != null) {

                if (jsShareType.getImgArray().size() != 0) {
                    bypassApproval(type, "true");//设置绕过分享
                    String[] imgArray = new String[jsShareType.getImgArray().size()];
                    for (int i = 0; i < jsShareType.getImgArray().size(); i++) {
                        imgArray[i] = jsShareType.getImgArray().get(i);
                    }
                    shareParams.setImageArray(imgArray);
                }
            } else {
                bypassApproval(type, "false");
            }
        }

        //分享回调
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                response.setCode(Constant.JS_RESPONSE_CODE_SUCCEED);
                response.setMsg("分享完成");
                callBack.onResponse(new Gson().toJson(response));
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                response.setCode(Constant.JS_RESPONSE_CODE_FAIL);
                response.setMsg("分享失败" + throwable.toString());
                callBack.onResponse(new Gson().toJson(response));
            }

            @Override
            public void onCancel(Platform platform, int i) {
                response.setCode(Constant.JS_RESPONSE_CODE_CANCEL);
                response.setMsg("分享取消");
                callBack.onResponse(new Gson().toJson(response));

            }
        });
        platform.share(shareParams);
        platform = null;
    }

    private void bypassApproval(String name, String BypassApproval) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("Id", "1");
        hashMap.put("SortId", "1");
        hashMap.put("ShareByAppClient", "true");
        hashMap.put("Enable", "true");
        hashMap.put("BypassApproval", BypassApproval);
        ShareSDK.setPlatformDevInfo(name, hashMap);
    }


    private void setWechatShareParams(Context context, Platform.ShareParams shareParams, ShareType jsShareType) {
        if (jsShareType.getWechatShareType() == 0) {
            jsShareType.setWechatShareType(Platform.SHARE_WEBPAGE);
        }
        String title = UserSpCache.getInstance(context).getStringData(UserSpCache.SHARE_TITLE);
        String content = UserSpCache.getInstance(context).getStringData(UserSpCache.SHARE_CONTENT);
        shareParams.setShareType(jsShareType.getWechatShareType());

        switch (jsShareType.getWechatShareType()) {
            case Platform.SHARE_TEXT:
                if (jsShareType.getType() == Constant.SHARE_TYPE_WECHAT) {
                    if (!TextUtils.isEmpty(jsShareType.getTitle())) {
                        shareParams.setTitle(jsShareType.getTitle());
                    } else {
                        shareParams.setTitle(title);
                    }
                }
                if (!TextUtils.isEmpty(jsShareType.getContent())) {
                    shareParams.setText(jsShareType.getContent());
                } else {
                    shareParams.setText(content);
                }
                break;
            case Platform.SHARE_WEBPAGE:
                if (jsShareType.getType() == Constant.SHARE_TYPE_WECHAT) {
                    if (!TextUtils.isEmpty(jsShareType.getTitle())) {
                        shareParams.setTitle(jsShareType.getTitle());
                    } else {
                        shareParams.setTitle(title);
                    }
                }
                if (!TextUtils.isEmpty(jsShareType.getContent())) {
                    shareParams.setText(jsShareType.getContent());
                } else {
                    shareParams.setText(content);
                }
                if (!TextUtils.isEmpty(jsShareType.getTitle())) {
                    shareParams.setTitle(jsShareType.getTitle());
                } else {
                    shareParams.setTitle(title);
                }
                shareParams.setImageUrl(jsShareType.getImgUrl());
                shareParams.setUrl(jsShareType.getUrl());
                break;
            case Platform.SHARE_IMAGE:
                if (jsShareType.getType() == Constant.SHARE_TYPE_WECHAT) {
                    if (!TextUtils.isEmpty(jsShareType.getTitle())) {
                        shareParams.setTitle(jsShareType.getTitle());
                    } else {
                        shareParams.setTitle(title);
                    }
                }
                if (!TextUtils.isEmpty(jsShareType.getContent())) {
                    shareParams.setText(jsShareType.getContent());
                } else {
                    shareParams.setText(content);
                }
                shareParams.setImageUrl(jsShareType.getImgUrl());
                break;
            case Platform.SHARE_VIDEO:
                if (jsShareType.getType() == Constant.SHARE_TYPE_WECHAT) {
                    if (!TextUtils.isEmpty(jsShareType.getTitle())) {
                        shareParams.setTitle(jsShareType.getTitle());
                    } else {
                        shareParams.setTitle(title);
                    }
                }
                if (!TextUtils.isEmpty(jsShareType.getContent())) {
                    shareParams.setText(jsShareType.getContent());
                } else {
                    shareParams.setText(content);
                }
                if (!TextUtils.isEmpty(jsShareType.getTitle())) {
                    shareParams.setTitle(jsShareType.getTitle());
                } else {
                    shareParams.setTitle(title);
                }
                shareParams.setImageUrl(jsShareType.getImgUrl());
                shareParams.setUrl(jsShareType.getUrl());
                break;
        }
    }

    private void setQQShareParams(Context context, Platform.ShareParams shareParams, ShareType
            shareType) {
        String title = UserSpCache.getInstance(context).getStringData(UserSpCache.SHARE_TITLE);
        String content = UserSpCache.getInstance(context).getStringData(UserSpCache.SHARE_CONTENT);
        if (TextUtils.isEmpty(shareType.getTitle())) {
            shareParams.setTitle(title);
        } else {
            shareParams.setTitle(shareType.getTitle());
        }
        if (!TextUtils.isEmpty(shareType.getContent())) {
            shareParams.setText(shareType.getContent());
        } else {
            shareParams.setText(content);
        }
        if (!TextUtils.isEmpty(shareType.getImgUrl())) {
            shareParams.setImageUrl(shareType.getImgUrl());
        }
        if (!TextUtils.isEmpty(shareType.getUrl())) {
            shareParams.setTitleUrl(shareType.getUrl());
        }
    }
}
