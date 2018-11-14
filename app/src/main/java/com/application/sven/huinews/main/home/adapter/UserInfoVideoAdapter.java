package com.application.sven.huinews.main.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.UserVideo;
import com.application.sven.huinews.entity.response.MovieDetailResponse;
import com.application.sven.huinews.main.home.activity.VideoDetailsActivity;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/10
 * effect:
 */
public class UserInfoVideoAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;

    private List<UserVideo> mDatas = new ArrayList<>();

    public UserInfoVideoAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setList(List<UserVideo> mDatas, boolean isRefresh) {
        if (mDatas == null && mDatas.size() <= 0) {
            return;
        }
        if (isRefresh) {
            this.mDatas.clear();
        }
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.recommend_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        final UserVideo data = mDatas.get(position);
        FrescoUtil.loadDefImg(vh.iv_thumb, data.getVideo_cover());
        vh.tv_content.setText(data.getTitle());
        // vh.tv_name.setText(data.getA_star());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoDetailsActivity.toThis(mContext, data.getId());
            }
        });
        vh.tv_name.setText(data.getCreate_time());
        vh.tv_play.setText(data.getPlay_count());
        vh.tv_duration.setText(CommonUtils.getDuration(data.getVideo_duration()));

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class VH extends RecyclerView.ViewHolder {

        SimpleDraweeView iv_thumb;
        TextView tv_duration;
        TextView tv_content;
        TextView tv_name;
        TextView tv_play;

        public VH(View v) {
            super(v);
            iv_thumb = v.findViewById(R.id.iv_thumb);
            tv_duration = v.findViewById(R.id.tv_duration);
            tv_content = v.findViewById(R.id.tv_content);
            tv_name = v.findViewById(R.id.tv_name);
            tv_play = v.findViewById(R.id.tv_play);
        }
    }
}
