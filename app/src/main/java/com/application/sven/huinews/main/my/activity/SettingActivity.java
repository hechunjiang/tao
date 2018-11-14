package com.application.sven.huinews.main.my.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.AppInfo;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.config.http.MyRetrofit;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.request.SetUserMsgRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.main.login.activity.LoginActivity;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.update.UpdateInfo;
import com.application.sven.huinews.view.dialog.CommonDialog;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.greenrobot.eventbus.EventBus;

/**
 * auther: sunfuyi
 * data: 2018/5/12
 * effect:设置
 */
public class SettingActivity extends BaseActivity {
    private TextView btn_logout;
    private TextView tv_cache;
    private TextView tv_version;
    private SwitchCompat set_switch;
    private RelativeLayout rl_update;
    private RelativeLayout rl_clear;
    private CommonDialog mDialog;
    private UpdateInfo updateInfo;

    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, SettingActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.titlebar);
        mTitleBar.setTitle(getString(R.string.setting));
        rl_update = findViewById(R.id.rl_update);
        rl_clear = findViewById(R.id.rl_clear);
        btn_logout = findViewById(R.id.btn_logout);
        tv_cache = findViewById(R.id.tv_cache);
        tv_version = findViewById(R.id.tv_version);
        set_switch = findViewById(R.id.set_switch);
    }

    @Override
    public void initEvents() {
        mTitleBar.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rl_update.setOnClickListener(this);
        rl_clear.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        mDialog.setDialogClickLisenter(new CommonDialog.dialogClickLisenter() {
            @Override
            public void onCancel() {
                mDialog.dismiss();
            }

            @Override
            public void onEnter() {
                mDialog.dismiss();
                setLogOut();
                LoginActivity.toThis(mContext, true, false);
                finish();
            }
        });


        set_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveUserAutoPay(isChecked);
            }
        });
    }

    @Override
    public void onClickEvent(View v) {
        if (v == rl_clear) {
            clearCache();
        } else if (v == rl_update) {
            updateApk();
        } else if (v == btn_logout) {
            mDialog.setDialogMsg(getString(R.string.logout_msg));
        }
    }

    @Override
    public void initObject() {
        mDialog = new CommonDialog(mContext);

        tv_version.setText("当前版本v" + AppInfo.getVersion(mContext));
        Fresco.getImagePipelineFactory().getMainFileCache().trimToMinimum();
        getCacheSize();


        if (TextUtils.isEmpty(UserSpCache.getInstance(this).getStringData(UserSpCache.KEY_PHONE))) {
            btn_logout.setVisibility(View.INVISIBLE);
        } else {
            btn_logout.setVisibility(View.VISIBLE);
        }

        boolean isAuto = UserSpCache.getInstance(mContext).getBoolean(UserSpCache.SAVE_AUTO_PAY);
        set_switch.setChecked(isAuto);
    }

    private void setLogOut() {
        UserSpCache.getInstance(mContext).logOut(mContext);

        //  EventBus.getDefault().post(Constant.LOGOUT_OK);
    }

    private void getCacheSize() {

        long cahceSize = Fresco.getImagePipelineFactory().getMainFileCache().getSize();
        tv_cache.setText(CommonUtils.formetFileSize(cahceSize));
    }

    private void clearCache() {
        Fresco.getImagePipeline().clearCaches();
        ToastUtils.showLong(mContext, getString(R.string.clear_cache_ok));
        tv_cache.setText("0B");
    }

    private void updateApk() {
        ToastUtils.showLong(mContext, "正在检查新版本，请稍后...");
        if (updateInfo == null) {
            updateInfo = new UpdateInfo(this);
        }
        if (permissionWRITE()) {
            updateInfo.setPermission(true);
        } else {
            updateInfo.setPermission(false);
        }
        updateInfo.getVersionInfo(false);
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
                updateInfo.setPermission(true);
                updateInfo.setClickUptate(true);
                updateInfo.uploadApk();
                // updateInfo.getVersionInfo(true);
            }
        } else {
            checkPermission();
        }
    }

    
    private void saveUserAutoPay(final boolean isAuto) {
        SetUserMsgRequest request = new SetUserMsgRequest();
        request.setIs_deduction(isAuto ? 2 : 1);
        MyRetrofit.getInstance(mContext, null).onAutoPay(request, new DataCallBack() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onSucceed(String json) {
                UserSpCache.getInstance(mContext).putBoolean(UserSpCache.SAVE_AUTO_PAY, isAuto);
                UserSpCache.getInstance(mContext).putBoolean(UserSpCache.SAVE_AUTO_PAY_CHECK, isAuto);

            }

            @Override
            public void onFail(BaseResponse baseResponse) {

            }
        });
    }
}
