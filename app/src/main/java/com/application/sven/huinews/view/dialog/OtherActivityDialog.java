package com.application.sven.huinews.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.utils.FrescoUtil;

/**
 * auther: sunfuyi
 * data: 2018/6/2
 * effect:
 */
public class OtherActivityDialog extends Dialog implements View.OnClickListener {


    ImageView btn_cancel;
    ImageView iv_centent;

    Handler handler = new Handler();

    public OtherActivityDialog(@NonNull Context context) {
        super(context, R.style.my_dialog);
        setContentView(R.layout.dialog_other_activity);
        btn_cancel = findViewById(R.id.btn_cancel);
        iv_centent = findViewById(R.id.iv_centent);
        btn_cancel.setOnClickListener(this);
        iv_centent.setOnClickListener(this);
    }

    public void showDialog(Drawable drawable) {
        iv_centent.setImageDrawable(drawable);

        try {
            show();
        } catch (Exception e) {

        }
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setCancelable(true);
                setCanceledOnTouchOutside(true);
            }
        }, 3000);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_cancel) {
            dismiss();
        } else if (id == R.id.iv_centent) {
            dismiss();
            if (mOnOpenBagListener != null) {
                mOnOpenBagListener.onOpen();
            }
        }
    }


    public interface OnOpenBagListener {
        void onOpen();
    }

    private OnOpenBagListener mOnOpenBagListener;

    public void setOnOpenBagListener(OnOpenBagListener onOpenBagListener) {
        mOnOpenBagListener = onOpenBagListener;
    }
}
