package com.application.sven.huinews.main.login.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.entity.request.FindPassRequest;
import com.application.sven.huinews.entity.request.SmsCodeRequest;
import com.application.sven.huinews.main.login.contract.FindPwdContract;
import com.application.sven.huinews.main.login.model.FindPwdModel;
import com.application.sven.huinews.main.login.presenter.FindPwdPresenter;
import com.application.sven.huinews.utils.CodeCountDown;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.ToastUtils;

/**
 * auther: sunfuyi
 * data: 2018/5/17
 * effect:
 */
public class FindPwdActivity extends BaseActivity<FindPwdPresenter, FindPwdModel> implements FindPwdContract.View {
    private EditText login_phone_et;
    private EditText input_code;
    private TextView btn_code;
    private EditText input_pwd;
    private TextView btn_find;
    private String mPhoneNum, mPass, mSmsCode;
    private CodeCountDown mCodeCountDown;

    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, FindPwdActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_find_pwd;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.titlebar);
        mTitleBar.setTitle(getString(R.string.forget_pass));

        login_phone_et = findViewById(R.id.login_phone_et);
        input_code = findViewById(R.id.input_code);
        btn_code = findViewById(R.id.btn_code);
        input_pwd = findViewById(R.id.input_pwd);
        btn_find = findViewById(R.id.btn_find);
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
        btn_find.setOnClickListener(this);
    }

    @Override
    public void onClickEvent(View v) {
        if (v == btn_code) {
            checkCanGetCode();
        } else if (v == btn_find) {
            checkCommit();
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
        mPhoneNum = login_phone_et.getText().toString().trim();
        if (!CommonUtils.checkPhoneNum(mPhoneNum)) {
            ToastUtils.showLong(mContext, getString(R.string.tip_input_phone));
            return;
        }
        showLoading();
        mPresenter.onRegCode();

    }

    private void checkCommit() {
        mPhoneNum = login_phone_et.getText().toString().trim();
        mPass = input_pwd.getText().toString().trim();
        mSmsCode = input_code.getText().toString().trim();
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
        mPresenter.onFindPwd();
    }

    @Override
    public FindPassRequest getFindPassRequest() {
        FindPassRequest request = new FindPassRequest();
        request.setPhone(login_phone_et.getText().toString().trim());
        request.setCode(input_code.getText().toString().trim());
        request.setPass(input_pwd.getText().toString().trim());
        return request;
    }

    @Override
    public SmsCodeRequest getSmsCodeRequest() {
        SmsCodeRequest request = new SmsCodeRequest();
        request.setPhone(login_phone_et.getText().toString().trim());
        request.setType(Constant.CODE_TO_FINDPWD);
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
    public void onFindPwdSucceed() {
        hideLoading();
        ToastUtils.showLong(mContext, getString(R.string.find_pwd_ok));
        finish();
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
