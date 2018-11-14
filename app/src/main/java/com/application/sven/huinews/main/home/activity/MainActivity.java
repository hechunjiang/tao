package com.application.sven.huinews.main.home.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.Comment;
import com.application.sven.huinews.entity.PushTask;
import com.application.sven.huinews.entity.events.VideoLikeEvent;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.SetUserMsgRequest;
import com.application.sven.huinews.entity.request.VideoCommentRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.entity.response.GiveGoldResponse;
import com.application.sven.huinews.entity.response.VideoListResponse;
import com.application.sven.huinews.main.home.contract.HomeContract;
import com.application.sven.huinews.main.home.fragment.HomeFragment;
import com.application.sven.huinews.main.home.fragment.HomeTabFragment;
import com.application.sven.huinews.main.home.fragment.HomeVideoFragment;
import com.application.sven.huinews.main.home.model.HomeModel;
import com.application.sven.huinews.main.home.presenter.HomePresenter;
import com.application.sven.huinews.main.login.activity.LoginActivity;
import com.application.sven.huinews.main.my.fragment.MineFragment;
import com.application.sven.huinews.main.preemption.fragment.PreeFragment;
import com.application.sven.huinews.main.read.fragment.ReadFragment;
import com.application.sven.huinews.main.video.adapter.VideoCommentAdapter;
import com.application.sven.huinews.main.web.activity.WebActivity;
import com.application.sven.huinews.utils.AesUtil;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.KeyBoradUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.glidUtils.GlideUtils;
import com.application.sven.huinews.utils.rsa.RSAConstant;
import com.application.sven.huinews.utils.rsa.RSAUtils;
import com.application.sven.huinews.utils.update.UpdateInfo;
import com.application.sven.huinews.view.EdittextUtlis;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.RefreshLayout;
import com.application.sven.huinews.view.dialog.AppUpdateDialog;
import com.application.sven.huinews.view.dialog.GiveGoldDialog;
import com.application.sven.huinews.view.dialog.OneDollarBillDialog;
import com.application.sven.huinews.view.dialog.OtherActivityDialog;
import com.application.sven.huinews.view.dialog.ReportDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.mob.tools.utils.Strings.getString;


public class MainActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeContract.View, HomeTabFragment.OnComentViewLisenter {
    private TextView mainBottomVideo, mainBottomMine, mainBottomRead, tv_movie;
    private ImageView iv_movie;
    private LinearLayout mainBottomPree;
    private HomeFragment mHomeFragment;
    private HomeVideoFragment mHomeVideoFragment;
    private PreeFragment mPreeFragment;
    private MineFragment mMineFragment;
    private ReadFragment mReadFragment;
    private FragmentTransaction mFragmentTransaction;
    private List<Fragment> fragments;
    private UpdateInfo mUpdateInfo;
    private OneDollarBillDialog mOneDollarBillDialog;//新人一元提现dialog
    private OtherActivityDialog mOtherActivityDialog;//活动推送dialog
    private GiveGoldDialog mGiveGoldDialog;
    private String etComment;
    private int videoId;
    private RefreshLayout refresh_view;
    private RecyclerView comment_rv;
    private VideoCommentAdapter mVideoCommentAdapter;
    private TextView btn_send;//发送

    private ImageButton btn_close;
    private LinearLayout video_comment_ll;
    private TextView tv_comment_count; //评论数
    private TextView et_video_comment;
    private TextView tvMsg;
    private VideoListResponse.VideoList.ListBean mCommentData; //评论数据
    private Animation animationIn, animationOut;
    private EmptyLayout comment_empty;
    private ReportDialog reportDialog; //举报
    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mainBottomVideo = findViewById(R.id.main_bottom_plays_rb);
        mainBottomPree = findViewById(R.id.main_bottom_news_rb);
        mainBottomMine = findViewById(R.id.main_bottom_mine_rb);
        iv_movie = findViewById(R.id.iv_movie);
        tv_movie = findViewById(R.id.tv_movie);
        mainBottomRead = findViewById(R.id.main_bottom_mine_read);

        video_comment_ll = findViewById(R.id.video_comment_ll);
        comment_rv = findViewById(R.id.comment_rv);
        comment_empty = findViewById(R.id.comment_empty);
        tv_comment_count = findViewById(R.id.tv_comment_count);
        et_video_comment = findViewById(R.id.et_video_comment);
        btn_send = findViewById(R.id.btn_send);
        refresh_view = findViewById(R.id.refreshView);
        btn_close = findViewById(R.id.btn_close);
    }

    @Override
    public void initEvents() {
        mainBottomVideo.setOnClickListener(this);
        mainBottomPree.setOnClickListener(this);
        mainBottomMine.setOnClickListener(this);
        mainBottomRead.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        et_video_comment.setOnClickListener(this);

        mVideoCommentAdapter.setItemOnLongClickLisenter(new VideoCommentAdapter.OnLongClickLisenter()

        {
            @Override
            public void onLongClick(final Comment comment) {
                if (reportDialog == null) {
                    reportDialog = new ReportDialog(mContext);
                }
                reportDialog.show();
                reportDialog.setOnReportLisenter(new ReportDialog.onReportLisenter() {
                    @Override
                    public void report() {
                        mPresenter.onVideoCommentReport(comment.getId());
                        reportDialog.dismiss();
                    }
                });
            }
        });

        mVideoCommentAdapter.setOnLikeListener(new VideoCommentAdapter.OnLikeListener() {
            @Override
            public void onLikeChange(Comment comment) {
                mPresenter.onVideoCommentLike(comment.getId());
            }
        });


        refresh_view.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = false;
                PAGE += 1;
                mPresenter.onVideoComment(getVideoCommentRequest(PAGE, videoId, Constant.COMMENT_UP));
            }
        });

        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = true;
                PAGE = 1;
                mPresenter.onVideoComment(getVideoCommentRequest(PAGE, videoId, Constant.COMMENT_UP));
            }
        });

    }

    @Override
    public void onClickEvent(View v) {
        if (v == mainBottomVideo) {
            switchFragment(2);
        } else if (v == mainBottomPree) {
            switchFragment(1);
        } else if (v == mainBottomRead) {
            switchFragment(0);
        } else if (v == mainBottomMine) {
            switchFragment(3);
        } else if (v == btn_close) {
            hideCommentView();
        } else if (v == et_video_comment) {
            setEtListener();
        } else if (v == btn_send) {
            sendComment();
        }
    }

    @Override
    public void initObject() {
        setMVP();
        // initTabFragment();
        switchFragment(0);
        updateApk();
        mPresenter.onActiatePush();
        setPushkey();
        setVideoCommentInfo();

        mPresenter.onGiveGold();

    }

   /* private void initTabFragment() {
        fragments = new ArrayList<>();
        mHomeFragment = new HomeFragment();
        mPreeFragment = new PreeFragment();
        mMineFragment = new MineFragment();
        fragments.add(mHomeFragment);
        fragments.add(mPreeFragment);
        fragments.add(mMineFragment);
    }*/

    /* private void switchFragment(int index) {
         switchTv(index);

         mFragmentTransaction = getSupportFragmentManager().beginTransaction();
         for (int i = 0; i < fragments.size(); i++) {
             Fragment fragment = fragments.get(i);
             if (index == i) {
                 if (fragment.isAdded()) {
                     mFragmentTransaction.show(fragment);
                 } else {
                     mFragmentTransaction.add(R.id.container, fragment);
                 }
             } else {
                 if (fragment.isAdded()) {
                     mFragmentTransaction.hide(fragment);
                 }
             }
         }
         mFragmentTransaction.commitAllowingStateLoss();
     }

 */
    private void switchFragment(int index) {
        selectedTab();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        hideFragments(ft);
        switchTv(index);

        switch (index) {
            case 2:
                if (mHomeFragment != null) {
                    ft.show(mHomeFragment);
                } else {
                    mHomeFragment = new HomeFragment();
                    ft.add(R.id.container, mHomeFragment);
                }
               /* if (mHomeVideoFragment != null) {
                    ft.show(mHomeVideoFragment);
                } else {
                    mHomeVideoFragment = new HomeVideoFragment();
                    ft.add(R.id.container, mHomeVideoFragment);
                }*/
                break;

            case 1:
                if (mPreeFragment != null) {
                    ft.show(mPreeFragment);
                } else {
                    mPreeFragment = new PreeFragment();
                    ft.add(R.id.container, mPreeFragment);
                }
                break;
            case 0:
                if (mReadFragment != null) {
                    ft.show(mReadFragment);
                } else {
                    mReadFragment = new ReadFragment();
                    ft.add(R.id.container, mReadFragment);
                }
                break;
            case 3:
                if (mMineFragment != null) {
                    ft.show(mMineFragment);
                } else {
                    mMineFragment = new MineFragment();
                    ft.add(R.id.container, mMineFragment);
                }
                break;
        }
        ft.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction ft) {
        if (mHomeFragment != null) {
            VideoViewManager.instance().releaseVideoPlayer();
            ft.hide(mHomeFragment);
        }
        if (mPreeFragment != null) {
            ft.hide(mPreeFragment);
        }
        if (mReadFragment != null) {
            ft.hide(mReadFragment);
        }
        if (mMineFragment != null) {
            ft.hide(mMineFragment);
        }

    }


    private void switchTv(int position) {
        if (position == 2) {
            mainBottomVideo.setSelected(true);
            mainBottomMine.setSelected(false);
            mainBottomRead.setSelected(false);
            iv_movie.setSelected(false);
            tv_movie.setSelected(false);
        } else if (position == 1) {
            mainBottomVideo.setSelected(false);
            iv_movie.setSelected(true);
            tv_movie.setSelected(true);
            mainBottomRead.setSelected(false);
            mainBottomMine.setSelected(false);
        } else if (position == 0) {
            mainBottomVideo.setSelected(false);
            iv_movie.setSelected(false);
            tv_movie.setSelected(false);
            mainBottomRead.setSelected(true);
            mainBottomMine.setSelected(false);
        } else if (position == 3) {
            mainBottomVideo.setSelected(false);
            iv_movie.setSelected(false);
            tv_movie.setSelected(false);
            mainBottomRead.setSelected(false);
            mainBottomMine.setSelected(true);

        }
    }

    //重置所有文本的选中状态
    public void selectedTab() {
        mainBottomVideo.setSelected(false);
        mainBottomMine.setSelected(false);
        iv_movie.setSelected(false);
        tv_movie.setSelected(false);
        mainBottomRead.setSelected(false);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (video_comment_ll.getVisibility() == View.VISIBLE) {
                hideCommentView();
                return true;
            }
          /*  if (GSYVideoManager.backFromWindowFull(this)) {
                return true;
            }*/
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showLong(mContext, "再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onToMainPageEvent(String mStr) {
        if (mStr.equals(Constant.TO_MAIN_PAGE_EVENT)) {
            EventBus.getDefault().removeStickyEvent(Constant.TO_MAIN_PAGE_EVENT);
            switchFragment(2);
        } else if (mStr.equals(Constant.TO_MINE_PAGE_EVENT)) {
            EventBus.getDefault().removeStickyEvent(Constant.TO_MINE_PAGE_EVENT);
            switchFragment(3);
        } else if (mStr.equals(Constant.TO_MINE_TO_LOGIN)) {
            LoginActivity.toThis(mContext, false, false);
        } else if (mStr.equals(Constant.EXIT_APP)) {
            finish();
        } else if (mStr.equals(Constant.TO_MAIN_MOVIE)) {
            switchFragment(1);
        } else if (mStr.equals(Constant.TO_MAIN_READ)) {
            switchFragment(2);
        }
    }

    private void updateApk() {
        if (mUpdateInfo == null) {
            mUpdateInfo = new UpdateInfo(this);
        }
        if (permissionWRITE()) {
            mUpdateInfo.setPermission(true);
        } else {
            mUpdateInfo.setPermission(false);
        }
        mUpdateInfo.getVersionInfo(true);

        mUpdateInfo.setCheckPermissionLinsenter(new UpdateInfo.checkPermissionLinsenter() {
            @Override
            public void checkPermission() {
                MainActivity.this.checkPermission();
            }
        });
    }


    private boolean permissionWRITE() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 权限申请，
     * 只需要申请读权限
     */
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);//自定义的code
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (permissionWRITE()) {
                mUpdateInfo.setPermission(true);
                mUpdateInfo.setClickUptate(true);
                mUpdateInfo.uploadApk();
                // updateInfo.getVersionInfo(true);
            }
        } else {
            checkPermission();
        }
    }


    String newPersonalUrl = "";

    @Override
    public void showNewsPersonalTask(final PushTask task) {
        if (!UserSpCache.getInstance(mContext).getBoolean(UserSpCache.RED_OPEN)) {
            return;
        }
        newPersonalUrl = task.getUrl();

        //新手一元提现
        if (mOneDollarBillDialog == null) {
            mOneDollarBillDialog = new OneDollarBillDialog(mContext);
        }
        mOneDollarBillDialog.show();
        mOneDollarBillDialog.setOnNewsPersonalListener(new OneDollarBillDialog.OnNewsPersonalListener() {
            @Override
            public void onOpen() {
                mPresenter.onRedBag();
                //   WebActivity.toThis(mContext, task.getUrl());
            }
        });
    }

    Handler handler = new Handler();

    @Override
    public void showOtherTask(final PushTask task) {
        //其他任务
        if (mOtherActivityDialog == null) {
            mOtherActivityDialog = new OtherActivityDialog(mContext);
        }

        GlideUtils.loadImgGetResult(this, task.getImgUrl(), new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(final Drawable resource, Transition<? super Drawable> transition) {
                //延迟弹出
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mOtherActivityDialog.showDialog(resource);
                    }
                }, 2000);
            }
        });

        mOtherActivityDialog.setOnOpenBagListener(new OtherActivityDialog.OnOpenBagListener() {
            @Override
            public void onOpen() {
                WebActivity.toThis(mContext, task.getUrl());
            }
        });
    }

    @Override
    public void onOpenRedBag() {
        WebActivity.toThis(mContext, newPersonalUrl);
    }

    @Override
    public VideoCommentRequest getVideoCommentRequest(int page, int videoId, String sort) {
        VideoCommentRequest request = new VideoCommentRequest();
        request.setPage(page + "");
        request.setVideo_id(videoId + "");
        request.setOrder(sort);
        return request;

    }

    @Override
    public AdCommentRequest getAdCommentRequest() {
        AdCommentRequest request = new AdCommentRequest();
        request.setCommentContent(etComment);
        request.setVideoId(videoId);
        return request;
    }

    @Override
    public void setComment(List<Comment> mComment) {
        if (isRefresh) {
            if (mComment.size() > 0) {
                comment_empty.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
            } else {
                comment_empty.setErrorType(EmptyLayout.NO_DATA, EmptyLayout.NO_COMMENT);
            }
            refresh_view.finishRefresh();
        } else {
            if (mComment.size() <= 0) {
                ToastUtils.showLong(mContext, "没有更多数据了");
            }
            refresh_view.finishLoadmore();
        }

        mVideoCommentAdapter.setData(mComment, isRefresh);
    }

    @Override
    public void adCommentOk() {
        ToastUtils.showLong(mContext, "评论成功");
        KeyBoradUtils.HideKeyboard(et_video_comment);
        mPresenter.onVideoComment(getVideoCommentRequest(1, videoId, Constant.COMMENT_TIME));
        mCommentData.setComment_count(mCommentData.getComment_count() + 1);
        tvMsg.setText(CommonUtils.getLikeCount(mCommentData.getComment_count()));
        tv_comment_count.setText("评论" + CommonUtils.getLikeCount(mCommentData.getComment_count()));
        // EventBus.getDefault().post(new VideoLikeEvent(mCommentData));
    }

    @Override
    public void setGiveGold(GiveGoldResponse.GoldData goldData) {
        if (!goldData.isStatus()) {
            mGiveGoldDialog = new GiveGoldDialog(mContext);
            mGiveGoldDialog.setGold(goldData.getGold());
            mGiveGoldDialog.setOnNewsPersonalListener(new GiveGoldDialog.OnNewsPersonalListener() {
                @Override
                public void onOpen() {

                    mPresenter.onToGiveGold();
                }
            });
        }
    }

    @Override
    public void settGiveGold() {
        ToastUtils.show(mContext, "领取成功", 1);
        WebActivity.toThis(mContext, Api.MY_INCOME_TYPE1);
    }


    private void setPushkey() {
        SetUserMsgRequest request = new SetUserMsgRequest();
        request.setDevice_tokens(UserSpCache.getInstance(mContext).getStringData(UserSpCache.KEY_DEVICE_TOKEN));
        MyRetrofit.getInstance(mContext, null).onPushKey(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showLog("msg---友盟pushKey 发送成功");
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    private void sendComment() {
        if (TextUtils.isEmpty(etComment)) {
            ToastUtils.showLong(mContext, getString(R.string.null_commnet));
            return;
        }
        mPresenter.onVideoAdComment();
    }


    private void setEtListener() {
        EdittextUtlis.showCommentEdit(this, et_video_comment, new EdittextUtlis.liveCommentResult() {
            @Override
            public void onResult(boolean confirmed, String comment) {
                if (confirmed) {
                    etComment = comment;
                    sendComment();
                }
            }
        });
    }

    private void setVideoCommentInfo() {
        comment_rv.setLayoutManager(new LinearLayoutManager(mContext));
        mVideoCommentAdapter = new VideoCommentAdapter(mContext);
        comment_rv.setAdapter(mVideoCommentAdapter);
    }

    @Override
    public void showCommentView(VideoListResponse.VideoList.ListBean mData, TextView tv_msg) {
        this.mCommentData = mData;
        this.tvMsg = tv_msg;
        isRefresh = true;
        comment_empty.setErrorType(EmptyLayout.LOADING, -1);
        if (animationIn == null) {
            animationIn = android.view.animation.AnimationUtils.loadAnimation(mContext, R.anim.movie_count_in);
        }
        video_comment_ll.startAnimation(animationIn);
        video_comment_ll.setVisibility(View.VISIBLE);

        tv_comment_count.setText("评论" + mData.getComment_count());
        videoId = mData.getId();
        mPresenter.onVideoComment(getVideoCommentRequest(1, mData.getId(), Constant.COMMENT_UP));
    }

    @Override
    public void hideCommentView() {
        if (animationOut == null) {
            animationOut = android.view.animation.AnimationUtils.loadAnimation(mContext, R.anim.movie_count_out);
        }
        video_comment_ll.startAnimation(animationOut);//开始动画
        video_comment_ll.setVisibility(View.GONE);
    }
}
