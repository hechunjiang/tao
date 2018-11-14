package com.application.sven.huinews.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.application.sven.huinews.R;

/**
 * auther: sunfuyi
 * data: 2018/6/1
 * effect:
 */
public class GoldComeDialog extends Dialog {
    private TextView mGoldCountTv;

    public GoldComeDialog(@NonNull Context context) {
        super(context, R.style.my_dialog);
        setContentView(R.layout.activity_dialog_gold);
        mGoldCountTv = findViewById(R.id.dialog_gold_count_tv);
    }

    public void showDialog(int count) {
        mGoldCountTv.setText(count + "");
        show();
    }
}
