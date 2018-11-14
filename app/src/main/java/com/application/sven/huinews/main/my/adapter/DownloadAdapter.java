package com.application.sven.huinews.main.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.download.DownloadData;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/11
 * effect:下载适配器
 */
public class DownloadAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private int type;
    private List<DownloadData> mDatas = new ArrayList<>();

    public DownloadAdapter(Context mContext, int type) {
        this.mContext = mContext;
        this.type = type;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<DownloadData> mDatas) {
        if (mDatas == null && mDatas.size() <= 0) {
            return;
        }
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    public void clearAll() {
        this.mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.download_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        if (type == 1) {
            vh.tv_progress.setVisibility(View.GONE);
            vh.mProgressBar.setVisibility(View.GONE);
        } else {
            vh.tv_progress.setVisibility(View.VISIBLE);
            vh.mProgressBar.setVisibility(View.VISIBLE);
        }

        vh.tv_content.setText(mDatas.get(position).getName());
        FrescoUtil.loadDefImg(vh.iv_thumb, mDatas.get(position).getImagePath());

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ProgressBar mProgressBar;
        TextView tv_progress;
        SimpleDraweeView iv_thumb;
        TextView tv_content;

        public VH(View v) {
            super(v);
            mProgressBar = v.findViewById(R.id.progress);
            tv_progress = v.findViewById(R.id.tv_progress);
            iv_thumb = v.findViewById(R.id.iv_thumb);
            tv_content = v.findViewById(R.id.tv_content);
        }
    }
}
