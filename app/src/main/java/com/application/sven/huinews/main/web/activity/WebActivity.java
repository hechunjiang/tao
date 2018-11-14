package com.application.sven.huinews.main.web.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.entity.JsGoldCome;
import com.application.sven.huinews.entity.jspush.JsShareType;
import com.application.sven.huinews.entity.response.WebBookInfo;
import com.application.sven.huinews.main.login.activity.LoginActivity;
import com.application.sven.huinews.main.my.activity.PayInfoActivity;
import com.application.sven.huinews.main.read.activity.BookDetailsActivity;
import com.application.sven.huinews.main.web.WebClient;
import com.application.sven.huinews.main.web.webjs.PersonalJs;
import com.application.sven.huinews.mob.login.WechatLogin;
import com.application.sven.huinews.mob.login.WechatLoginCallBack;
import com.application.sven.huinews.mob.login.WechatLoginResponse;
import com.application.sven.huinews.mob.share.MobShare;
import com.application.sven.huinews.mob.share.ShareCallBack;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.view.EmptyLayout;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.mob.tools.MobLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * auther: sunfuyi
 * data: 2018/5/12
 * effect:
 */
public class WebActivity extends BaseActivity {
    private SwipeRefreshLayout mWebSr;
    private BridgeWebView mWebView;
    private EmptyLayout emptyLayout;
    private String mUrl;
    private PersonalJs mPersonalJs;
    private boolean isError;
    Handler handler = new Handler();

    public static void toThis(Context mContext, String url) {
        Intent intent = new Intent(mContext, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.WEB_URL, url);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mUrl = bundle.getString(Constant.WEB_URL);
        }
        EventBus.getDefault().register(this);
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.titlebar);
        emptyLayout = findViewById(R.id.mEmptyLayout);
        mWebSr = findViewById(R.id.web_sr);
        mWebView = findViewById(R.id.webview);
    }

    @Override
    public void initEvents() {
        mTitleBar.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return;
                }
                finish();
            }
        });
        mTitleBar.setCloseClickLisenter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        emptyLayout.setOnEmptyRefreshLisenter(new EmptyLayout.onEmptyRefreshLisenter() {
            @Override
            public void onEmptyRefresh() {
                isError = false;
                mWebView.reload();
            }
        });


    }

    @Override
    public void onClickEvent(View v) {

    }

    @Override
    public void initObject() {
        mPersonalJs = new PersonalJs(mContext);
        setWebInfo();
        initJsMethod();

    }

    private void setWebInfo() {
        if (!TextUtils.isEmpty(mUrl)) {
            mWebSr.setRefreshing(true);
            mWebView.loadUrl(mUrl);
        }

        mWebSr.setColorSchemeColors(getResources().getColor(R.color.color_def));
        mWebSr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mWebView != null) {
                    if (mPersonalJs.isRefreshUrl()) {
                        LogUtil.showLog("msg----mUrl:" + mUrl);
                        mWebView.reload();
                    } else {
                        mWebSr.setRefreshing(false);
                    }
                }
            }
        });
        // 设置子视图是否允许滚动到顶部
        mWebSr.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(SwipeRefreshLayout parent, @Nullable View child) {
                return mWebView.getScrollY() > 0;
            }
        });
        mWebView.clearCache(true);
        mWebView.setDefaultHandler(new DefaultHandler());
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //为解决6.0系统上，这个api会调用两次，而且第一次是显示url的系统bug
                if (null != title && !view.getUrl().contains(title)) {
                    mTitleBar.setTitle(title);
                }
                super.onReceivedTitle(view, title);
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.setWebViewClient(new WebClient(mWebView) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                LogUtil.showLog("msg--mWebView:onReceivedError");
                isError = true;
                if (error.getErrorCode() == -2 || error.getErrorCode() == -8) {
                    emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR, EmptyLayout.NETWORK_ERROR);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                LogUtil.showLog("msg--mWebView:onPageStarted");
                if (url.contains(Api.BASE_WEB_URL)) {
                    mUrl = url;
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                LogUtil.showLog("msg--mWebView:onPageFinished");
                mWebSr.setRefreshing(false);
                if (!isError) {
                    emptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
                    if (mWebSr != null) {
                        mWebView.setVisibility(View.VISIBLE);
                    }
                }

                super.onPageFinished(view, url);
            }
        });
    }

    private void initJsMethod() {

        mWebView.addJavascriptInterface(mPersonalJs, Constant.PERSONAL);


        mWebView.registerHandler(PersonalJs.METHOD_TO_MAIN_PAGE, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                mPersonalJs.goToMainNewsPage();
            }

        });

        mWebView.registerHandler(PersonalJs.METHOD_TO_MINE_PAGE, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //跳转个人中心
                mPersonalJs.goToMinePage();
            }
        });

        mWebView.registerHandler(PersonalJs.METHOD_SHARE_TO_ONE, new BridgeHandler() {

            @Override
            public void handler(String data, final CallBackFunction function) {
                ToastUtils.showLong(mContext, getString(R.string.shareing));
                MobShare mobShare = new MobShare(mContext);
                mobShare.shareToOne(mContext, data, new ShareCallBack() {
                    @Override
                    public void onResponse(String response) {
                        function.onCallBack(response);
                    }
                });
            }
        });

        mWebView.registerHandler(PersonalJs.METHOD_WECHAT_LOGIN, new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                ToastUtils.showLong(mContext, getString(R.string.wechat_login));
                WechatLogin wechatLogin = new WechatLogin(mContext);
                wechatLogin.wechatLogin(new WechatLoginCallBack() {
                    @Override
                    public void onResponse(WechatLoginResponse response) {
                        final String responseData = new Gson().toJson(response);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                function.onCallBack(responseData);
                            }
                        }, 1000);
                    }
                });
            }
        });

        mWebView.registerHandler(PersonalJs.METHOD_BOOK, new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                WebBookInfo mWebBookInfo = new Gson().fromJson(data, WebBookInfo.class);
                BookDetailsActivity.toThis(mContext, mWebBookInfo.getType());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openLoginPageEvent(String mStr) {
        if (mStr.equals(Constant.TO_MINE_TO_LOGIN) || mStr.equals(Constant.TO_MAIN_PAGE_EVENT) || mStr.equals(Constant.TO_MINE_PAGE_EVENT)) {
            finish();
        } else if (mStr.equals(Constant.TO_PAY_PAGE)) {
            PayInfoActivity.toThis(mContext, 2);
        } else if (mStr.equals(Constant.PAY_OK)) {
            mWebView.reload();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGoldComeEvent(JsGoldCome event) {
        openGoldDialog(event.getCount());
    }
}
