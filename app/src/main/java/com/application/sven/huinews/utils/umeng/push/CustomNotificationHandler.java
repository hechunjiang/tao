package com.application.sven.huinews.utils.umeng.push;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.main.home.activity.MainActivity;
import com.application.sven.huinews.main.home.activity.VideoDetailsActivity;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.main.read.activity.BookDetailsActivity;
import com.application.sven.huinews.main.web.activity.WebActivity;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;


/**
 * auther: sunfuyi
 * data: 2018/7/30
 * effect:
 */
public class CustomNotificationHandler extends UmengNotificationClickHandler {
    private static final String TAG = CustomNotificationHandler.class.getName();

    @Override
    public void dismissNotification(Context context, UMessage msg) {
        super.dismissNotification(context, msg);
    }

    @Override
    public void launchApp(Context context, UMessage msg) {

        super.launchApp(context, msg);
    }

    @Override
    public void openActivity(Context context, UMessage msg) {
        super.openActivity(context, msg);
    }

    @Override
    public void openUrl(Context context, UMessage msg) {
        super.openUrl(context, msg);
    }

    @Override
    public void dealWithCustomAction(Context context, UMessage msg) {
        intentAct(context, msg);
        super.dealWithCustomAction(context, msg);
    }

    @Override
    public void autoUpdate(Context context, UMessage msg) {
        super.autoUpdate(context, msg);
    }


    private void intentAct(Context context, UMessage msg) {
        PushEntry pushEntry = new PushEntry();
        PushData pushData = new Gson().fromJson(msg.custom, PushData.class);
        for (Map.Entry entry : msg.extra.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();

            if (key.equals("actionMethod")) {
                pushEntry.setActionMethod(value);
            }
            if (key.equals("actionMethodParam")) {
                pushEntry.setActionMethodParam(value);
            }
            if (key.equals("actionUrl")) {
                pushEntry.setActionUrl(value);
            }

            if (key.equals("actionName")) {
                pushEntry.setActionName(value);
            }
        }

        if (pushEntry == null) {
            return;
        }


        switch (pushEntry.getActionMethod()) {
            case "open_video_detail":
                //  VideoDetailsActivity.toThis(context, pushData.getRid());
                Intent i = new Intent(context, VideoDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.BUNDLE_TO_VIDEO_ID, pushData.getRid());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtras(bundle);
                context.startActivity(i);

                break;
            case "open_user_center":
               /* Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/
                // EventBus.getDefault().postSticky(Constant.TO_MINE_PAGE_EVENT);
                break;
            case "open_shoutu_index":
                // WebActivity.toThis(context, Api.MAKEMONEY);
                Intent i1 = new Intent(context, WebActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString(Constant.WEB_URL, Api.MAKEMONEY);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i1.putExtras(bundle1);
                context.startActivity(i1);
                break;
            case "open_movies_detail":
                // MovieDetailsActivity.toThis(context, pushData.getRid(), 0, pushData.getPid());
                Intent intent1 = new Intent(context, MovieDetailsActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt(Constant.BUNDLE_MOVIE_ID, pushData.getRid());
                bundle2.putInt(Constant.BUNDLE_MOVIE_PLAY_TIME, 0);
                bundle2.putInt(Constant.BUNDLE_MOVIE_PLAY_POSITION, pushData.getPid());
                intent1.putExtras(bundle2);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
                break;
            case "open_book_detail":
                //BookDetailsActivity.toThis(context, pushData.getPid());
                Intent intent2 = new Intent(context, BookDetailsActivity.class);
                Bundle bundle5 = new Bundle();
                bundle5.putInt(Constant.BOOK_ID, pushData.getRid());
                intent2.putExtras(bundle5);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
                break;
            case "open_charge":
                // WebActivity.toThis(context, Api.MY_DIAMOND);

                Intent i3 = new Intent(context, WebActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putString(Constant.WEB_URL, Api.MY_DIAMOND);
                i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i3.putExtras(bundle3);
                context.startActivity(i3);
                break;
            case "open_url":
                //  WebActivity.toThis(context, pushData.getUrl());

                Intent i4 = new Intent(context, WebActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putString(Constant.WEB_URL, pushData.getUrl());
                i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i4.putExtras(bundle4);
                context.startActivity(i4);
                break;
        }
        /*//测试id
        if (pushEntry.getActionMethod().equals("open_active")) {
            String urlJson = CommonUtils.body(pushEntry.getActionMethodParam(), String.class);
            //   PushClickUrl pushClickUrl = new Gson().fromJson(urlJson, PushClickUrl.class);
            //     LogUtils.logLocalD("urlJson:" + pushClickUrl.toString());
            //跳转url
            Intent intent = new Intent(context, WebActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Common.BUNDLE_TO_WEB_URL, urlJson);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
            context.startActivity(intent);


        } else if (pushEntry.getActionMethod().equals(Common.PUSH_ACTION_VIDEO_DETAILS)) {
            LogUtils.logLocalE("msg----:getActionMethodParam:" + pushEntry.getActionMethodParam());
            String urlJson = pushEntry.getActionMethodParam();
            LogUtils.logLocalE("msg---urlJson:" + urlJson);
            PushClickVideo pushClickVideo = new Gson().fromJson(urlJson, PushClickVideo.class);
            // LogUtils.logLocalD("urlJson:" + pushClickVideo.toString());
            //跳转视频详情页面
            Intent intent = new Intent(context, VideoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Common.BUNDLE_TO_VIDEO_ID, pushClickVideo.getVideo_id());
            bundle.putBoolean(Common.BUNDLE_TO_ADD_GOLD, pushClickVideo.isIs_add_gold());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(bundle);
            context.startActivity(intent);


        } else if (pushEntry.getActionMethod().equals(Common.PUSH_ACTION_OPEN_USER_CENTER)) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            EventBus.getDefault().postSticky(Common.PUSH_ACTION_OPEN_USER_CENTER);

        }

*/


        //1=>"open_video_detail",//推送跳转视频
        //    	2=>"open_active",//推送跳转url
        //    	3=>"open_user_center",//跳转个人中心
        //    	4=>"open_shoutu_index",//跳转收徒界面
        //    	5=>"open_movies_detail",//影视详情
        //    	6=>"open_book_detail",//书籍详情
        //    	7=>"open_charge"//充值成功
    }
}
