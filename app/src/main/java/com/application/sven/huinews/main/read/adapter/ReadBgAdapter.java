package com.application.sven.huinews.main.read.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;

/**
 * auther: sunfuyi
 * data: 2018/7/9
 * effect:
 */
public class ReadBgAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private onItemClickListener onItemClickListener;
    private int[] bgColors = {
            R.drawable.read_bg_select_bg4,
            R.drawable.read_bg_select_bg2,
            R.drawable.read_bg_select_bg3,
            R.drawable.read_bg_select_bg1,
            R.drawable.read_bg_select_bg5,
            R.drawable.read_bg_select_bg6};

    private int currPosition = 0;

    public ReadBgAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setSelected(int position) {
        this.currPosition = position;
        notifyDataSetChanged();
    }

    public int[] getData() {
        return bgColors;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.read_bg_select_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        vh.tv_bg.setBackgroundResource(bgColors[position]);
        if (currPosition == position) {
            vh.tv_bg.setText("√");
        } else {
            vh.tv_bg.setText("");
        }

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onSelected(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bgColors.length;
    }

    class VH extends RecyclerView.ViewHolder {

        TextView tv_bg;

        public VH(View itemView) {
            super(itemView);
            tv_bg = itemView.findViewById(R.id.tv_bg);
        }

    }


    public interface onItemClickListener {
        void onSelected(int posiiton);
    }

    public void setOnItemClickListener(onItemClickListener l) {
        this.onItemClickListener = l;
    }


}
