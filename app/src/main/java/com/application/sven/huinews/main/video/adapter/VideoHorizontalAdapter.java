package com.application.sven.huinews.main.video.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.main.home.activity.UserInfoActivity;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.view.MyVideoProgress;
import com.application.sven.huinews.view.video.controller.HorizontalVideoController;
import com.dueeeke.videoplayer.listener.VideoListener;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/11
 * effect: 横向视频适配器
 */
public class VideoHorizontalAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<VideoListResponse.VideoList.ListBean> mDatas = new ArrayList<>();

    private boolean isRefresh;

    public VideoHorizontalAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<VideoListResponse.VideoList.ListBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.horizontal_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final VH vh = (VH) holder;
        final VideoListResponse.VideoList.ListBean mData = mDatas.get(position);
        FrescoUtil.loadDefImg(vh.user_head, mData.getUser_avatar());
        vh.tv_user_name.setText(mData.getUser_nickname());
        vh.tv_content.setText(mData.getTitle());
        vh.tv_like.setText(CommonUtils.getLikeCount(mData.getLike_count()));
        // vh.tv_like.setTextColor(mData.isIs_up() ? mContext.getResources().getColor(R.color.c_eb3e44) : mContext.getResources().getColor(R.color.color_line));
        vh.iv_like.setImageResource(mData.isIs_up() ? R.mipmap.icon_zan_2 : R.mipmap.icon_zan_1);

        vh.tv_msg.setText(CommonUtils.getLikeCount(mData.getComment_count()));

        vh.tv_collection.setText(CommonUtils.getLikeCount(mData.getCollect_count()));
        //vh.tv_collection.setTextColor(mData.isIs_collected() ? mContext.getResources().getColor(R.color.c_eb3e44) : mContext.getResources().getColor(R.color.color_line));
        vh.iv_collection.setImageResource(mData.isIs_collected() ? R.mipmap.icon_collectioned : R.mipmap.icon_collection);


        vh.video_player.setPlayerConfig(vh.mPlayerConfig);
        vh.video_player.setUrl(mData.getVideo_url());
        vh.video_player.setVideoController(vh.mController);

        FrescoUtil.loadDefImg(vh.mController.getThumb(), mData.getVideo_cover());


        vh.tv_follow.setBackgroundResource(mData.isIs_follow() ? R.drawable.follow_bg_cli : R.drawable.follow_bg_nor);
        vh.tv_follow.setText(mData.isIs_follow() ? R.string.followed : R.string.follow);
        vh.tv_follow.setTextColor(mData.isIs_follow() ? mContext.getResources().getColor(R.color.c_333333) : mContext.getResources().getColor(R.color.color_white));

        vh.user_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.toThis(mContext, mData.getUser_id());
            }
        });

        vh.tv_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.toThis(mContext, mData.getUser_id());
            }
        });

        vh.btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.setIs_up(!mData.isIs_up());
                mData.setLike_count(mData.isIs_up() ? mData.getLike_count() + 1 : mData.getLike_count() - 1);
                vh.iv_like.setImageResource(mData.isIs_up() ? R.mipmap.icon_zan_2 : R.mipmap.icon_zan_1);
                //  vh.tv_like.setTextColor(mData.isIs_up() ? mContext.getResources().getColor(R.color.c_eb3e44) : mContext.getResources().getColor(R.color.color_line));
                vh.tv_like.setText(CommonUtils.getLikeCount(mData.getLike_count()));
                if (onItemFuncationClickLisenter != null) {
                    onItemFuncationClickLisenter.onLike(mData);
                }
            }
        });

        vh.tv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.setIs_follow(!mData.isIs_follow());
                vh.tv_follow.setBackgroundResource(mData.isIs_follow() ? R.drawable.follow_bg_cli : R.drawable.follow_bg_nor);
                vh.tv_follow.setText(mData.isIs_follow() ? R.string.followed : R.string.follow);
                vh.tv_follow.setTextColor(mData.isIs_follow() ? mContext.getResources().getColor(R.color.c_333333) : mContext.getResources().getColor(R.color.color_white));
                if (onItemFuncationClickLisenter != null) {
                    onItemFuncationClickLisenter.onFollow(mData);
                }
            }
        });

        vh.btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemFuncationClickLisenter != null) {
                    onItemFuncationClickLisenter.onComment(mData, vh.tv_msg);
                }
            }
        });

        vh.btn_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.setIs_collected(!mData.isIs_collected());
                mData.setCollect_count(mData.isIs_collected() ? mData.getCollect_count() + 1 : mData.getCollect_count() - 1);
                vh.iv_collection.setImageResource(mData.isIs_collected() ? R.mipmap.icon_collectioned : R.mipmap.icon_collection);
                vh.tv_collection.setText(CommonUtils.getLikeCount(mData.getCollect_count()));
                if (onItemFuncationClickLisenter != null) {
                    onItemFuncationClickLisenter.onCollection(mData);
                }
            }
        });
        vh.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemFuncationClickLisenter != null) {
                    onItemFuncationClickLisenter.onShare(mData);
                }
            }
        });


        /********视频播放监听*********/
        vh.video_player.setVideoListener(new VideoListener() {
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

        /******进度条拖动监听*******/

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class VH extends RecyclerView.ViewHolder {
        View maskView;
        SimpleDraweeView user_head, iv_thumb;
        LinearLayout btn_like, btn_msg, btn_collection, btn_share;
        TextView tv_user_name;
        TextView tv_content, tv_collection, tv_msg, tv_like;
        TextView tv_follow;
        ImageView iv_like, iv_collection;
        RelativeLayout video_progress_layout; //进度动画
        MyVideoProgress video_progress;
        IjkVideoView video_player;
        PlayerConfig mPlayerConfig;

        private HorizontalVideoController mController;

        public VH(View v) {
            super(v);
            maskView = v.findViewById(R.id.mask_view);
            btn_share = v.findViewById(R.id.btn_share);
            btn_like = v.findViewById(R.id.btn_like);
            btn_msg = v.findViewById(R.id.btn_msg);
            btn_collection = v.findViewById(R.id.btn_collection);
            user_head = v.findViewById(R.id.user_head);
            tv_user_name = v.findViewById(R.id.tv_user_name);
            iv_thumb = v.findViewById(R.id.iv_thumb);
            tv_content = v.findViewById(R.id.tv_content);
            tv_collection = v.findViewById(R.id.tv_collection);
            tv_msg = v.findViewById(R.id.tv_msg);
            tv_follow = v.findViewById(R.id.tv_follow);
            iv_like = v.findViewById(R.id.iv_like);
            tv_like = v.findViewById(R.id.tv_like);
            iv_collection = v.findViewById(R.id.iv_collection);
            //   video_progress_layout = v.findViewById(R.id.video_progress_layout);
            // video_progress = v.findViewById(R.id.video_progress);


            video_player = v.findViewById(R.id.video_player);

            mController = new HorizontalVideoController(mContext);
            mPlayerConfig = new PlayerConfig.Builder().enableCache()./*setLooping().*/addToPlayerManager().build();
        }
    }


    public interface onItemFuncationClickLisenter {
        void onShare(VideoListResponse.VideoList.ListBean mData);

        void onFollow(VideoListResponse.VideoList.ListBean mData);

        void onLike(VideoListResponse.VideoList.ListBean mData);

        void onComment(VideoListResponse.VideoList.ListBean mData, TextView tv_msg);

        void onCollection(VideoListResponse.VideoList.ListBean mData);
    }

    private onItemFuncationClickLisenter onItemFuncationClickLisenter;

    public void setOnShareClickLisenter(onItemFuncationClickLisenter onItemFuncationClickLisenter) {
        this.onItemFuncationClickLisenter = onItemFuncationClickLisenter;
    }


    /**
     * 视频播放监听暴露接口
     */
    public interface onVideoPlayStatusLisenter {
        void onVideoComplete(VideoListResponse.VideoList.ListBean mData);

        void onVideoPlaying(VideoListResponse.VideoList.ListBean mData);

        void onVideoPaused();

        void onVideoError();

    }

    private onVideoPlayStatusLisenter mOnVideoPlayStatusLisenter;

    public void setOnVideoPlayStatusLisenter(onVideoPlayStatusLisenter mOnVideoPlayStatusLisenter) {
        this.mOnVideoPlayStatusLisenter = mOnVideoPlayStatusLisenter;
    }


}
