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
import com.application.sven.huinews.main.preemption.adapter.SGridAdapter;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.itemDecoration.DividerItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class SGridViewHodler extends RecyclerView.ViewHolder {
    private Context mContext;
    private TextView type_name;
    private TextView tv_right;
    private RecyclerView rv;
    private SGridAdapter mAdapter;
    private DividerItemDecoration itemDecoration;
    private SimpleDraweeView type_icon;
    private List<List<MovieListResponse.MovieData.ListsBean>> mDatas;

    private int mCurrentCount = 0;

    public SGridViewHodler(Context mContext, View v) {
        super(v);
        this.mContext = mContext;
        rv = v.findViewById(R.id.rv);
        type_icon = v.findViewById(R.id.type_icon);
        type_name = v.findViewById(R.id.type_name);
        tv_right = v.findViewById(R.id.tv_right);
        mAdapter = new SGridAdapter(mContext);
        if (itemDecoration == null) {
            itemDecoration = new DividerItemDecoration(mContext);
        }
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        rv.setLayoutManager(manager);
        rv.removeItemDecoration(itemDecoration);
        rv.addItemDecoration(itemDecoration);
        rv.setAdapter(mAdapter);
    }


    public void setSGridInfo(final MovieListResponse.MovieData mMovieData) {
        mDatas = CommonUtils.getData(mMovieData.getLists(), 6);
        type_icon.setImageURI(mMovieData.getIcon());
        type_name.setText(mMovieData.getSub_name());

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
                    setSGridInfo(mMovieData);
                }
            });
        }
    }

}
