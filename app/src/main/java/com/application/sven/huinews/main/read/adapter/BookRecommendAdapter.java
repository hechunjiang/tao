package com.application.sven.huinews.main.read.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.BookDetailsResponse;
import com.application.sven.huinews.main.read.activity.BookDetailsActivity;
import com.application.sven.huinews.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookRecommendAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<BookDetailsResponse.BookDetails.RecommenBean> mRecommenBooks = new ArrayList<>();

    public BookRecommendAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<BookDetailsResponse.BookDetails.RecommenBean> mRecommenBooks) {
        this.mRecommenBooks = mRecommenBooks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.book_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        FrescoUtil.loadDefImg(vh.iv_book, mRecommenBooks.get(position).getPic());
        vh.tv_book_name.setText(mRecommenBooks.get(position).getTitle());

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickLisenter != null) {
                    mOnItemClickLisenter.itemClick(mRecommenBooks.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mRecommenBooks.size() > 6) {
            return 6;
        }
        return mRecommenBooks.size();
    }

    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_book;
        TextView tv_book_name;

        public VH(View itemView) {
            super(itemView);
            iv_book = itemView.findViewById(R.id.iv_book);
            tv_book_name = itemView.findViewById(R.id.tv_book_name);
        }
    }

    public interface OnItemClickLisenter {
        void itemClick(int bookId);

    }

    private OnItemClickLisenter mOnItemClickLisenter;

    public void setmOnItemClickLisenter(OnItemClickLisenter l) {
        this.mOnItemClickLisenter = l;
    }


}
