package com.application.sven.huinews.main.read.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.request.BookContentRequest;
import com.application.sven.huinews.entity.response.BookList;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.main.read.activity.BookDetailsActivity;
import com.application.sven.huinews.main.read.activity.BookReadActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/10
 * effect:
 */
public class BannerAdapter extends PagerAdapter {
    private Context context;
    private List<BookList.ListsBean> data = new ArrayList<>();

    public BannerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BookList.ListsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final BookList.ListsBean mData = data.get(position);

        View view = View.inflate(context, R.layout.type_top_banner_list_item, null);
        SimpleDraweeView imageView = view.findViewById(R.id.iv_thumb);
        TextView banner_title = view.findViewById(R.id.banner_title);
        TextView banner_dis = view.findViewById(R.id.banner_dis);
        imageView.setImageURI(mData.getPic());
        banner_title.setVisibility(View.GONE);
        banner_dis.setVisibility(View.GONE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookDetailsActivity.toThis(context, mData.getId());
            }
        });


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
