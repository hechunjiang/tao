package com.application.sven.huinews.main.preemption.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.ScreensUitls;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfy. on 2018/5/9 0009.
 */

public class SGridAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MovieListResponse.MovieData.ListsBean> mData = new ArrayList<>();

    public SGridAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<MovieListResponse.MovieData.ListsBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.type_mountain1_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        final MovieListResponse.MovieData.ListsBean data = mData.get(position);
        vh.iv_thumb.setImageURI(data.getM_img());
        vh.banner_title.setText(data.getM_name());
        FrescoUtil.loadDefImg(vh.iv_thumb, data.getM_img());

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailsActivity.toThis(mContext, data.getId(), 0, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData.size() > 6) {
            return 6;
        }
        return mData.size();
    }

    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_thumb;
        TextView banner_title;

        public VH(View v) {
            super(v);
            iv_thumb = v.findViewById(R.id.iv_thumb);
            banner_title = v.findViewById(R.id.banner_title);
        }
    }
}
