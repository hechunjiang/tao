package com.application.sven.huinews.main.read.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.sven.huinews.R;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.main.read.adapter.viewholder.ReadOneGridVH;
import com.application.sven.huinews.main.read.adapter.viewholder.ReadOneThreeVH;
import com.application.sven.huinews.main.read.adapter.viewholder.ReadThreeGridVH;
import com.application.sven.huinews.main.read.adapter.viewholder.ReadThreeThreeVH;
import com.application.sven.huinews.main.read.adapter.viewholder.ReadTopVH;
import com.application.sven.huinews.main.read.adapter.viewholder.ReadTwoGridVH;
import com.application.sven.huinews.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/9
 * effect:
 */
public class ReadAdapter extends RecyclerView.Adapter {
    public static final int TYPE_TOP_BANNER = 1;
    public static final int TYPE_ONE_THREE = 2;
    public static final int TYPE_THREE_GRID = 3;
    public static final int TYPE_THREE_THREE = 4;
    public static final int TYPE_TWO_GRID = 5;
    public static final int TYPE_ONE_LIST = 6;

    private Context mContext;
    private LayoutInflater mInflater;
    private List<BookList> mDatas = new ArrayList<>();

    public ReadAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<BookList> mDatas) {
        if (mDatas == null && mDatas.size() == 0) {
            return;
        }
        this.mDatas.clear();

        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    public void setData(List<BookList> mDatas, boolean isRefresh) {
        if (mDatas == null && mDatas.size() == 0) {
            return;
        }
        if (isRefresh) {
            this.mDatas.clear();
        }

        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View typeView;
        switch (viewType) {
            case TYPE_TOP_BANNER:
                typeView = mInflater.inflate(R.layout.read_top_view, parent, false);
                holder = new ReadTopVH(mContext, typeView);
                break;
            case TYPE_ONE_THREE:
                typeView = mInflater.inflate(R.layout.read_one_three_list_item, parent, false);
                holder = new ReadOneThreeVH(mContext, typeView);
                break;
            case TYPE_THREE_GRID:
                typeView = mInflater.inflate(R.layout.read_three_grid_list_item, parent, false);
                holder = new ReadThreeGridVH(mContext, typeView);
                break;
            case TYPE_THREE_THREE:
                typeView = mInflater.inflate(R.layout.read_thee_three_list_item, parent, false);
                holder = new ReadThreeThreeVH(mContext, typeView);
                break;
            case TYPE_TWO_GRID:
                typeView = mInflater.inflate(R.layout.read_three_grid_list_item, parent, false);
                holder = new ReadTwoGridVH(mContext, typeView);
                break;
            case TYPE_ONE_LIST:
                typeView = mInflater.inflate(R.layout.read_three_grid_list_item, parent, false);
                holder = new ReadOneGridVH(mContext, typeView);
                break;

        }

        return holder;
    }

    ReadTopVH mReadTopVH;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookList mBookList = mDatas.get(position);
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_TOP_BANNER:
                if (mReadTopVH==null){
                    mReadTopVH = (ReadTopVH) holder;
                }

                mReadTopVH.setData(mBookList);
                break;
            case TYPE_ONE_THREE:
                ReadOneThreeVH mReadOneThreeVH = (ReadOneThreeVH) holder;
                mReadOneThreeVH.setData(mBookList);
                break;
            case TYPE_THREE_GRID:
                ReadThreeGridVH mReadThreeGridVH = (ReadThreeGridVH) holder;
                mReadThreeGridVH.setData(mBookList);
                break;
            case TYPE_THREE_THREE:
                ReadThreeThreeVH mReadThreeThreeVH = (ReadThreeThreeVH) holder;
                mReadThreeThreeVH.setData(mBookList);
                break;
            case TYPE_TWO_GRID:
                ReadTwoGridVH mReadTwoGridVH = (ReadTwoGridVH) holder;
                mReadTwoGridVH.setData(mBookList);
                break;
            case TYPE_ONE_LIST:
                ReadOneGridVH mReadOneGridVH = (ReadOneGridVH) holder;
                mReadOneGridVH.setData(mBookList);
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {
        int type = getItemDataType(mDatas.get(position).getLayout());
        return type;
    }

    /**
     * 获取布局信息
     *
     * @param str
     * @return
     */
    private int getItemDataType(String str) {
        int type = 0;
        if (str.equals(Constant.TYPE_X_TOP_BANNER)) {
            type = TYPE_TOP_BANNER;
        } else if (str.equals(Constant.TYPE_X_LEFT_L)) {
            type = TYPE_ONE_THREE;
        } else if (str.equals(Constant.TYPE_X_S_GRID)) {
            type = TYPE_THREE_GRID;
        } else if (str.equals(Constant.TYPE_X_RIGHT_T)) {
            type = TYPE_THREE_THREE;
        } else if (str.equals(Constant.TYPE_X_B_GRID)) {
            type = TYPE_TWO_GRID;
        } else if (str.equals(Constant.TYPE_X_LIST)) {
            type = TYPE_ONE_LIST;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void onDestoryHandler() {
        if (mReadTopVH != null) {
            mReadTopVH.onDestroyHandler();
        }
    }

    public void onStartHandler(){
        if (mReadTopVH!=null){
            mReadTopVH.onStartHandler();
        }
    }
}
