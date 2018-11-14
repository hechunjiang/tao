package com.application.sven.huinews.main.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.PhoneUtils;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.request.RegisterRequest;
import com.application.sven.huinews.entity.request.SmsCodeRequest;
import com.application.sven.huinews.main.login.contract.RegisterContract;
import com.application.sven.huinews.main.login.model.RegisterModel;
import com.application.sven.huinews.main.login.presenter.RegisterPresenter;
import com.application.sven.huinews.mob.login.WechatLoginResponse;
import com.application.sven.huinews.utils.CodeCountDown;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class BindActivity extends BaseActivity<RegisterPresenter, RegisterModel> implements RegisterContract.View {
    private EditText reg_phone_et;
    private EditText input_code;
    private EditText input_pwd;
    private EditText input_inv;
    private TextView btn_code;
    private TextView btn_reg;
    private String mPhoneNum, mPass, mSmsCode, mYq;
    private CodeCountDown mCodeCountDown;
    private WechatLoginResponse mWechatLoginResponse;


    public static void toThis(Context mContext, WechatLoginResponse response) {
        Intent intent = new Intent(mContext, BindActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUNDLE_TO_BIND_PHONE, response);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mWechatLoginResponse = bundle.getParcelable(Constant.BUNDLE_TO_BIND_PHONE);
        }
        return R.layout.activity_bind;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.titlebar);
        mTitleBar.setTitle(getString(R.string.bind));

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
        mPresenter.onReg(true);
    }

    @Override
    public SmsCodeRequest getSmsCodeRequest() {
        SmsCodeRequest request = new SmsCodeRequest();
        request.setPhone(reg_phone_et.getText().toString().trim());
        request.setType(Constant.CODE_TO_REG);
        return request;
    }

    @Override
    public RegisterRequest getRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setRegisterCode(input_code.getText().toString().trim());
        request.setPass(input_pwd.getText().toString().trim());
        request.setPhone(reg_phone_et.getText().toString().trim());
        request.setHeadIcon(mWechatLoginResponse.getUserIcon());
        request.setOpenId(mWechatLoginResponse.getOpenId());
        request.setSex(mWechatLoginResponse.getSex().equals("0") ? "1" : "2");
        request.setNickName(mWechatLoginResponse.getUserName());
        request.setInvitCode(input_inv.getText().toString().trim());
        request.setUnionid(UserSpCache.getInstance(getBaseContext()).getStringData(UserSpCache.WX_UNIONID));
        request.setMobileBrand(PhoneUtils.getPhoneBrand());
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
        EventBus.getDefault().post(Constant.REGISTER_OK);
        finish();
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
        hideLoading();
        ToastUtils.showLong(mContext, msg);
    }
}
