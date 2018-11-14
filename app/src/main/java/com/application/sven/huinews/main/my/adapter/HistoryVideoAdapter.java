package com.application.sven.huinews.main.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.sven.huinews.R;
import com.application.sven.huinews.main.preemption.adapter.RecommedAdapter;

/**
 * auther: sunfuyi
 * data: 2018/5/10
 * effect:
 */
public class HistoryVideoAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public HistoryVideoAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.history_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickLisenter != null) {
                    mOnItemClickLisenter.onItemClick();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class VH extends RecyclerView.ViewHolder {

        public VH(View v) {
            super(v);
        }
    }

    public interface OnItemClickLisenter {
        void onItemClick();
    }

    private OnItemClickLisenter mOnItemClickLisenter;

    public void setmOnItemClickLisenter(OnItemClickLisenter m) {
        this.mOnItemClickLisenter = m;
    }


}
