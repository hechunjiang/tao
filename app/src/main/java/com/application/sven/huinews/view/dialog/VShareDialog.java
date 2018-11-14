package com.application.sven.huinews.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.config.Constant;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect: 分享弹窗
 */
public class VShareDialog extends Dialog implements View.OnClickListener {
    private TextView wechatShare, wechatZoomShare, qqShare, qqZoomShare, reportTv, dialog_like, dialog_shouchang, dialog_report_tv;
    private Button cancleBtn;
    private View report_line;
    private LinearLayout report_ll;
    private Context mContext;
    private boolean isLiked, isCollection;

    public VShareDialog(@NonNull Context context) {
        super(context, R.style.my_dialog);
        this.mContext = context;
        setContentView(R.layout.v_diaog_share);
        Window mWindow = getWindow();
        WindowManager.LayoutParams mParams = mWindow.getAttributes();
        mParams.gravity = Gravity.BOTTOM;
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindow.setAttributes(mParams);

        initView();
    }

    private void initView() {
        wechatShare = findViewById(R.id.dialog_share_wechat_tv);
        wechatZoomShare = findViewById(R.id.dialog_share_wechat_zoom_tv);
        qqShare = findViewById(R.id.dialog_share_qq_tv);
        qqZoomShare = findViewById(R.id.dialog_share_qq_zoom_tv);
        cancleBtn = findViewById(R.id.dialog_share_cancel_btn);
        reportTv = findViewById(R.id.dialog_report_tv);
        report_line = findViewById(R.id.report_line);
        report_ll = findViewById(R.id.report_ll);
        dialog_like = findViewById(R.id.dialog_like);
        dialog_shouchang = findViewById(R.id.dialog_shouchang);
        dialog_report_tv = findViewById(R.id.dialog_report_tv);

        wechatShare.setOnClickListener(this);
        wechatZoomShare.setOnClickListener(this);
        qqShare.setOnClickListener(this);
        qqZoomShare.setOnClickListener(this);
        cancleBtn.setOnClickListener(this);
        reportTv.setOnClickListener(this);
        dialog_report_tv.setOnClickListener(this);
        dialog_like.setOnClickListener(this);
        dialog_shouchang.setOnClickListener(this);
    }

    public void hideReport() {
        reportTv.setVisibility(View.GONE);
        report_ll.setVisibility(View.GONE);
        report_line.setVisibility(View.GONE);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_share_wechat_zoom_tv:
                setShare(Constant.SHARE_TYPE_WECHAT_ZOOM);
                break;
            case R.id.dialog_share_wechat_tv:
                setShare(Constant.SHARE_TYPE_WECHAT);
                break;
            case R.id.dialog_share_qq_zoom_tv:
                setShare(Constant.SHARE_TYPE_QQ_ZOOM);
                break;
            case R.id.dialog_share_qq_tv:
                setShare(Constant.SHARE_TYPE_QQ);
                break;
            case R.id.dialog_report_tv:
                setShare(Constant.SHARE_TYPE_REPORT);
                break;
            case R.id.dialog_share_cancel_btn:
                dismiss();
                break;
            case R.id.dialog_shouchang:
                if (mOnDialogLisenter != null) {
                    isCollection = !isCollection;
                    dialog_shouchang.setCompoundDrawables(null, isCollection ? collectioned : collection, null, null);

                    mOnDialogLisenter.onCollection(isCollection);
                }
                break;
            case R.id.dialog_like:
                if (mOnDialogLisenter != null) {
                    isLiked = !isLiked;
                    dialog_like.setCompoundDrawables(null, isLiked ? liked : like, null, null);
                    mOnDialogLisenter.onLike(isLiked);
                }
                break;


        }
    }

    private void setShare(int type) {
        dismiss();
        if (mOnShareListener != null) {
            mOnShareListener.onShare(type);
        }
    }


    Drawable like, liked, collection, collectioned;

    public void setVideoShare(boolean isLiked, boolean isCollectioned) {
        this.isCollection = isCollectioned;
        this.isLiked = isLiked;
        like = mContext.getResources().getDrawable(R.mipmap.dialog_like);
        like.setBounds(0, 0, like.getMinimumWidth(), like.getMinimumHeight());
        liked = mContext.getResources().getDrawable(R.mipmap.dialog_liked);
        liked.setBounds(0, 0, liked.getMinimumWidth(), liked.getMinimumHeight());

        collection = mContext.getResources().getDrawable(R.mipmap.dialog_shouchang);
        collection.setBounds(0, 0, collection.getMinimumWidth(), collection.getMinimumHeight());
        collectioned = mContext.getResources().getDrawable(R.mipmap.dialog_shouchang_cli);
        collectioned.setBounds(0, 0, collectioned.getMinimumWidth(), collectioned.getMinimumHeight());

        dialog_shouchang.setCompoundDrawables(null, isCollectioned ? collectioned : collection, null, null);
        dialog_like.setCompoundDrawables(null, isLiked ? liked : like, null, null);

        show();
      /*  mData.setIs_up(!mData.isIs_up());
        mData.setLike_count(mData.isIs_up() ? mData.getLike_count() + 1 : mData.getLike_count() - 1);
        homeOneBigViewHolder.btn_like.setCompoundDrawables(mData.isIs_up() ? mLikeCheck : mLikeNormal, null, null, null);
        homeOneBigViewHolder.btn_like.setTextColor(mData.isIs_up() ? mContext.getResources().getColor(R.color.c_eb3e44) : mContext.getResources().getColor(R.color.color_line));
*/
    }

    public interface OnShareListener {
        void onShare(int type);
    }

    private OnShareListener mOnShareListener;

    public void setOnShareListener(OnShareListener onShareListener) {
        mOnShareListener = onShareListener;
    }

    private OnDialogLisenter mOnDialogLisenter;

    public interface OnDialogLisenter {
        void onCollection(boolean isCollection);

        void onLike(boolean isLiked);
    }

    public void OnDialogLisenter(OnDialogLisenter mOnDialogLisenter) {
        this.mOnDialogLisenter = mOnDialogLisenter;
    }

}
