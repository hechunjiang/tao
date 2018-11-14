package com.application.sven.huinews.main.preemption.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.MovieDetailResponse;
import com.application.sven.huinews.main.preemption.entity.MovieCount;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/11
 * effect: 选集下载
 */
public class MovieCountDownloadAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MovieDetailResponse.MovieDetailData.ExtBean> mMovieCounts = new ArrayList<>();

    public MovieCountDownloadAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }


    public void setData(List<MovieDetailResponse.MovieDetailData.ExtBean> mMovieCounts) {
        this.mMovieCounts = mMovieCounts;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.movie_count_download_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        vh.tv_count.setText(mMovieCounts.get(position).getNow_set_number() + "");
        boolean isSelected = mMovieCounts.get(position).isSelected();
        vh.iv_download.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
        vh.tv_count.setTextColor(isSelected ? mContext.getResources().getColor(R.color.color_white) : mContext.getResources().getColor(R.color.c_cdcdcd));
        vh.tv_count.setBackgroundResource(isSelected ? R.drawable.movie_count_cli : R.drawable.movie_count_nor);
        vh.tv_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMovieDownloadCountClickLisenter != null) {
                    onMovieDownloadCountClickLisenter.setSelected(position);
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
        ImageView iv_download;

        public VH(View v) {
            super(v);
            tv_count = v.findViewById(R.id.tv_count);
            iv_download = v.findViewById(R.id.iv_download);
        }
    }

    public interface onMovieDownloadCountClickLisenter {
        void setSelected(int postiion);
    }

    private onMovieDownloadCountClickLisenter onMovieDownloadCountClickLisenter;

    public void setOnMovieCountClickLisenter(onMovieDownloadCountClickLisenter onMovieCountClickLisenter) {
        this.onMovieDownloadCountClickLisenter = onMovieCountClickLisenter;
    }
}
