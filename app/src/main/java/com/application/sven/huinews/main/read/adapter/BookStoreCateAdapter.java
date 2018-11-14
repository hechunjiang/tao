package com.application.sven.huinews.main.read.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.BookStoreCate;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookStoreCateAdapter extends RecyclerView.Adapter {


    private Context mContext;
    private LayoutInflater mInflater;
    private List<BookStoreCate.SearchMsgBean.DataBean> mBookStoreCate = new ArrayList<>();
    private int curPos = -1;

    public BookStoreCateAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<BookStoreCate.SearchMsgBean.DataBean> mBookStoreCate) {
        this.mBookStoreCate = mBookStoreCate;
        this.mBookStoreCate.remove(0);
        notifyDataSetChanged();
    }

    public void setCurPosisiton(int position) {
        this.curPos = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.book_store_cate_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        vh.tv_cate.setText(mBookStoreCate.get(position).getName());
        if (curPos == position) {
            vh.tv_cate.setSelected(true);
        } else {
            vh.tv_cate.setSelected(false);
        }

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookStoreCate.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView tv_cate;

        public VH(View itemView) {
            super(itemView);
            tv_cate = itemView.findViewById(R.id.tv_cate);
        }
    }

    private onItemClickListener mOnItemClickListener;


    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}
