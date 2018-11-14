package com.application.sven.huinews.main.home.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.application.sven.huinews.R;
import com.application.sven.huinews.main.home.adapter.HomeAdThreeImgAdapter;
import com.application.sven.huinews.utils.itemDecoration.StaggerItemDecoration;

/**
 * Created by sfy. on 2018/5/9 0009.
 */

public class HomeAdThreeViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private HomeAdThreeImgAdapter mAdapter;
    private RecyclerView rv;
    private StaggerItemDecoration itemDecoration;

    public HomeAdThreeViewHolder(Context mContext, View v) {
        super(v);
        this.mContext = mContext;
        rv = v.findViewById(R.id.rv);
        mAdapter = new HomeAdThreeImgAdapter(mContext);
        if (itemDecoration == null) {
            itemDecoration = new StaggerItemDecoration(mContext);
        }
    }

    public void setAdThreeInfo() {
        rv.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv.removeItemDecoration(itemDecoration);
        rv.addItemDecoration(itemDecoration);
        rv.setAdapter(mAdapter);
    }
}
