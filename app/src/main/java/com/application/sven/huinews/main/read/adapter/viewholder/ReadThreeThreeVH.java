package com.application.sven.huinews.main.read.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.main.read.activity.BookStoreActivity;
import com.application.sven.huinews.main.read.adapter.BookAdapter;
import com.application.sven.huinews.main.read.adapter.VBookAdapter;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.TimeUtils;
import com.application.sven.huinews.utils.itemDecoration.BookItemDecoration;
import com.application.sven.huinews.utils.itemDecoration.DividerItemDecoration;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * auther: sunfuyi
 * data: 2018/7/9
 * effect:
 */
public class ReadThreeThreeVH extends RecyclerView.ViewHolder {
    private Context mContext;
    private TextView item_name;
    private SimpleDraweeView item_icon;
    private RecyclerView h_rv, v_rv;
    private TextView tv_right;
    private CountdownView mCountdownView;
    private BookAdapter mHAdapter;
    private VBookAdapter mVAdapter;
    private BookItemDecoration itemDecoration;

    private TimeUtils mTimeUtils;
    public ReadThreeThreeVH(Context mContext, View itemView) {
        super(itemView);
        this.mContext = mContext;
        h_rv = itemView.findViewById(R.id.h_rv);
        v_rv = itemView.findViewById(R.id.v_rv);
        item_name = itemView.findViewById(R.id.item_name);
        item_icon = itemView.findViewById(R.id.item_icon);

        mCountdownView = itemView.findViewById(R.id.countDownView);
        tv_right = itemView.findViewById(R.id.tv_right);

        mVAdapter = new VBookAdapter(mContext);
        mHAdapter = new BookAdapter(mContext);
        itemDecoration = new BookItemDecoration(mContext);
        h_rv.setLayoutManager(new GridLayoutManager(mContext, 3));
        h_rv.removeItemDecoration(itemDecoration);
        h_rv.addItemDecoration(itemDecoration);
        h_rv.setAdapter(mHAdapter);

        v_rv.setLayoutManager(new LinearLayoutManager(mContext));
        // v_rv.removeItemDecoration(itemDecoration);
        //v_rv.addItemDecoration(itemDecoration);
        v_rv.setAdapter(mVAdapter);
        mTimeUtils = new TimeUtils();
    }

    public void setData(BookList mBookList) {
        List<BookList.ListsBean> hBookList = new ArrayList<>();
        List<BookList.ListsBean> vBookList = new ArrayList<>();
        for (int i = 0; i < mBookList.getLists().size(); i++) {
            if (i > 2) {
                vBookList.add(mBookList.getLists().get(i));
            } else {
                hBookList.add(mBookList.getLists().get(i));
            }
        }
        mHAdapter.setData(hBookList);
        mVAdapter.setDatas(vBookList);

        item_name.setText(mBookList.getSub_name());
        FrescoUtil.loadDefImg(item_icon, mBookList.getIcon());

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
