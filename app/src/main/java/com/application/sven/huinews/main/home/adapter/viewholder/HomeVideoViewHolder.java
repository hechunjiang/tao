package com.application.sven.huinews.main.home.adapter.viewholder;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.main.home.activity.UserInfoActivity;
import com.application.sven.huinews.main.video.activity.VerticalVideoActivity;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ScreensUitls;
import com.application.sven.huinews.utils.glidUtils.GlideUtils;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by sfy. on 2018/5/9 0009.
 */

public class HomeVideoViewHolder extends RecyclerView.ViewHolder {
    Context mContext;
    RelativeLayout gv_ll;
    public SimpleDraweeView iv_thumb;
    TextView tv_show, tv_content, user_name;
    public SimpleDraweeView user_head;

    public HomeVideoViewHolder(Context mContext, View v) {
        super(v);
        this.mContext = mContext;
        iv_thumb = v.findViewById(R.id.iv_thumb);
        gv_ll = v.findViewById(R.id.gv_ll);
        tv_show = v.findViewById(R.id.tv_show);
        tv_content = v.findViewById(R.id.tv_content);
        user_head = v.findViewById(R.id.user_head);
        user_name = v.findViewById(R.id.user_name);
    }

    public void setSmallVideoInfo(final VideoListResponse.VideoList.ListBean videoInfo) {
        setImageParams(videoInfo.getVideo_width(), videoInfo.getVideo_height(), iv_thumb);
        iv_thumb.setImageURI(Uri.parse(videoInfo.getVideo_cover()));
        user_head.setImageURI(Uri.parse(videoInfo.getUser_avatar()));


        tv_show.setText(videoInfo.getPlay_count());
        tv_content.setText(videoInfo.getTitle());
        user_name.setText(videoInfo.getUser_nickname());

        user_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.toThis(mContext, videoInfo.getUser_id());
            }
        });

        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.toThis(mContext, videoInfo.getUser_id());
            }
        });
    }

    private void setImageParams(int width, int height, SimpleDraweeView iv) {
        int scteenWidth = ScreensUitls.getScreenWidth(mContext);
        int screenHeight = ScreensUitls.getScreenHeight(mContext);

        int mHeight = screenHeight / 2;
        ViewGroup.LayoutParams para = iv.getLayoutParams();
        para.height = mHeight;
        iv.setLayoutParams(para);
    }
}
