package com.application.sven.huinews.config;

import android.content.Context;

import com.application.sven.huinews.db.SpUtil;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class ChannelConfig {
    /**
     * 视频频道列表
     *
     * @param mContext
     * @return
     */
    public static List<VideoChannelResponse.ChannelData.VBean> getVideoChannelList(Context mContext) {
        List<VideoChannelResponse.ChannelData.VBean> responses = SpUtil.getList(mContext, Constant.CHANNEL_VIDEO_LIST);
        return responses;
    }

    /**
     * 默认小视频频道
     *
     * @param mContext
     * @return
     */
    public static List<VideoChannelResponse.ChannelData.VBean> getVideoDefChannelList(Context mContext) {
        String defStr = "[{\"id\":1,\"name\":\"精选\",\"temp_type\":\"transverse\",\"type\":1},{\"id\":2,\"name\":\"小视频\",\"temp_type\":\"waterfall\",\"type\":1},{\"id\":4,\"name\":\"搞笑\",\"temp_type\":\"transverse\",\"type\":1},{\"id\":5,\"name\":\"音乐\",\"temp_type\":\"transverse\",\"type\":1},{\"id\":7,\"name\":\"游戏\",\"temp_type\":\"transverse\",\"type\":1}]";
        List<VideoChannelResponse.ChannelData.VBean> responses = new Gson().fromJson(defStr, new TypeToken<List<VideoChannelResponse.ChannelData.VBean>>() {
        }.getType());
        return responses;
    }


    /**
     * 影视频道列表
     *
     * @param mContext
     * @return
     */
    public static List<VideoChannelResponse.ChannelData.MBean> getMovieChannelList(Context mContext) {
        List<VideoChannelResponse.ChannelData.MBean> responses = SpUtil.getList(mContext, Constant.CHANNEL_MOVIE_LIST);
        return responses;
    }

    /**
     * 默认影视频道
     *
     * @param context
     * @return
     */
    public static List<VideoChannelResponse.ChannelData.MBean> getMovieDefChannelList(Context context) {
        String defMovies = "[{\"id\":8,\"name\":\"推荐\",\"temp_type\":\"transverse\",\"type\":2},{\"id\":9,\"name\":\"电影\",\"temp_type\":\"transverse\",\"type\":2},{\"id\":10,\"name\":\"电视剧\",\"temp_type\":\"transverse\",\"type\":2},{\"id\":11,\"name\":\"动漫\",\"temp_type\":\"transverse\",\"type\":2},{\"id\":12,\"name\":\"综艺\",\"temp_type\":\"transverse\",\"type\":2}]";
        List<VideoChannelResponse.ChannelData.MBean> responses = new Gson().fromJson(defMovies, new TypeToken<List<VideoChannelResponse.ChannelData.MBean>>() {
        }.getType());
        return responses;
    }

    ;

    /**
     * 书籍频道列表
     *
     * @param mContext
     * @return
     */
    public static List<VideoChannelResponse.ChannelData.BBean> getBookChannelList(Context mContext) {
        List<VideoChannelResponse.ChannelData.BBean> responses = SpUtil.getList(mContext, Constant.CHANNEL_BOOK_LIST);
        return responses;
    }

    /**
     * 默认书籍频道
     *
     * @param mContext
     * @return
     */
    public static List<VideoChannelResponse.ChannelData.BBean> getBookDefChannelList(Context mContext) {
        String defStr = "[{\"id\":13,\"name\":\"精选\",\"temp_type\":\"transverse\",\"type\":3},{\"id\":14,\"name\":\"男生\",\"temp_type\":\"transverse\",\"type\":3},{\"id\":15,\"name\":\"女生\",\"temp_type\":\"transverse\",\"type\":3},{\"id\":16,\"name\":\"新书\",\"temp_type\":\"transverse\",\"type\":3}]";
        List<VideoChannelResponse.ChannelData.BBean> responses = new Gson().fromJson(defStr, new TypeToken<List<VideoChannelResponse.ChannelData.BBean>>() {
        }.getType());
        return responses;
    }

    /**
     * 男频，女频
     *
     * @param mContext
     * @return
     */
    public static List<VideoChannelResponse.ChannelData.BBean> getBookStoryTab(Context mContext) {
        List<VideoChannelResponse.ChannelData.BBean> mBeans = SpUtil.getList(mContext, Constant.CHANNEL_BOOK_LIST);
        List<VideoChannelResponse.ChannelData.BBean> mBookStoreTab = new ArrayList<>();
        if (mBeans != null && mBeans.size() > 0) {
            for (int i = 0; i < mBeans.size(); i++) {
                if (mBeans.get(i).getName().equals("男生") || mBeans.get(i).getName().equals("女生")) {
                    mBookStoreTab.add(mBeans.get(i));
                }
            }
        }

        for (int i = 0; i < mBookStoreTab.size(); i++) {
            LogUtil.showLog("msg---mBookStore:" + mBookStoreTab.get(i).getName());
        }
        return mBookStoreTab;
    }


    /**
     * 观看历史
     *
     * @return
     */
    public static ArrayList<ChannelBean> getHistoryChanel() {
        ArrayList<ChannelBean> channelBeans = new ArrayList<>();
        channelBeans.add(new ChannelBean(0, "小视频"));
        channelBeans.add(new ChannelBean(1, "抢先影视"));
        return channelBeans;

    }


    public static ArrayList<ChannelBean> getSearchChanel() {
        ArrayList<ChannelBean> channelBeans = new ArrayList<>();
        channelBeans.add(new ChannelBean(0, "小视频"));
        channelBeans.add(new ChannelBean(1, "电影"));
        channelBeans.add(new ChannelBean(2, "电视剧"));
        return channelBeans;
    }

    public static List<ChannelBean> getDownloadChannel() {
        List<ChannelBean> channelBeans = new ArrayList<>();
        channelBeans.add(new ChannelBean(0, "正在缓存"));
        channelBeans.add(new ChannelBean(1, "缓存完毕"));
        return channelBeans;
    }


    public static String getSearchType(int pos) {
        String mType = "";
        switch (pos) {
            case 0:
                mType = "video";
                break;
            case 1:
                mType = "film";
                break;
            case 2:
                mType = "tvplay";
                break;
        }
        return mType;
    }


}
