package com.application.sven.huinews.main.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.entity.request.RegisterRequest;
import com.application.sven.huinews.entity.request.SmsCodeRequest;
import com.application.sven.huinews.main.home.activity.MainActivity;
import com.application.sven.huinews.main.login.contract.RegisterContract;
import com.application.sven.huinews.main.login.model.RegisterModel;
import com.application.sven.huinews.main.login.presenter.RegisterPresenter;
import com.application.sven.huinews.utils.CodeCountDown;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * auther: sunfuyi
 * data: 2018/5/17
 * effect:注册
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter, RegisterModel> implements RegisterContract.View {

    private EditText reg_phone_et;
    private EditText input_code;
    private EditText input_pwd;
    private EditText input_inv;
    private TextView btn_code;
    private TextView btn_reg;
    private String mPhoneNum, mPass, mSmsCode, mYq;
    private CodeCountDown mCodeCountDown;
    private boolean isToken;

    public static void toThis(Context mContext, boolean isToken) {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constant.BUNDLE_TO_REGISTER_TOKEN, isToken);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isToken = bundle.getBoolean(Constant.BUNDLE_TO_REGISTER_TOKEN);
        }
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.titlebar);
        mTitleBar.setTitle(getString(R.string.register));

        reg_phone_et = findViewById(R.id.reg_phone_et);
        input_code = findViewById(R.id.input_code);
        input_pwd = findViewById(R.id.input_pwd);
        input_inv = findViewById(R.id.input_inv);
        btn_code = findViewById(R.id.btn_code);
        btn_reg = findViewById(R.id.btn_reg);
    }

    @Override
    public void initEvents() {
        mTitleBar.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_code.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
    }

    @Override
    public void onClickEvent(View v) {
        if (v == btn_code) {
            checkCanGetCode();
        } else if (v == btn_reg) {
            checkCanRegister();
        }
    }

    @Override
    public void initObject() {
        setMVP();
        mCodeCountDown = new CodeCountDown(btn_code);
    }


    /**
     * 获取验证码
     */
    private void checkCanGetCode() {
        mPhoneNum = reg_phone_et.getText().toString().trim();
        if (!CommonUtils.checkPhoneNum(mPhoneNum)) {
            ToastUtils.showLong(mContext, getString(R.string.tip_input_phone));
            return;
        }
        showLoading();
        mPresenter.onRegCode();

    }

    /**
     * 注册
     */
    private void checkCanRegister() {
        mPhoneNum = reg_phone_et.getText().toString().trim();
        mPass = input_pwd.getText().toString().trim();
        mSmsCode = input_code.getText().toString().trim();
        mYq = input_inv.getText().toString().trim();
        if (!CommonUtils.checkPhoneNum(mPhoneNum)) {
            ToastUtils.showLong(mContext, getString(R.string.tip_input_phone));
            return;
        }
        if (!CommonUtils.checkPhoneCode(mSmsCode)) {
            ToastUtils.showLong(mContext, getString(R.string.tip_input_code));
            return;
        }
        if (!CommonUtils.checkPassWord(mPass)) {
            ToastUtils.showLong(mContext, getString(R.string.tip_input_pass));
            return;
        }
        showLoading();
        mPresenter.onReg(false);
    }

    @Override
    public SmsCodeRequest getSmsCodeRequest() {
        SmsCodeRequest request = new SmsCodeRequest();
        request.setType(Constant.CODE_TO_REG);
        request.setPhone(reg_phone_et.getText().toString().trim());
        return request;
    }

    @Override
    public RegisterRequest getRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setPhone(mPhoneNum);
        request.setPass(mPass);

        request.setRegisterCode(mSmsCode);
        if (!TextUtils.isEmpty(mYq)) {
            request.setInvitCode(mYq);
        }
        return request;
    }

    @Override
    public void getSmsCodeSucceed() {
        mCodeCountDown.start();
        mCodeCountDown.setNotClick();
        hideLoading();
        ToastUtils.showLong(mContext, getString(R.string.code_ok));
    }

    @Override
    public void onRegisterSucceed() {
        hideLoading();
        if (isToken) {
            MainActivity.toThis(mContext);
            finish();
        } else {
            EventBus.getDefault().post(Constant.REGISTER_OK);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCodeCountDown.cancel();
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

}
