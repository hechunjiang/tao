package com.application.sven.huinews.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.view.newread.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, "wxa23177a56a15d5cb");
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                //支付成功
                //发送eventbuscode,如果发货页面收到，会执行自动销毁。
                EventBus.getDefault().post(Constant.PAY_OK);
                ToastUtils.showToast(WXPayEntryActivity.this, "支付成功");
            }
            if (resp.errCode == -1) {
                //支付失败
                ToastUtils.showToast(WXPayEntryActivity.this, "支付失败");
            }

            if (resp.errCode == -2) {
                //取消支付
                ToastUtils.showToast(WXPayEntryActivity.this, "取消支付");
            }
            finish();
            overridePendingTransition(0,0);
        }
    }
}
