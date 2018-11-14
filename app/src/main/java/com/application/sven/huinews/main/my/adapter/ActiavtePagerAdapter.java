package com.application.sven.huinews.main.my.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.ActiavtePageResponse;
import com.application.sven.huinews.main.web.activity.WebActivity;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.glidUtils.GlideUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class ActiavtePagerAdapter extends PagerAdapter {
    private Context context;

    private List<ActiavtePageResponse.Data> mList;

    public ActiavtePagerAdapter(Context context, List<ActiavtePageResponse.Data> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //getView
        View view = View.inflate(context, R.layout.mine_active_pager_list_item, null);
        SimpleDraweeView imageView = view.findViewById(R.id.sd_view);
        FrescoUtil.loadDefImg(imageView, mList.get(position).getImages());
        //添加到容器中
        container.addView(view);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebActivity.toThis(context, mList.get(position).getJump_url());
            }
        });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}

