package com.application.sven.huinews.config.api;

import com.application.sven.huinews.BuildConfig;

/**
 * auther: sunfuyi
 * data: 2018/5/12
 * effect:
 */
public class Api {
    //interface
    public static final String APP = "app/";
    //base
    public static final String BASE_URL = BuildConfig.BASE_URL + APP; //测试地址
    public static final String BASE_WEB_URL = "http://www.aizhuifan.cn/";//测试H5地址
    public static final String BASE_H5 = BuildConfig.BASE_WEB_URL + "Version_2.0/personal/";
    public static final String PAY_URL = "https://android.news.88acca.com/" + APP;

    //h5
    public static final String MASTERLEVEL = BASE_H5 + "masterLevel.html"; //金牌师傅
    public static final String TIPS = BASE_H5 + "tips.html"; //赚钱秘籍
    public static final String MY_INCOME_TYPE1 = BASE_H5 + "incomeDetails.html?type=1"; //我的金币/兑换体现/收益明细
    public static final String MY_INCOME_TYPE2 = BASE_H5 + "incomeDetails.html?type=2"; //我的余额
    public static final String TASK_HALL = BASE_H5 + "task_hall.html"; //任务大厅
    public static final String MAKEMONEY = BASE_H5 + "makeMoney.html"; //邀请收徒
    public static final String ENTERCODE = BASE_H5 + "enterCode.html"; //输入邀请码
    public static final String WITHDRAW = BASE_H5 + "1yuan_withdraw.html"; //新手一元提现
    public static final String HELP_FEEDBACK = BASE_H5 + "problems.html"; //帮助与反馈
    public static final String MESSAGE_CENTER = BASE_H5 + "messageCenter.html"; //消息
    public static final String TOP_TEN = BASE_H5 + "bookRank.html"; //排行榜
    public static final String MY_DIAMOND = BASE_H5 + "myDiamond.html?type=1"; //我的钻石


    //config
    public static final String HEADER_OS = "os";
    public static final String HEADER_MEID = "meid";
    public static final String HEADER_VERSION = "version";
    public static final String HEADER_TICKET = "ticket";


    public static final String APP_CONFIG = "Setting/config"; //配置
    public static final String LOGIN_TEMP = "Reg/f_reg"; //临时用户登录
    public static final String MOBILE_BRAND = "mobile_brand";
    public static final String SET_USER_MSG = "user/setUserMsg"; //用户信息新增

    //sign parm
    public static final String TYPE_ID = "typeId";
    public static final String TYPE = "type";
    public static final String IS_DEDUCITON = "is_deduction";
    public static final String DEVICE_TOKENS = "device_tokens";
    public static final String PAGE = "page";
    public static final String BU_ID = "buId";
    public static final String LIMIT = "limit";
    public static final String ID = "id";
    public static final String PLAY_ID = "playId";
    public static final String A_ID = "aId";
    public static final String R_ID = "rId";
    public static final String USER_ID = "user_id";
    public static final String ORDER = "order";
    public static final String VIDEO_ID = "video_id";
    public static final String PHONE = "phone";
    public static final String PASS = "pass";
    public static final String HEAD_IMG = "headimg";
    public static final String F_INVIT_CODE = "f_invit_code";
    public static final String VERIFY = "verify";
    public static final String OPEN_ID = "openid";
    public static final String NICK_NAME = "nickname";
    public static final String SEX = "sex";
    public static final String UNIONID = "unionid";
    public static final String DEL_TYPE = "delType";
    public static final String MOVIES_ID = "movies_id";
    public static final String MOVIESID = "moviesId";
    public static final String PLAY__ID = "play_id";
    public static final String PLAYID = "playId";
    public static final String PLAY__TIME = "play_time";
    public static final String KEY_WORDS = "keywords";
    public static final String ACTIVITY_TYPE = "activity_type";
    public static final String CODE = "code";
    public static final String SHARE_CHANNEL = "share_channel";
    public static final String CONTENT = "content";
    public static final String COMMENT_ID = "comment_id";
    public static final String USER_AGENT = "user_agent";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String MOBILE_MODEL = "mobile_model";
    public static final String MOBILE_VERSION = "mobile_version";
    public static final String IP = "ip";
    public static final String NETWORD_TYPE = "network_type";
    public static final String SORT = "sort";
    public static final String CATY_ID = "caty_id";
    public static final String IS_END = "is_end";
    public static final String BOOK_ID = "bookId";
    public static final String CHAPTER_ID = "chapterId";
    public static final String PAY_MODEL = "payModel";
    public static final String PAY_FROM = "payFrom";


    public static final String GIVE_GOLD = "mission_new/info?id=1006";
    public static final String TO_GIVE_GOLD = "mission_new/handler?id=1006";
    //channel info
    public static final String CHANNEL = "Pub/getRType"; //栏目分类

    //video info
    public static final String VIDEO_LIST = "Resources/getVList"; //视频列表
    public static final String VIDEO_LIKE = "video/like";//视频点赞
    public static final String VIDEO_COLLECTION = "Collection/cResource";//视频收藏
    public static final String VIDEO_CANCEL_COLLECTION = "Collection/delCollect";//取消收藏
    public static final String VIDEO_COMMENT = "comment_video/lists";//视频点赞
    public static final String VIDEO_SHARE_URL = "Activateshare/videoShare"; // 视频分享
    public static final String VIDEO_SHARE_VISIT = "Datapre/shareVisit"; // 视频分享统计
    public static final String VIDEO_REPORT = "Report/video"; // 视频举报
    public static final String VIDEO_GET_GOLD = "mission_new/handler";//观看视频获取金币
    public static final String VIDEO_EDIT_COMMENT = "comment_video/push";//视频发布评论
    public static final String VIDEO_COMMENT_LIKE = "comment_video/like"; //评论点赞
    public static final String VIDEO_COMMENT_REPORT = "Report/videocomment"; //评论举报
    public static final String VIDEO_DETAILS = "Resources/getDetail";//视频详情
    public static final String VIDEO_VISIT = "datapre/videoVisit"; //视频播放统计
    public static final String VIDEO_WATCH_COUNT = "Resources/getWatchCount"; // 每天观看金币数量


    //movie info
    public static final String MOVIE_LIST = "Resouremovies/getTList";
    public static final String MOVIE_DETAIL = "Resouremovies/resourceDetail";
    public static final String MOVIE_RESOURCE_PLAY = "Resouremovies/resourcePlayy"; //视频解析获取资源
    public static final String MOVIE_VISIT = "Datapre/moviesVisit";//影视播放统计
    public static final String MOVIE_LIST_MORE = "Resouremovies/getLists";//影视列表
    public static final String MOVIE_SHARE = "Activateshare/moviesShare"; //影视分享
    public static final String MOVIE_GOLD_PAY = "Resouremovies/payy"; //金币支付


    //user
    public static final String USER_MSG = "User/getUserMsg"; //获取他人用户信息
    public static final String USER_VIDEO = "Follow/getUserVideo";//用户发布的视频列表
    public static final String FOLLOW_LIST = "Follow/followLists"; //关注用户列表
    public static final String FOLLOW_USER = "Follow/sFollow";//关注用户
    public static final String CANCEL_FOLLOW_USER = "Follow/cancelFollow";//取消关注用户
    public static final String FIND_PASS = "Reg/findpass"; //找回密码


    //user info
    public static final String USER_INDEX = "User/index"; // 用户基本信息
    public static final String USER_FLAG = "Apprentice/seniorMasterData";//用户身份信息
    public static final String COLLECTION_LIST = "Collection/clists";//收藏列表
    public static final String MOVIE_HISTORY = "Resouremovies/watchHistory";//观看历史记录
    public static final String USER_CENTER_ACTIAVTE_PAGE = "Activate/getActiavtePage";//活动轮播
    public static final String RED_BAG = "Redbag/getOneRed"; //新人领取红包
    public static final String USER_INFO_HINTS = "User/infoHints"; //任务红点提醒


    //reg and login
    public static final String SMS_CODE = "Pub/sendsms"; //短信验证码
    public static final String REGISTER = "Reg/t_reg"; //注册
    public static final String WECHAT_REGISTER = "Reg/wx_t_reg"; //微信注册
    public static final String LOGIN = "Login/index"; //登录
    public static final String CHECK_IS_BIND_WX = "Login/wxbind";//微信登录

    //search
    public static final String SEARCH = "Resourcesearch/index"; //搜索
    public static final String SEARCH_HOT = "Search/getkeyword";//热门搜索

    //push
    public static final String PUSH_ACTIVATE = "Activate/push";//活动推送

    //ads
    public static final String ADS = "ad/getAd";//广告获取


    //book
    public static final String BOOK_LIST = "Book/getTList";//小说分类获取
    public static final String BOOK_DETAILS = "Book/bookDetail";//小说详情
    public static final String BOOK_CHAPTER = "Book/bookChapter";//小说章节
    public static final String BOOK_STORE = "Book/getLists";//小说书库
    public static final String BOOK_CATEGROY = "Book/getBookstoreType";//小说分类
    public static final String BOOK_TOP_LIST = "Book/getTopList";//小说排行
    public static final String BOOK_ADD = "Selfbook/addBookShelf";//加入书架
    public static final String BOOK_DEL = "Selfbook/delBook";//删除书架
    public static final String BOOK_SHELF_LIST = "Selfbook/getlist"; //书架列表
    public static final String BOOK_IS_COLLECTION = "Selfbook/isCollection"; //是否加入书架
    public static final String BOOK_CONTENT = "Book/getContent"; //书籍内容
    public static final String PAY_MSG = "pay/getPayMsg"; //充值数据
    public static final String PAY_INFO = "pay/toPay"; //获取充值信息
    public static final String PAY_CHAPTER = "Book/pay";//章节支付
    public static final String BOOK_SHARE = "Activateshare/bookShare"; //小说分享

}
