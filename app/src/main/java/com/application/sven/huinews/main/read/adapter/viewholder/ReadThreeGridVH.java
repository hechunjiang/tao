package com.application.sven.huinews.main.read.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.main.read.activity.BookStoreActivity;
import com.application.sven.huinews.main.read.adapter.BookAdapter;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.TimeUtils;
import com.application.sven.huinews.utils.itemDecoration.BookItemDecoration;
import com.application.sven.huinews.utils.itemDecoration.DividerItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;

import cn.iwgang.countdownview.CountdownView;

/**
 * auther: sunfuyi
 * data: 2018/7/9
 * effect:
 */
public class ReadThreeGridVH extends RecyclerView.ViewHolder {

    private Context mContext;
    private TextView item_name;
    private SimpleDraweeView item_icon;
    private RecyclerView book_rv;

    private TextView tv_right;
    private CountdownView mCountdownView;
    private BookAdapter mBookAdapter;
    private BookItemDecoration itemDecoration;
    private TimeUtils mTimeUtils;

    public ReadThreeGridVH(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        book_rv = itemView.findViewById(R.id.book_rv);
        item_name = itemView.findViewById(R.id.item_name);
        item_icon = itemView.findViewById(R.id.item_icon);
        mCountdownView = itemView.findViewById(R.id.countDownView);
        tv_right = itemView.findViewById(R.id.tv_right);

        mBookAdapter = new BookAdapter(mContext);
        itemDecoration = new BookItemDecoration(mContext);
        book_rv.setLayoutManager(new GridLayoutManager(mContext, 3));
        book_rv.removeItemDecoration(itemDecoration);
        book_rv.addItemDecoration(itemDecoration);
        book_rv.setAdapter(mBookAdapter);

        mTimeUtils = new TimeUtils();
    }


    public void setData(BookList mBookList) {
        item_name.setText(mBookList.getSub_name());
        FrescoUtil.loadDefImg(item_icon, mBookList.getIcon());
        mBookAdapter.setData(mBookList.getLists());

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
