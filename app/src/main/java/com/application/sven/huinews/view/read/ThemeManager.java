package com.application.sven.huinews.view.read;

import android.graphics.Color;
import android.view.View;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.R;
import com.application.sven.huinews.db.UserSpCache;

/**
 * auther: sunfuyi
 * data: 2018/7/7
 * effect:
 */
public class ThemeManager {

    public static final int NORMAL = 0;
    public static final int YELLOW = 1;
    public static final int GREEN = 2;
    public static final int FANS = 3;
    public static final int KHAKI_DEEP = 4;
    public static final int KHAKI_SHALLOW = 5;
    public static final int NIGHT = 6;

    public static void setReaderTheme(int theme, View v) {
        switch (theme) {
            case NORMAL:
                v.setBackgroundColor(Color.parseColor("#E6D2B5"));
                break;
            case YELLOW:
                v.setBackgroundColor(Color.parseColor("#9fc9e1"));
                break;
            case GREEN:
                v.setBackgroundColor(Color.parseColor("#CCE8CF"));
                break;
            case FANS:
                v.setBackgroundColor(Color.parseColor("#F5F4F0"));
                break;
            case KHAKI_DEEP:
                v.setBackgroundColor(Color.parseColor("#816768"));
                break;
            case KHAKI_SHALLOW:
                v.setBackgroundColor(Color.parseColor("#A8718B"));
                break;
            case NIGHT:
                v.setBackgroundColor(Color.parseColor("#424242"));
            default:
                break;
        }
    }


    public static int setReaderBgColor(int theme) {
        int color = -1;
        switch (theme) {
            case NORMAL:
                color = Color.parseColor("#E6D2B5");

                break;
            case YELLOW:
                color = Color.parseColor("#9fc9e1");
                break;
            case GREEN:
                color = Color.parseColor("#CCE8CF");
                break;
            case FANS:
                color = Color.parseColor("#F5F4F0");
                break;
            case KHAKI_DEEP:
                color = Color.parseColor("#816768");
                break;
            case KHAKI_SHALLOW:
                color = Color.parseColor("#A8718B");
                break;
            case NIGHT:
                color = Color.parseColor("#424242");
            default:
                break;
        }
        return color;
    }


    /**
     * 保存主题颜色
     *
     * @param position
     */
    public static void cacheThemeBg(int position) {
        UserSpCache.getInstance(AppConfig.getAppContext()).putInt(UserSpCache.READ_THEME_COLOR, position);
    }

    /**
     * 获取主题颜色
     *
     * @return
     */
    public static int getCacheThemeBg() {
        int theme = 0;
        if (UserSpCache.getInstance(AppConfig.getAppContext()).getInt(UserSpCache.READ_THEME_COLOR) != 0) {
            theme = UserSpCache.getInstance(AppConfig.getAppContext()).getInt(UserSpCache.READ_THEME_COLOR);
        }
        return theme;
    }

    /**
     * 缓存字体大小
     *
     * @param fontSizePx
     */
    public static void cacheFontSize(int fontSizePx) {
        UserSpCache.getInstance(AppConfig.getAppContext()).putInt(UserSpCache.READ_THEME_FONT_SIZE, fontSizePx);
    }

    /**
     * 获取字体大小
     *
     * @return
     */
    public static int getReadFontSize() {
        return UserSpCache.getInstance(AppConfig.getAppContext()).getInt(UserSpCache.READ_THEME_FONT_SIZE);
    }

    public static void cacheSeekBarProgress(int progress) {
        UserSpCache.getInstance(AppConfig.getAppContext()).putInt(UserSpCache.READ_SEEK_BAR_PROGRESS, progress);
    }

    public static int getReadSeekBarPorgress() {
        return UserSpCache.getInstance(AppConfig.getAppContext()).getInt(UserSpCache.READ_SEEK_BAR_PROGRESS);
    }


    public static void cachePageModel(int pageModel) {
        UserSpCache.getInstance(AppConfig.getAppContext()).putInt(UserSpCache.READ_PAGE_MODEL, pageModel);
    }

    public static int getPageModel() {
        return UserSpCache.getInstance(AppConfig.getAppContext()).getInt(UserSpCache.READ_PAGE_MODEL);

    }

}
