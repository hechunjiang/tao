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
 * data: 2018/6/2
 * effect:
 */
public class PersonLoginDialog extends Dialog {
    private TextView btnLogin;

    public PersonLoginDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        initView();
    }

    public PersonLoginDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    protected PersonLoginDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.person_login_dialog, null);
        setContentView(v);
        setCanceledOnTouchOutside(true);

        btnLogin = v.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onLoginLisenter != null) {
                    onLoginLisenter.toLogin();
                }
            }
        });
    }


    public interface OnLoginLisenter {
        void toLogin();
    }

    private OnLoginLisenter onLoginLisenter;

    public void setOnLoginLisenter(OnLoginLisenter l) {
        this.onLoginLisenter = l;
    }
}
