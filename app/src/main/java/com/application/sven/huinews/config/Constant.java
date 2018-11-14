package com.application.sven.huinews.config;

import android.os.Environment;

import java.io.File;

/**
 * Created by sfy. on 2018/5/8 0008.
 */

public class Constant {
    //response error code
    public static final int RESPONSE_ERROR = 10001;

    //event bus
    public static final String TO_MAIN_PAGE_EVENT = "toMainPageEvent";
    public static final String TO_MINE_PAGE_EVENT = "toMinePageEvent";
    public static final String TO_MINE_TO_LOGIN = "toMainLogin";
    public static final String TO_PAY_PAGE = "openPayPage";
    public static final String PAY_CHAPTER = "pay_chapter";
    public static final String TO_MAIN_MOVIE = "toMainPageMovie";
    public static final String TO_MAIN_READ = "toMainPageRead";
    public static final String TO_USER_CENTER = "toUserCenter";

    public static final String REGISTER_OK = "regOkToMinePageEvent"; //注册成功广播，用于跳转到个人中心，关闭登录界面
    public static final String LOGIN_OK = "loginOkToMinePageEvent"; //登录成功广播，用于跳转到个人中心
    public static final String LOGOUT_OK = "logoutOkToMinePageEvent"; //注销成功
    public static final String REFRESH_HOME = "refresh_home"; //刷新首页
    public static final String REFRESH_MINE_MOVIDE_HISTORY = "refresh_mine_history_movie"; //刷新历史观看
    public static final String EXIT_APP = "out_app"; //退出app
    public static final String CANCEL_FOLLOW = "cancel_follow"; //取消关注
    public static final String PAY_OK = "PAY_OK";

    //js method name
    public static final String PERSONAL = "personal";

    //SharedPreferences key
    public static final String CHANNEL_VIDEO_LIST = "channel_movie_list";
    public static final String CHANNEL_MOVIE_LIST = "channel_video_list";
    public static final String CHANNEL_BOOK_LIST = "channel_book_list";

    //bundle
    public static final String BUNDLE_VIDEO_INFO = "bundle_video_info";
    public static final String BUNDLE_VIDEO_LIST = "bundle_video_list";
    public static final String BUNDLE_VIDEO_LIST_POSITION = "bundle_video_list_position";
    public static final String BUNDLE_MOVIE_ID = "bundle_movie_id";
    public static final String BUNDLE_MOVIE_PLAY_TIME = "bundle_movie_play_time";
    public static final String BUNDLE_MOVIE_PLAY_POSITION = "bundle_movie_play_position";
    public static final String BUNLDE_USER_ID = "bundle_user_id";
    public static final String BUNDLE_TO_BIND_PHONE = "bundle_to_bind_phone";
    public static final String BUNDLE_TO_LOGOUT = "bundle_to_logout";
    public static final String BUNDLE_TO_MUST_LOGIN = "bundle_to_must_login";
    public static final String BUNDLE_TO_VIDEO_ID = "bundle_to_video_id";
    public static final String BUNDLE_TO_USERINFO = "bundle_to_user_info";
    public static final String BUNDLE_TO_MORE_MOVIE_BID = "bundle_to_more_movie_bid";
    public static final String BUNDLE_TO_REGISTER_TOKEN = "bundle_to_register_token";
    public static final String BUNDLE_TO_BOOK_READ = "bundle_to_book_read";
    public static final String BUNDLE_TO_CHAPTER_ID = "bundle_to_chapter_id";
    public static final String BUNDLE_TO_BOOK_NAME = "bundle_to_book_name";
    public static final String BUNDLE_TO_BOOK_CATELOG = "bundle_to_book_catelog";
    public static final String BUNDLE_TO_PAY_CENTER = "bundle_to_pay_center";


    //sign key
    public static final String SIGN_KEY = "6b5695e8570e4176b84153a870634156";

    //RealM name
    public static String REALM_NAME = "taovideo.realm";
    public static String HOME_TAB_PAGE_INDEX = "homeTabPageIndex";
    public static String PREE_TAB_PAGE_INDEX = "preeTabPageIndex";
    public static String HISTORY_TAB_PAGE_INDEX = "historyTabPageIndex";
    public static String DOWNLOAD_TAB_PAGE_INDEX = "downloadTabPageIndex";
    public static String COLLECTION_TAB_PAGE_INDEX = "collectionTabPageIndex";
    public static String SEARCH_TAB_PAGE_INDEX = "searchTabPageIndex";
    public static String READ_TAB_PAGE_INDEX = "readTabPageIndex";
    public static String BOOK_UPDATE_TAB_PAGE_INDEX = "bookUpdateTabPageIndex";
    public static String BOOK_STORE_POSITION = "position";
    public static String WEB_URL = "webUrl";

    //shareType
    public static final int SHARE_TYPE_WECHAT = 0; //微信好友
    public static final int SHARE_TYPE_WECHAT_ZOOM = 1; //微信朋友圈
    public static final int SHARE_TYPE_QQ = 2; //qq好友
    public static final int SHARE_TYPE_QQ_ZOOM = 3; //qq空间
    public static final int SHARE_TYPE_REPORT = 4; //举报

    //share responseCode
    public static final int JS_RESPONSE_CODE_SUCCEED = 200;
    public static final int JS_RESPONSE_CODE_FAIL = -1;
    public static final int JS_RESPONSE_CODE_CANCEL = -2;

    //wechatlogin response
    public static final String WX_LOGIN_KEY_CODE = "code";
    public static final String WX_LOGIN_KEY_UNIONID = "unionid";
    public static final String WX_LOGIN_KEY_OPEN_ID = "openid";
    public static final String WX_LOGIN_KEY_SEX = "sex";
    public static final String WX_LOGIN_KEY_USER_NAME = "nickname";
    public static final String WX_LOGIN_KEY_USER_ICON = "headimgurl";


    //video info parms
    public static final int CHANNEL_TYPE_VIDEO = 1; //视频
    public static final int CHANNEL_TYPE_MOVIE = 2; //影视
    public static final String TYPE_WATERFALL = "waterfall"; //瀑布流
    public static final String TYPE_TRANSVERSE = "transverse"; //横向布局
    public static final int LIKE_TYPE = 1; //视频点赞
    public static final int UN_LIKE_TYPE = 2; //取消视频点赞
    public static final String COMMENT_UP = "up"; //点赞数量排序
    public static final String COMMENT_TIME = "time"; //评论时间排序
    public static final String MOVIE_HISTORY_LAST = "last";//观看历史  最后一次
    public static final String MOVIE_HISTORY_LIST = "list";//观看历史  列表
    public static final String ACTIVATE_PAG = "1";//活动轮播

    //movide layout time
    public static final String TYPE_B_GRID = "b_grid";  //大栅格布局（4图,2图横排）
    public static final String TYPE_S_GRID = "s_grid";  //小栅格布局（6图,3图竖排）
    public static final String TYPE_ALBUM = "album";  //相册布局
    public static final String TYPE_BANNER = "banner";  //横幅布局（轮播）
    public static final String TYPE_MOUNTAIN = "mountain";  //倒山布局
    public static final String TYPE_TOP_BANNER = "top_banner";  //顶部轮播
    public static final String TYPE_CONTINUE = "continue";  //续播
    //book layout item
    public static final String TYPE_X_TOP_BANNER = "x_top_banner";//书籍顶部轮播
    public static final String TYPE_X_S_GRID = "x_s_grid";//书籍小栅格布局（6图,3图竖排）
    public static final String TYPE_X_LEFT_L = "x_left_l";//书籍L布局
    public static final String TYPE_X_RIGHT_T = "x_right_t";//书籍右T布局
    public static final String TYPE_X_B_GRID = "x_b_grid";//书籍大栅格布局（6图,2图横排）
    public static final String TYPE_X_LIST = "x_list";//书籍列表布局

    public static final String BOOK_ID = "book_id";
    public static final String BOOK_CHAPTER_DESC = "desc";
    public static final String BOOK_CHAPTER_ASC = "asc";

    //register code
    public static final String CODE_TO_REG = "reg"; //注册发送验证码
    public static final String CODE_TO_FINDPWD = "findpwd"; //找回密码发送验证码


    public static final String M3U8 = "m3u8";

    public static final String SAVE_APK_PATH = Environment.getExternalStorageDirectory() + File.separator + "juNews" + File.separator + "apk" + File.separator;
    public static final String SAVE_MOVIE_PATH = Environment.getExternalStorageDirectory() + File.separator + "juNews" + File.separator + "movie" + File.separator + "m3u8" + File.separator;


    public static final String BOOK_PATH = Environment.getExternalStorageDirectory() + File.separator + "juNews" + File.separator + "book" + File.separator;

    //定义红包类型，一元新手红包or其他红包
    public static final String RED_TYPE_YY = "oneRed"; //一元新手红包
    public static final String RED_TYPE_GOLD = "gold"; // 金币红包


    public static final String SUFFIX_TXT = ".txt";
    public static final String SUFFIX_PDF = ".pdf";
    public static final String SUFFIX_EPUB = ".epub";
    public static final String SUFFIX_ZIP = ".zip";
    public static final String SUFFIX_CHM = ".chm";
}
