package com.application.sven.huinews.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.main.login.activity.RegisterActivity;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:登录提示
 */
public class LoginTipDialog extends Dialog implements View.OnClickListener {
    private TextView btn_reg;
    private ImageButton btn_close;

    public LoginTipDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        initView();
    }

    public LoginTipDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected LoginTipDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_login_tip, null);
        btn_reg = view.findViewById(R.id.btn_reg);
        btn_close = view.findViewById(R.id.btn_close);
        setContentView(view);
        setCanceledOnTouchOutside(true);

        btn_reg.setOnClickListener(this);
        btn_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_reg) {
            RegisterActivity.toThis(getContext(), false);
        } else if (id == R.id.btn_close) {
            dismiss();
        }
    }
}
