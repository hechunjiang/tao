package com.application.sven.huinews.main.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.FollowList;
import com.application.sven.huinews.entity.response.FollowListResponse;
import com.application.sven.huinews.main.home.activity.UserInfoActivity;
import com.application.sven.huinews.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/10
 * effect:
 */
public class FollowAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<FollowList> mDatas = new ArrayList<>();

    public FollowAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<FollowList> mDatas, boolean isRefresh) {
        if (mDatas == null && mDatas.size() < 0) {
            return;
        }
        if (isRefresh) {
            this.mDatas.clear();
        }

        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.follow_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        final FollowList mData = mDatas.get(position);
        vh.tv_name.setText(mData.getNickname());
        FrescoUtil.loadDefImg(vh.user_head, mData.getHeadimg());


        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.toThis(mContext, mData.getUser_id());
            }
        });
        vh.btn_cancel_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnCancelFollowLisenter != null) {
                    mOnCancelFollowLisenter.onCancel(mData.getUser_id());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView user_head;
        TextView tv_name;
        TextView btn_cancel_follow;

        public VH(View v) {
            super(v);
            user_head = v.findViewById(R.id.user_head);
            tv_name = v.findViewById(R.id.tv_name);
            btn_cancel_follow = v.findViewById(R.id.btn_cancel_follow);
        }

    }

    public interface OnCancelFollowLisenter {
        void onCancel(int user_id);
    }

    private OnCancelFollowLisenter mOnCancelFollowLisenter;

    public void setOnCancelFollowLisenter(OnCancelFollowLisenter mOnCancelFollowLisenter) {
        this.mOnCancelFollowLisenter = mOnCancelFollowLisenter;
    }
}
