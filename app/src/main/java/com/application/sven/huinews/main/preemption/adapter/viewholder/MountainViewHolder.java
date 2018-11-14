package com.application.sven.huinews.main.preemption.adapter.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.activity.MoreMovieActivity;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.main.preemption.adapter.MountainAdapter;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.itemDecoration.DividerItemDecoration;
import com.application.sven.huinews.utils.itemDecoration.MovieBGridItem;
import com.application.sven.huinews.utils.itemDecoration.StaggerItemDecoration;
import com.application.sven.huinews.utils.itemDecoration.VideoRecordItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class MountainViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private TextView type_name;
    private RecyclerView rv;
    private SimpleDraweeView iv_thumb, type_icon;
    private TextView tv_title;
    private TextView tv_right;
    private MountainAdapter mAdapter;
    private MovieListResponse.MovieData.ListsBean mListBean;
    private DividerItemDecoration mStaggerItemDecoration;
    private List<List<MovieListResponse.MovieData.ListsBean>> mDatas;
    private List<List<MovieListResponse.MovieData.ListsBean>> mTempDatas;
    private int mCurrentCount = 0;

    public MountainViewHolder(Context context, View v) {
        super(v);
        this.mContext = context;
        rv = v.findViewById(R.id.rv);
        type_icon = v.findViewById(R.id.type_icon);
        iv_thumb = v.findViewById(R.id.iv_thumb);
        tv_title = v.findViewById(R.id.banner_title);
        type_name = v.findViewById(R.id.type_name);
        tv_right = v.findViewById(R.id.tv_right);
        mAdapter = new MountainAdapter(mContext);
        if (mStaggerItemDecoration == null) {
            mStaggerItemDecoration = new DividerItemDecoration(mContext);
        }

        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        rv.setLayoutManager(manager);
        rv.removeItemDecoration(mStaggerItemDecoration);
        rv.addItemDecoration(mStaggerItemDecoration);
        rv.setAdapter(mAdapter);
    }

    public void setMountain(final MovieListResponse.MovieData mMovieData) {
        mDatas = CommonUtils.getData(mMovieData.getLists(), 4);
        mTempDatas = CommonUtils.getData(mMovieData.getLists(), 4);
        this.mListBean = mTempDatas.get(mCurrentCount).get(0);

        iv_thumb.setImageURI(mListBean.getM_img());
        tv_title.setText(mListBean.getM_name());
        type_name.setText(mMovieData.getSub_name());

        type_icon.setImageURI(mMovieData.getIcon());


        iv_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailsActivity.toThis(mContext, mListBean.getId(), 0, 0);
            }
        });

        mAdapter.setData(mDatas.get(mCurrentCount));

        if (mMovieData.getShow_more_type().equals("none")) {
            tv_right.setVisibility(View.GONE);
        } else if (mMovieData.getShow_more_type().equals("to_list")) {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText(mContext.getString(R.string.more));
            tv_right.setTextColor(mContext.getResources().getColor(R.color.c_999999));
            Drawable mDrawable = mContext.getResources().getDrawable(R.mipmap.next);
            mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
            tv_right.setCompoundDrawables(null, null, mDrawable, null);


            tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MoreMovieActivity.toThis(mContext, mMovieData.getId());
                }
            });
        } else if (mMovieData.getShow_more_type().equals("another_batch")) {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText("换一批");
            tv_right.setTextColor(mContext.getResources().getColor(R.color.color_def));
            Drawable mDrawable = mContext.getResources().getDrawable(R.mipmap.icon_new);
            mDrawable.setBounds(0, 0, mDrawable.getMinimumWidth(), mDrawable.getMinimumHeight());
            tv_right.setCompoundDrawables(null, null, mDrawable, null);
            tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentCount++;
                    if (mCurrentCount == mDatas.size()) {
                        mCurrentCount = 0;
                    }
                    setMountain(mMovieData);
                }
            });
        }
    }


}