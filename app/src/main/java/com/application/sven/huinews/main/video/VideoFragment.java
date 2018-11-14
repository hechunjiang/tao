package com.application.sven.huinews.main.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseFragment;
import com.application.sven.huinews.base.BaseVideoFragment;
import com.application.sven.huinews.config.VideoData;
import com.application.sven.huinews.main.video.adapter.VideoCommentAdapter;
import com.application.sven.huinews.mob.share.MobShare;
import com.application.sven.huinews.mob.share.ShareCallBack;
import com.application.sven.huinews.view.VerticalViewPager;
import com.application.sven.huinews.view.dialog.ShareDialog;
import com.dueeeke.videoplayer.player.IjkVideoView;

/**
 * auther: sunfuyi
 * data: 2018/5/11
 * effect:  垂直播放视频fragment
 */
public class VideoFragment extends BaseVideoFragment implements View.OnClickListener {
    private View view;
    //   private VerticalVideoPlayer videoPlayer;
    private VerticalViewPager verticalViewPager;
    private VideoData videoData;
    private LinearLayout video_comment_ll;
    private TextView btn_comment, btn_share;
    private ImageButton btn_close;
    private RecyclerView comment_rv;
    private VideoCommentAdapter mAdapter;

    private ShareDialog mShareDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_video, null);
        initView();
        initEvents();
        initData(false);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }

    public void addFragment(VerticalViewPager viewPager) {
        verticalViewPager = viewPager;
    }

    @Override
    public void fragmentVisible(boolean isFirstVisible) {
        initData(true);
    }

    private void initView() {
        //  videoPlayer = view.findViewById(R.id.viewPlayer);
        video_comment_ll = view.findViewById(R.id.video_comment_ll);
        btn_comment = view.findViewById(R.id.btn_comment);
        btn_close = view.findViewById(R.id.btn_close);
        btn_share = view.findViewById(R.id.btn_share);
        comment_rv = view.findViewById(R.id.comment_rv);


        comment_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new VideoCommentAdapter(getContext());
        comment_rv.setAdapter(mAdapter);
    }

    private void initEvents() {
        btn_comment.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        btn_share.setOnClickListener(this);
    }

    private void initData(boolean isPlay) {
        if (videoData != null) {
            //    videoPlayer.setUp(videoData.getVideoUrl(), true);
            //  videoPlayer.setLooping(true);
            //  videoPlayer.setThumbUrl(videoData.getCoverUrl());
            if (!isPlay) {
                //    videoPlayer.setThumbVisible();

            } else {
                //   videoPlayer.startPlayLogic();
            }
        }


    }

    int position;

    public void setData(VideoData data, int position) {
        videoData = data;
        this.position = position;
        // if (videoPlayer != null) {
        //     initData(false);
        // }

    }

    public void onResume() {
        super.onResume();
        //  if (this.videoPlayer.getCurrentState() == 5) {
        //     this.videoPlayer.onVideoResume();
        // }
    }

    public void onPause() {
        super.onPause();
      /*  if (this.videoPlayer.getCurrentState() == 2) {
            this.videoPlayer.onVideoPause();
        }*/
    }

    public void onDestroy() {
        super.onDestroy();
        //  this.videoPlayer.release();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_comment) {
            showCommentView();
        } else if (id == R.id.btn_close) {
            hideCommentView();
        } else if (id == R.id.btn_share) {
            showShareDialog();
        }
    }


    private void showShareDialog() {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(getContext());
        }
        mShareDialog.show();

        mShareDialog.setOnShareListener(new ShareDialog.OnShareListener() {
            @Override
            public void onShare(int type) {
                MobShare mobShare = new MobShare(getContext());
                mobShare.shareToOne(getContext(), "", new ShareCallBack() {
                    @Override
                    public void onResponse(String response) {
                        //todo 分享成功处理
                    }
                });
            }
        });
    }

    /**
     * 显示评论界面
     */
    private void showCommentView() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.movie_count_in);
        video_comment_ll.startAnimation(animation);
        video_comment_ll.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏评论界面
     */
    private void hideCommentView() {

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.movie_count_out);
        video_comment_ll.startAnimation(animation);//开始动画
        video_comment_ll.setVisibility(View.GONE);
    }

}
