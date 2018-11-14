package com.application.sven.huinews.main.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.sven.huinews.R;

/**
 * auther: sunfuyi
 * data: 2018/5/10
 * effect:
 */
public class OneKeyFollowAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public OneKeyFollowAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.onekey_follow_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class VH extends RecyclerView.ViewHolder {
        public VH(View v) {
            super(v);
        }
    }
}
