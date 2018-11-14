package com.application.sven.huinews.main.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.entity.request.UserMsgRequest;
import com.application.sven.huinews.entity.request.UserVideoRequest;
import com.application.sven.huinews.entity.response.UserInfoMsg;
import com.application.sven.huinews.entity.response.UserVideoResponse;
import com.application.sven.huinews.main.home.adapter.UserInfoVideoAdapter;

import com.application.sven.huinews.main.home.contract.UserMsgContract;
import com.application.sven.huinews.main.home.model.UserMsgModel;
import com.application.sven.huinews.main.home.presenter.UserMsgPresenter;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.MeasureUtlis;
import com.application.sven.huinews.utils.statusbar.Eyes;
import com.application.sven.huinews.view.RefreshLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * auther: sunfuyi
 * data: 2018/5/10
 * effect: 用户信息和用户发布的视频列表
 */
public class UserInfoActivity extends BaseActivity<UserMsgPresenter, UserMsgModel> implements NestedScrollView.OnScrollChangeListener, UserMsgContract.View {
    private NestedScrollView scrollview;
    private RefreshLayout refresh_view;
    private ImageButton btn_back;
    private RecyclerView rv;
    private RelativeLayout head_view;
    private RelativeLayout title_view;
    private TextView tv_title;
    private TextView user_name;
    private TextView btn_follow;
    private SimpleDraweeView user_head;
    private View lineView;
    private int userId;
    private UserInfoMsg.UserInfo userInfo;

    private UserInfoVideoAdapter mAdapter;

    public static void toThis(Context mContext, int userId) {
        Intent intent = new Intent(mContext, UserInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.BUNLDE_USER_ID, userId);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        Eyes.setStatusBarColor(this, getResources().getColor(R.color.c_313131));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userId = bundle.getInt(Constant.BUNLDE_USER_ID);
        }
        return R.layout.activity_userinfo;
    }

    @Override
    public void initView() {
        rv = findViewById(R.id.rv);
        btn_back = findViewById(R.id.btn_back);
        scrollview = findViewById(R.id.scrollview);
        head_view = findViewById(R.id.head_view);
        title_view = findViewById(R.id.title_view);
        tv_title = findViewById(R.id.tv_title);
        lineView = findViewById(R.id.title_line);
        user_head = findViewById(R.id.user_head);
        user_name = findViewById(R.id.user_name);
        btn_follow = findViewById(R.id.btn_follow);
        refresh_view = findViewById(R.id.refresh_view);


    }

    @Override
    public void initEvents() {
        btn_back.setOnClickListener(this);
        btn_follow.setOnClickListener(this);
        scrollview.setOnScrollChangeListener(this);

        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                PAGE = 1;
                isRefresh = true;
                mPresenter.onUserVideo();
            }
        });

        refresh_view.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                PAGE += 1;
                isRefresh = false;
                mPresenter.onUserVideo();
            }
        });
    }

    @Override
    public void onClickEvent(View v) {
        if (v == btn_back) {
            finish();
        } else if (v == btn_follow) {
            userInfo.setIs_follow(!userInfo.isIs_follow());
            if (userInfo.isIs_follow()) {
                mPresenter.onFollowUser(userInfo.getUser_id());
            } else {
                mPresenter.onCancelFollowUser(userInfo.getUser_id());
            }
            mPresenter.onUserMsg();
        }
    }


    @Override
    public void initObject() {
        tv_title.setText("蘑菇头电影");

        setMVP();

        setUserInfoVideo();
    }

    private void setUserInfoVideo() {
        mAdapter = new UserInfoVideoAdapter(mContext);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(mAdapter);
        rv.setNestedScrollingEnabled(false);


        mPresenter.onUserMsg();
        mPresenter.onUserVideo();
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int headViewH = MeasureUtlis.getViewHeight(head_view);
        int titleViewH = MeasureUtlis.getViewHeight(title_view);
        if (scrollY <= 0) {
            head_view.setBackgroundColor(getResources().getColor(R.color.c_313131));
            tv_title.setTextColor(Color.TRANSPARENT);
            btn_back.setImageResource(R.mipmap.icon_back_white);
            title_view.setBackgroundColor(Color.TRANSPARENT);
            lineView.setVisibility(View.INVISIBLE);
            tv_title.setText(userInfo.getNickname());
            // Eyes.setStatusBarColor(this, getResources().getColor(R.color.c_313131));
        } else if ((headViewH - scrollY) <= titleViewH) {
            tv_title.setTextColor(getResources().getColor(R.color.c_333333));
            btn_back.setImageResource(R.mipmap.icon_back);
            title_view.setBackgroundColor(getResources().getColor(R.color.color_white));
            lineView.setVisibility(View.VISIBLE);
            tv_title.setText(userInfo.getNickname());
            //   Eyes.setStatusBarLightMode(this, getResources().getColor(R.color.color_white));
        } else {
            tv_title.setTextColor(Color.TRANSPARENT);
            title_view.setBackgroundColor(Color.TRANSPARENT);
            btn_back.setImageResource(R.mipmap.icon_back_white);
            lineView.setVisibility(View.INVISIBLE);
            tv_title.setText(userInfo.getNickname());
            //  Eyes.setStatusBarColor(this, getResources().getColor(R.color.c_313131));
        }
    }


    @Override
    public UserMsgRequest getUserMsgRequest() {
        UserMsgRequest request = new UserMsgRequest();
        request.setUser_id(userId);
        request.setType(2);
        return request;
    }

    @Override
    public UserVideoRequest getUserVideoRequest() {
        UserVideoRequest request = new UserVideoRequest();
        request.setLimit(LIMIT);
        request.setPage(PAGE);
        request.setUser_id(userId);
        return request;
    }

    @Override
    public void setUserInfo(UserInfoMsg.UserInfo userInfo) {
        this.userInfo = userInfo;
        FrescoUtil.loadDefImg(user_head, userInfo.getHeadimg());
        user_name.setText(userInfo.getNickname());
        if (userInfo.isIs_follow()) {
            btn_follow.setText("已关注");
            btn_follow.setTextColor(getResources().getColor(R.color.color_white));
            btn_follow.setBackground(getResources().getDrawable(R.drawable.follow_bg_nor));
        } else {
            btn_follow.setText("+关注");
            btn_follow.setTextColor(getResources().getColor(R.color.c_333333));
            btn_follow.setBackground(getResources().getDrawable(R.drawable.user_info_follow_bg));
        }
    }

    @Override
    public void setUserVideo(UserVideoResponse.Data userVideo) {
        if (isRefresh) {
            refresh_view.finishRefresh();
            refresh_view.setEnableLoadmore(true);
        } else {
            refresh_view.finishLoadmore();
        }
        mAdapter.setList(userVideo.getLists(), isRefresh);
    }
}
