package com.application.sven.huinews.main.read.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.BookChapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/7
 * effect:
 */
public class CataLogSortAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<BookChapters.ListsBean> mBookChapters = new ArrayList<>();

    public CataLogSortAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<BookChapters.ListsBean> mBookChapters, boolean isRefresh) {
        if (mBookChapters.size() < 0 && mBookChapters == null) {
            return;
        }
        if (isRefresh) {
            this.mBookChapters.clear();
        }
        this.mBookChapters.addAll(mBookChapters);
        notifyDataSetChanged();

    }

    public void refreshStatus(int chapterId) {
        if (mBookChapters != null && mBookChapters.size() > 0) {
            for (int i = 0; i < mBookChapters.size(); i++) {
                if (mBookChapters.get(i).getId() == chapterId) {
                    mBookChapters.get(i).setIs_gold(1);
                    notifyItemChanged(i);
                    break;
                }
            }
        }
    }


    /**
     * 倒序or正序
     */
    public void setSort() {
        if (mBookChapters == null && mBookChapters.size() <= 0) {
            return;
        }
        Collections.reverse(mBookChapters);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.catalog_sort_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        vh.tv_chapter.setText(mBookChapters.get(position).getChapter_name());
        vh.iv_free.setImageResource(mBookChapters.get(position).getIs_gold() == 2 ? R.mipmap.icon_lock : R.mipmap.icon_free);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickLisenter != null) {
                    mOnItemClickLisenter.toOpenBook(mBookChapters.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookChapters.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView tv_chapter;
        ImageView iv_free;

        public VH(View itemView) {
            super(itemView);
            tv_chapter = itemView.findViewById(R.id.tv_chapter);
            iv_free = itemView.findViewById(R.id.iv_free);
        }
    }


    public interface OnItemClickLisenter {
        void toOpenBook(int chapterId);
    }

    private OnItemClickLisenter mOnItemClickLisenter;

    public void setOnItemClickLisenter(OnItemClickLisenter mOnItemClickLisenter) {
        this.mOnItemClickLisenter = mOnItemClickLisenter;
    }
}
