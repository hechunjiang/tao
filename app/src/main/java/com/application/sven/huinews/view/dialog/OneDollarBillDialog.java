package com.application.sven.huinews.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.application.sven.huinews.R;

/**
 * auther: sunfuyi
 * data: 2018/6/2
 * effect: 新用户打开一元提现功能
 */
public class OneDollarBillDialog extends Dialog {
    private ImageView iv_one_dollar;
    private ImageButton btn_cancel;

    public OneDollarBillDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        setContentView(R.layout.one_dollar_bill_dialog);

        iv_one_dollar = findViewById(R.id.iv_one_dollar);
        btn_cancel = findViewById(R.id.btn_cancel);

        iv_one_dollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mOnNewsPersonalListener != null) {
                    mOnNewsPersonalListener.onOpen();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OnNewsPersonalListener {
        void onOpen();
    }

    private OnNewsPersonalListener mOnNewsPersonalListener;

    public void setOnNewsPersonalListener(OnNewsPersonalListener onNewsPersonalListener) {
        this.mOnNewsPersonalListener = onNewsPersonalListener;
    }


}
