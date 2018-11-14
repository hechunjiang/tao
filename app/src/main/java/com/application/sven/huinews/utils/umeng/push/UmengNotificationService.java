package com.application.sven.huinews.utils.umeng.push;

import android.content.Context;
import android.content.Intent;

import com.application.sven.huinews.utils.LogUtil;

import com.umeng.message.UmengMessageService;

import org.android.agoo.common.AgooConstants;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class UmengNotificationService extends UmengMessageService {
    @Override
    public void onMessage(Context context, Intent intent) {

        String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        LogUtil.showLog("msg---友盟:"+message);
    }
}
