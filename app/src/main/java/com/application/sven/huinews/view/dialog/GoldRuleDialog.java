package com.application.sven.huinews.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.main.web.activity.WebActivity;
import com.application.sven.huinews.utils.update.VersionInfo;

/**
 * auther: sunfuyi
 * data: 2018/6/6
 * effect:
 */
public class GoldRuleDialog extends Dialog {
    private TextView tv_conut;
    private TextView btn_cancel;
    private TextView btn_enter;
    private OnDialogEnterListener mOnDialogEnterListener;

    public GoldRuleDialog(@NonNull Context context) {
        super(context, R.style.dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_gold_rule);
        initView();
    }

    private void initView() {
        tv_conut = findViewById(R.id.tv_conut);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_enter = findViewById(R.id.btn_enter);

        int watchTime = UserSpCache.getInstance(getContext()).getInt(UserSpCache.WATCH_TIME);
        tv_conut.setText(String.format(getContext().getString(R.string.zmz_info), watchTime));
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mOnDialogEnterListener!=null){
                    mOnDialogEnterListener.toWeb();
                }
                dismiss();
            }
        });
    }

    public interface OnDialogEnterListener {
        void toWeb();
    }

    public void setOnDialogEnterListener(OnDialogEnterListener mOnDialogEnterListener) {
        this.mOnDialogEnterListener = mOnDialogEnterListener;
    }

}
