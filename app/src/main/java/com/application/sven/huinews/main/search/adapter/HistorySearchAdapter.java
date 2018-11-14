package com.application.sven.huinews.main.search.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/12
 * effect: 历史搜索适配器
 */
public class HistorySearchAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> historyStr = new ArrayList<>();

    public HistorySearchAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<String> historyStr) {
        this.historyStr.clear();
        this.historyStr = historyStr;
        notifyDataSetChanged();
    }

    public void clearData(int position) {
        historyStr.remove(position);
        notifyDataSetChanged();
    }

    public void clearAll() {
        historyStr.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = mInflater.inflate(R.layout.history_search_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        vh.tv_search.setText(historyStr.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnLongClickLisenter != null) {
                    mOnLongClickLisenter.itemClick(position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongClickLisenter != null) {
                    mOnLongClickLisenter.longClick(position);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyStr.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView tv_search;

        public VH(View itemView) {
            super(itemView);
            tv_search = itemView.findViewById(R.id.tv_search);
        }
    }

    public interface onLongClickLisenter {
        void longClick(int position);

        void itemClick(int position);
    }

    private onLongClickLisenter mOnLongClickLisenter;

    public void setOnLongClickLisenter(onLongClickLisenter l) {
        this.mOnLongClickLisenter = l;
    }
}
