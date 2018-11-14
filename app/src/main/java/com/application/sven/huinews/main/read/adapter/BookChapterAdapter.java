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
import com.application.sven.huinews.main.read.activity.BookReadActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookChapterAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<BookDetailsResponse.BookDetails.LatelyBean> mChapters = new ArrayList<>();

    public BookChapterAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }


    public void setData(List<BookDetailsResponse.BookDetails.LatelyBean> mChapters) {
        this.mChapters = mChapters;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.book_info_chapter_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        vh.tv_chapter.setText(mChapters.get(position).getChapter_name());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickLisenter != null) {
                    mOnItemClickLisenter.toOpenBook(mChapters.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mChapters.size() > 3) {
            return 3;
        }
        return mChapters.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView tv_chapter;

        public VH(View itemView) {
            super(itemView);
            tv_chapter = itemView.findViewById(R.id.tv_chapter);
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
