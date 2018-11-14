package com.application.sven.huinews.main.preemption.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
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
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class BannerAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MovieListResponse.MovieData.ListsBean> movieList = new ArrayList<>();

    public BannerAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<MovieListResponse.MovieData.ListsBean> movieList) {
        if (movieList == null && movieList.size() <= 0) {
            return;
        }

        this.movieList = movieList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.type_banner_list_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        final MovieListResponse.MovieData.ListsBean mMovieData = movieList.get(position);
        vh.iv.setImageURI(Uri.parse(mMovieData.getM_img()));
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
        return movieList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView iv;
        TextView tv_title;
        TextView tv_content;

        public VH(View v) {
            super(v);
            iv = v.findViewById(R.id.iv_thumb);
            tv_title = v.findViewById(R.id.tv_title);
            tv_content = v.findViewById(R.id.tv_content);
        }
    }
}
