package com.application.sven.huinews.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.application.sven.huinews.R;

/**
 * auther: sunfuyi
 * data: 2018/7/24
 * effect:
 */
public class MovieShareDialog extends Dialog {

    private ImageView iv;
    private ImageButton btn_cancel;

    public MovieShareDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        setContentView(R.layout.movie_share_dialog);
        iv = findViewById(R.id.iv);
        btn_cancel = findViewById(R.id.btn_cancel);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMovieShareOnClickLisenter!=null){
                    onMovieShareOnClickLisenter.onShare();
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


    public interface OnMovieShareOnClickLisenter {
        void onShare();
    }

    private OnMovieShareOnClickLisenter onMovieShareOnClickLisenter;

    public void setOnOpenBagListener(OnMovieShareOnClickLisenter onMovieShareOnClickLisenter) {
        this.onMovieShareOnClickLisenter = onMovieShareOnClickLisenter;
    }
}
