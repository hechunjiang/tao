package com.application.sven.huinews.main.preemption.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.utils.glidUtils.GlideUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class TopBannerPagerAdapter extends PagerAdapter {
    private Context context;
    private List<MovieListResponse.MovieData.ListsBean> data = new ArrayList<>();

    public TopBannerPagerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MovieListResponse.MovieData.ListsBean> data) {
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
        final MovieListResponse.MovieData.ListsBean mData = data.get(position);

        View view = View.inflate(context, R.layout.type_top_banner_list_item, null);
        SimpleDraweeView imageView = view.findViewById(R.id.iv_thumb);
        TextView banner_title = view.findViewById(R.id.banner_title);
        TextView banner_dis = view.findViewById(R.id.banner_dis);
        imageView.setImageURI(mData.getM_img());
        banner_title.setText(mData.getM_name());
        banner_dis.setText(mData.getM_s_desc());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieDetailsActivity.toThis(context, mData.getId(),0,0);
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
