package com.application.sven.huinews.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.application.sven.huinews.R;

/**
 * auther: sunfuyi
 * data: 2018/5/28
 * effect:
 */
public class SearchDelDialog extends Dialog implements View.OnClickListener {
    private TextView tvMsg;
    private TextView btn_cancel;
    private TextView btn_enter;
    private int type;
    private int position;

    public SearchDelDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        initView();
    }

    public SearchDelDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected SearchDelDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_common, null);
        tvMsg = view.findViewById(R.id.dialog_msg);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_enter = view.findViewById(R.id.btn_enter);
        setContentView(view);


        setCanceledOnTouchOutside(true);
        btn_cancel.setOnClickListener(this);
        btn_enter.setOnClickListener(this);

    }


    public void setSearchMsg(String message, int type, int posiiton) {
        this.type = type;
        this.position = posiiton;
        tvMsg.setText(message);
        show();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (mDialogClickLisenter == null) {
            return;
        }
        if (id == R.id.btn_cancel) {
            mDialogClickLisenter.onCancel();
        } else if (id == R.id.btn_enter) {
            mDialogClickLisenter.onEnter(type, position);
        }
    }

    public interface dialogClickLisenter {
        void onCancel();

        void onEnter(int type,int posiiton);
    }

    private dialogClickLisenter mDialogClickLisenter;

    public void setDialogClickLisenter(dialogClickLisenter dialogClickLisenter) {
        this.mDialogClickLisenter = dialogClickLisenter;
    }


}
