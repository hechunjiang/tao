package com.application.sven.huinews.main.video.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.Comment;
import com.application.sven.huinews.main.home.activity.UserInfoActivity;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/11
 * effect: 评论列表适配器
 */
public class VideoCommentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Comment> mComments = new ArrayList<>();
    private Drawable likeDrawable, disLikeDrawable;

    public VideoCommentAdapter(Context mContext) {
        this.mContext = mContext;
        likeDrawable = mContext.getResources().getDrawable(R.mipmap.comment_liked);
        likeDrawable.setBounds(0, 0, likeDrawable.getMinimumWidth(), likeDrawable.getMinimumHeight());
        disLikeDrawable = mContext.getResources().getDrawable(R.mipmap.comment_like);
        disLikeDrawable.setBounds(0, 0, disLikeDrawable.getMinimumWidth(), disLikeDrawable.getMinimumHeight());
    }


    public void setData(List<Comment> mComment, boolean isRefresh) {
        if (mComment == null && mComment.size() <= 0) {
            return;
        }
        if (isRefresh) {
            this.mComments.clear();
        }
        this.mComments.addAll(mComment);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_list_item, parent, false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        final Comment mComment = mComments.get(position);
        FrescoUtil.loadDefImg(vh.user_head, mComment.getAvatar());
        vh.user_name.setText(mComment.getNickname());
        vh.tv_like.setText(CommonUtils.getLikeCount(mComment.getLike_count()));
        vh.tv_comment.setText(mComment.getContent());


        vh.tv_like.setCompoundDrawables(null, mComment.isIs_up() ? likeDrawable : disLikeDrawable, null, null);
        vh.tv_like.setTextColor(mContext.getResources().getColor(mComment.isIs_up() ? R.color.c_eb3e44 : R.color.color_line));

        vh.tv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mComment.isIs_up()) {
                    ToastUtils.showLong(mContext, "你已点过赞了");
                    return;
                }
                if (mComment.isIs_sure()) {
                    ToastUtils.showLong(mContext, "不能为自己点赞");
                    return;
                }

                mComment.setLike_count(mComment.getLike_count() + 1);
                mComment.setIs_up(!mComment.isIs_up());
                if (mOnLikeListener != null) {
                    mOnLikeListener.onLikeChange(mComment);
                }
                notifyItemChanged(position);
            }
        });


        vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnLongClickLisenter != null) {
                    mOnLongClickLisenter.onLongClick(mComment);
                }
                return false;
            }
        });


        vh.user_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.toThis(mContext, Integer.valueOf(mComment.getUser_id()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView user_head;
        TextView user_name;
        TextView tv_comment;
        TextView tv_like;

        public VH(View v) {
            super(v);
            user_head = v.findViewById(R.id.user_head);
            user_name = v.findViewById(R.id.user_name);
            tv_comment = v.findViewById(R.id.tv_comment);
            tv_like = v.findViewById(R.id.tv_like);
        }
    }


    //点赞
    public interface OnLikeListener {
        void onLikeChange(Comment comment);
    }

    private OnLikeListener mOnLikeListener;

    public void setOnLikeListener(OnLikeListener onLikeListener) {
        mOnLikeListener = onLikeListener;
    }


    //举报
    private OnLongClickLisenter mOnLongClickLisenter;

    public interface OnLongClickLisenter {
        void onLongClick(Comment comment);
    }

    public void setItemOnLongClickLisenter(OnLongClickLisenter mOnLongClickLisenter) {
        this.mOnLongClickLisenter = mOnLongClickLisenter;
    }
}
