package com.application.sven.huinews.view.video;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.MovieDetailResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/21
 * effect: 错误状态view
 */
public class StatusView extends LinearLayout {
    private RecyclerView rv_line;
    private TextView tvMessage;
    private TextView btnAction;

    public StatusView(Context context) {
        this(context, null);
    }

    public StatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.player_status_view, this);
        rv_line = root.findViewById(R.id.rv_line);
        tvMessage = root.findViewById(R.id.message);
        btnAction = root.findViewById(R.id.status_btn);

        setClickable(true);


    }


    /**
     * 设置提示，
     *
     * @param msg
     */
    public void setMessage(String msg) {
        if (tvMessage != null) tvMessage.setText(msg);
    }

    /**
     * 设置按钮点击，
     *
     * @param text     线路 size == 1     重试，   线路 size >1   btnAction Gone
     * @param listener
     */
    public void setButtonTextAndAction(String text, OnClickListener listener) {
        if (btnAction != null) {
            btnAction.setText(text);
            btnAction.setOnClickListener(listener);
        }
    }

    /**
     * 设置线路
     *
     * @param mMovieDataAnalys
     */
    public void setLineRv(List<MovieDetailResponse.MovieDetailData.AnalyBean> mMovieDataAnalys) {
        LineAdapter adapter = new LineAdapter();
        int spanCount = 0;
        if (mMovieDataAnalys == null) {
            return;
        }

        spanCount = mMovieDataAnalys.size() <= 3 ? mMovieDataAnalys.size() : 3;
        if (spanCount == 1) {
            rv_line.setVisibility(GONE);
            btnAction.setVisibility(View.VISIBLE);
            setMessage(getContext().getResources().getString(R.string.player_error_message));
            setButtonTextAndAction(getContext().getResources().getString(R.string.player_retry), new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnErrorStatusLisenter != null) {
                        mOnErrorStatusLisenter.onRetry();
                    }
                }
            });
        } else {
            rv_line.setVisibility(View.VISIBLE);
            btnAction.setVisibility(View.GONE);
            setMessage(getContext().getResources().getString(R.string.player_error));
            GridLayoutManager manager = new GridLayoutManager(getContext(), spanCount);
            rv_line.setLayoutManager(manager);
            rv_line.setAdapter(adapter);
            adapter.setData(mMovieDataAnalys);
        }
    }

    public interface onErrorStatusLisenter {
        void swichLine(MovieDetailResponse.MovieDetailData.AnalyBean analy);

        void onRetry();
    }

    private onErrorStatusLisenter mOnErrorStatusLisenter;

    public void setOnErrorStatusLisenter(onErrorStatusLisenter mOnErrorStatusLisenter) {
        this.mOnErrorStatusLisenter = mOnErrorStatusLisenter;
    }

    class LineAdapter extends RecyclerView.Adapter {

        private List<MovieDetailResponse.MovieDetailData.AnalyBean> mMovieDataAnalys = new ArrayList<>();

        public void setData(List<MovieDetailResponse.MovieDetailData.AnalyBean> mMovieDataAnalys) {
            this.mMovieDataAnalys = mMovieDataAnalys;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.line_list_item, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            VH vh = (VH) holder;
            final MovieDetailResponse.MovieDetailData.AnalyBean analy = mMovieDataAnalys.get(position);
            vh.lint_num.setText("线路" + (position + 1));
            vh.lint_num.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnErrorStatusLisenter != null) {
                        mOnErrorStatusLisenter.swichLine(analy);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mMovieDataAnalys.size();
        }

        class VH extends RecyclerView.ViewHolder {
            TextView lint_num;

            public VH(View v) {
                super(v);
                lint_num = v.findViewById(R.id.tv_line);
            }
        }
    }
}
