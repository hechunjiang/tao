package com.application.sven.huinews.config.http;

import android.text.TextUtils;

import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.entity.request.ActiavtePageRequest;
import com.application.sven.huinews.entity.request.AdCommentRequest;
import com.application.sven.huinews.entity.request.AdsRequest;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class SignJson {
    /**
     * 临时登录
     *
     * @param mobile_brand
     * @return
     */
    public static String singOnTempLogin(String mobile_brand) {
        ParmsUtils parmsUtils = new ParmsUtils();
        if (!TextUtils.isEmpty(mobile_brand)) {
            parmsUtils.getPostBody(Api.MOBILE_BRAND, mobile_brand);
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 获取频道信息
     *
     * @param request
     * @return
     */
   /* public static String signChannelType(ChannelTypeRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();

        parmsUtils.getPostBody(Api.TYPE, request.getType() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }*/

    /**
     * 视频信息
     *
     * @param request
     * @return
     */
    public static String signVideoList(VideoListRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.TYPE_ID, request.getTypeId() + "");
        parmsUtils.getPostBody(Api.PAGE, request.getPage() + "");
        parmsUtils.getPostBody(Api.LIMIT, request.getLimit() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 影视列表
     *
     * @param request
     * @return
     */
    public static String signMovieList(MovieListRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.TYPE_ID, request.getTypeId() + "");

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 影视详情
     *
     * @param request
     * @return
     */
    public static String signMovieDetail(MovieDetailRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.ID, request.getMovieId() + "");

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 获取视频解析源
     *
     * @param request
     * @return
     */
    public static String signMovieResPlay(MovieResPlayRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.PLAY_ID, request.getPlayId() + "");
        parmsUtils.getPostBody(Api.A_ID, request.getaId() + "");
        parmsUtils.getPostBody(Api.R_ID, request.getrId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 影视分享
     *
     * @param request
     * @return
     */
    public static String signMovieShare(MovieShareRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.MOVIES_ID, request.getMovies_id() + "");
        parmsUtils.getPostBody(Api.PLAY__ID, request.getPlay_id() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    public static String signMoviePay(MoviePayRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.MOVIESID, request.getMoviesId() + "");
        parmsUtils.getPostBody(Api.PLAYID, request.getPlayId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    public static String signUserMsg(UserMsgRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.USER_ID, request.getUser_id() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 获取用户发布的视频
     *
     * @param request
     * @return
     */
    public static String signUserVideo(UserVideoRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.USER_ID, request.getUser_id() + "");
        parmsUtils.getPostBody(Api.PAGE, request.getPage() + "");
        parmsUtils.getPostBody(Api.LIMIT, request.getLimit() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 视频点赞
     *
     * @param request
     * @return
     */
    public static String signVideoLike(VideoLikeRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.TYPE, request.getType() + "");
        parmsUtils.getPostBody(Api.ID, request.getId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 视频收藏or小视频收藏
     *
     * @param request
     * @return
     */
    public static String signVideoCollection(VideoCollectionRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.TYPE, request.getType() + "");
        parmsUtils.getPostBody(Api.R_ID, request.getrId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 视频评论列表
     *
     * @param request
     * @return
     */
    public static String signVideoComment(VideoCommentRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.PAGE, request.getPage());
        parmsUtils.getPostBody(Api.ORDER, request.getOrder());
        parmsUtils.getPostBody(Api.VIDEO_ID, request.getVideo_id());
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 短信验证码
     *
     * @param request
     * @return
     */
    public static String signGetSmsCode(SmsCodeRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.PHONE, request.getPhone());
        parmsUtils.getPostBody(Api.TYPE, request.getType());
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 注册
     *
     * @param request
     * @return
     */
    public static String singRegister(RegisterRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        if (!TextUtils.isEmpty(request.getPhone())) {
            parmsUtils.getPostBody(Api.PHONE, request.getPhone());
        }
        if (!TextUtils.isEmpty(request.getPass())) {
            parmsUtils.getPostBody(Api.PASS, request.getPass());
        }
        if (!TextUtils.isEmpty(request.getHeadIcon())) {
            parmsUtils.getPostBody(Api.HEAD_IMG, request.getHeadIcon());
        }
        parmsUtils.getPostBody(Api.F_INVIT_CODE, request.getInvitCode());
        if (!TextUtils.isEmpty(request.getRegisterCode())) {
            parmsUtils.getPostBody(Api.VERIFY, request.getRegisterCode());
        }
        if (!TextUtils.isEmpty(request.getMobileBrand())) {
            parmsUtils.getPostBody(Api.MOBILE_BRAND, request.getMobileBrand());
        }
        if (!TextUtils.isEmpty(request.getOpenId())) {
            parmsUtils.getPostBody(Api.OPEN_ID, request.getOpenId());
        }
        if (!TextUtils.isEmpty(request.getNickName())) {
            parmsUtils.getPostBody(Api.NICK_NAME, request.getNickName());
        }
        if (!TextUtils.isEmpty(request.getSex())) {
            parmsUtils.getPostBody(Api.SEX, request.getSex());
        }
        if (!TextUtils.isEmpty(request.getUnionid())) {
            parmsUtils.getPostBody(Api.UNIONID, request.getUnionid());
        }

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 登录
     *
     * @param request
     * @return
     */
    public static String singOnLogin(LoginRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        if (!TextUtils.isEmpty(request.getAccount())) {
            parmsUtils.getPostBody(Api.PHONE, request.getAccount());
        }
        if (!TextUtils.isEmpty(request.getPassword())) {
            parmsUtils.getPostBody(Api.PASS, request.getPassword());
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);

        return json;
    }

    /**
     * 绑定微信
     *
     * @param request
     * @return
     */
    public static String signBindWx(BindWxRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        if (request.getUserName() != null && !request.getUserName().equals("")) {
            parmsUtils.getPostBody(Api.NICK_NAME, request.getUserName() + "");
        }
        if (request.getHeadImg() != null && !request.getHeadImg().equals("")) {
            parmsUtils.getPostBody(Api.HEAD_IMG, request.getHeadImg() + "");
        }
        if (request.getOpenId() != null && !request.getOpenId().equals("")) {
            parmsUtils.getPostBody(Api.OPEN_ID, request.getOpenId() + "");
        }
        if (request.getSex() != null && !request.getSex().equals("")) {
            parmsUtils.getPostBody(Api.SEX, request.getSex() + "");
        }
        if (!TextUtils.isEmpty(request.getUnionid())) {
            parmsUtils.getPostBody(Api.UNIONID, request.getUnionid());
        }

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 收藏列表
     *
     * @param request
     * @return
     */
    public static String signCollection(CollectionRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.TYPE, request.getType() + "");
        parmsUtils.getPostBody(Api.PAGE, request.getPage() + "");
        parmsUtils.getPostBody(Api.LIMIT, request.getLimit() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 取消收藏
     *
     * @param request
     * @return
     */
    public static String signCollectionCancel(VideoCollectionCancelRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.TYPE, request.getType() + "");
        parmsUtils.getPostBody(Api.R_ID, request.getrId() + "");
        parmsUtils.getPostBody(Api.DEL_TYPE, request.getDelType() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 影视统计
     *
     * @param request
     * @return
     */
    public static String signMovieVisit(MovieVisitRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.MOVIES_ID, request.getMovies_id() + "");
        parmsUtils.getPostBody(Api.PLAY__ID, request.getPlay_id() + "");
        parmsUtils.getPostBody(Api.PLAY__TIME, request.getPlay_time() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 观看历史
     *
     * @param request
     * @return
     */
    public static String signMovieHistory(MovieWatchHistoryRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.TYPE, request.getType() + "");
        parmsUtils.getPostBody(Api.LIMIT, request.getLimit() + "");
        parmsUtils.getPostBody(Api.PAGE, request.getPage() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 获取轮播
     *
     * @param request
     * @return
     */
    public static String signActiavtePage(ActiavtePageRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.TYPE, request.getType());
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 更多影视
     *
     * @param request
     * @return
     */
    public static String signMovieMoreList(MovieMoreListRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.KEY_WORDS, request.getKeywords());
        parmsUtils.getPostBody(Api.LIMIT, request.getLimit() + "");
        parmsUtils.getPostBody(Api.PAGE, request.getPage() + "");
        parmsUtils.getPostBody(Api.BU_ID, request.getBuId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 搜索
     *
     * @param request
     * @return
     */
    public static String signSearch(SearchRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.KEY_WORDS, request.getKeywords());
        parmsUtils.getPostBody(Api.TYPE, request.getType());
        parmsUtils.getPostBody(Api.LIMIT, request.getLimit() + "");
        parmsUtils.getPostBody(Api.PAGE, request.getPage() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 视频分享
     *
     * @param request
     * @return
     */
    public static String signVideoShare(VideoShareUrlRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.VIDEO_ID, request.getVideoId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 分享计数签名
     *
     * @param request
     * @return
     */
    public static String signShareVisit(ShareVisitRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.ACTIVITY_TYPE, request.getActivityType());
        parmsUtils.getPostBody(Api.CODE, request.getCode());
        parmsUtils.getPostBody(Api.SHARE_CHANNEL, request.getShareChannel());
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 举报
     *
     * @param request
     * @return
     */
    public static String signVideoReport(VideoReportRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        if (!TextUtils.isEmpty(request.getVideoId())) {
            parmsUtils.getPostBody(Api.ID, request.getVideoId());
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 关注列表
     *
     * @param request
     * @return
     */
    public static String signFollowList(FollowListRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.LIMIT, request.getLimit() + "");
        parmsUtils.getPostBody(Api.PAGE, request.getPage() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 金币任务
     *
     * @param request
     * @return
     */
    public static String signTaskGold(VideoTaskRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.ID, request.getId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 添加评论
     *
     * @param request
     * @return
     */
    public static String signVideoAdComment(AdCommentRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.CONTENT, request.getCommentContent());
        parmsUtils.getPostBody(Api.VIDEO_ID, request.getVideoId() + "");
        Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 评论点赞
     *
     * @param request
     * @return
     */
    public static String signVideoCommentLike(VideoCommentLikeRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.COMMENT_ID, request.getId());
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 广告接口
     *
     * @param request
     * @return
     */
    public static String signAdver(AdsRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        if (!TextUtils.isEmpty(request.getUser_agent())) {
            parmsUtils.getPostBody(Api.USER_AGENT, request.getUser_agent());
        }
        if (!TextUtils.isEmpty(request.getWidth())) {
            parmsUtils.getPostBody(Api.WIDTH, request.getWidth());
        }
        if (!TextUtils.isEmpty(request.getHeight())) {
            parmsUtils.getPostBody(Api.HEIGHT, request.getHeight());
        }
        if (!TextUtils.isEmpty(request.getMobile_brand())) {
            parmsUtils.getPostBody(Api.MOBILE_BRAND, request.getMobile_brand());
        }
        if (!TextUtils.isEmpty(request.getMobile_model())) {
            parmsUtils.getPostBody(Api.MOBILE_MODEL, request.getMobile_model());
        }
        if (!TextUtils.isEmpty(request.getMobile_version())) {
            parmsUtils.getPostBody(Api.MOBILE_VERSION, request.getMobile_version());
        }
        if (!TextUtils.isEmpty(request.getIp())) {
            parmsUtils.getPostBody(Api.IP, request.getIp());
        }
        if (!TextUtils.isEmpty(request.getPage() + "")) {
            parmsUtils.getPostBody(Api.PAGE, request.getPage() + "");
        }

        if (!TextUtils.isEmpty(request.getNetwork_type() + "")) {
            parmsUtils.getPostBody(Api.NETWORD_TYPE, request.getNetwork_type() + "");
        }

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 小视频详情
     *
     * @param request
     * @return
     */
    public static String signVideoDetails(VideoDetailsRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.R_ID, request.getrId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 找回密码
     *
     * @param request
     * @return
     */
    public static String signFindPass(FindPassRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        if (!TextUtils.isEmpty(request.getPhone())) {
            parmsUtils.getPostBody(Api.PHONE, request.getPhone());
        }
        if (!TextUtils.isEmpty(request.getPass())) {
            parmsUtils.getPostBody(Api.PASS, request.getPass());
        }
        if (!TextUtils.isEmpty(request.getCode())) {
            parmsUtils.getPostBody(Api.VERIFY, request.getCode());
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }


    /**
     * 书籍分类列表
     *
     * @param request
     * @return
     */
    public static String signBookList(BookListRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        if (!TextUtils.isEmpty(request.getTypeId() + "")) {
            parmsUtils.getPostBody(Api.TYPE_ID, request.getTypeId() + "");
        }

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 书籍详情
     *
     * @param request
     * @return
     */
    public static String signBookDetails(BookDetailsRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        if (!TextUtils.isEmpty(request.getId() + "")) {
            parmsUtils.getPostBody(Api.ID, request.getId() + "");
        }

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 章节列表
     *
     * @param request
     * @return
     */
    public static String signBookChapters(BookChapterRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();

        parmsUtils.getPostBody(Api.ID, request.getId() + "");
        parmsUtils.getPostBody(Api.SORT, request.getSort() + "");

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 书库查询
     *
     * @param request
     * @return
     */
    public static String signBookStore(BookStoreRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();

        parmsUtils.getPostBody(Api.KEY_WORDS, request.getKeywords() + "");
        parmsUtils.getPostBody(Api.SORT, request.getSort() + "");
        parmsUtils.getPostBody(Api.CATY_ID, request.getCaty_id() + "");
        parmsUtils.getPostBody(Api.TYPE, request.getType() + "");
        parmsUtils.getPostBody(Api.IS_END, request.getIs_end() + "");

        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 排行榜
     *
     * @param request
     * @return
     */
    public static String signBookList(BookTopListRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();

        parmsUtils.getPostBody(Api.TYPE, request.getType() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 添加书库
     *
     * @param request
     * @return
     */
    public static String signBookAdd(BookShelfAddRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.BOOK_ID, request.getBookId() + "");
        parmsUtils.getPostBody(Api.CHAPTER_ID, request.getChapterId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 书架列表
     *
     * @param request
     * @return
     */
    public static String signBookShelfList(BookShelfListRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.LIMIT, request.getLimit() + "");
        parmsUtils.getPostBody(Api.PAGE, request.getPage() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 删除书架
     *
     * @param request
     * @return
     */
    public static String signBookShelfDel(BookShelfDelRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.TYPE, request.getType() + "");
        parmsUtils.getPostBody(Api.BOOK_ID, request.getBookId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 书籍是否加入书架
     *
     * @param request
     * @return
     */
    public static String signBookInStore(BookisInStoreRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.BOOK_ID, request.getBookId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 书籍内容
     *
     * @param request
     * @return
     */
    public static String signBookContent(BookContentRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.BOOK_ID, request.getBookId() + "");
        parmsUtils.getPostBody(Api.CHAPTER_ID, request.getChapterId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 订单信息
     *
     * @param request
     * @return
     */
    public static String signPayInfo(PayInfoRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.PAY_FROM, request.getPayFrom() + "");
        parmsUtils.getPostBody(Api.PAY_MODEL, request.getPayModel() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 章节支付
     *
     * @param request
     * @return
     */
    public static String signChapterPay(BookChapterPayRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.TYPE, request.getType() + "");
        parmsUtils.getPostBody(Api.BOOK_ID, request.getBookId() + "");
        parmsUtils.getPostBody(Api.CHAPTER_ID, request.getChapterId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 自动扣除钻石
     *
     * @param request
     * @return
     */
    public static String signAutoPay(SetUserMsgRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.IS_DEDUCITON, request.getIs_deduction() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 发送友盟key
     * @param request
     * @return
     */
    public static String signPushKey(SetUserMsgRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.DEVICE_TOKENS, request.getIs_deduction() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }

    /**
     * 书籍分享
     *
     * @param request
     * @return
     */
    public static String signBookShare(BookShareRequest request) {
        ParmsUtils parmsUtils = new ParmsUtils();
        parmsUtils.getPostBody(Api.BOOK_ID, request.getBookId() + "");
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(parmsUtils.params);
        return json;
    }
}
