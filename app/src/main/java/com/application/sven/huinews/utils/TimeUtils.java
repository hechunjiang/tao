package com.application.sven.huinews.utils;

import android.content.Context;


import com.application.sven.huinews.db.UserSpCache;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sfy. on 2018/3/22 0022.
 */

public class TimeUtils {


    public TimeUtils() {

    }


    public long phoneTime(Context mContext) {
        //服务器时间
        long serviceTime = UserSpCache.getInstance(mContext).getLong(UserSpCache.SIGN_SERVICE_TIME) * 1000;
        //获取服务器时间时的本地时间
        long localTime = UserSpCache.getInstance(mContext).getLong(UserSpCache.SIGN_LOCAL_TIME);
        //当前时间
        long phoneTime = new Date().getTime();

        //  LogUtil.showLog("msg----phoneTime:" + phoneTime + " sericeTime:" + serviceTime + "localTime:" + localTime);
        long nowTime = phoneTime + (serviceTime - localTime);
        Date w_ret = new Date();
        w_ret.setTime(nowTime);

        LogUtil.showLog("msg----phoneTime:" + phoneTime + " sericeTime:" + serviceTime + "localTime:" + localTime + " ====最终时间：" + w_ret.getTime());
        return w_ret.getTime();
    }

    public long getTimesnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() / 1000) * 1000;
    }

    public long countDownTime(long nowTime) {
        return getTimesnight() - nowTime * 1000;
    }
}