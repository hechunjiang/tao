package com.application.sven.huinews.main.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.MoreMovieListResponse;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfy. on 2018/5/9 0009.
 */

public class MoreMovieAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MoreMovieListResponse.MoreMovieList.ListsBean> mMovieDatas = new ArrayList<>();

    public MoreMovieAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<MoreMovieListResponse.MoreMovieList.ListsBean> mMovieDatas, boolean isRefresh) {
        if (mMovieDatas == null && mMovieDatas.size() == 0) {
            return;
        }
        if (isRefresh) {
            this.mMovieDatas.clear();
        }
        this.mMovieDatas.addAll(mMovieDatas);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.type_mountain1_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        final MoreMovieListResponse.MoreMovieList.ListsBean mData = mMovieDatas.get(position);
       // FrescoUtil.loadDefImg(vh.iv_thumb, mData.getM_img());
        FrescoUtil.loadDefImg(vh.iv_thumb,mData.getM_img());
        vh.banner_title.setText(mData.getM_name());

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailsActivity.toThis(mContext, mData.getId(),0,0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieDatas.size();
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
