package com.application.sven.huinews.main.preemption.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.utils.LogUtil;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class ContinueViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private TextView tv;

    public ContinueViewHolder(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        tv = itemView.findViewById(R.id.tv_name);
    }

    public void setName(final MovieListResponse.MovieData mMovieData) {
        tv.setText("续播：" + mMovieData.getLists().get(0).getM_name());

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailsActivity.toThis(mContext, mMovieData.getLists().get(0).getId(), mMovieData.getLists().get(0).getPlay_time(),mMovieData.getLists().get(0).getPlay_id());
            }
        });
    }
}
