package com.application.sven.huinews.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.sven.huinews.R;

/**
 * auther: sunfuyi
 * data: 2018/8/2
 * effect:
 */
public class GiveGoldDialog extends Dialog {
    private ImageView iv_one_dollar;
    private ImageButton btn_cancel;
    private TextView tv_gold_count;

    public GiveGoldDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        setContentView(R.layout.give_gold_dialog);

        iv_one_dollar = findViewById(R.id.iv_one_dollar);
        btn_cancel = findViewById(R.id.btn_cancel);
        tv_gold_count = findViewById(R.id.tv_gold_count);

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

    public void setGold(int count) {
        tv_gold_count.setText(String.format(getContext().getResources().getString(R.string.give_gold), count));
        show();
    }

    public interface OnNewsPersonalListener {
        void onOpen();
    }

    private OnNewsPersonalListener mOnNewsPersonalListener;

    public void setOnNewsPersonalListener(OnNewsPersonalListener onNewsPersonalListener) {
        this.mOnNewsPersonalListener = onNewsPersonalListener;
    }
}
