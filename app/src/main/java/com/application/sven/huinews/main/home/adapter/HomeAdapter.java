package com.application.sven.huinews.main.home.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.HomeBean;
import com.application.sven.huinews.db.BaseDB;
import com.application.sven.huinews.entity.request.VideoInfoRequest;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.main.home.AdModel;
import com.application.sven.huinews.main.home.adapter.viewholder.HomeAdOneViewHolder;
import com.application.sven.huinews.main.home.adapter.viewholder.HomeAdThreeViewHolder;
import com.application.sven.huinews.main.home.adapter.viewholder.HomeOneBigViewHolder;
import com.application.sven.huinews.main.home.adapter.viewholder.HomeVideoViewHolder;
import com.application.sven.huinews.main.video.activity.HorizontalVideoActivity;
import com.application.sven.huinews.main.video.activity.VerticalVideoActivity;
import com.application.sven.huinews.main.video.adapter.VideoHorizontalAdapter;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.application.sven.huinews.view.dialog.ShareDialog;
import com.dueeeke.videoplayer.listener.VideoListener;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sfy. on 2018/5/9 0009.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    public static final int TYPE_ONE_IMGE = 0; //大图
    public static final int TYPE_SMALL_VIDEO = 1; //小视频
    public static final int TYPE_AD_RIGHT_IMG = 2;//右小图广告
    public static final int TYPE_AD_THREE_IMG = 3;//三小图广告
    public static final int TYPE_AD_ONE_IMG = 4;//大图广告

    private String mLayoutType;
    private LayoutInflater mInflater;
    private Context mContext;
    private Activity activity;
    // private List<VideoListResponse.VideoList.ListBean> mVideoList = new ArrayList<>();
    private List<Object> mDats = new ArrayList<>();
    private int channelId;
    private Drawable mLikeCheck, mLikeNormal;

    private BaseDB mBaseDB;


    public HomeAdapter(Context mContext, Activity activity) {
        this.mContext = mContext;
        this.activity = activity;
        mInflater = LayoutInflater.from(mContext);

        mLikeCheck = mContext.getResources().getDrawable(R.mipmap.comment_liked);
        mLikeCheck.setBounds(0, 0, mLikeCheck.getMinimumWidth(), mLikeCheck.getMinimumHeight());
        mLikeNormal = mContext.getResources().getDrawable(R.mipmap.comment_like);
        mLikeNormal.setBounds(0, 0, mLikeNormal.getMinimumWidth(), mLikeNormal.getMinimumHeight());
        mBaseDB = BaseDB.getInstance(mContext);
    }


    public int getDataList() {
        return mDats.size();
    }

    public List<Object> datas() {
        return mDats;
    }

    public void setList(int channelId, List list, boolean isRefresh, String layout) {
        this.mLayoutType = layout;
        this.channelId = channelId;
        if (list == null && list.size() == 0) {
            return;
        }
        if (isRefresh) {
            mDats.clear();
        }
        mDats.addAll(list);
        notifyDataSetChanged();

    }

    /**
     * 刷新item
     *
     * @param mData
     */
    public void notifyOneChange(VideoListResponse.VideoList.ListBean mData) {
        int index = mDats.indexOf(mData);
        if (index < 0) {
            return;
        }
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setIs_up(mData.isIs_up());
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setLike_count(mData.getLike_count());
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setIs_collected(mData.isIs_collected());
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setCollect_count(mData.getCollect_count());
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setIs_follow(mData.isIs_follow());
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setSaveRedVideo(readNewsCanGetCoin(mData) ? false : true);
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setComment_count(mData.getComment_count());
        notifyItemChanged(index);
    }

    private boolean isRefresh;

    public void refreshItem(boolean isRefresh) {
        this.isRefresh = isRefresh;
        notifyDataSetChanged();
    }

    /**
     * 更新数据
     *
     * @param mData
     */
    public void setOneChange(VideoListResponse.VideoList.ListBean mData) {
        int index = mDats.indexOf(mData);
        if (index < 0) {
            return;
        }
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setIs_up(mData.isIs_up());
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setLike_count(mData.getLike_count());
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setIs_collected(mData.isIs_collected());
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setCollect_count(mData.getCollect_count());
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setIs_follow(mData.isIs_follow());
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setSaveRedVideo(readNewsCanGetCoin(mData) ? false : true);
        ((VideoListResponse.VideoList.ListBean) mDats.get(index)).setComment_count(mData.getComment_count());

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View typeView;
        switch (viewType) {
            case TYPE_ONE_IMGE:
                typeView = mInflater.inflate(R.layout.type_one_img_list_item, parent, false);
                holder = new HomeOneBigViewHolder(mContext, typeView);
                break;
            case TYPE_SMALL_VIDEO:
                typeView = mInflater.inflate(R.layout.type_video_list_item, parent, false);
                holder = new HomeVideoViewHolder(mContext, typeView);
                break;
            case TYPE_AD_RIGHT_IMG:
                typeView = mInflater.inflate(R.layout.type_ad_right_img_list_item, parent, false);
                holder = new HomeAdOneViewHolder(mContext, typeView);
                break;
            case TYPE_AD_THREE_IMG:
                typeView = mInflater.inflate(R.layout.type_ad_three_img_list_item, parent, false);
                holder = new HomeAdThreeViewHolder(mContext, typeView);
                break;
            case TYPE_AD_ONE_IMG:
                typeView = mInflater.inflate(R.layout.type_ad_big_img_list_item_video, parent, false);
                holder = new HomeAdOneViewHolder(mContext, typeView);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = 0;
        if (mDats.get(position) instanceof AdModel) {
            AdModel adModel = (AdModel) mDats.get(position);
            if (adModel.getType().equals(AdsConfig.AD_TYPE_TENCENT_RIGHT)) {
                viewType = TYPE_AD_RIGHT_IMG;
            } else {
                viewType = TYPE_AD_ONE_IMG;
            }
        } else if (mLayoutType.equals(Constant.TYPE_WATERFALL)) {
            viewType = 1;
        } else {
            viewType = 0;
        }

        switch (viewType) {
            case TYPE_ONE_IMGE:
                final HomeOneBigViewHolder homeOneBigViewHolder = (HomeOneBigViewHolder) holder;
                final VideoListResponse.VideoList.ListBean mData = (VideoListResponse.VideoList.ListBean) mDats.get(position);

                homeOneBigViewHolder.setOnBigImgInfo(mData);

               /* homeOneBigViewHolder.iv_thumb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VideoInfoRequest mRequest = new VideoInfoRequest();
                        mRequest.setmVideoInfo(mData);
                        mRequest.setPosition(position);
                        mRequest.setChannel_id(channelId);
                        HorizontalVideoActivity.toThis(mContext, mRequest);
                    }
                });*/


                homeOneBigViewHolder.btn_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemFunClickLisenter != null) {
                            mOnItemFunClickLisenter.onLike(mData);
                        }

                        mData.setIs_up(!mData.isIs_up());
                        mData.setLike_count(mData.isIs_up() ? mData.getLike_count() + 1 : mData.getLike_count() - 1);
                        homeOneBigViewHolder.btn_like.setCompoundDrawables(mData.isIs_up() ? mLikeCheck : mLikeNormal, null, null, null);
                        homeOneBigViewHolder.btn_like.setTextColor(mData.isIs_up() ? mContext.getResources().getColor(R.color.c_eb3e44) : mContext.getResources().getColor(R.color.color_line));

                        notifyDataSetChanged();
                    }
                });

                homeOneBigViewHolder.btn_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemFunClickLisenter != null) {
                            mOnItemFunClickLisenter.onComment(mData, homeOneBigViewHolder.btn_comment);
                        }
                    }
                });

                homeOneBigViewHolder.btn_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemFunClickLisenter != null) {
                            mOnItemFunClickLisenter.onShare(mData, homeOneBigViewHolder.btn_like);
                        }
                    }
                });


                homeOneBigViewHolder.video_player.setVideoListener(new VideoListener() {
                    @Override
                    public void onVideoStarted() {
                        //视频开始播放时开始金币动画
                        LogUtil.showLog("video_player----video:onVideoStarted");
                        if (mOnVideoPlayStatusLisenter != null) {
                            mOnVideoPlayStatusLisenter.onVideoPlaying(mData);
                        }
                    }

                    @Override
                    public void onVideoPaused() {
                        LogUtil.showLog("video_player----video:onVideoPaused");
                        // vh.video_progress.stopProgress();
                        if (mOnVideoPlayStatusLisenter != null) {
                            mOnVideoPlayStatusLisenter.onVideoPaused();
                        }
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.showLog("video_player----video:onComplete");
                        if (mOnVideoPlayStatusLisenter != null) {
                            mOnVideoPlayStatusLisenter.onVideoComplete(mData);
                        }
                    }

                    @Override
                    public void onPrepared() {
                        LogUtil.showLog("video_player----video:onPrepared");
                    }

                    @Override
                    public void onError() {
                        LogUtil.showLog("video_player----video:onError");
                        if (mOnVideoPlayStatusLisenter != null) {
                            mOnVideoPlayStatusLisenter.onVideoError();
                        }
                    }

                    @Override
                    public void onInfo(int what, int extra) {
                        LogUtil.showLog("video_player----video:onInfo");
                    }
                });

                break;
            case TYPE_SMALL_VIDEO:
                final HomeVideoViewHolder homeVideoViewHolder = (HomeVideoViewHolder) holder;
                homeVideoViewHolder.setSmallVideoInfo((VideoListResponse.VideoList.ListBean) mDats.get(position));
                homeVideoViewHolder.iv_thumb.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        VideoInfoRequest mRequest = new VideoInfoRequest();
                        mRequest.setmVideoInfo((VideoListResponse.VideoList.ListBean) mDats.get(position));
                        mRequest.setPosition(position);
                        mRequest.setChannel_id(channelId);
                        VerticalVideoActivity.toThis(activity, mRequest, homeVideoViewHolder.iv_thumb);
                    }
                });
                break;
            case TYPE_AD_RIGHT_IMG:
                HomeAdOneViewHolder homeAdOneViewHolder1 = (HomeAdOneViewHolder) holder;
                AdModel adModel1 = (AdModel) mDats.get(position);
                NativeExpressADView adView1 = adModel1.getmViews();
                homeAdOneViewHolder1.setTencentRightImg(adView1);
                break;
            case TYPE_AD_THREE_IMG:
                HomeAdThreeViewHolder homeAdThreeViewHolder = (HomeAdThreeViewHolder) holder;
                homeAdThreeViewHolder.setAdThreeInfo();
                break;
            case TYPE_AD_ONE_IMG:
                HomeAdOneViewHolder homeAdOneViewHolder = (HomeAdOneViewHolder) holder;
                AdModel adModel = (AdModel) mDats.get(position);
                NativeExpressADView adView = adModel.getmViews();
                homeAdOneViewHolder.setTencentAds(adView);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        int type = 0;
        if (mDats.get(position) instanceof AdModel) {
            AdModel adModel = (AdModel) mDats.get(position);
            if (adModel.getType().equals(AdsConfig.AD_TYPE_TENCENT_RIGHT)) {
                type = TYPE_AD_RIGHT_IMG;
            } else {
                type = TYPE_AD_ONE_IMG;
            }
        } else {
            int categtoy = 0;
            if (mLayoutType.equals(Constant.TYPE_WATERFALL)) {
                categtoy = 1;
            } else {
                categtoy = 0;
            }
            switch (categtoy) {
                case TYPE_ONE_IMGE:
                    type = TYPE_ONE_IMGE;
                    break;
                case TYPE_SMALL_VIDEO:
                    type = TYPE_SMALL_VIDEO;
                    break;
                case TYPE_AD_RIGHT_IMG:
                    type = TYPE_AD_RIGHT_IMG;
                    break;
                case TYPE_AD_THREE_IMG:
                    type = TYPE_AD_THREE_IMG;
                    break;
                case TYPE_AD_ONE_IMG:
                    type = TYPE_AD_ONE_IMG;
                    break;
            }
        }

        return type;
    }

    @Override
    public int getItemCount() {
        return mDats.size();
    }


    public interface onItemFunClickLisenter {
        void onLike(VideoListResponse.VideoList.ListBean mData);

        void onShare(VideoListResponse.VideoList.ListBean mData, TextView tv_like);

        void onComment(VideoListResponse.VideoList.ListBean mData, TextView tv_msg);

        void onCollection(VideoListResponse.VideoList.ListBean mData);
    }

    private onItemFunClickLisenter mOnItemFunClickLisenter;

    public void setOnItemFunClickLisenter(onItemFunClickLisenter mOnItemFunClickLisenter) {
        this.mOnItemFunClickLisenter = mOnItemFunClickLisenter;
    }

    private boolean readNewsCanGetCoin(VideoListResponse.VideoList.ListBean mData) {
        //判断数据库中是否存在改视频，如果有返回false
        if (hasReadVideo(mData.getId() + "")) {
            return false;
        }
        return true;
    }

    private boolean hasReadVideo(String newsId) {
        BaseDB mBaseDB = BaseDB.getInstance(mContext);
        return mBaseDB.getAllVideoList().contains(newsId);
    }


    /**
     * 视频播放监听暴露接口
     */
    public interface onVideoPlayStatusLisenter {
        void onVideoComplete(VideoListResponse.VideoList.ListBean mData);

        void onVideoPlaying(VideoListResponse.VideoList.ListBean mData);

        void onVideoPaused();

        void onVideoError();

        void onVideoPrepared();

    }

    private VideoHorizontalAdapter.onVideoPlayStatusLisenter mOnVideoPlayStatusLisenter;

    public void setOnVideoPlayStatusLisenter(VideoHorizontalAdapter.onVideoPlayStatusLisenter mOnVideoPlayStatusLisenter) {
        this.mOnVideoPlayStatusLisenter = mOnVideoPlayStatusLisenter;
    }


}

