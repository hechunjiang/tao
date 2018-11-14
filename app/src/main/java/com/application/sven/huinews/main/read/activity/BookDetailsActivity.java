package com.application.sven.huinews.main.read.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.Comment;
import com.application.sven.huinews.entity.request.BookContentRequest;
import com.application.sven.huinews.entity.request.BookDetailsRequest;
import com.application.sven.huinews.entity.request.BookShareRequest;
import com.application.sven.huinews.entity.request.BookShelfAddRequest;
import com.application.sven.huinews.entity.request.BookisInStoreRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.response.BookDetailsResponse;
import com.application.sven.huinews.entity.response.BookShareResponse;
import com.application.sven.huinews.main.login.activity.LoginActivity;
import com.application.sven.huinews.main.my.activity.PayInfoActivity;
import com.application.sven.huinews.main.read.adapter.BookChapterAdapter;
import com.application.sven.huinews.main.read.adapter.BookRecommendAdapter;
import com.application.sven.huinews.main.read.adapter.CataLogSortAdapter;
import com.application.sven.huinews.main.read.contract.BookDetailsContract;
import com.application.sven.huinews.main.read.model.BookDetailsModel;
import com.application.sven.huinews.main.read.presenter.BookDetailsPresenter;
import com.application.sven.huinews.mob.share.MobShare;
import com.application.sven.huinews.mob.share.ShareCallBack;
import com.application.sven.huinews.mob.share.ShareResponse;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.itemDecoration.BookItemDecoration;
import com.application.sven.huinews.utils.itemDecoration.GridBookItemDecoration;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.dialog.ShareDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookDetailsActivity extends BaseActivity<BookDetailsPresenter, BookDetailsModel> implements BookDetailsContract.View {
    private SimpleDraweeView iv_book;
    private TextView tv_book_name;
    private TextView tv_book_auther;
    private RatingBar book_rating;
    private TextView tv_book_len;
    private TextView tv_book_dis;
    private TextView tv_book_categroy;
    private TextView tv_book_update;
    private TextView btn_chapter;
    private TextView btn_add, btn_open;
    private ImageButton btn_back;
    private ProgressBar loading;
    private RecyclerView rv_chapter, rv_recommend;
    private LinearLayout btn_pay;
    private ImageButton btn_share;
    private BookChapterAdapter mChapterAdapter;
    private BookRecommendAdapter mRecommendAdapter;
    private BookDetailsResponse.BookDetails mBookDetails;
    private ShareDialog mShareDialog;
    private int book_id;
    private boolean isDisOpen;

    public static void toThis(Context mContext, int bookId) {
        Intent intent = new Intent(mContext, BookDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.BOOK_ID, bookId);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            book_id = bundle.getInt(Constant.BOOK_ID);
        }
        return R.layout.activity_book_details;
    }

    @Override
    public void initView() {
        iv_book = findViewById(R.id.iv_book);
        tv_book_name = findViewById(R.id.tv_book_name);
        tv_book_auther = findViewById(R.id.tv_book_auther);
        book_rating = findViewById(R.id.book_rating);
        tv_book_len = findViewById(R.id.tv_book_len);
        tv_book_dis = findViewById(R.id.tv_book_dis);
        tv_book_update = findViewById(R.id.tv_book_update);
        tv_book_categroy = findViewById(R.id.tv_book_categroy);
        rv_chapter = findViewById(R.id.rv_chapter);
        rv_recommend = findViewById(R.id.rv_recommend);
        btn_chapter = findViewById(R.id.btn_chapter);
        btn_add = findViewById(R.id.btn_add);
        btn_open = findViewById(R.id.btn_open);
        btn_pay = findViewById(R.id.btn_pay);
        btn_share = findViewById(R.id.btn_share);
        mEmptyLayout = findViewById(R.id.mEmptyLayout);
        loading = findViewById(R.id.loading);
        btn_back = findViewById(R.id.btn_back);
    }

    @Override
    public void initEvents() {
        btn_chapter.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_open.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        tv_book_dis.setOnClickListener(this);

        mChapterAdapter.setOnItemClickLisenter(new BookChapterAdapter.OnItemClickLisenter() {
            @Override
            public void toOpenBook(int chapterId) {
                BookReadActivity.toThis(mContext, book_id, chapterId, mBookDetails.getTitle(), true);
            }
        });
    }

    @Override
    public void onClickEvent(View v) {
        if (v == btn_chapter) {
            CatalogActivity.toThis(mContext, book_id, mBookDetails.getTitle());
        } else if (v == btn_add) {
            onAddBook();
        } else if (v == btn_open) {
            openBook();
        } else if (v == btn_pay) {
            if (TextUtils.isEmpty(UserSpCache.getInstance(mContext).getStringData(UserSpCache.KEY_PHONE))) {
                ToastUtils.showShort(mContext, "请先注册登录");
                LoginActivity.toThis(mContext, false, false);
                return;
            }
            PayInfoActivity.toThis(mContext, 1);
        } else if (v == btn_share) {
            showShareDialog();
        } else if (v == btn_back) {
            finish();
        } else if (v == tv_book_dis) {
            isDisOpen = !isDisOpen;
            tv_book_dis.setMaxLines(isDisOpen ? Integer.MAX_VALUE : 3);
        }
    }

    @Override
    public void initObject() {
        setMVP();

        onBookRvInfo();
        bookDetails();


    }

    private void bookDetails() {
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        mPresenter.onBookIsInStore();
        mPresenter.onBookDetails();
    }

    @Override
    public BookDetailsRequest getBookDetailsRequest() {
        BookDetailsRequest request = new BookDetailsRequest();
        request.setId(book_id);
        return request;
    }

    @Override
    public BookShelfAddRequest getBookShelfAddRequest() {
        BookShelfAddRequest request = new BookShelfAddRequest();
        request.setBookId(book_id);
        request.setChapterId(0);
        return request;
    }

    @Override
    public BookisInStoreRequest getBookisInStoreRequest() {
        BookisInStoreRequest request = new BookisInStoreRequest();
        request.setBookId(book_id);
        return request;
    }

    @Override
    public BookShareRequest getBookShareRequest() {
        BookShareRequest request = new BookShareRequest();
        request.setBookId(book_id);
        return request;
    }

    @Override
    public void setBookDetails(BookDetailsResponse.BookDetails mBookInfo) {
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        onBookInfo(mBookInfo);
    }

    @Override
    public void addBookOk() {
        loading.setVisibility(View.GONE);
        ToastUtils.showShort(mContext, "加入书架成功");
        mPresenter.onBookIsInStore();
    }

    @Override
    public void setBookInStore(boolean isIn) {
        btn_add.setText(isIn ? "已加入书架" : "加入书架");
        btn_add.setEnabled(isIn ? false : true);
        btn_add.setTextColor(isIn ? getResources().getColor(R.color.c_999999) : getResources().getColor(R.color.c_333333));
    }

    @Override
    public void setBookShare(BookShareResponse.BookShare mBookShare, final int type) {
        String shareJson = mPresenter.getShareJson(mBookShare, type);
        MobShare mMobShare = new MobShare(mContext);
        mMobShare.shareToOne(mContext, shareJson, new ShareCallBack() {
            @Override
            public void onResponse(String response) {
                ShareResponse mShareResponse = new Gson().fromJson(response, ShareResponse.class);
                if (mShareResponse.getCode() == Constant.JS_RESPONSE_CODE_SUCCEED) {
                    LogUtil.showLog("msg---分享成功");
                    mPresenter.onShareVisit(response, ShareVisitRequest.BOOK_DETAIL, type);

                } else if (mShareResponse.getCode() == Constant.JS_RESPONSE_CODE_FAIL) {
                    ToastUtils.showLong(mContext, "分享失败，请稍后再试");

                }

            }
        });
    }

    private void onBookInfo(BookDetailsResponse.BookDetails mBookDetails) {
        this.mBookDetails = mBookDetails;
        FrescoUtil.loadDefImg(iv_book, mBookDetails.getPic());
        tv_book_name.setText(mBookDetails.getTitle());
        tv_book_auther.setText(mBookDetails.getAuthor());
        tv_book_categroy.setText(mBookDetails.getCay_name());
        tv_book_len.setText(mBookDetails.getSize() + "  " + (mBookDetails.getBook_status() == 1 ? "连载中" : "完结"));
        book_rating.setRating(mBookDetails.getScore());
        tv_book_dis.setText(mBookDetails.getDescription());
        tv_book_update.setText("更新于 " + CommonUtils.timeStamp2Date(mBookDetails.getLast_up_time() + ""));
        mChapterAdapter.setData(mBookDetails.getLately());
        mRecommendAdapter.setData(mBookDetails.getRecommen());

    }

    private void onBookRvInfo() {
        mChapterAdapter = new BookChapterAdapter(mContext);
        rv_chapter.setLayoutManager(new LinearLayoutManager(mContext));
        rv_chapter.setAdapter(mChapterAdapter);
        rv_chapter.setNestedScrollingEnabled(false);
        mRecommendAdapter = new BookRecommendAdapter(mContext);
        BookItemDecoration mBookItemDecoration = new BookItemDecoration(mContext);
        rv_recommend.removeItemDecoration(mBookItemDecoration);
        rv_recommend.addItemDecoration(mBookItemDecoration);
        rv_recommend.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv_recommend.setAdapter(mRecommendAdapter);
        rv_recommend.setNestedScrollingEnabled(false);

        mRecommendAdapter.setmOnItemClickLisenter(new BookRecommendAdapter.OnItemClickLisenter() {
            @Override
            public void itemClick(int bookId) {
                BookDetailsActivity.toThis(mContext, bookId);
                finish();
            }
        });


    }

    private void onAddBook() {
        loading.setVisibility(View.VISIBLE);
        mPresenter.onAddBookShelf();
    }

    private void openBook() {
        // Collections.reverse(mBookDetails.getLately()); // 倒序排列
        BookReadActivity.toThis(mContext, mBookDetails.getId(), mBookDetails.getFirst().getId(), mBookDetails.getTitle());
    }

    /**
     * 分享弹窗
     */
    private void showShareDialog() {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(mContext);
        }
        mShareDialog.hideReport();
        mShareDialog.show();
        mShareDialog.setOnShareListener(new ShareDialog.OnShareListener() {
            @Override
            public void onShare(int type) {
                mPresenter.onBookShare(type);

            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorTip(int code, String msg) {
        if (code == DataCallBack.NO_LOGIN) {
            loading.setVisibility(View.GONE);
            LoginActivity.toThis(mContext, false, false);
        } else if (code == DataCallBack.NET_TIME_OUT) {
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.INTERNAL_SERVER_ERROR) {
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.NETWORK_ERROR) {
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR, EmptyLayout.NETWORK_ERROR);
        }
        ToastUtils.showShort(mContext, msg);
    }
}
