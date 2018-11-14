package com.application.sven.huinews.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.application.sven.huinews.R;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.events.TokenExpireEvent;
import com.application.sven.huinews.entity.request.MovieVisitRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.main.login.activity.LoginActivity;
import com.application.sven.huinews.main.welcome.activity.SplashActivity;
import com.application.sven.huinews.utils.AppStatusManager;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.TUtil;
import com.application.sven.huinews.utils.statusbar.Eyes;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.TitleBar;
import com.application.sven.huinews.view.dialog.CommonDialog;
import com.application.sven.huinews.view.dialog.GoldComeDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by sfy. on 2018/5/7 0007.
 */

public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity implements View.OnClickListener {
    protected Context mContext;
    protected TitleBar mTitleBar;
    protected EmptyLayout mEmptyLayout;
    public P mPresenter;
    public M mModel;
    protected int LIMIT = 20;
    protected int PAGE = 1;
    protected boolean isRefresh = true;
    private Dialog mLoadingDialog;

    private GoldComeDialog mGoldComeDialog; //金币dialog
    private MediaPlayer mediaPlayer;
    protected int statusBarColor = 0;
    protected View statusBarView = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //  checkAppStatus();
        super.onCreate(savedInstanceState);
        Eyes.setStatusBarLightMode(this, getResources().getColor(R.color.color_white));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止横屏
        setContentView(getLayoutId());

        mContext = this;
        initView();
        initObject();
        initEvents();

        initFontScale();

        mGoldComeDialog = new GoldComeDialog(this);
        mLoadingDialog = new Dialog(this, R.style.dialog);
        mLoadingDialog.setContentView(R.layout.dialog_loading);

        PushAgent.getInstance(this).onAppStart();
    }

    /**
     * 初始化MVP
     */
    public void setMVP() {
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        mPresenter.setVM(this, mModel);
    }

    /**
     * 获取布局文件
     */
    public abstract int getLayoutId();


    /**
     * 初始化View
     */
    public abstract void initView();

    /**
     * 设置监听
     */
    public abstract void initEvents();

    /**
     * 处理监听事件
     */
    public abstract void onClickEvent(View v);

    @Override
    public void onClick(View v) {
        onClickEvent(v);
    }
    /*********************跳转相关**********************************/
    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 初始化事物
     */

    public abstract void initObject();

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }

    private void initFontScale() {
        Configuration configuration = getResources().getConfiguration();
        configuration.fontScale = (float) 1;
        //0.85 小, 1 标准大小, 1.15 大，1.3 超大 ，1.45 特大
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }


    private Handler mDialogDisMissHandler = new Handler();

    /**
     * 金币动画
     *
     * @param count
     */
    public void openGoldDialog(int count) {
        // 0 金币代表今日次数已领完
        if (count == 0) {
            return;
        }
        if (mGoldComeDialog == null) {
            mGoldComeDialog = new GoldComeDialog(this);
        }
        mGoldComeDialog.showDialog(count);
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.gold_come);
        }
        mediaPlayer.start();
        mDialogDisMissHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mGoldComeDialog.dismiss();
            }
        }, 2000);
    }


    private CommonDialog mCommonDialog;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenExpire(TokenExpireEvent event) {
        if (mCommonDialog == null) {
            mCommonDialog = new CommonDialog(mContext);

        }
        if (!UserSpCache.getInstance(mContext).getBoolean(UserSpCache.KEY_TOKEN_IS_EXPIRE)) {
            UserSpCache.getInstance(mContext).clearCache();
            UserSpCache.getInstance(mContext).putBoolean(UserSpCache.KEY_TOKEN_IS_EXPIRE, true);
        }

        mCommonDialog.setDialogMsg(getResources().getString(R.string.retry_login));

        mCommonDialog.setDialogClickLisenter(new CommonDialog.dialogClickLisenter() {
            @Override
            public void onCancel() {
                android.os.Process.killProcess(android.os.Process.myPid());
                mCommonDialog.dismiss();
            }

            @Override
            public void onEnter() {
                LoginActivity.toThis(mContext, false, true);
                finish();
            }
        });
    }

    public void setMoviesVisit(MovieVisitRequest request) {
        LogUtil.showLog("msg-----保存观看记录:" + request.toString());
        MyRetrofit.getInstance(mContext, null).onMovieVisit(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                LogUtil.showLog("msg---保存观看记录成功");
                EventBus.getDefault().post(Constant.REFRESH_MINE_MOVIDE_HISTORY);
            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }

    private void checkAppStatus() {
        if (AppStatusManager.getInstance().getAppStatus() == AppStatusManager.AppStatusConstant.APP_FORCE_KILLED) {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }

    public void showBaseLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new Dialog(this, R.style.dialog);
            mLoadingDialog.setContentView(R.layout.dialog_loading);
        }
        mLoadingDialog.show();
    }

    public void hideBaseLoading() {
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    protected void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        if (statusBarView != null) {
            statusBarView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    protected void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        if (statusBarView != null) {
            statusBarView.setBackgroundColor(statusBarColor);
        }
    }
}
