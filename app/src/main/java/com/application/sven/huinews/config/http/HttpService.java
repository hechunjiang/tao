package com.application.sven.huinews.config.http;


import com.application.sven.huinews.config.api.Api;
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

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface HttpService {
    @GET(Api.APP_CONFIG)
    Observable<String> appConfig(@HeaderMap HashMap<String, String> map, @Query("new_auth") int new_auth);

    @POST(Api.LOGIN_TEMP)
    Observable<String> onTempLogin(@HeaderMap HashMap<String, String> map, @Body TempLoginRequest request);

    @POST(Api.CHANNEL)
    Observable<String> onChannelType(@HeaderMap HashMap<String, String> map, @Body ChannelTypeRequest request);

    @POST(Api.VIDEO_LIST)
    Observable<String> onVideoList(@HeaderMap HashMap<String, String> map, @Body VideoListRequest request);

    @POST(Api.MOVIE_LIST)
    Observable<String> onMovieList(@HeaderMap HashMap<String, String> map, @Body MovieListRequest request);

    @POST(Api.MOVIE_DETAIL)
    Observable<String> onMovieDetail(@HeaderMap HashMap<String, String> map, @Body MovieDetailRequest request);

    @POST(Api.MOVIE_RESOURCE_PLAY)
    Observable<String> onMovieResPlay(@HeaderMap HashMap<String, String> map, @Body MovieResPlayRequest request);

    @POST(Api.USER_VIDEO)
    Observable<String> onUserVideo(@HeaderMap HashMap<String, String> map, @Body UserVideoRequest request);

    @POST(Api.USER_MSG)
    Observable<String> onUserMsg(@HeaderMap HashMap<String, String> map, @Body UserMsgRequest request);

    @POST(Api.FOLLOW_LIST)
    Observable<String> onFollowList(@HeaderMap HashMap<String, String> map, @Body FollowListRequest request);

    @POST(Api.FOLLOW_USER)
    Observable<String> onFollowUser(@HeaderMap HashMap<String, String> map, @Body UserMsgRequest request);

    @POST(Api.CANCEL_FOLLOW_USER)
    Observable<String> onCancelFollowUser(@HeaderMap HashMap<String, String> map, @Body UserMsgRequest request);

    @POST(Api.VIDEO_LIKE)
    Observable<String> onVideoLike(@HeaderMap HashMap<String, String> map, @Body VideoLikeRequest request);

    @POST(Api.VIDEO_COLLECTION)
    Observable<String> onVideoCollection(@HeaderMap HashMap<String, String> map, @Body VideoCollectionRequest request);

    @POST(Api.VIDEO_COMMENT)
    Observable<String> onVideoComment(@HeaderMap HashMap<String, String> map, @Body VideoCommentRequest request);

    @POST(Api.USER_INDEX)
    Observable<String> onUserIndex(@HeaderMap HashMap<String, String> map, @Body BaseRequest request);

    @POST(Api.USER_FLAG)
    Observable<String> onUserFlag(@HeaderMap HashMap<String, String> map, @Body BaseRequest request);

    @POST(Api.REGISTER)
    Observable<String> onRegister(@HeaderMap HashMap<String, String> map, @Body RegisterRequest request);

    @POST(Api.LOGIN)
    Observable<String> onLogin(@HeaderMap HashMap<String, String> map, @Body LoginRequest request);

    @POST(Api.SMS_CODE)
    Observable<String> onSmsCode(@HeaderMap HashMap<String, String> map, @Body SmsCodeRequest request);

    @POST(Api.CHECK_IS_BIND_WX)
    Observable<String> checkIsBindWx(@HeaderMap HashMap<String, String> map, @Body BindWxRequest request);

    @POST(Api.WECHAT_REGISTER)
    Observable<String> onWxRegister(@HeaderMap HashMap<String, String> map, @Body RegisterRequest request);

    @POST(Api.COLLECTION_LIST)
    Observable<String> onCollection(@HeaderMap HashMap<String, String> map, @Body CollectionRequest request);

    @POST(Api.VIDEO_CANCEL_COLLECTION)
    Observable<String> onCancelCollection(@HeaderMap HashMap<String, String> map, @Body VideoCollectionCancelRequest request);

    @POST(Api.MOVIE_VISIT)
    Observable<String> onMovieVisit(@HeaderMap HashMap<String, String> map, @Body MovieVisitRequest request);

    @POST(Api.MOVIE_HISTORY)
    Observable<String> onMovieHistory(@HeaderMap HashMap<String, String> map, @Body MovieWatchHistoryRequest request);

    @POST(Api.USER_CENTER_ACTIAVTE_PAGE)
    Observable<String> actiavtePage(@HeaderMap HashMap<String, String> map, @Body ActiavtePageRequest request);

    @POST(Api.MOVIE_LIST_MORE)
    Observable<String> onMovieMoreList(@HeaderMap HashMap<String, String> map, @Body MovieMoreListRequest request);

    @POST(Api.SEARCH)
    Observable<String> onSearch(@HeaderMap HashMap<String, String> map, @Body SearchRequest request);

    @POST(Api.SEARCH_HOT)
    Observable<String> onHotSearch(@HeaderMap HashMap<String, String> map, @Body BaseRequest request);

    @POST(Api.VIDEO_SHARE_URL)
    Observable<String> onVideoShare(@HeaderMap HashMap<String, String> map, @Body VideoShareUrlRequest request);

    @POST(Api.VIDEO_SHARE_VISIT)
    Observable<String> shareVisit(@HeaderMap HashMap<String, String> map, @Body ShareVisitRequest request);

    @POST(Api.VIDEO_REPORT)
    Observable<String> videoReport(@HeaderMap HashMap<String, String> map, @Body VideoReportRequest request);

    @POST(Api.VIDEO_GET_GOLD)
    Observable<String> onGoldByTask(@HeaderMap HashMap<String, String> map, @Body VideoTaskRequest request);

    @POST(Api.VIDEO_EDIT_COMMENT)
    Observable<String> onVideoAdComment(@HeaderMap HashMap<String, String> map, @Body AdCommentRequest request);

    @POST(Api.PUSH_ACTIVATE)
    Observable<String> onPushActivate(@HeaderMap HashMap<String, String> map, @Body BaseRequest request);

    @POST(Api.RED_BAG)
    Observable<String> onRedBag(@HeaderMap HashMap<String, String> map, @Body BaseRequest request);

    @POST(Api.VIDEO_COMMENT_REPORT)
    Observable<String> onVideoCommentReport(@HeaderMap HashMap<String, String> map, @Body VideoReportRequest request);

    @POST(Api.VIDEO_COMMENT_LIKE)
    Observable<String> onVideoCommentLike(@HeaderMap HashMap<String, String> map, @Body VideoCommentLikeRequest request);

    @POST(Api.USER_INFO_HINTS)
    Observable<String> onHintsInfo(@HeaderMap HashMap<String, String> map, @Body BaseRequest request);

    @POST(Api.VIDEO_DETAILS)
    Observable<String> onVideoDetails(@HeaderMap HashMap<String, String> map, @Body VideoDetailsRequest request);

    @POST(Api.ADS)
    Observable<String> onAds(@HeaderMap HashMap<String, String> map, @Body AdsRequest request);


    @POST(Api.FIND_PASS)
    Observable<String> findPass(@HeaderMap HashMap<String, String> map, @Body FindPassRequest request);

    @POST(Api.MOVIE_SHARE)
    Observable<String> onMovieShate(@HeaderMap HashMap<String, String> map, @Body MovieShareRequest request);

    @POST(Api.MOVIE_GOLD_PAY)
    Observable<String> onMoviePay(@HeaderMap HashMap<String, String> map, @Body MoviePayRequest request);

    @POST(Api.VIDEO_VISIT)
    Observable<String> onVideoVisit(@HeaderMap HashMap<String, String> map, @Body VideoShareUrlRequest request);

    @POST(Api.VIDEO_WATCH_COUNT)
    Observable<String> onWatchCount(@HeaderMap HashMap<String, String> map, @Body BaseRequest request);

    @GET
    Observable<String> adExposure(@Url String url);

    @GET
    Observable<String> adClick(@Url String url);

    @Streaming
    @GET
    Observable<ResponseBody> downloadApk(@Url String url);


    @POST(Api.BOOK_LIST)
    Observable<String> onBookList(@HeaderMap HashMap<String, String> map, @Body BookListRequest request);

    @POST(Api.BOOK_DETAILS)
    Observable<String> onBookDetails(@HeaderMap HashMap<String, String> map, @Body BookDetailsRequest request);


    @POST(Api.BOOK_CHAPTER)
    Observable<String> onBookChapters(@HeaderMap HashMap<String, String> map, @Body BookChapterRequest request);

    @POST(Api.BOOK_STORE)
    Observable<String> onBookStore(@HeaderMap HashMap<String, String> map, @Body BookStoreRequest request);

    @POST(Api.BOOK_CATEGROY)
    Observable<String> onBookCategroy(@HeaderMap HashMap<String, String> map, @Body BaseRequest request);

    @POST(Api.BOOK_SHELF_LIST)
    Observable<String> onBookShelfList(@HeaderMap HashMap<String, String> map, @Body BookShelfListRequest request);


    @POST(Api.BOOK_TOP_LIST)
    Observable<String> onBookTopList(@HeaderMap HashMap<String, String> map, @Body BaseRequest request);

    @POST(Api.BOOK_ADD)
    Observable<String> onBookAdd(@HeaderMap HashMap<String, String> map, @Body BookShelfAddRequest request);

    @POST(Api.BOOK_DEL)
    Observable<String> onBookDel(@HeaderMap HashMap<String, String> map, @Body BookShelfDelRequest request);

    @POST(Api.PAY_MSG)
    Observable<String> onPayMsg(@HeaderMap HashMap<String, String> map, @Body BaseRequest request);

    @POST(Api.PAY_INFO)
    Observable<String> onPayInfo(@HeaderMap HashMap<String, String> map, @Body PayInfoRequest request);

    @POST(Api.BOOK_IS_COLLECTION)
    Observable<String> onBookInStore(@HeaderMap HashMap<String, String> map, @Body BookisInStoreRequest request);

    @POST(Api.BOOK_CONTENT)
    Observable<String> onBookContent(@HeaderMap HashMap<String, String> map, @Body BookContentRequest request);


    @POST(Api.PAY_CHAPTER)
    Observable<String> onBookChapterPay(@HeaderMap HashMap<String, String> map, @Body BookChapterPayRequest request);

    @POST(Api.SET_USER_MSG)
    Observable<String> onSetUserMsg(@HeaderMap HashMap<String, String> map, @Body SetUserMsgRequest request);

    @POST(Api.BOOK_SHARE)
    Observable<String> onBookShare(@HeaderMap HashMap<String, String> map, @Body BookShareRequest request);

    @POST(Api.GIVE_GOLD)
    Observable<String> onGiveGold(@HeaderMap HashMap<String, String> map, @Body BaseRequest request);

    @POST(Api.TO_GIVE_GOLD)
    Observable<String> onToGiveGold(@HeaderMap HashMap<String, String> map, @Body BaseRequest request);

}
