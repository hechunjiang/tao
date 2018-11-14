package com.application.sven.huinews.main.read.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.BookStoreList;
import com.application.sven.huinews.main.read.activity.BookDetailsActivity;
import com.application.sven.huinews.utils.FrescoUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/27
 * effect:
 */
public class BookStoreAdapter extends BaseQuickAdapter<BookStoreList.Book, BaseViewHolder> {
    public BookStoreAdapter(int layoutResId, @Nullable List<BookStoreList.Book> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final BookStoreList.Book item) {

        SimpleDraweeView iv_book = helper.getView(R.id.iv_book);
        FrescoUtil.loadDefImg(iv_book, item.getPic());

        helper.setText(R.id.tv_book_name, item.getTitle());
        helper.setText(R.id.tv_auther_name, item.getAuthor());
        helper.setText(R.id.tv_book_dis, item.getDescription());
        helper.setText(R.id.tv_book_type, item.getCay_name());


        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailsActivity.toThis(mContext, item.getId());
            }
        });
    }
}
