package com.application.sven.huinews.main.read.adapter.viewholder;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.main.login.activity.LoginActivity;
import com.application.sven.huinews.main.my.activity.PayInfoActivity;
import com.application.sven.huinews.main.read.activity.BookFreeActivity;
import com.application.sven.huinews.main.read.activity.BookShelfActivity;
import com.application.sven.huinews.main.read.activity.BookStoreActivity;
import com.application.sven.huinews.main.read.activity.BookUpdateActivity;
import com.application.sven.huinews.main.read.adapter.BannerAdapter;
import com.application.sven.huinews.main.web.activity.WebActivity;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.ToastUtils;

/**
 * auther: sunfuyi
 * data: 2018/7/9
 * effect:
 */
public class ReadTopVH extends RecyclerView.ViewHolder {
    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout tips;
    private TextView btn_bookstore, btn_chasingbook, btn_ranking, btn_newbook, btn_bookshelf;
    private ImageView[] ivTip;
    private ImageView iv_pay;
    private BannerAdapter mBannerAdapter;
    boolean isDragging; //banner拖拽

    public ReadTopVH(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        mViewPager = itemView.findViewById(R.id.viewpager);
        tips = itemView.findViewById(R.id.viewGroup);
        btn_bookstore = itemView.findViewById(R.id.btn_bookstore);
        btn_chasingbook = itemView.findViewById(R.id.btn_chasingbook);
        btn_ranking = itemView.findViewById(R.id.btn_ranking);
        btn_newbook = itemView.findViewById(R.id.btn_newbook);
        btn_bookshelf = itemView.findViewById(R.id.btn_bookshelf);
        iv_pay = itemView.findViewById(R.id.iv_pay);

        mBannerAdapter = new BannerAdapter(mContext);

    }

    public void setData(final BookList mBookList) {
        tips.removeAllViews();
        ivTip = new ImageView[mBookList.getLists().size()];
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
            tips.addView(ivTip[i], margin);
        }
        mViewPager.setAdapter(mBannerAdapter);
        mBannerAdapter.setData(mBookList.getLists());
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int count = mBookList.getLists().size();
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

        mCount = mBookList.getLists().size();
        mHandler.removeCallbacks(runnableForViewPager);
        mHandler.postDelayed(runnableForViewPager, TIME);


        /********************我是分割线***********处理banner下两个布局的信息*****************/


        btn_bookstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookStoreActivity.toThis(mContext);
            }
        });
        btn_newbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookFreeActivity.toThis(mContext);
            }
        });

        btn_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.toThis(mContext, Api.TOP_TEN);
            }
        });

        btn_chasingbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookUpdateActivity.toThis(mContext);
            }
        });

        btn_bookshelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(UserSpCache.getInstance(mContext).getStringData(UserSpCache.KEY_PHONE))) {
                    ToastUtils.showShort(mContext, "请先注册登录");
                    LoginActivity.toThis(mContext, false, false);
                    return;
                }
                BookShelfActivity.toThis(mContext);
            }
        });

        iv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(UserSpCache.getInstance(mContext).getStringData(UserSpCache.KEY_PHONE))) {
                    ToastUtils.showShort(mContext, "请先注册登录");
                    LoginActivity.toThis(mContext, false, false);
                    return;
                }
                PayInfoActivity.toThis(mContext,1);
            }
        });

    }

    Handler mHandler = new Handler();
    int TIME = 3000;
    int itemPosition;
    int mCount;
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
