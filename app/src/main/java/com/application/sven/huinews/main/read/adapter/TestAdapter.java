package com.application.sven.huinews.main.read.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/10
 * effect:
 */
public class TestAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<BookList.ListsBean> mDatas = new ArrayList<>();

    public TestAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
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


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH vh = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.book_list_item, parent, false);
            vh = new VH();
            vh.iv_book = convertView.findViewById(R.id.iv_book);
            vh.tv_name = convertView.findViewById(R.id.tv_book_name);
            convertView.setTag(vh);
        } else {
            vh = (VH) convertView.getTag();
        }

        vh.tv_name.setText(mDatas.get(position).getTitle());
        FrescoUtil.loadDefImg(vh.iv_book, mDatas.get(position).getPic());
        return convertView;
    }

    class VH {
        SimpleDraweeView iv_book;
        TextView tv_name;
    }
}
