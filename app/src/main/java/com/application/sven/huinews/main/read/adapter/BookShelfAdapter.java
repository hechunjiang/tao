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
import com.application.sven.huinews.entity.CollectionList;
import com.application.sven.huinews.entity.response.BookShelfList;
import com.application.sven.huinews.main.my.adapter.CollectionAdapter;
import com.application.sven.huinews.main.read.activity.BookDetailsActivity;
import com.application.sven.huinews.main.read.activity.BookReadActivity;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.LogUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * auther: sunfuyi
 * data: 2018/7/12
 * effect:
 */
public class BookShelfAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<BookShelfList.ListsBean> mBookList = new ArrayList<>();
    private Map<BookShelfList.ListsBean, Boolean> selectedMap = new HashMap<>();
    private List<BookShelfList.ListsBean> collPos = new ArrayList<>();
    private boolean isEditing, isAll;

    public BookShelfAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public List<BookShelfList.ListsBean> getmBookList() {
        return mBookList;
    }

    public Map<BookShelfList.ListsBean, Boolean> getSelectedMap() {
        return selectedMap;
    }

    public void setDatas(List<BookShelfList.ListsBean> mBookList, boolean isRefresh) {

        if (mBookList == null && mBookList.size() <= 0) {
            return;
        }
        if (isRefresh) {
            this.mBookList.clear();
            selectedMap.clear();
        }

        this.mBookList.addAll(mBookList);
        for (int i = 0; i < mBookList.size(); i++) {
            selectedMap.put(mBookList.get(i), false);
        }
        notifyDataSetChanged();

    }

    public void setEdit(boolean isEditing) {
        this.isEditing = isEditing;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.book_shelf_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        if (mBookList.get(position).getTitle().equals("addBook")) {
            // vh.tv_book_name.setText(mBookList.get(position).getTitle());
        } else {
            vh.iv_checkbox.setVisibility(isEditing ? View.VISIBLE : View.GONE);
            final boolean isSelected = selectedMap.get(mBookList.get(position));
            vh.iv_checkbox.setImageResource(isSelected ? R.mipmap.icon_book_cli : R.mipmap.icon_book_nor);
            vh.tv_book_name.setText(mBookList.get(position).getTitle());
            FrescoUtil.loadDefImg(vh.iv_book, mBookList.get(position).getPic());
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEditing) {
                        setSelected(isSelected, mBookList.get(position));
                    } else {
                        BookReadActivity.toThis(mContext, mBookList.get(position).getId(), 0, mBookList.get(position).getTitle());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_book;
        ImageView iv_checkbox;
        TextView tv_book_name;

        public VH(View itemView) {
            super(itemView);
            iv_book = itemView.findViewById(R.id.iv_book);
            iv_checkbox = itemView.findViewById(R.id.iv_checkbox);
            tv_book_name = itemView.findViewById(R.id.tv_book_name);
        }
    }

    /**
     * 勾选
     *
     * @param isSelected
     * @param mBook
     */
    private void setSelected(boolean isSelected, BookShelfList.ListsBean mBook) {
        if (isSelected) {
            collPos.remove(mBook);
            selectedMap.put(mBook, false);
        } else {
            collPos.add(mBook);
            selectedMap.put(mBook, true);
        }
        if (mOnSelectedLisenter != null) {
            mOnSelectedLisenter.setOnSelected(collPos);
        }
        notifyDataSetChanged();
    }

    /**
     * 全选
     *
     * @param b
     */
    public void setIsAll(boolean b) {
        collPos.clear();
        if (mBookList == null && mBookList.size() <= 0) {
            return;
        }
        this.isAll = b;
        for (int i = 0; i < mBookList.size(); i++) {
            selectedMap.put(mBookList.get(i), isAll);
            collPos.add(mBookList.get(i));
        }
        notifyDataSetChanged();
    }

    public void delData(int position, boolean isAll) {
        if (isAll) {
            mBookList.clear();
        } else {
            collPos.remove(mBookList.get(position));
            selectedMap.remove(mBookList.get(position));
        }
        notifyDataSetChanged();
    }

    /**
     * 删除全部
     */
    public void delAll() {
        collPos.clear();
        mBookList.clear();
        selectedMap.clear();
        notifyDataSetChanged();
    }


    private OnSelectedLisenter mOnSelectedLisenter;


    public interface OnSelectedLisenter {
        void setOnSelected(List<BookShelfList.ListsBean> mList);
    }

    public void setOnSelectedLisenter(OnSelectedLisenter mOnSelectedLisenter) {
        this.mOnSelectedLisenter = mOnSelectedLisenter;
    }
}
