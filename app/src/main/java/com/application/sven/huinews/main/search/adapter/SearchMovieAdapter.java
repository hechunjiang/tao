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
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/28
 * effect:
 */
public class SearchMovieAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<SearchList> mSearchLists = new ArrayList<>();

    public SearchMovieAdapter(Context mContext) {
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
        View v = mInflater.inflate(R.layout.search_movie_list_item, parent, false);
        return new MovieVideoVH(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieVideoVH movieViewVH = (MovieVideoVH) holder;
        final SearchList mData = mSearchLists.get(position);
        FrescoUtil.loadDefImg(movieViewVH.iv_thumb, mData.getVideo_cover());
        movieViewVH.tv_content.setText("简介:" + mData.getM_desc());
        movieViewVH.tv_title.setText(mData.getTitle());
        movieViewVH.tv_movie_star.setText("主演:" + mData.getA_star());

        movieViewVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailsActivity.toThis(mContext, mData.getId(), 0, 0);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mSearchLists.size();
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
