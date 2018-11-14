package com.application.sven.huinews.main.preemption.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class BGridAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MovieListResponse.MovieData.ListsBean> movieList = new ArrayList<>();

    public BGridAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<MovieListResponse.MovieData.ListsBean> movieList) {
        if (movieList == null && movieList.size() <= 0) {
            return;
        }

        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.type_b_grid_list_item, parent, false);
        return new VH(v);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        final MovieListResponse.MovieData.ListsBean mMovieData = movieList.get(position);
        vh.iv_thumb.setImageURI(Uri.parse(mMovieData.getM_img()));
        vh.tv_title.setText(mMovieData.getM_name());
        vh.tv_content.setText(mMovieData.getM_s_desc());

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailsActivity.toThis(mContext, mMovieData.getId(),0,0);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (this.movieList.size() > 4) {
            return 4;
        } else {
            return movieList.size();
        }
    }

    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_thumb;
        TextView tv_title;
        TextView tv_content;

        public VH(View v) {
            super(v);
            iv_thumb = v.findViewById(R.id.iv_thumb);
            tv_title = v.findViewById(R.id.tv_title);
            tv_content = v.findViewById(R.id.tv_content);
        }
    }
}
