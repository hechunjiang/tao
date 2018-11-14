package com.application.sven.huinews.main.home.fragment;


import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseFragment;
import com.application.sven.huinews.config.ChannelBean;
import com.application.sven.huinews.config.ChannelConfig;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.request.VideoTaskRequest;
import com.application.sven.huinews.entity.response.VideoChannelResponse;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.main.home.adapter.TabPagerAdapter;
import com.application.sven.huinews.main.search.activity.SearchActivity;
import com.application.sven.huinews.main.web.activity.WebActivity;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.view.MyVideoProgress;
import com.application.sven.huinews.view.dialog.GoldRuleDialog;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfy. on 2018/5/7 0007.
 * 首页
 */

public class HomeFragment extends BaseFragment {
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageButton btn_search;

    private GoldRuleDialog mGoldRuleDialog; //金币规则dialog
    private RelativeLayout video_progress_layout;
    private MyVideoProgress video_progress;
    private TextView tv_gold_count;
    private int videoCount, mCount; //播放次数
    private int mCurrentProgress; //停止时 的进度
    private long mDuration;
    private VideoListResponse.VideoList.ListBean mVideoData;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initObject() {
        initTab();
        if (mGoldRuleDialog == null) {
            mGoldRuleDialog = new GoldRuleDialog(mContext);
        }
    }

    @Override
    protected void initView(View v) {
        mTabLayout = v.findViewById(R.id.tablayout);
        mViewPager = v.findViewById(R.id.view_pager);
        btn_search = v.findViewById(R.id.btn_search);

        video_progress_layout = v.findViewById(R.id.video_progress_layout);
        video_progress = v.findViewById(R.id.video_progress);
        tv_gold_count = v.findViewById(R.id.tv_gold_count);

        mDuration = UserSpCache.getInstance(mContext).getInt(UserSpCache.WATCH_TIME);
        if (mDuration < 1) {
            mDuration = 20;
        }
        mDuration = mDuration * 1000;
        video_progress.setDuration(mDuration);
    }

    @Override
    public void initEvents() {
        btn_search.setOnClickListener(this);
        video_progress_layout.setOnClickListener(this);

        mGoldRuleDialog.setOnDialogEnterListener(new GoldRuleDialog.OnDialogEnterListener() {
            @Override
            public void toWeb() {
                WebActivity.toThis(mContext, Api.MY_INCOME_TYPE1);
                if (video_progress != null) {
                    video_progress.stopProgress();
                    mCurrentProgress = video_progress.getProgressCurrent();
                    mDuration = video_progress.surplusTime();
                }
            }
        });

        /***** 金币动画监听****/

        video_progress.OnVideoProgressLisenter(new MyVideoProgress.OnVideoProgressLisenter() {
            @Override
            public void end() {
                mCurrentProgress = 0;
                mDuration = 20000;
                video_progress.setDuration(mDuration);
                video_progress.setProgress(mCurrentProgress);
                video_progress.stopProgress();

                EventBus.getDefault().post("progressEnd");

                //进度完成后增加金币
                //视频播放完成
                /*if (!mPresenter.isCanGetCoinByVideo(mVideoData)) {
                    return;
                }
                if (mVideoData.getIs_gold() == 1) {
                    mPresenter.onVideoGoldTask(mVideoData, VideoTaskRequest.TASK_ID_READ_NEWS);
                } else if (mVideoData.getIs_redpack() == 1) {
                    mPresenter.onVideoGoldTask(mVideoData, VideoTaskRequest.TASK_ID_READ_RED_NEWS);
                }*/
            }
        });
    }

    @Override
    public void OnClickEvents(View v) {
        if (v == btn_search) {
            SearchActivity.toThis(mContext);
        } else if (v == video_progress_layout) {
            mGoldRuleDialog.show();
        }
    }

    private void initTab() {
        final List<VideoChannelResponse.ChannelData.VBean> arrayList;
        if (ChannelConfig.getVideoChannelList(mContext) == null || ChannelConfig.getVideoChannelList(mContext).size() == 0) {
            arrayList = ChannelConfig.getVideoDefChannelList(mContext);
        } else {
            arrayList = ChannelConfig.getVideoChannelList(mContext);
        }
        TabPagerAdapter myContentPagerAdapter = new TabPagerAdapter(getChildFragmentManager(), arrayList, 1);
        mViewPager.setAdapter(myContentPagerAdapter);
        //mViewPager.setOffscreenPageLimit(arrayList.size());
        mTabLayout.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.showLog("msg----onPageSelected:" + arrayList.get(position).getTemp_type());
                if (arrayList.get(position).getTemp_type().equals(Constant.TYPE_WATERFALL)) {
                    video_progress_layout.setVisibility(View.GONE);
                } else {
                    video_progress_layout.setVisibility(View.VISIBLE);
                }
                cachePorgress();
                VideoViewManager.instance().releaseVideoPlayer();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void setVideoPlay(VideoListResponse.VideoList.ListBean mData, int videoCount) {
        this.mVideoData = mData;
        this.videoCount = videoCount;
        mDuration = video_progress.surplusTime();
        video_progress.stopProgress();
        mCurrentProgress = video_progress.getProgressCurrent();

        if (mData.getIs_gold() == 0 && mData.getIs_redpack() == 0) {
            video_progress_layout.setVisibility(View.GONE);
        } else if (videoCount > 0) {
            video_progress_layout.setVisibility(View.VISIBLE);
            video_progress.setProgress(mCurrentProgress);
            video_progress.setDuration(mDuration);
            video_progress.animStart();
        } else {
            video_progress_layout.setVisibility(View.GONE);
        }
    }

    public void onVideoPaused() {
        video_progress.stopProgress();
        mCurrentProgress = video_progress.getProgressCurrent();
    }

    public void onVideoComplete() {
        video_progress.stopProgress();
        mCurrentProgress = video_progress.getProgressCurrent();
    }

    public void hideGoldProgress() {
        video_progress_layout.setVisibility(View.GONE);
    }

    public RelativeLayout getVideo_progress_layout() {
        return video_progress_layout;
    }

    public TextView getTv_gold_count() {
        return tv_gold_count;
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.showLog("msg---HomeFragment:onPause");
        VideoViewManager.instance().releaseVideoPlayer();
        if (video_progress != null) {
            cachePorgress();
            video_progress.stopProgress();
        }
    }

    private void cachePorgress() {
        mDuration = video_progress.surplusTime();
        video_progress.stopProgress();
        mCurrentProgress = video_progress.getProgressCurrent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.showLog("msg---HomeFragment:onDestroyView");
        if (video_progress != null) {
            cachePorgress();
            video_progress.stopProgress();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (video_progress != null) {
                cachePorgress();
                video_progress.stopProgress();
            }
        }
    }
}
