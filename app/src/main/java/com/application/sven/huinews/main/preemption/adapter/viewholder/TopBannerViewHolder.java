package com.application.sven.huinews.main.preemption.adapter.viewholder;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.adapter.TopBannerPagerAdapter;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.LogUtil;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class TopBannerViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout viewGroup;
    private ImageView[] ivTip;
    private TopBannerPagerAdapter mAdapter;
    Handler mHandler = new Handler();
    int TIME = 3000;
    int itemPosition;
    int mCount;
    boolean isDragging; //banner拖拽

    public TopBannerViewHolder(Context context, View v) {

        super(v);
        this.mContext = context;
        mViewPager = v.findViewById(R.id.viewpager);
        viewGroup = v.findViewById(R.id.viewGroup);
        mAdapter = new TopBannerPagerAdapter(mContext);
    }

    public void setTopBannerInfo(final MovieListResponse.MovieData mMovieData) {

        viewGroup.removeAllViews();
        ivTip = new ImageView[mMovieData.getLists().size()];
        for (int i = 0; i < ivTip.length; i++) {
            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            margin.setMargins(CommonUtils.dip2px(mContext, (float) 4.5), 0, 0, 0);
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            ivTip[i] = iv;
            if (i == 0) {
                ivTip[i].setBackgroundResource(R.drawable.banner_tip_cli);
            } else {
                ivTip[i].setBackgroundResource(R.drawable.banner_tip_nor);

            }
            viewGroup.addView(ivTip[i], margin);
        }
        mViewPager.setAdapter(mAdapter);
        mAdapter.setData(mMovieData.getLists());
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int count = mMovieData.getLists().size();
                int pos = position % count;
                for (int i = 0; i < count; i++) {
                    if (pos == i) {
                        ivTip[pos].setBackgroundResource(R.drawable.banner_tip_cli);
                    } else {
                        ivTip[i].setBackgroundResource(R.drawable.banner_tip_nor);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        // 用户拖拽
                        isDragging = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        // 空闲状态
                        isDragging = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        // 被释放时
                        isDragging = false;
                        break;
                    default:
                        break;
                }
            }
        });
        mCount = mMovieData.getLists().size();
        mHandler.removeCallbacks(runnableForViewPager);
        mHandler.postDelayed(runnableForViewPager, TIME);
    }


    /**
     * ViewPager的定时器
     */
    Runnable runnableForViewPager = new Runnable() {
        @Override
        public void run() {
            if (!isDragging) {
                itemPosition++;
                if (mCount>0){
                    mViewPager.setCurrentItem(itemPosition % mCount);
                }

            }
            mHandler.postDelayed(this, TIME);
        }
    };


    public void onDestroyHandler() {
        if (mHandler != null) {
            mHandler.removeCallbacks(runnableForViewPager);
        }
    }

    public void onStartHandler() {
        if (mHandler != null) {
            mHandler.removeCallbacks(runnableForViewPager);
            mHandler.postDelayed(runnableForViewPager, TIME);
        }
    }
}
