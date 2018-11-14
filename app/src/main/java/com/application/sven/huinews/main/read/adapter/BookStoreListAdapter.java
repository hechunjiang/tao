package com.application.sven.huinews.main.read.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.BookStoreList;
import com.application.sven.huinews.main.read.activity.BookDetailsActivity;
import com.application.sven.huinews.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/12
 * effect:
 */
public class BookStoreListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    public List<BookStoreList.Book> mBooks = new ArrayList<>();

    public BookStoreListAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setDatas(List<BookStoreList.Book> mBooks, boolean isRefresh) {
        if (mBooks.size() <= 0 && mBooks == null) {
            return;
        }
        if (isRefresh) {
            this.mBooks.clear();
        }
        this.mBooks.addAll(mBooks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.book_store_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        final BookStoreList.Book mBook = mBooks.get(position);
        FrescoUtil.loadDefImg(vh.iv_book, mBook.getPic());
        vh.tv_auther_name.setText(mBook.getAuthor());
        vh.tv_book_dis.setText(mBook.getDescription());
        vh.tv_book_type.setText(mBook.getCay_name());
        vh.tv_book_name.setText(mBook.getTitle());

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailsActivity.toThis(mContext, mBook.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public void clearData() {
        mBooks.clear();
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_book;
        TextView tv_book_name;
        TextView tv_book_dis;
        TextView tv_auther_name;
        TextView tv_book_type;

        public VH(View itemView) {
            super(itemView);
            iv_book = itemView.findViewById(R.id.iv_book);
            tv_book_name = itemView.findViewById(R.id.tv_book_name);
            tv_book_dis = itemView.findViewById(R.id.tv_book_dis);
            tv_auther_name = itemView.findViewById(R.id.tv_auther_name);
            tv_book_type = itemView.findViewById(R.id.tv_book_type);
        }
    }
}
