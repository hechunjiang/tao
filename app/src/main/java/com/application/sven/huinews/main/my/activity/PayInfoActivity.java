package com.application.sven.huinews.main.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.entity.request.PayInfoRequest;
import com.application.sven.huinews.entity.response.PayInfoResponse;
import com.application.sven.huinews.entity.response.PayMsgResponse;
import com.application.sven.huinews.main.my.adapter.PayInfoAdapter;
import com.application.sven.huinews.main.my.contract.PayMsgContract;
import com.application.sven.huinews.main.my.model.PayMsgModel;
import com.application.sven.huinews.main.my.presenter.PayMsgPresenter;
import com.application.sven.huinews.utils.PayInfo;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.itemDecoration.DividerItemDecoration;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.dialog.CommonDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * auther: sunfuyi
 * data: 2018/7/12
 * effect: 支付界面
 */
public class PayInfoActivity extends BaseActivity<PayMsgPresenter, PayMsgModel> implements PayMsgContract.View {
    private RecyclerView rv_pay;
    private PayInfoAdapter mPayInfoAdapter;
    private LinearLayout btn_pay;
    private TextView tv_diamond, tv_pay_msg;
    private ProgressBar def_pro;
    private EmptyLayout mEmptyLayout;
    private CommonDialog commonDialog;
    private int payId = -1;
    private int type; //1/个人中心和首页， 2、章节阅读 3、我的钻石界面

    public static void toThis(Context mContext, int type) {
        Intent intent = new Intent(mContext, PayInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.BUNDLE_TO_PAY_CENTER, type);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        EventBus.getDefault().register(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getInt(Constant.BUNDLE_TO_PAY_CENTER);
        }
        return R.layout.activity_pay_info;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.title_bar);
        mTitleBar.setTitle("充值中心");
        rv_pay = findViewById(R.id.rv_pay);
        tv_diamond = findViewById(R.id.tv_diamond);
        tv_pay_msg = findViewById(R.id.tv_pay_msg);
        mEmptyLayout = findViewById(R.id.mEmptyLayout);
        btn_pay = findViewById(R.id.btn_pay);
        def_pro = findViewById(R.id.loading);
    }

    @Override
    public void initEvents() {
        mTitleBar.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_pay.setOnClickListener(this);

        commonDialog.setDialogClickLisenter(new CommonDialog.dialogClickLisenter() {
            @Override
            public void onCancel() {
                EventBus.getDefault().post(Constant.TO_MAIN_MOVIE);
                commonDialog.dismiss();
                finish();
            }

            @Override
            public void onEnter() {
                EventBus.getDefault().post(Constant.TO_MAIN_READ);
                commonDialog.dismiss();
                finish();
            }
        });
    }


    @Override
    public void onClickEvent(View v) {
        if (v == btn_pay) {
            if (payId!=-1){
                def_pro.setVisibility(View.VISIBLE);
                mPresenter.onPayInfo();
            }else{
                ToastUtils.show(mContext,"请选择充值金额",1);
            }

        }
    }

    @Override
    public void initObject() {
        setMVP();
        setPayRv();
        onPayMsg();
        commonDialog = new CommonDialog(mContext);
    }

    private void setPayRv() {
        mPayInfoAdapter = new PayInfoAdapter(mContext);
        rv_pay.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv_pay.addItemDecoration(new DividerItemDecoration(mContext));
        rv_pay.setAdapter(mPayInfoAdapter);

        mPayInfoAdapter.setOnItemClickListener(new PayInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PayMsgResponse.PayMsg.PaymsgBean payMsg, int position) {
                mPayInfoAdapter.setCurrPosition(position);
                payId = payMsg.getId();

            }
        });
    }

    private void onPayMsg() {
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        mPresenter.onPayMsg();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorTip(int code, String msg) {

    }


    @Override
    public void setPayMsg(PayMsgResponse.PayMsg payMsg) {
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        tv_diamond.setText(payMsg.getDiamond() + "钻石");
        mPayInfoAdapter.setDatas(payMsg.getPaymsg());
        tv_pay_msg.setText(payMsg.getPayprompt());
    }

    @Override
    public PayInfoRequest getPayInfoRequest() {
        PayInfoRequest request = new PayInfoRequest();
        request.setId(payId);
        request.setPayFrom("charge");
        request.setPayModel("wxpay");
        request.setChannel("book");
        return request;
    }

    @Override
    public void setPayInfo(PayInfoResponse.PayInfo payInfo) {
        PayInfo mPayInfo = new PayInfo(mContext);
        mPayInfo.wxPay(payInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        def_pro.setVisibility(View.GONE);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        def_pro.setVisibility(View.GONE);
    }

    @Subscribe
    public void onEvents(String str) {
        if (str.equals(Constant.PAY_OK)) {
            onPayMsg();
            if (type == 2) {
                //如果是章节进入，支付成功后需要刷新章节内容
                EventBus.getDefault().post(Constant.PAY_CHAPTER);
                finish();
            } else if (type == 1) {
                commonDialog.setPayOkInfo();
            }
        }
    }
}
