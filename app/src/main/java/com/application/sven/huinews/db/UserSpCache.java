package com.application.sven.huinews.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.entity.User;
import com.application.sven.huinews.entity.response.DataConfig;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class UserSpCache {
    public static final String KEY_CURRENT_DATA_TIME = "key_current_data_time";
    public static final String KEY_DEVICE_TOKEN = "key_device_token";

    public static final String KEY_IS_FIRST_OPEN_APP = "key_is_first_open_app";
    public static final String KEY_IS_SECEND_OPEN_APP = "key_is_secend_open_app";
    public static final String KEY_TOKEN_IS_EXPIRE = "key_token_is_expire";

    public static final String KEY_TICKET = "key_ticket";
    public static final String KEY_IS_USER_LOGIN = "key_is_user_login";
    public static final String KEY_USER = "key_user";
    public static final String KEY_PHONE = "key_phone";
    public static final String KEY_PASS = "key_pass";
    public static final String KEY_USER_ID = "key_user_id";
    public static final String QQ = "qq";
    public static final String IP = "ip";
    public static final String WATCH_TIME = "watch_time";
    public static final String RED_OPEN = "redopen";

    public static final String OPEN_COUNT = "open_count"; //记录打开的次数
    public static final String NEEDCOUNT_LOGIN = "need_count_login"; //记录服务器打开次数
    public static final String SIGN_SERVICE_TIME = "sign_service_time"; //同步服务器时间之服务器时间
    public static final String SIGN_LOCAL_TIME = "sign_localtime"; //同步服务器时间之获取服务器时间时的手机时间

    public static final String V_AT_COUNT = "v_at_count";//服务端配置的金币次数
    public static final String V_AT_RED = "v_at_red";//服务的配置的红包次数
    public static final String OPEN_GOLD_COUNT = "open_gold_count"; //当前金币获取次数
    public static final String OPEN_RED_COUNT = "open_red_count"; //当前红包获取次数
    public static final String SHARE_TITLE = "share_title";
    public static final String SHARE_CONTENT = "share_content";
    public static final String WX_UNIONID = "unionid";
    public static final String AD_TYPE = "ad_type";

    public static final String READ_THEME_COLOR = "read_theme_color";
    public static final String READ_THEME_FONT_SIZE = "read_theme_font_size";
    public static final String READ_SEEK_BAR_PROGRESS = "read_seek_bar_progress";
    public static final String READ_PAGE_MODEL = "read_page_model";

    public static final String SAVE_AUTO_PAY = "save_auto_pay";
    public static final String SAVE_AUTO_PAY_CHECK = "save_auto_pay_check";
    private SharedPreferences mSharedPreferences;
    private final static String CACHE_NAME = "USER_CACHE";
    private static UserSpCache mInstance;

    private UserSpCache(Context context) {
        mSharedPreferences = context.getSharedPreferences(CACHE_NAME, Context.MODE_PRIVATE);
    }

    public static UserSpCache getInstance(Context context) {
        if (mInstance == null) {
            synchronized (UserSpCache.class) {
                if (mInstance == null) {
                    mInstance = new UserSpCache(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 存储String类型的键值对
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getStringData(String key) {
        return mSharedPreferences.getString(key, "");
    }

    /**
     * 存储long
     *
     * @param key
     * @param value
     */
    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, -1);
    }

    /**
     * 存储int
     *
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, -1);
    }


    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public void deleteData(String key) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public void clearCache() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存广告类型信息
     *
     * @param adType
     * @throws Exception
     */
    public void putAdType(DataConfig.Data.ADTYPEBean adType) throws Exception {
        if (adType instanceof Serializable) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(adType);
                String temp = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
                editor.putString(AD_TYPE, temp);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("User must implements Serializable");
        }
    }

    /**
     * 获取广告类型
     *
     * @return
     */
    public DataConfig.Data.ADTYPEBean getAdType() {
        String temp = mSharedPreferences.getString(AD_TYPE, "");
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
        DataConfig.Data.ADTYPEBean adType = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            adType = (DataConfig.Data.ADTYPEBean) ois.readObject();
        } catch (IOException e) {
        } catch (ClassNotFoundException e1) {

        }
        return adType;
    }

    public void putUser(User user) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_USER, new Gson().toJson(user));
        editor.putString(KEY_USER_ID, user.getId());
        editor.commit();
    }

    public User getUser() {
        String userJson = mSharedPreferences.getString(KEY_USER, "");
        if (TextUtils.isEmpty(userJson)) {
            return new User();
        }
        return new Gson().fromJson(userJson, User.class);
    }


    public void logOut(Context mContext) {
        int totalCount = UserSpCache.getInstance(mContext).getInt(UserSpCache.NEEDCOUNT_LOGIN);
        long signTime = UserSpCache.getInstance(mContext).getLong(UserSpCache.SIGN_SERVICE_TIME);
        long localTime = UserSpCache.getInstance(mContext).getLong(UserSpCache.SIGN_LOCAL_TIME);
        int qq = UserSpCache.getInstance(mContext).getInt(UserSpCache.QQ);
        DataConfig.Data.ADTYPEBean data = UserSpCache.getInstance(mContext).getAdType();
        UserSpCache.getInstance(mContext.getApplicationContext()).clearCache();
        UserSpCache.getInstance(mContext).putString(UserSpCache.KEY_IS_FIRST_OPEN_APP, "has_open_app");
        UserSpCache.getInstance(mContext).putString(UserSpCache.KEY_IS_SECEND_OPEN_APP, "isSecend");
        UserSpCache.getInstance(mContext).putInt(UserSpCache.NEEDCOUNT_LOGIN, totalCount);
        UserSpCache.getInstance(mContext).putLong(UserSpCache.SIGN_SERVICE_TIME, signTime);
        UserSpCache.getInstance(mContext).putLong(UserSpCache.SIGN_LOCAL_TIME, localTime);
        UserSpCache.getInstance(mContext).putInt(UserSpCache.QQ, qq);
        try {
            UserSpCache.getInstance(mContext).putAdType(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveAutoPay(Context mContext, boolean isAuto) {
        UserSpCache.getInstance(mContext).putBoolean(SAVE_AUTO_PAY, isAuto);
    }

    public boolean isAutoPay(Context mContext) {
        return UserSpCache.getInstance(mContext).getBoolean(SAVE_AUTO_PAY);
    }
}
