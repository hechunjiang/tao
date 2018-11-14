package com.application.sven.huinews.main.preemption.adapter.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.activity.MoreMovieActivity;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.main.preemption.adapter.AlbumAdapter;
import com.application.sven.huinews.utils.CardTransformer;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.ScreensUitls;
import com.application.sven.huinews.utils.glidUtils.GlideUtils;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Context mContext;
    private TextView type_name;
    private ViewPager viewPager;
    private LinearLayout vp_root;
    private TextView btn_more;
    private List<List<MovieListResponse.MovieData.ListsBean>> mDatas;
    private int mCurrentCount = 0;
    private int buId;

    public AlbumViewHolder(Context mContext, View v) {
        super(v);
        this.mContext = mContext;
        viewPager = v.findViewById(R.id.viewpager);
        vp_root = v.findViewById(R.id.vp_root);
        btn_more = v.findViewById(R.id.btn_more);
        type_name = v.findViewById(R.id.type_name);

        initEvents();
    }

    private void initEvents() {
        btn_more.setOnClickListener(this);
    }

    public void setAlbumInfo(final MovieListResponse.MovieData mMovieData) {
        buId = mMovieData.getId();
        mDatas = CommonUtils.getData(mMovieData.getLists(), 3);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageTransformer(true, new CardTransformer());
        viewPager.setAdapter(new ViewPagerAdapter(mMovieData.getLists()));
        viewPager.setCurrentItem(1);
        viewPager.setPageMargin(0);

        vp_root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });


        type_name.setText(mMovieData.getSub_name());
        if (mMovieData.getShow_more_type().equals("none")) {
            btn_more.setVisibility(View.GONE);
        } else if (mMovieData.getShow_more_type().equals("to_list")) {
            btn_more.setVisibility(View.VISIBLE);
            btn_more.setText(mContext.getString(R.string.more));
            btn_more.setTextColor(mContext.getResources().getColor(R.color.c_999999));
            Drawable mDrawable = mContext.getResources().getDrawable(R.mipmap.next);
            mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
            btn_more.setCompoundDrawables(null, null, mDrawable, null);


            btn_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoreMovieActivity.toThis(mContext, mMovieData.getId());
                }
            });
        } else if (mMovieData.getShow_more_type().equals("another_batch")) {
            btn_more.setVisibility(View.VISIBLE);
            btn_more.setText("换一批");
            btn_more.setTextColor(mContext.getResources().getColor(R.color.color_def));
            Drawable mDrawable = mContext.getResources().getDrawable(R.mipmap.icon_new);
            mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
            btn_more.setCompoundDrawables(null, null, mDrawable, null);
            btn_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentCount++;
                    if (mCurrentCount == mDatas.size()) {
                        mCurrentCount = 0;
                    }
                    setAlbumInfo(mMovieData);
                }
            });
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_more) {
            MoreMovieActivity.toThis(mContext, buId);
        }
    }


    class ViewPagerAdapter extends PagerAdapter {
        private List<MovieListResponse.MovieData.ListsBean> listsBeanList;

        public ViewPagerAdapter(List<MovieListResponse.MovieData.ListsBean> listsBeanList) {
            this.listsBeanList = listsBeanList;
        }

        @Override
        public int getCount() {
            return listsBeanList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.type_album_list_item, null);
            SimpleDraweeView iv = view.findViewById(R.id.iv_thumb);
            int screenWidth = ScreensUitls.getScreenWidth(mContext);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();
            params.weight = screenWidth / 3;
            params.leftMargin = CommonUtils.dip2px(mContext, 5);
            params.rightMargin = CommonUtils.dip2px(mContext, 5);
            iv.setLayoutParams(params);
            TextView tv = view.findViewById(R.id.tv_title);
            FrescoUtil.loadDefImg(iv, listsBeanList.get(position).getM_img());
            tv.setText(listsBeanList.get(position).getM_name());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieDetailsActivity.toThis(mContext, listsBeanList.get(position).getId(), 0, 0);
                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
