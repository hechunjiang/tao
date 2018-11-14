package com.application.sven.huinews.main.preemption.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class MountainAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<MovieListResponse.MovieData.ListsBean> mData = new ArrayList<>();

    public MountainAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MovieListResponse.MovieData.ListsBean> mData) {
        if (mData.size()>3){
            mData.remove(0);
        }
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.type_mountain1_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MovieListResponse.MovieData.ListsBean data = mData.get(position);
        VH vh = (VH) holder;
        FrescoUtil.loadDefImg(vh.iv_thumb, data.getM_img());
        // FrescoUtil.setImageScale(vh.iv_thumb, data.getM_img(), FrescoUtil.VERTICAL);
        vh.tv_title.setText(data.getM_name());

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailsActivity.toThis(context, data.getId(),0,0);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mData.size() > 3 ? 3 : mData.size();
    }


    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_thumb;
        TextView tv_title;

        public VH(View v) {
            super(v);
            iv_thumb = v.findViewById(R.id.iv_thumb);
            tv_title = v.findViewById(R.id.banner_title);
        }
    }
}
