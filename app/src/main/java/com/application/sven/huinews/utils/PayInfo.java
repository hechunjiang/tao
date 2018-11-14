package com.application.sven.huinews.utils;

import android.content.Context;

import com.application.sven.huinews.entity.response.PayInfoResponse;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect:
 */
public class PayInfo {
    private Context mContext;

    public PayInfo(Context mContext) {
        this.mContext = mContext;
    }

    public void wxPay(PayInfoResponse.PayInfo response) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(mContext, response.getAppid(), false);
        PayReq request = new PayReq();
        //签名
        String stringSignTemp = "appid=" + response.getAppid() + "&noncestr=" + response.getNoncestr() + "&package=Sign=WXPay&partnerid=" + response.getPartnerid() + "&prepayid=" + response.getPrepayid() + "&timestamp=" + response.getTimestamp() + "&key=" + "dvsdvdvsvhbsdhvbsdgvgsdvsdhvbshd";
        String sign = MD5.getMessageDigest(stringSignTemp.getBytes()).toUpperCase();


        request.appId = response.getAppid();
        request.partnerId = response.getPartnerid();
        request.prepayId = response.getPrepayid();
        request.packageValue = response.getPackageX();
        request.nonceStr = response.getNoncestr();
        request.timeStamp = response.getTimestamp();
        request.sign = sign;
        msgApi.sendReq(request);
    }
}
