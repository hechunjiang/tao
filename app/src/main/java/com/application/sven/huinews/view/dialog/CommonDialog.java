package com.application.sven.huinews.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.application.sven.huinews.R;

import org.w3c.dom.Text;

/**
 * auther: sunfuyi
 * data: 2018/5/12
 * effect: 公用dialog
 */
public class CommonDialog extends Dialog implements View.OnClickListener {
    private TextView tvMsg;
    private TextView btn_cancel;
    private TextView btn_enter;
    private int type;

    public CommonDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        initView();
    }

    public CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected CommonDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
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


    public void setDialogMsg(String message) {
        tvMsg.setText(message);
        show();
    }

    public void setPayOkInfo() {
        btn_cancel.setText("去抢先");
        btn_enter.setText("去阅读");
        tvMsg.setText("充值成功");
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
            mDialogClickLisenter.onEnter();
        }
    }

    public interface dialogClickLisenter {
        void onCancel();

        void onEnter();
    }

    private dialogClickLisenter mDialogClickLisenter;

    public void setDialogClickLisenter(dialogClickLisenter dialogClickLisenter) {
        this.mDialogClickLisenter = dialogClickLisenter;
    }


}
