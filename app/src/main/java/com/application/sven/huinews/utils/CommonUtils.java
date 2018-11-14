package com.application.sven.huinews.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.application.sven.huinews.entity.response.MovieListResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tv.danmaku.ijk.media.player.ffmpeg.FFmpegApi;

/**
 * Created by Sven on 2018/2/5.
 */

public class CommonUtils {
    private static final int PAGE_SIZE = 20;

   /* public static boolean canReadNewsGetGold(MyNews myNews) {
        if (myNews.getId().contains("1")) {
            return true;
        }
        return false;
    }*/

    public static boolean isNoMoreData(int listSize) {
        if (listSize < PAGE_SIZE) {
            return true;
        }
        return false;
    }

    public static boolean checkPhoneNum(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) {
            return false;
        }
        if (phoneNum.length() != 11) {
            return false;
        }
        String regExp = "^(1)\\d{10}$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.matches();
    }

    public static boolean checkPassWord(String passWord) {
        if (TextUtils.isEmpty(passWord)) {
            return false;
        }
        if (passWord.length() < 6) {
            return false;
        }
        if (passWord.length() > 22) {
            return false;
        }
        return true;
    }

    public static boolean checkPhoneCode(String phoneCode) {
        if (phoneCode.length() != 4) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(phoneCode).matches();
    }

    public static boolean checkComment(String commentContent) {
        if (TextUtils.isEmpty(commentContent)) {
            return false;
        }
        return true;
    }

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    public static void reduceMarginsInTabs(TabLayout tabLayout, int marginOffset) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            for (int i = 0; i < ((ViewGroup) tabStrip).getChildCount(); i++) {
                View tabView = tabStripGroup.getChildAt(i);
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).leftMargin = marginOffset;
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).rightMargin = marginOffset;
                }
            }
            tabLayout.requestLayout();
        }
    }

    public static String getLikeCount(int likeCount) {
        if (likeCount < 10000) {
            return likeCount + "";
        }
        double doubleMoney = likeCount * 0.0001;
        DecimalFormat decimalFormat = new DecimalFormat("######0.00");
        return decimalFormat.format(doubleMoney) + "W";
    }

    public static String getPlayCount(int playCount) {
        if (playCount < 10000) {
            return playCount + "次播放";
        }
        double doubleMoney = playCount * 0.0001;
        DecimalFormat decimalFormat = new DecimalFormat("######0.00");
        return decimalFormat.format(doubleMoney) + "万次播放";
    }

    public static String getDuration(long duration) {
        // duration = duration / 100;
        return /*format(duration / 3600) + ":" + */format(duration / 60 % 60) + ":" + format(duration % 60);
    }

    public static String getDurationInt(long duration) {
        duration = duration / 1000;
        return format(duration / 3600) + ":" + format(duration / 60 % 60) + ":" + format(duration % 60);

    }

    private static String format(long i) {
        return i < 10 ? "0" + i : String.valueOf(i);
    }

    /**
     * 时间戳获取日期时间
     *
     * @param time
     * @return
     */
    public static String getDataTime(long time) {
        time = time * 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(time);
        return date;
    }

    public static String getLongTime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(time);
        return date;
    }

    public static String getDataTimeDay(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(time);
        return date;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int convertDpToPixel(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.density);
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static String body(String str, Type mListType) {
        String o = null;
        try {
            List<String> mlist = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(str);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                //获取key值
                String key = keys.next().toString();
                LogUtil.showLog("key:" + key);
                //获取value值
                String value = null;
                o = jsonObject.getString(key);
                //   mlist.add(value);
            }


        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return o;
    }

    /**
     * 毫秒转化为秒
     *
     * @param mss
     * @return
     */
    public static String formatMillisecondsToSeconds(long mss) {
        Date date = new Date(mss);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String seconds = sdf.format(date);
        return seconds;
    }

    public static String ToDBC(String input) {
        // 导致TextView异常换行的原因：安卓默认数字、字母不能为第一行以后每行的开头字符，因为数字、字母为半角字符
        // 所以我们只需要将半角字符转换为全角字符即可
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

    /**
     * 正则匹配字符串中的数字
     *
     * @param s
     * @return
     */
    public static String numToString(String s) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        return m.replaceAll("").trim();
    }

    public static Drawable setLeftDrawable(Context mContext, int res) {
        Drawable mDrawable = mContext.getResources().getDrawable(res);
        mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
        return mDrawable;
    }

    /**
     * 将str的集合转为逗号隔开的字符串
     *
     * @param mStrs
     * @return
     */
    /**
     * 组装出以某个字符串间隔的字符串
     *
     * @param list
     * @param strInterval 间隔字符串
     * @return
     */
    public static String listToStr(List<String> list, String strInterval) {
        String str = "";

        if (list == null)
            return str;

        for (int i = 0; i < list.size(); i++) {
            str = str + list.get(i) + strInterval;
        }

        if (str.lastIndexOf(strInterval) != -1)
            str = str.substring(0, str.lastIndexOf(strInterval));

        return str;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String formetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取url地址的后缀
     *
     * @param url
     * @return
     */
    public static String getFileExtensionFromUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            int fragment = url.lastIndexOf('#');
            if (fragment > 0) {
                url = url.substring(0, fragment);
            }

            int query = url.lastIndexOf('?');
            if (query > 0) {
                url = url.substring(0, query);
            }

            int filenamePos = url.lastIndexOf('/');
            String filename =
                    0 <= filenamePos ? url.substring(filenamePos + 1) : url;

            // if the filename contains special characters, we don't
            // consider it valid for our matching purposes:
            if (!filename.isEmpty() &&
                    Pattern.matches("[a-zA-Z_0-9\\.\\-\\(\\)\\%]+", filename)) {
                int dotPos = filename.lastIndexOf('.');
                if (0 <= dotPos) {
                    return filename.substring(dotPos + 1);
                }
            }
        }

        return "";
    }


    /**
     * 对数据进行分组
     * 实现换一批
     *
     * @param mDatas
     * @param showSize
     * @return
     */
    public static List<List<MovieListResponse.MovieData.ListsBean>> getData(List<MovieListResponse.MovieData.ListsBean> mDatas, int showSize) {
        List<List<MovieListResponse.MovieData.ListsBean>> groupDatas = new ArrayList<>();
        List<MovieListResponse.MovieData.ListsBean> temp = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            if (i % showSize == 0) {
                if (!temp.isEmpty()) {
                    groupDatas.add(temp);
                }
                temp = new ArrayList<>();
                temp.add(mDatas.get(i));
            } else {
                temp.add(mDatas.get(i));
            }
        }
        if (!temp.isEmpty()) {
            groupDatas.add(temp);
        }
        return groupDatas;
    }

    public static String timeStamp2Date(String seconds) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        String format = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }
    public static String delHTMLTag(String htmlStr){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }
}
