package com.application.sven.huinews.main.preemption.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.main.preemption.entity.MovieCount;

import java.util.List;

/**
 * Created by sfy. on 2018/5/9 0009.
 */

public class MovieCountViewAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MovieCount> mMovieCounts;

    public MovieCountViewAdapter(Context mContext, List<MovieCount> mMovieCounts) {
        this.mContext = mContext;
        this.mMovieCounts = mMovieCounts;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.movie_count_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        
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
}
