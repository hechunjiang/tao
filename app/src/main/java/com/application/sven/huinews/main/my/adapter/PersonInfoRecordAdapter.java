package com.application.sven.huinews.main.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.MovieHistory;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.glidUtils.GlideUtils;
import com.application.sven.huinews.utils.umeng.UMengUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class PersonInfoRecordAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MovieHistory.ListsBean> mMovieHistorys = new ArrayList<>();

    public PersonInfoRecordAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<MovieHistory.ListsBean> mMovieHistorys) {
        this.mMovieHistorys = mMovieHistorys;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.mine_record_list_item, parent, false);
        VH vh = new VH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;

        final MovieHistory.ListsBean data = mMovieHistorys.get(position);
        FrescoUtil.loadDefImg(vh.videoThumb, data.getM_img());
        vh.videoTitle.setText(data.getM_name());

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(mContext, UMengUtils.MOVIE_HISTORY);
                MobclickAgent.onEvent(mContext, UMengUtils.MOVIE_HISTORY, UMengUtils.MOVIE_HISTORY);
                MovieDetailsActivity.toThis(mContext, data.getId(), data.getPlay_time(), data.getPlay_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieHistorys.size() > 8 ? 8 : mMovieHistorys.size();
    }

    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView videoThumb;
        TextView videoTitle;

        public VH(View itemView) {
            super(itemView);
            videoThumb = itemView.findViewById(R.id.video_thumb);
            videoTitle = itemView.findViewById(R.id.video_title);
        }
    }
}
