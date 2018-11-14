package com.application.sven.huinews.main.preemption.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.MovieDetailResponse;
import com.application.sven.huinews.main.preemption.entity.MovieCount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfy. on 2018/5/9 0009.
 * 选集
 */

public class MovieCountAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MovieDetailResponse.MovieDetailData.ExtBean> mMovieCounts;
    private boolean isSelected;

    public MovieCountAdapter(Context mContext) {
        this.mContext = mContext;

        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<MovieDetailResponse.MovieDetailData.ExtBean> mMovieCounts) {
        this.mMovieCounts = mMovieCounts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.movie_count_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        vh.tv_count.setText((position + 1) + "");
        boolean isSelected = mMovieCounts.get(position).isSelected();
        vh.tv_count.setTextColor(isSelected ? mContext.getResources().getColor(R.color.color_white) : mContext.getResources().getColor(R.color.c_cdcdcd));
        vh.tv_count.setBackgroundResource(isSelected ? R.drawable.movie_count_cli : R.drawable.movie_count_nor);

        vh.tv_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMovieCountClickLisenter != null) {
                    if (!mMovieCounts.get(position).isSelected()){
                        onMovieCountClickLisenter.setSelected(position);
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieCounts.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView tv_count;

        public VH(View v) {
            super(v);
            tv_count = v.findViewById(R.id.tv_count);
        }
    }

    public interface onMovieCountClickLisenter {
        void setSelected(int postiion);
    }

    private onMovieCountClickLisenter onMovieCountClickLisenter;

    public void setOnMovieCountClickLisenter(onMovieCountClickLisenter onMovieCountClickLisenter) {
        this.onMovieCountClickLisenter = onMovieCountClickLisenter;
    }
}
