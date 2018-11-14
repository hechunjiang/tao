package com.application.sven.huinews.config.http;

import android.content.Context;

import com.application.sven.huinews.AppConfig;
import com.application.sven.huinews.BuildConfig;
import com.application.sven.huinews.config.PhoneUtils;
import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.request.ActiavtePageRequest;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.AdsRequest;
import com.application.sven.huinews.entity.request.BaseRequest;
import com.application.sven.huinews.entity.request.BindWxRequest;
import com.application.sven.huinews.entity.request.BookChapterPayRequest;
import com.application.sven.huinews.entity.request.BookChapterRequest;

import com.application.sven.huinews.entity.request.BookContentRequest;
import com.application.sven.huinews.entity.request.BookDetailsRequest;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.request.BookShareRequest;
import com.application.sven.huinews.entity.request.BookShelfAddRequest;
import com.application.sven.huinews.entity.request.BookShelfDelRequest;
import com.application.sven.huinews.entity.request.BookShelfListRequest;
import com.application.sven.huinews.entity.request.BookStoreRequest;
import com.application.sven.huinews.entity.request.BookTopListRequest;
import com.application.sven.huinews.entity.request.BookisInStoreRequest;
import com.application.sven.huinews.entity.request.ChannelTypeRequest;
import com.application.sven.huinews.entity.request.CollectionRequest;
import com.application.sven.huinews.entity.request.FindPassRequest;
import com.application.sven.huinews.entity.request.FollowListRequest;
import com.application.sven.huinews.entity.request.LoginRequest;
import com.application.sven.huinews.entity.request.MovieDetailRequest;
import com.application.sven.huinews.entity.request.MovieListRequest;
import com.application.sven.huinews.entity.request.MovieMoreListRequest;
import com.application.sven.huinews.entity.request.MoviePayRequest;
import com.application.sven.huinews.entity.request.MovieResPlayRequest;
import com.application.sven.huinews.entity.request.MovieShareRequest;
import com.application.sven.huinews.entity.request.MovieVisitRequest;
import com.application.sven.huinews.entity.request.MovieWatchHistoryRequest;
import com.application.sven.huinews.entity.request.PayInfoRequest;
import com.application.sven.huinews.entity.request.RegisterRequest;
import com.application.sven.huinews.entity.request.SearchRequest;
import com.application.sven.huinews.entity.request.SetUserMsgRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.request.SmsCodeRequest;
import com.application.sven.huinews.entity.request.TempLoginRequest;
import com.application.sven.huinews.entity.request.UserMsgRequest;
import com.application.sven.huinews.entity.request.UserVideoRequest;
import com.application.sven.huinews.entity.request.VideoCollectionCancelRequest;
import com.application.sven.huinews.entity.request.VideoCollectionRequest;
import com.application.sven.huinews.entity.request.VideoCommentLikeRequest;
import com.application.sven.huinews.entity.request.VideoCommentRequest;
import com.application.sven.huinews.entity.request.VideoDetailsRequest;
import com.application.sven.huinews.entity.request.VideoLikeRequest;
import com.application.sven.huinews.entity.request.VideoListRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.request.VideoShareUrlRequest;
import com.application.sven.huinews.entity.request.VideoTaskRequest;
import com.application.sven.huinews.entity.response.BaseResponse;
import com.application.sven.huinews.utils.HasUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.TimeUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * auther: sunfuyi
 * data: 2018/5/12
 * effect:
 */
public class MyRetrofit {
    private Context mContext;
    private static MyRetrofit mInstance;
    private HttpService mHttpService;

    public MyRetrofit(Context mContext, String httpUrl) {
        this.mContext = mContext;
        String http = null;
        if (httpUrl == null) {
            http = Api.BASE_URL;
        } else {
            http = httpUrl;
        }

        LogUtil.showLog("msg-----baseUrl:" + http);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(http)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mHttpService = retrofit.create(HttpService.class);
    }


    public static MyRetrofit getInstance(Context context, String httpUrl) {
        if (mInstance == null) {
            synchronized (MyRetrofit.class) {
                if (mInstance == null) {
                    mInstance = new MyRetrofit(context, httpUrl);
                }
            }
        }
        return mInstance;
    }


    public HttpService getService() {
        return mHttpService;
    }


    /**
     * 头部信息
     *
     * @return
     */
    private HashMap<String, String> getHeaderMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put(Api.HEADER_OS, "android");
        map.put(Api.HEADER_MEID, PhoneUtils.getAndroidId(mContext));
        map.put(Api.HEADER_VERSION, BuildConfig.VERSION_NAME);
        map.put(Api.HEADER_TICKET, UserSpCache.getInstance(mContext).getStringData(UserSpCache.KEY_TICKET));
        return map;
    }

    /**
     * 当前时间
     *
     * @return
     */
    private String nowTime() {
        TimeUtils timeUtils = new TimeUtils();
        return timeUtils.phoneTime(mContext) + "";
    }

    /**
     * 随机字符串
     *
     * @return
     */
    private String nonceStr() {
        return HasUtils.getInstance().nonceStr();
    }

    /**
     * 配置签名
     *
     * @param time
     * @param nonceStr
     * @param json
     * @return
     */
    private String signStr(String time, String nonceStr, String json) {
        try {

            //  String ticket = "g3hz2q50eq9_eXSts6yzp8WVh9mGuYenhYuyn4iOfGmFiJbNu5qGaYuJgGuz3rebsc2ZzIOXe2GQi8eskouAaoChqtm7YqWtimeNqb-7xGrIq3uVkLZvbQ";
            String ticket = UserSpCache.getInstance(mContext).getStringData(UserSpCache.KEY_TICKET);
            return HasUtils.getInstance().sign(time, nonceStr, json, ticket);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取配置信息
     *
     * @param callBack
     */
    public void appConfig(DataCallBack callBack) {

     /*   String json = "{\"id\":389432,\"type\":1}";
        String sign = signStr("1530002758225", "VMNqnrfDpuj", json);

        LogUtil.showLog("msg----加密：" + sign);*/
        mHttpService.appConfig(getHeaderMap(), 1)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 临时用户登录
     *
     * @param request
     * @param callBack
     */
    public void onTempLogin(TempLoginRequest request, DataCallBack callBack) {
        String json = SignJson.singOnTempLogin(request.getMobileBrand());
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));


        mHttpService.onTempLogin(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 频道信息
     *
     * @param request
     * @param callBack
     */
    public void onChannelType(ChannelTypeRequest request, DataCallBack callBack) {
        //  String json = SignJson.signChannelType(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), ""));
        mHttpService.onChannelType(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 视频列表获取
     *
     * @param request
     * @param callBack
     */
    public void onVideoList(VideoListRequest request, DataCallBack callBack) {
        String json = SignJson.signVideoList(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));

        mHttpService.onVideoList(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 影视首页列表获取
     *
     * @param request
     * @param callBack
     */
    public void onMovieList(MovieListRequest request, DataCallBack callBack) {
        String json = SignJson.signMovieList(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));

        mHttpService.onMovieList(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 影视详情
     *
     * @param request
     * @param callBack
     */
    public void onMovieDetail(MovieDetailRequest request, DataCallBack callBack) {
        String json = SignJson.signMovieDetail(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));

        mHttpService.onMovieDetail(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 视频解析
     *
     * @param request
     * @param callBack
     */
    public void onMovieResPlay(MovieResPlayRequest request, DataCallBack callBack) {
        String json = SignJson.signMovieResPlay(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));

        mHttpService.onMovieResPlay(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 影视分享
     *
     * @param request
     * @param callBack
     */
    public void onMovieShare(MovieShareRequest request, DataCallBack callBack) {
        String json = SignJson.signMovieShare(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));

        mHttpService.onMovieShate(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 影视金币支付
     *
     * @param request
     * @param callBack
     */
    public void onMoviePay(MoviePayRequest request, DataCallBack callBack) {
        String json = SignJson.signMoviePay(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));

        mHttpService.onMoviePay(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 用户信息
     *
     * @param request
     * @param callBack
     */
    public void onUserMsg(UserMsgRequest request, DataCallBack callBack) {
        String json = SignJson.signUserMsg(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));

        mHttpService.onUserMsg(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 用户发布的视频
     *
     * @param request
     * @param callBack
     */
    public void onUserVideo(UserVideoRequest request, DataCallBack callBack) {
        String json = SignJson.signUserVideo(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));

        mHttpService.onUserVideo(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 用户关注列表
     *
     * @param callBack
     */
    public void onFollowList(FollowListRequest request, DataCallBack callBack) {
        String json = SignJson.signFollowList(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onFollowList(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 关注用户
     *
     * @param request
     * @param callBack
     */
    public void onFollowUser(UserMsgRequest request, DataCallBack callBack) {
        String json = SignJson.signUserMsg(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));

        mHttpService.onFollowUser(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 取消关注
     *
     * @param request
     * @param callBack
     */
    public void onCancelFollowUser(UserMsgRequest request, DataCallBack callBack) {
        String json = SignJson.signUserMsg(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));

        mHttpService.onCancelFollowUser(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 视频点赞
     *
     * @param request
     * @param callBack
     */
    public void onVideoLike(VideoLikeRequest request, DataCallBack callBack) {
        String json = SignJson.signVideoLike(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onVideoLike(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 收藏视频or收藏影视
     *
     * @param request
     * @param callBack
     */
    public void onVideoCollection(VideoCollectionRequest request, DataCallBack callBack) {
        String json = SignJson.signVideoCollection(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onVideoCollection(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 视频评论列表
     *
     * @param request
     * @param callBack
     */
    public void onVideoComment(VideoCommentRequest request, DataCallBack callBack) {
        String json = SignJson.signVideoComment(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onVideoComment(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 获取用户个人信息
     *
     * @param callBack
     */
    public void onUserIndex(DataCallBack callBack) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setNonce_str(nonceStr());
        baseRequest.setTime(nowTime());
        baseRequest.setSign(signStr(baseRequest.getTime(), baseRequest.getNonce_str(), ""));
        mHttpService.onUserIndex(getHeaderMap(), baseRequest)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 用户身份信息
     *
     * @param callBack
     */
    public void onUserFlag(DataCallBack callBack) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setNonce_str(nonceStr());
        baseRequest.setTime(nowTime());
        baseRequest.setSign(signStr(baseRequest.getTime(), baseRequest.getNonce_str(), ""));
        mHttpService.onUserFlag(getHeaderMap(), baseRequest)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 短信验证
     *
     * @param request
     * @param callBack
     */
    public void onSmsCode(SmsCodeRequest request, DataCallBack callBack) {
        String json = SignJson.signGetSmsCode(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onSmsCode(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 手机注册
     *
     * @param request
     * @param callBack
     */
    public void onRegister(RegisterRequest request, DataCallBack callBack) {
        request.setMobileBrand(PhoneUtils.getPhoneBrand());
        String json = SignJson.singRegister(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onRegister(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 登录
     *
     * @param request
     * @param callBack
     */
    public void onLogin(LoginRequest request, DataCallBack callBack) {
        String json = SignJson.singOnLogin(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onLogin(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 微信登录
     *
     * @param request
     * @param callBack
     */
    public void onCheckIsBindWx(BindWxRequest request, DataCallBack callBack) {
        String json = SignJson.signBindWx(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.checkIsBindWx(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 微信绑定注册
     *
     * @param request
     * @param callBack
     */
    public void onWxRegister(RegisterRequest request, DataCallBack callBack) {
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), SignJson.singRegister(request)));
        mHttpService.onWxRegister(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 收藏列表
     *
     * @param request
     * @param callBack
     */
    public void onCollection(CollectionRequest request, DataCallBack callBack) {
        String json = SignJson.signCollection(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onCollection(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 取消收藏
     *
     * @param request
     * @param callBack
     */
    public void onCollectionCancel(VideoCollectionCancelRequest request, DataCallBack callBack) {
        String json = SignJson.signCollectionCancel(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onCancelCollection(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 影视统计
     *
     * @param request
     * @param callBack
     */
    public void onMovieVisit(MovieVisitRequest request, DataCallBack callBack) {
        String json = SignJson.signMovieVisit(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onMovieVisit(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 观看历史
     *
     * @param request
     * @param callBack
     */
    public void onMovieHistory(MovieWatchHistoryRequest request, DataCallBack callBack) {
        String json = SignJson.signMovieHistory(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onMovieHistory(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 个人中心活动轮播
     *
     * @param request
     * @param callBack
     */
    public void onActiavtePage(ActiavtePageRequest request, DataCallBack callBack) {
        String json = SignJson.signActiavtePage(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.actiavtePage(getHeaderMap(), request).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 更多影视
     *
     * @param request
     * @param callBack
     */
    public void onMovieMoreList(MovieMoreListRequest request, DataCallBack callBack) {
        String json = SignJson.signMovieMoreList(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onMovieMoreList(getHeaderMap(), request).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 搜索
     *
     * @param request
     * @param callBack
     */
    public void onSearch(SearchRequest request, DataCallBack callBack) {
        String json = SignJson.signSearch(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onSearch(getHeaderMap(), request).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 热门搜索
     *
     * @param callBack
     */
    public void onSearchHot(DataCallBack callBack) {
        BaseRequest request = new BaseRequest();
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), ""));
        mHttpService.onHotSearch(getHeaderMap(), request).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 视频分享
     *
     * @param request
     * @param callBack
     */
    public void onVideoShare(VideoShareUrlRequest request, DataCallBack callBack) {
        String json = SignJson.signVideoShare(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onVideoShare(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 分享计数
     *
     * @param request
     * @param callBack
     */

    public void onShareVisit(ShareVisitRequest request, DataCallBack callBack) {
        String json = SignJson.signShareVisit(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.shareVisit(getHeaderMap(), request).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 视频举报
     *
     * @param request
     * @param callBack
     */
    public void onVideoReport(VideoReportRequest request, DataCallBack callBack) {
        String json = SignJson.signVideoReport(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.videoReport(getHeaderMap(), request).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 金币任务
     *
     * @param request
     * @param callBack
     */
    public void onGoldByTask(VideoTaskRequest request, DataCallBack callBack) {
        String json = SignJson.signTaskGold(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onGoldByTask(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 视频添加评论
     *
     * @param request
     * @param callBack
     */
    public void onVideoAdComment(AdCommentRequest request, DataCallBack callBack) {
        String json = SignJson.signVideoAdComment(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onVideoAdComment(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 首页活动（新手一元提现和活动）
     *
     * @param callBack
     */
    public void onTaskPush(DataCallBack callBack) {
        BaseRequest request = new BaseRequest();
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), ""));
        mHttpService.onPushActivate(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 新人领取红包
     *
     * @param callBack
     */
    public void onRedBag(DataCallBack callBack) {
        BaseRequest request = new BaseRequest();
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), ""));
        mHttpService.onRedBag(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 评论点赞
     *
     * @param request
     * @param callBack
     */
    public void videoCommentLike(VideoCommentLikeRequest request, DataCallBack callBack) {
        String json = SignJson.signVideoCommentLike(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onVideoCommentLike(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 评论举报
     *
     * @param request
     * @param callBack
     */
    public void videoCommentRepost(VideoReportRequest request, DataCallBack callBack) {
        String json = SignJson.signVideoReport(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onVideoCommentReport(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 任务红点显示or隐藏
     *
     * @param callBack
     */
    public void onHitsInfo(DataCallBack callBack) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setNonce_str(nonceStr());
        baseRequest.setTime(nowTime());
        baseRequest.setSign(signStr(baseRequest.getTime(), baseRequest.getNonce_str(), ""));
        mHttpService.onHintsInfo(getHeaderMap(), baseRequest).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 视频详情
     *
     * @param request
     * @param callBack
     */
    public void onVideoDetails(VideoDetailsRequest request, DataCallBack callBack) {
        String json = SignJson.signVideoDetails(request);
        request.setNonce_str(nonceStr());
        request.setTime(nowTime());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onVideoDetails(getHeaderMap(), request).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 广告接口
     *
     * @param request
     * @param callBack
     */
    public void onAds(AdsRequest request, DataCallBack callBack) {
        String json = SignJson.signAdver(request);
        request.setNonce_str(nonceStr());
        request.setTime(nowTime());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onAds(getHeaderMap(), request).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 广告曝光
     *
     * @param s
     * @param callBack
     */
    public void adImp(String s, DataCallBack callBack) {
        mHttpService.adExposure(s)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 广告点击
     *
     * @param s
     * @param callBack
     */
    public void adClick(String s, DataCallBack callBack) {
        mHttpService.adClick(s)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 找回密码
     *
     * @param request
     * @param callBack
     */
    public void onFindPass(FindPassRequest request, DataCallBack callBack) {
        String json = SignJson.signFindPass(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.findPass(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 视频播放统计
     *
     * @param request
     * @param callBack
     */
    public void onVideoVisit(VideoShareUrlRequest request, DataCallBack callBack) {
        String json = SignJson.signVideoShare(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onVideoVisit(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 每天观看视频获取金币爽
     *
     * @param callBack
     */
    public void onVideoWatchCount(DataCallBack callBack) {
        BaseRequest request = new BaseRequest();
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), ""));
        mHttpService.onWatchCount(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 获取书籍分类列表
     *
     * @param callBack
     */
    public void onBookList(BookListRequest request, DataCallBack callBack) {
        String json = SignJson.signBookList(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onBookList(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 获取书籍详情
     *
     * @param callBack
     */
    public void onBookDetails(BookDetailsRequest request, DataCallBack callBack) {
        String json = SignJson.signBookDetails(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onBookDetails(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 章节列表
     *
     * @param request
     * @param callBack
     */
    public void onBookChapters(BookChapterRequest request, DataCallBack callBack) {
        String json = SignJson.signBookChapters(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onBookChapters(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 书库
     *
     * @param request
     * @param callBack
     */
    public void onBookStore(BookStoreRequest request, DataCallBack callBack) {
        String json = SignJson.signBookStore(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onBookStore(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 小说分类
     *
     * @param callBack
     */
    public void onBookCategroy(DataCallBack callBack) {
        BaseRequest request = new BaseRequest();
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), ""));
        mHttpService.onBookCategroy(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 排行榜
     *
     * @param request
     * @param callBack
     */
    public void onBookTopList(BookTopListRequest request, DataCallBack callBack) {
        String json = SignJson.signBookList(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onBookTopList(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 加入书库
     *
     * @param request
     * @param callBack
     */
    public void onBookAdd(BookShelfAddRequest request, DataCallBack callBack) {
        String json = SignJson.signBookAdd(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onBookAdd(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 书架列表
     *
     * @param request
     * @param callBack
     */
    public void onBookShelfList(BookShelfListRequest request, DataCallBack callBack) {
        String json = SignJson.signBookShelfList(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onBookShelfList(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 删除书架
     *
     * @param request
     * @param callBack
     */
    public void onBookShelfDel(BookShelfDelRequest request, DataCallBack callBack) {
        String json = SignJson.signBookShelfDel(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onBookDel(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 充值信息
     *
     * @param callBack
     */
    public void onPayMsg(DataCallBack callBack) {
        BaseRequest request = new BaseRequest();
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), ""));
        mHttpService.onPayMsg(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 判断书籍是否加入书架
     *
     * @param request
     * @param callBack
     */
    public void onBookIsInStore(BookisInStoreRequest request, DataCallBack callBack) {
        String json = SignJson.signBookInStore(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onBookInStore(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 书籍内容
     *
     * @param request
     * @param callBack
     */
    public void onBookContent(BookContentRequest request, DataCallBack callBack) {
        String json = SignJson.signBookContent(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onBookContent(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 充值订单获取
     *
     * @param callBack
     */
    public void onPayInfo(PayInfoRequest request, DataCallBack callBack) {
        String json = SignJson.signPayInfo(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onPayInfo(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 章节支付
     *
     * @param request
     * @param callBack
     */
    public void onChapterPay(BookChapterPayRequest request, DataCallBack callBack) {
        String json = SignJson.signChapterPay(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onBookChapterPay(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }


    /**
     * 是否自动扣除钻石
     *
     * @param request
     * @param callBack
     */
    public void onAutoPay(SetUserMsgRequest request, DataCallBack callBack) {
        String json = SignJson.signAutoPay(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onSetUserMsg(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 推送key
     *
     * @param request
     * @param callBack
     */
    public void onPushKey(SetUserMsgRequest request, DataCallBack callBack) {
        String json = SignJson.signPushKey(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onSetUserMsg(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 小说分享
     *
     * @param request
     * @param callBack
     */
    public void onBookShare(BookShareRequest request, DataCallBack callBack) {
        String json = SignJson.signBookShare(request);
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), json));
        mHttpService.onBookShare(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 赠送金币
     *
     * @param callBack
     */
    public void onGiveGold(DataCallBack callBack) {
        BaseRequest request = new BaseRequest();
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), ""));
        mHttpService.onGiveGold(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    /**
     * 领取金币
     */
    public void onToGiveGold(DataCallBack callBack) {
        BaseRequest request = new BaseRequest();
        request.setTime(nowTime());
        request.setNonce_str(nonceStr());
        request.setSign(signStr(request.getTime(), request.getNonce_str(), ""));
        mHttpService.onToGiveGold(getHeaderMap(), request)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }
}
