package com.application.sven.huinews.main.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.request.LoginRequest;
import com.application.sven.huinews.main.home.activity.MainActivity;
import com.application.sven.huinews.main.login.contract.LoginContract;
import com.application.sven.huinews.main.login.model.LoginModel;
import com.application.sven.huinews.main.login.presenter.LoginPresenter;
import com.application.sven.huinews.mob.login.WechatLoginResponse;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:登录
 */
public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {
    private TextView btn_reg;
    private TextView login_btn;
    private TextView btn_find;
    private EditText login_phone_et;
    private EditText login_pwd_et;
    private ImageButton btn_close;
    private ImageView iv_wechat;
    private String mPhoneNum, mPass;
    private boolean isLogout;
    private boolean mustLogin;//是否为必须登录时跳转进来
    private long exitTime;

    public static void toThis(Context mContext, boolean isLogout, boolean mustLogin) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constant.BUNDLE_TO_LOGOUT, isLogout);
        bundle.putBoolean(Constant.BUNDLE_TO_MUST_LOGIN, mustLogin);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }



    @Override
    public int getLayoutId() {
        EventBus.getDefault().register(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isLogout = bundle.getBoolean(Constant.BUNDLE_TO_LOGOUT);
            mustLogin = bundle.getBoolean(Constant.BUNDLE_TO_MUST_LOGIN);
        }
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        btn_reg = findViewById(R.id.btn_reg);
        btn_find = findViewById(R.id.btn_find);
        btn_close = findViewById(R.id.btn_close);
        iv_wechat = findViewById(R.id.iv_wechat);
        login_phone_et = findViewById(R.id.login_phone_et);
        login_pwd_et = findViewById(R.id.login_pwd_et);
        login_btn = findViewById(R.id.login_btn);
    }

    @Override
    public void initEvents() {
        btn_reg.setOnClickListener(this);
        btn_find.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        iv_wechat.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClickEvent(View v) {
        if (v == btn_reg) {
            LogUtil.showLog("msg----mustLogin：" + mustLogin);
            RegisterActivity.toThis(mContext, mustLogin);
        } else if (v == btn_find) {
            FindPwdActivity.toThis(mContext);
        } else if (v == btn_close) {
            if (isLogout) {
                exitApp();
                return;
            }
            finish();

        } else if (v == iv_wechat) {
            showLoading();
            mPresenter.onWechatLogin();
        } else if (v == login_btn) {
            checkCanLogin();
        }
    }

    @Override
    public void initObject() {
        setMVP();
    }

    private void checkCanLogin() {
        mPhoneNum = login_phone_et.getText().toString().trim();
        mPass = login_pwd_et.getText().toString().trim();
        if (!CommonUtils.checkPhoneNum(mPhoneNum)) {
            ToastUtils.showLong(mContext, getString(R.string.tip_input_phone));
            return;
        }
        if (!CommonUtils.checkPassWord(mPass)) {
            ToastUtils.showLong(mContext, getString(R.string.tip_input_pass));
            return;
        }
        showLoading();
        mPresenter.onLogin();
    }

    @Override
    public LoginRequest getLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setAccount(mPhoneNum);
        request.setPassword(mPass);
        return request;
    }

    @Override
    public void onWechatLoginSucceed(WechatLoginResponse response) {
        hideLoading();
        BindActivity.toThis(mContext, response);
    }


    @Override
    public void onLoginSucceed() {
        if (mustLogin) {
            MainActivity.toThis(mContext);
            finish();
            return;
        }
        ToastUtils.showLong(mContext, getString(R.string.login_ok));
        hideLoading();
        EventBus.getDefault().post(Constant.LOGIN_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoading();
    }

    @Subscribe
    public void onRegOkEvents(String str) {
        if (str.equals(Constant.REGISTER_OK)) {
            finish();
        }
    }

    @Override
    public void showLoading() {
        showBaseLoading();
    }

    @Override
    public void hideLoading() {
        hideBaseLoading();
    }

    @Override
    public void showErrorTip(int code, String msg) {
        ToastUtils.showLong(mContext, msg);
        hideLoading();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isLogout) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                exitApp();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showLong(mContext, getString(R.string.exit_app));
            exitTime = System.currentTimeMillis();
        } else {
            EventBus.getDefault().post(Constant.EXIT_APP);
            finish();
        }
    }
}
