package com.application.sven.huinews.main.search.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.SearchList;
import com.application.sven.huinews.entity.response.SearchResponse;
import com.application.sven.huinews.main.home.activity.VideoDetailsActivity;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect: 搜索
 */
public class SearchAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<SearchList> mSearchLists = new ArrayList<>();

    public SearchAdapter(Context mContext) {
        this.mContext = mContext;

        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<SearchList> mSearchList, boolean isRefresh) {
        if (mSearchList == null && mSearchList.size() == 0) {
            return;
        }
        if (isRefresh) {
            this.mSearchLists.clear();
        }
        this.mSearchLists.addAll(mSearchList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.mSearchLists.clear();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.search_small_video_list_item, parent, false);
        return new SmallVideoVH(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SmallVideoVH smallVideoVH = (SmallVideoVH) holder;
        final SearchList mData = mSearchLists.get(position);
        FrescoUtil.loadDefImg(smallVideoVH.iv_thumb, mData.getVideo_cover());
        smallVideoVH.tv_content.setText(mData.getTitle());
        smallVideoVH.tv_duration.setText(CommonUtils.getDuration(mData.getVideo_duration()));
        smallVideoVH.tv_name.setText(mData.getCreate_time());
        smallVideoVH.tv_display.setText(mData.getPlay_count());

        smallVideoVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoDetailsActivity.toThis(mContext, mData.getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mSearchLists.size();
    }

    class SmallVideoVH extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_thumb;
        TextView tv_duration;
        TextView tv_content;
        TextView tv_name;
        TextView tv_display;

        public SmallVideoVH(View v) {
            super(v);
            iv_thumb = v.findViewById(R.id.iv_thumb);
            tv_duration = v.findViewById(R.id.tv_duration);
            tv_content = v.findViewById(R.id.tv_content);
            tv_name = v.findViewById(R.id.tv_name);
            tv_display = v.findViewById(R.id.tv_display);
        }
    }

    class MovieVideoVH extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_thumb;
        TextView tv_title;
        TextView tv_movie_star;
        TextView tv_content;

        public MovieVideoVH(View v) {
            super(v);
            iv_thumb = v.findViewById(R.id.iv_thumb);
            tv_title = v.findViewById(R.id.tv_title);
            tv_movie_star = v.findViewById(R.id.tv_movie_star);
            tv_content = v.findViewById(R.id.tv_content);
        }
    }
}
