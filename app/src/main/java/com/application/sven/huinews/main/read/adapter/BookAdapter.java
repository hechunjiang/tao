package com.application.sven.huinews.main.read.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.main.read.activity.BookDetailsActivity;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.ScreensUitls;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/9
 * effect:
 */
public class BookAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<BookList.ListsBean> mDatas = new ArrayList<>();

    public BookAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<BookList.ListsBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public void setLastThreeData(List<BookList.ListsBean> mDatas) {
        if (mDatas.size() > 3) {
            mDatas.remove(0);
        }
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.book_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        final BookList.ListsBean mData = mDatas.get(position);
        FrescoUtil.loadDefImg(vh.iv_book, mData.getPic());
        vh.tv_name.setText(mData.getTitle());

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailsActivity.toThis(mContext, mData.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_book;
        TextView tv_name;

        public VH(View itemView) {
            super(itemView);
            iv_book = itemView.findViewById(R.id.iv_book);
            tv_name = itemView.findViewById(R.id.tv_book_name);
        }
    }
}
