package com.application.sven.huinews.main.home.adapter.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.db.BaseDB;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.main.home.activity.UserInfoActivity;
import com.application.sven.huinews.main.video.activity.HorizontalVideoActivity;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.view.dialog.ShareDialog;
import com.application.sven.huinews.view.video.controller.HorizontalVideoController;
import com.dueeeke.videoplayer.listener.VideoListener;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by sfy. on 2018/5/9 0009.
 */

public class HomeOneBigViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Context mContext;
    public TextView tv_content, btn_like, btn_comment, tv_duration, tv_play;
    public SimpleDraweeView userHead, iv_thumb;
    private LinearLayout ll_count;
    private TextView user_name;
    public ImageButton btn_share;
    private ImageView icon_red;
    private ShareDialog mShareDialog;
    public IjkVideoView video_player;
    private VideoListResponse.VideoList.ListBean videoInfo;
    private Drawable mLikeCheck, mLikeNormal;
    private BaseDB mBaseDB;
    private HorizontalVideoController mController;
    private PlayerConfig mPlayerConfig;

    public HomeOneBigViewHolder(Context mContext, View v) {
        super(v);
        this.mContext = mContext;
        userHead = v.findViewById(R.id.head_view);
        tv_content = v.findViewById(R.id.tv_content);
        // iv_thumb = v.findViewById(R.id.iv_thumb);
        btn_share = v.findViewById(R.id.btn_share);
        user_name = v.findViewById(R.id.user_name);
        iv_thumb = v.findViewById(R.id.iv_thumb);
        btn_like = v.findViewById(R.id.btn_like);
        btn_comment = v.findViewById(R.id.btn_comment);
        tv_duration = v.findViewById(R.id.tv_duration);
        tv_play = v.findViewById(R.id.tv_play);
        icon_red = v.findViewById(R.id.icon_red);
        video_player = v.findViewById(R.id.video_player);
        ll_count = v.findViewById(R.id.ll_count);

        mController = new HorizontalVideoController(mContext);
        mPlayerConfig = new PlayerConfig.Builder().enableCache()./*setLooping().*/addToPlayerManager().build();

        mLikeCheck = mContext.getResources().getDrawable(R.mipmap.comment_liked);
        mLikeCheck.setBounds(0, 0, mLikeCheck.getMinimumWidth(), mLikeCheck.getMinimumHeight());
        mLikeNormal = mContext.getResources().getDrawable(R.mipmap.comment_like);
        mLikeNormal.setBounds(0, 0, mLikeNormal.getMinimumWidth(), mLikeNormal.getMinimumHeight());

        mBaseDB = BaseDB.getInstance(mContext);
    }

    public void setOnBigImgInfo(VideoListResponse.VideoList.ListBean videoInfo) {
        this.videoInfo = videoInfo;
        FrescoUtil.loadDefImg(userHead, videoInfo.getUser_avatar());
        // FrescoUtil.loadDefImg(iv_thumb, videoInfo.getVideo_cover());
        tv_content.setText(videoInfo.getTitle());
        tv_content.setVisibility(TextUtils.isDigitsOnly(videoInfo.getTitle()) ? View.GONE : View.VISIBLE);
        user_name.setText(videoInfo.getUser_nickname());

        btn_like.setText(CommonUtils.getLikeCount(videoInfo.getLike_count()));
        btn_like.setCompoundDrawables(videoInfo.isIs_up() ? mLikeCheck : mLikeNormal, null, null, null);
        btn_like.setTextColor(videoInfo.isIs_up() ? mContext.getResources().getColor(R.color.c_eb3e44) : mContext.getResources().getColor(R.color.color_line));

        btn_comment.setText(CommonUtils.getLikeCount(videoInfo.getComment_count()));
        tv_play.setText(videoInfo.getPlay_count());
        tv_duration.setText(CommonUtils.getDuration(videoInfo.getVideo_duration()));

        if (videoInfo.isSaveRedVideo()) {
            //判断当前视频是否已经观看过了
            icon_red.setVisibility(View.GONE);
        } else {
            icon_red.setVisibility(View.GONE);
            if (videoInfo.getIs_gold() == 1 && videoInfo.getIs_redpack() == 0) {
                //  icon_red.setVisibility(View.VISIBLE);
                icon_red.setImageResource(R.mipmap.icon_gold);
            } else if (videoInfo.getIs_gold() == 0 && videoInfo.getIs_redpack() == 1) {
                // icon_red.setVisibility(View.VISIBLE);
                icon_red.setImageResource(R.mipmap.icon_hongbao);
            } else {
                icon_red.setVisibility(View.GONE);
            }
        }

        initEvents();

        mController.setVideoInfo(videoInfo.getPlay_count(), videoInfo.getVideo_duration());
        /********视频播放监听*********/
        video_player.setPlayerConfig(mPlayerConfig);
        video_player.setUrl(videoInfo.getVideo_url());
        video_player.setVideoController(mController);

        video_player.setScreenScale(IjkVideoView.SCREEN_SCALE_MATCH_PARENT);
        FrescoUtil.loadDefImg(mController.getThumb(), videoInfo.getVideo_cover());


    }

    private void initEvents() {
        userHead.setOnClickListener(this);
        //  iv_thumb.setOnClickListener(this);
        btn_share.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.head_view) {
            //  UserInfoActivity.toThis(mContext, this.videoInfo.getUser_id());
        } /*else if (id == R.id.iv_thumb) {
            HorizontalVideoActivity.toThis(mContext,);
        }*//* else if (id == R.id.btn_share) {
            if (mShareDialog == null) {
                mShareDialog = new ShareDialog(mContext);
            }
            mShareDialog.show();*/
    }


}
