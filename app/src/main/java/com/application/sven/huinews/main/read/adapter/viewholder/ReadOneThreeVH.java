package com.application.sven.huinews.main.read.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.main.read.activity.BookDetailsActivity;
import com.application.sven.huinews.main.read.activity.BookStoreActivity;
import com.application.sven.huinews.main.read.adapter.BookAdapter;
import com.application.sven.huinews.main.read.adapter.TestAdapter;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.TimeUtils;
import com.application.sven.huinews.utils.itemDecoration.BookItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;

import cn.iwgang.countdownview.CountdownView;

/**
 * auther: sunfuyi
 * data: 2018/7/9
 * effect:
 */
public class ReadOneThreeVH extends RecyclerView.ViewHolder {
    private Context mContext;
    private TextView item_name, tv_book_name, tv_book_dis, tv_auther_name, tv_book_type;
    private LinearLayout ll;
    private SimpleDraweeView iv_book, item_icon;
    private RecyclerView book_rv;
    private BookAdapter mBookAdapter;
    private TextView tv_right;
    private CountdownView mCountdownView;
    private BookItemDecoration itemDecoration;
    private BookList.ListsBean mData;
    private TestAdapter mAdapter;
    private GridView gv;
    private TimeUtils mTimeUtils;

    public ReadOneThreeVH(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        book_rv = itemView.findViewById(R.id.book_rv);
        // gv = itemView.findViewById(R.id.gv);
        item_name = itemView.findViewById(R.id.item_name);
        item_icon = itemView.findViewById(R.id.item_icon);
        iv_book = itemView.findViewById(R.id.iv_book);
        tv_book_name = itemView.findViewById(R.id.tv_book_name);
        tv_book_dis = itemView.findViewById(R.id.tv_book_dis);
        tv_auther_name = itemView.findViewById(R.id.tv_auther_name);
        tv_book_type = itemView.findViewById(R.id.tv_book_type);
        mCountdownView = itemView.findViewById(R.id.countDownView);
        tv_right = itemView.findViewById(R.id.tv_right);
        ll = itemView.findViewById(R.id.ll);
        mBookAdapter = new BookAdapter(mContext);
        itemDecoration = new BookItemDecoration(mContext);

        book_rv.setLayoutManager(new GridLayoutManager(mContext, 3));
        book_rv.removeItemDecoration(itemDecoration);
        book_rv.addItemDecoration(itemDecoration);
        book_rv.setAdapter(mBookAdapter);
/*
        mAdapter = new TestAdapter(mContext);
        gv.setAdapter(mAdapter);*/
        mTimeUtils = new TimeUtils();

    }

    public void setData(BookList mBookList) {
        this.mData = mBookList.getLists().get(0);
        mBookAdapter.setLastThreeData(mBookList.getLists());
        //  mAdapter.setLastThreeData(mBookList.getLists());
        FrescoUtil.loadDefImg(item_icon, mBookList.getIcon());
        FrescoUtil.loadDefImg(iv_book, mData.getPic());
        item_name.setText(mBookList.getSub_name());
        tv_book_name.setText(mData.getTitle());
        tv_book_dis.setText(mData.getDescription());
        tv_auther_name.setText(mData.getAuthor());
        tv_book_type.setText(mData.getCay_name());
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailsActivity.toThis(mContext,mData.getId());
            }
        });

        if (mBookList.getShow_more_type().equals("time")) {
            mCountdownView.start(mTimeUtils.countDownTime(mBookList.getNow_time()));
            mCountdownView.setVisibility(View.VISIBLE);
            tv_right.setVisibility(View.GONE);
        } else {
            mCountdownView.setVisibility(View.GONE);
            tv_right.setVisibility(View.VISIBLE);
        }

        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookStoreActivity.toThis(mContext);
            }
        });

    }
}
