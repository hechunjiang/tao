package com.application.sven.huinews.main.read.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.db.BaseDB;
import com.application.sven.huinews.db.Dao.Book;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.CollectionList;
import com.application.sven.huinews.entity.request.BookChapterPayRequest;
import com.application.sven.huinews.entity.request.BookChapterRequest;
import com.application.sven.huinews.entity.request.BookContentRequest;
import com.application.sven.huinews.entity.request.BookShareRequest;
import com.application.sven.huinews.entity.request.BookShelfAddRequest;
import com.application.sven.huinews.entity.request.BookShelfDelRequest;
import com.application.sven.huinews.entity.request.BookisInStoreRequest;
import com.application.sven.huinews.entity.request.ShareVisitRequest;
import com.application.sven.huinews.entity.request.VideoReportRequest;
import com.application.sven.huinews.entity.request.VideoShareUrlRequest;
import com.application.sven.huinews.entity.response.BookChapterInfoResponse;
import com.application.sven.huinews.entity.response.BookChapterItem;
import com.application.sven.huinews.entity.response.BookChapters;
import com.application.sven.huinews.entity.response.BookShareResponse;
import com.application.sven.huinews.entity.response.BookShelfList;
import com.application.sven.huinews.main.login.activity.LoginActivity;
import com.application.sven.huinews.main.my.activity.PayInfoActivity;
import com.application.sven.huinews.main.read.adapter.BookReadAdapter;
import com.application.sven.huinews.main.read.adapter.CataLogSortAdapter;
import com.application.sven.huinews.main.read.adapter.ReadBgAdapter;
import com.application.sven.huinews.main.read.contract.BookReadContract;
import com.application.sven.huinews.main.read.model.BookReadModel;
import com.application.sven.huinews.main.read.presenter.BookReadPresenter;
import com.application.sven.huinews.mob.share.MobShare;
import com.application.sven.huinews.mob.share.ShareCallBack;
import com.application.sven.huinews.mob.share.ShareResponse;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ScreensUitls;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.statusbar.Eyes;
import com.application.sven.huinews.utils.umeng.UMengUtils;
import com.application.sven.huinews.view.dialog.CommonDialog;
import com.application.sven.huinews.view.dialog.ShareDialog;
import com.application.sven.huinews.view.newread.OnPageChangeListener;
import com.application.sven.huinews.view.newread.PageView;
import com.application.sven.huinews.view.read.ThemeManager;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.view.View.VISIBLE;

/**
 * auther: sunfuyi
 * data: 2018/7/14
 * effect:
 */
public class BookReadActivity extends BaseActivity<BookReadPresenter, BookReadModel> implements BookReadContract.View, OnPageChangeListener {
    private View decodeView;

    private PageView pageView;
    private LinearLayout top_view, bottom_view, pop_window, ll_set, ll_section_progress, read_side;
    private ImageButton btn_back, btn_more;
    private TextView btn_catalog, btn_datnight, btn_set, tv_section_progress, btn_pre_chapter, btn_next_chapter, tv_pop_book_detail, tv_pop_book_store, tv_pop_share;
    private TextView read_tv_flip_over_cover, read_tv_flip_over_simulation, read_tv_flip_over_slide, read_tv_flip_over_none;
    private SeekBar seek_font_bar, seek_bar;
    private ImageView btn_font_reduce, btn_font_plus;
    private RecyclerView bg_rv, read_rv_chapters;
    private DrawerLayout read_drawer;
    private int bookId, chapterId, currentChapterId, currentChapterPage; //书籍id，章节id，当前章节id，当前章节当前页码
    private String bookTilte;
    private BookChapterItem mBookChapterItem;
    private List<BookChapters.ListsBean> mBookChapters;
    private BookReadAdapter mReadAdapter;
    private ProgressBar loading;
    private boolean isShowPop = false;
    private boolean isDayNight;
    private int curTheme = -1;
    private CataLogSortAdapter mSortAdapter;
    private ShareDialog mShareDialog;
    private CommonDialog mDialog;
    private boolean bookIsInStore;
    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    private Animation mBottomInAnim;
    private Animation mBottomOutAnim;
    private View[] flipOverViews;
    private Book mBook;
    private boolean isCateLog;
    private boolean isAutoPay;
    private boolean isRefreshCateLog;
    private boolean isClcikPay;


    public static void toThis(Context mContext, int bookId, int chapterId, String bookTilte) {
        Intent intent = new Intent(mContext, BookReadActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.BOOK_ID, bookId);
        bundle.putInt(Constant.BUNDLE_TO_CHAPTER_ID, chapterId);
        bundle.putString(Constant.BUNDLE_TO_BOOK_NAME, bookTilte);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public static void toThis(Context mContext, int bookId, int chapterId, String bookTilte, boolean isCatelog) {
        Intent intent = new Intent(mContext, BookReadActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.BOOK_ID, bookId);
        bundle.putInt(Constant.BUNDLE_TO_CHAPTER_ID, chapterId);
        bundle.putString(Constant.BUNDLE_TO_BOOK_NAME, bookTilte);
        bundle.putBoolean(Constant.BUNDLE_TO_BOOK_CATELOG, isCatelog);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        Eyes.setStatusBarLightMode(this, getResources().getColor(R.color.c_ff222222));
        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bookId = bundle.getInt(Constant.BOOK_ID);
            chapterId = bundle.getInt(Constant.BUNDLE_TO_CHAPTER_ID);
            bookTilte = bundle.getString(Constant.BUNDLE_TO_BOOK_NAME);
            isCateLog = bundle.getBoolean(Constant.BUNDLE_TO_BOOK_CATELOG);
            if (!isCateLog) {
                mBook = BaseDB.getInstance(mContext).getBook(bookId);
                if (mBook != null) {
                    chapterId = mBook.getLatestReadChapterId();
                    currentChapterId = chapterId;
                    LogUtil.showLog("msg---进入时保存的book:" + mBook.toString());
                } else {
                    mBook = new Book();
                }
            } else {
                mBook = new Book();
            }
        }
        EventBus.getDefault().register(this);
        return R.layout.activity_read;
    }

    @Override
    public void initView() {
        pageView = findViewById(R.id.pv_read);
        decodeView = getWindow().getDecorView();
        btn_back = findViewById(R.id.btn_back);
        top_view = findViewById(R.id.top_view);
        bottom_view = findViewById(R.id.bottom_view);
        pop_window = findViewById(R.id.pop_window);
        btn_back = findViewById(R.id.btn_back);
        btn_more = findViewById(R.id.btn_more);
        btn_datnight = findViewById(R.id.btn_datnight);
        btn_catalog = findViewById(R.id.btn_catalog);
        ll_set = findViewById(R.id.ll_set);
        btn_set = findViewById(R.id.btn_set);
        bg_rv = findViewById(R.id.bg_rv);
        seek_font_bar = findViewById(R.id.seek_font_bar);
        btn_font_reduce = findViewById(R.id.btn_font_reduce);
        btn_font_plus = findViewById(R.id.btn_font_plus);
        seek_bar = findViewById(R.id.seek_bar);
        seek_bar.setEnabled(false);
        btn_pre_chapter = findViewById(R.id.btn_pre_chapter);
        btn_next_chapter = findViewById(R.id.btn_next_chapter);
        ll_section_progress = findViewById(R.id.ll_section_progress);
        tv_section_progress = findViewById(R.id.tv_section_progress);
        read_drawer = findViewById(R.id.read_drawer);
        read_rv_chapters = findViewById(R.id.read_rv_chapters);
        read_side = findViewById(R.id.read_side);
        tv_pop_book_detail = findViewById(R.id.tv_pop_book_detail);
        tv_pop_book_store = findViewById(R.id.tv_pop_book_store);
        tv_pop_share = findViewById(R.id.tv_pop_share);
        loading = findViewById(R.id.loading);
        read_tv_flip_over_cover = findViewById(R.id.read_tv_flip_over_cover);
        read_tv_flip_over_simulation = findViewById(R.id.read_tv_flip_over_simulation);
        read_tv_flip_over_slide = findViewById(R.id.read_tv_flip_over_slide);
        read_tv_flip_over_none = findViewById(R.id.read_tv_flip_over_none);
        flipOverViews = new View[]{
                read_tv_flip_over_cover,
                read_tv_flip_over_simulation,
                read_tv_flip_over_slide,
                read_tv_flip_over_none
        };

    }

    @Override
    public void initEvents() {

        btn_back.setOnClickListener(this);
        btn_more.setOnClickListener(this);
        btn_set.setOnClickListener(this);
        btn_datnight.setOnClickListener(this);
        btn_font_plus.setOnClickListener(this);
        btn_font_reduce.setOnClickListener(this);
        bottom_view.setOnClickListener(this);
        btn_catalog.setOnClickListener(this);
        btn_next_chapter.setOnClickListener(this);
        btn_pre_chapter.setOnClickListener(this);
        tv_pop_book_detail.setOnClickListener(this);
        tv_pop_share.setOnClickListener(this);
        tv_pop_book_store.setOnClickListener(this);
        read_tv_flip_over_cover.setOnClickListener(this);
        read_tv_flip_over_simulation.setOnClickListener(this);
        read_tv_flip_over_slide.setOnClickListener(this);
        read_tv_flip_over_none.setOnClickListener(this);


        pageView.setTouchListener(new PageView.TouchListener() {
            @Override
            public void center() {
                toggleMenu(true);
            }

            @Override
            public void cancel() {

            }

        });

        pageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (top_view.getVisibility() == View.VISIBLE) {
                    hideReadBar();
                    return true;
                }
                return false;
            }
        });
        pageView.setOnPayInfoLisenter(new PageView.OnPayInfoLisenter() {
            @Override
            public void onSelectedAuto(boolean isSelected) {
                isAutoPay = isSelected;
                UserSpCache.getInstance(mContext).putBoolean(UserSpCache.SAVE_AUTO_PAY, isSelected);
                LogUtil.showLog("msg----isAuto:" + isSelected);
            }

            @Override
            public void onClickPay(int chaperId) {
                if (TextUtils.isEmpty(UserSpCache.getInstance(mContext).getStringData(UserSpCache.KEY_PHONE))) {
                    ToastUtils.showShort(mContext, "请先注册登录");
                    LoginActivity.toThis(mContext, false, false);
                    return;
                }
                Intent intent = new Intent(mContext, PayInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.BUNDLE_TO_PAY_CENTER, 2);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1000);
            }


            @Override
            public void onPayCurrChapter() {
                isRefreshCateLog = true;
                isClcikPay = true;
                loading.setVisibility(View.VISIBLE);
                chapterId = currentChapterId;
                //当前章节购买
                mPresenter.onBookChapterPay();
                UserSpCache.getInstance(mContext).putBoolean(UserSpCache.SAVE_AUTO_PAY_CHECK, isClcikPay);
            }
        });
        mSortAdapter.setOnItemClickLisenter(new CataLogSortAdapter.OnItemClickLisenter() {
            @Override
            public void toOpenBook(int chapterId) {
                loading.setVisibility(View.VISIBLE);
                BookReadActivity.this.chapterId = chapterId;
                currentChapterId = chapterId;
                read_drawer.closeDrawers();
                // toggleMenu(true);
                if (isAutoPay) {
                    autoType = 2;
                } else {
                    autoType = 1;
                }
                doLoadData(true);
            }
        });

        mDialog.setDialogClickLisenter(new CommonDialog.dialogClickLisenter() {
            @Override
            public void onCancel() {
                mDialog.dismiss();
                finish();
            }

            @Override
            public void onEnter() {
                mDialog.dismiss();
                loading.setVisibility(View.VISIBLE);
                mPresenter.onAddBookShelf();
            }
        });
    }


    @Override
    public void onClickEvent(View v) {
        if (v == btn_back) {
            if (bookIsInStore) {
                //书架已经存在该书籍
                finish();
            } else {
                //书架没有该书籍
                showBackDialog();
            }

        } else if (v == btn_more) {
            showPop();
        } else if (v == btn_set) {
            showSet();
        } else if (v == btn_datnight) {
            changeMode();
        } else if (v == read_tv_flip_over_cover) {
            setPageMode(PageView.PAGE_MODE_COVER);
        } else if (v == read_tv_flip_over_slide) {
            setPageMode(PageView.PAGE_MODE_SLIDE);
        } else if (v == read_tv_flip_over_simulation) {
            setPageMode(PageView.PAGE_MODE_SIMULATION);
        } else if (v == read_tv_flip_over_none) {
            setPageMode(PageView.PAGE_MODE_NONE);
        } else if (v == btn_font_plus) {
            fontSizePlus();
        } else if (v == btn_font_reduce) {
            fontSizeReduce();
        } else if (v == bottom_view) {
            LogUtil.showLog("点击了底部空白区域");
        } else if (v == btn_catalog) {
            read_drawer.openDrawer(read_side);
        } else if (v == btn_next_chapter) {
            loadNexPage();
        } else if (v == btn_pre_chapter) {
            loadPrePage();
        } else if (v == tv_pop_book_detail) {
            finish();
        } else if (v == tv_pop_book_store) {
            BookShelfActivity.toThis(mContext);
        } else if (v == tv_pop_share) {
            showShareDialog();
        }
    }

    @Override
    public void initObject() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) top_view.getLayoutParams();
        params.topMargin = ScreensUitls.getStatusBarHeight(this) - 2;
        top_view.setLayoutParams(params);
        initReader();
        setMVP();
        loadData();
        setBgList();
        initBookChapters();
        mDialog = new CommonDialog(mContext);
        bookStatistics();
    }

    private void initReader() {
        isAutoPay = UserSpCache.getInstance(mContext).getBoolean(UserSpCache.SAVE_AUTO_PAY);
        isClcikPay = UserSpCache.getInstance(mContext).getBoolean(UserSpCache.SAVE_AUTO_PAY_CHECK);
        //  isRefreshCateLog = isAutoPay?true:false;
        curTheme = ThemeManager.getCacheThemeBg();
        pageView.setPageBackground(ThemeManager.setReaderBgColor(curTheme));
        //设置字体信息
        seek_font_bar.setMax(10);
        int fontSizePx = ThemeManager.getReadFontSize();
        pageView.setTextSize(fontSizePx == -1 ? 50 : ThemeManager.getReadFontSize());
        int progress = ThemeManager.getReadSeekBarPorgress();
        seek_font_bar.setProgress(progress == -1 ? 5 : ThemeManager.getReadSeekBarPorgress());
        seek_font_bar.setOnSeekBarChangeListener(new SeekBarChangeListener());


        isDayNight = curTheme == ThemeManager.NIGHT ? true : false;
        curTheme = isDayNight ? ThemeManager.NORMAL : curTheme;
        Drawable drawable = ContextCompat.getDrawable(this, isDayNight ? R.mipmap.icon_read_day : R.mipmap.icon_read_night);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        btn_datnight.setCompoundDrawables(null, drawable, null, null);
        btn_datnight.setText(isDayNight ? R.string.day_mode : R.string.night_mode);

        setPageMode(ThemeManager.getPageModel() == -1 ? 0 : ThemeManager.getPageModel());

    }

    private void loadData() {
        mPresenter.onBookChapter();
        mPresenter.onBookIsInStore();
    }

    private void loadPageContent(boolean isOpenBook) {
        mPresenter.onBookContent(isOpenBook);
    }

    int autoType = 1;

    @Override
    public BookContentRequest getBookConentRequest() {
        BookContentRequest request = new BookContentRequest();
        request.setBookId(bookId);
        request.setChapterId(chapterId);
        request.setType(autoType);
        LogUtil.showLog("msg--BookContentRequest-::" + request.toString());
        return request;
    }

    @Override
    public BookChapterRequest getBookChapterRequest() {
        BookChapterRequest request = new BookChapterRequest();
        request.setId(bookId);
        request.setLimit(Integer.MAX_VALUE);
        request.setSort(Constant.BOOK_CHAPTER_ASC);
        return request;
    }

    @Override
    public BookChapterPayRequest getBookChapterPayRequest() {
        BookChapterPayRequest request = new BookChapterPayRequest();
        request.setBookId(bookId);
        request.setChapterId(currentChapterId);
        request.setType(pageView != null && pageView.isAutoPay() ? 2 : 1);
        return request;
    }

    @Override
    public BookisInStoreRequest getBookisInStoreRequest() {
        BookisInStoreRequest request = new BookisInStoreRequest();
        request.setBookId(bookId);
        return request;
    }

    @Override
    public BookShelfAddRequest getBookShelfAddRequest() {
        BookShelfAddRequest request = new BookShelfAddRequest();
        request.setChapterId(currentChapterId);
        request.setBookId(bookId);
        return request;
    }

    @Override
    public BookShareRequest getBookShareRequest() {
        BookShareRequest request = new BookShareRequest();
        request.setBookId(bookId);
        return request;
    }


    @Override
    public void setBookChapters(BookChapters mBookChapters) {
        this.mBookChapters = mBookChapters.getLists();
        mSortAdapter.setData(this.mBookChapters, true);


        if (!isRefreshCateLog) {
            loading.setVisibility(View.GONE);
            if (chapterId == 0) {
                chapterId = mBookChapters.getLists().get(0).getId();
            }
            if (isAutoPay) {
                autoType = 2;
            } else {
                autoType = 1;
            }

            doLoadData(true);
        } else {
            isRefreshCateLog = false;
            doLoadData(false);
        }

        seek_bar.setEnabled(true);
        seek_bar.setMax(this.mBookChapters.size());
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int section = seekBar.getProgress() - 1;
                if (section < 1) {
                    section = 1;
                }
                BookChapters.ListsBean chapterItem = BookReadActivity.this.mBookChapters.get(section);
                tv_section_progress.setText(section + "/" + BookReadActivity.this.mBookChapters.size() + "   " + chapterItem.getChapter_name());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                ll_section_progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ll_section_progress.setVisibility(View.GONE);
                int section = seekBar.getProgress() - 1;
                if (section < 0) {
                    section = 0;
                }
                BookChapters.ListsBean chapterItem = BookReadActivity.this.mBookChapters.get(section);
                chapterId = chapterItem.getId();
                doLoadData(true);

            }
        });

    }


    private void doLoadData(boolean isOpen) {
        int indexOfSectionList = indexOfSectionList(this.mBookChapters, chapterId);
        if (isOpen) {
            autoType = isAutoPay && isClcikPay ? 2 : 1;
            if ((mReadAdapter == null || !mReadAdapter.hasSection(indexOfSectionList))) {
                autoType = isAutoPay && isClcikPay ? 2 : 1;
                loadPageContent(isOpen);
            } else {
                if (mReadAdapter != null) {
                    loading.setVisibility(View.GONE);
                    pageView.openSection(indexOfSectionList, 0);
                    setSectionDisplay(indexOfSectionList);
                }
            }
        }

        if (indexOfSectionList + 1 < mBookChapters.size()) {
            if (mReadAdapter == null || !mReadAdapter.hasSection(indexOfSectionList + 1)) {
                BookChapters.ListsBean chapterItem = mBookChapters.get(indexOfSectionList + 1);
                chapterId = chapterItem.getId();
                autoType = isAutoPay && isClcikPay ? 2 : 1;
                loadPageContent(false);
            } else {
                BookChapters.ListsBean chapterItem = mBookChapters.get(indexOfSectionList + 1);
                chapterId = chapterItem.getId();
                autoType = isAutoPay && isClcikPay ? 2 : 1;
                loadPageContent(false);
            }
        }

        if (indexOfSectionList - 1 >= 0) {
            if (mReadAdapter == null || !mReadAdapter.hasSection(indexOfSectionList - 1)) {
                BookChapters.ListsBean chapterItem = mBookChapters.get(indexOfSectionList - 1);
                chapterId = chapterItem.getId();
                autoType = isAutoPay && isClcikPay ? 2 : 1;
                loadPageContent(false);
            }
        }
    }

    @Override
    public void setBookChapterInfo(BookChapterItem mBookChapterInfo, boolean isOpenBook) {
        loading.setVisibility(View.GONE);
        this.mBookChapterItem = mBookChapterInfo;
        int listIndex = indexOfSectionList(this.mBookChapters, mBookChapterInfo.getId());
        if (mReadAdapter == null) {
            mReadAdapter = new BookReadAdapter();
            mReadAdapter.addData(listIndex, mBookChapterInfo);
            pageView.setAdapter(mReadAdapter);
            pageView.setPageMode(ThemeManager.getPageModel() == -1 ? 0 : ThemeManager.getPageModel());
            pageView.setOnPageChangeListener(this);
        } else {
            mReadAdapter.addData(listIndex, mBookChapterInfo);
        }

        pageView.setBancle(mBookChapterInfo.getLeft_diamond());

        if (mBookChapterInfo.getShow() == 1) {
            mSortAdapter.refreshStatus(mBookChapterInfo.getId());
        }

        if (isOpenBook) {
            this.mBookChapterItem = mBookChapterInfo;
            currentChapterId = this.mBookChapters.get(listIndex).getId();
            loading.setVisibility(View.GONE);
            pageView.openSection(listIndex, (mBookChapterInfo.getBook_id() == mBook.getBookId() ? mBook.getLastestReadPage() : 0));
            setSectionDisplay(listIndex);
        }
        if (isRefreshCateLog) {
            mPresenter.onBookChapter();

        }
    }

    @Override
    public void setBookInStore(boolean isIn) {
        this.bookIsInStore = isIn;
        LogUtil.showLog("msg---书籍是否在书架中:" + bookIsInStore);
    }

    @Override
    public void addBookOk() {
        loading.setVisibility(View.GONE);
        ToastUtils.show(mContext, "加入书架成功", 1);
        finish();
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
                    mPresenter.onShareVisit(response, ShareVisitRequest.BOOK_DETAIL, type);
                } else if (mShareResponse.getCode() == Constant.JS_RESPONSE_CODE_FAIL) {
                    ToastUtils.showLong(mContext, "分享失败，请稍后再试");
                }
            }
        });
    }


    private boolean isFirstChapterChange = true;


    BookChapters.ListsBean mChapterItem;

    public void onChapterChange(int pos) {
        if (!isFirstChapterChange) {
            BookChapters.ListsBean chapterItem = this.mBookChapters.get(pos);
            mChapterItem = chapterItem;
            chapterId = chapterItem.getId();
            currentChapterId = chapterId;
            pageView.setCurrentSection(pos);
            if (isAutoPay && isClcikPay) {
                if (chapterItem.getIs_gold() == 2) {
                    mPresenter.onBookChapterPay();
                }
            }

            LogUtil.showLog("msg---当前章节：" + chapterItem.getChapter_name() + " ---当前章节id:" + chapterItem.getChapter_name() + "-----" + currentChapterId);
            doLoadData(false);
            setSectionDisplay(pos);
        } else {
            isFirstChapterChange = false;
        }

        //  LogUtil.showLog("msg----siFirstChapterChange:" + isFirstChapterChange);
        mBook.setLatestReadChapterId(currentChapterId);
        mBook.setLastestReadPage(currentChapterPage);
        BaseDB.getInstance(mContext).updateBook(mBook);

    }

    @Override
    public void onPageCountChange(int count) {

    }

    @Override
    public void onPageChange(int pos) {
        currentChapterPage = pos;
        mBook.setLastestReadPage(currentChapterPage);
        mBook.setLatestReadChapterId(currentChapterId);
        BaseDB.getInstance(mContext).updateBook(mBook);
    }

    /**
     * 加载下一章
     */
    private void loadNexPage() {
        int indexOfSectionList = indexOfSectionList(this.mBookChapters, currentChapterId);
        if (indexOfSectionList + 1 < mBookChapters.size()) {
            BookChapters.ListsBean mChapterItem = mBookChapters.get(indexOfSectionList + 1);
            chapterId = mChapterItem.getId();
            currentChapterId = chapterId;
            doLoadData(true);
        }
    }

    /**
     * 加载上一章
     */
    private void loadPrePage() {
        int indexOfSectionList = indexOfSectionList(this.mBookChapters, currentChapterId);
        if (indexOfSectionList - 1 >= 0) {
            BookChapters.ListsBean mChapterItem = this.mBookChapters.get(indexOfSectionList - 1);
            chapterId = mChapterItem.getId();
            currentChapterId = chapterId;
            doLoadData(true);
        }
    }

    private void setSectionDisplay(int section) {
        seek_bar.setProgress(section + 1);
    }

    /**
     * 找到sectionIndex对应的章节在bookSectionItems中的下标
     */
    private int indexOfSectionList(@NonNull List<BookChapters.ListsBean> mBookChapterItems, int sectionId) {
        for (int i = 0; i < mBookChapterItems.size(); i++) {
            if (mBookChapterItems.get(i).getId() == sectionId) {
                return i;
            }
        }
        return -1;
    }

    private void toggleMenu(boolean b) {
        initMenuAnim();
        if (top_view.getVisibility() == View.VISIBLE) {
            hideReadBar();
        } else {
            showReadBar();
        }
    }

    Handler handler = new Handler();

    private void hideReadBar() {

        top_view.startAnimation(mTopOutAnim);
        bottom_view.startAnimation(mBottomOutAnim);
        top_view.setVisibility(View.GONE);
        bottom_view.setVisibility(View.GONE);
        pop_window.setVisibility(View.GONE);
        isShowPop = false;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideStatusBar();
                decodeView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
            }
        }, 100);
    }

    private void showReadBar() {
        top_view.startAnimation(mTopInAnim);
        bottom_view.startAnimation(mBottomInAnim);
        top_view.setVisibility(View.VISIBLE);
        bottom_view.setVisibility(View.VISIBLE);
        showStatusBar();
        decodeView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    }

    /**
     * 初始化菜单弹出隐藏动画
     */
    private void initMenuAnim() {
        if (mTopInAnim != null) {
            return;
        }
        mTopInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_in);
        mTopOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_out);
        mBottomInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in);
        mBottomInAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                pageView.setCanTouch(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mBottomOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_out);
        mBottomOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                pageView.setCanTouch(true);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //退出的速度要快
        mTopOutAnim.setDuration(200);
        mBottomOutAnim.setDuration(200);
    }

    private void showPop() {
        isShowPop = !isShowPop;
        if (isShowPop) {
            pop_window.setVisibility(View.VISIBLE);
        } else {
            pop_window.setVisibility(View.GONE);
        }
    }

    /**
     * 显示隐藏设置
     */
    private void showSet() {
        ll_set.setVisibility(ll_set.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    private void initBookChapters() {
        mSortAdapter = new CataLogSortAdapter(mContext);
        read_rv_chapters.setLayoutManager(new LinearLayoutManager(this));
        read_rv_chapters.setAdapter(mSortAdapter);
    }


    private void setBgList() {
        final ReadBgAdapter mReadBgAdapter = new ReadBgAdapter(mContext);
        bg_rv.setLayoutManager(new GridLayoutManager(mContext, 6));
        bg_rv.setAdapter(mReadBgAdapter);
        mReadBgAdapter.setOnItemClickListener(new ReadBgAdapter.onItemClickListener() {
            @Override
            public void onSelected(int posiiton) {
                //选中当前色
                mReadBgAdapter.setSelected(posiiton);
                setReadTheme(posiiton);
                Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.icon_read_night);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btn_datnight.setCompoundDrawables(null, drawable, null, null);
                btn_datnight.setText(R.string.night_mode);
                isDayNight = false;

            }
        });
        mReadBgAdapter.setSelected(curTheme);
    }


    private void saveReadLocation() {
        mBook.setBookId(bookId);
        mBook.setLatestReadChapterId(currentChapterId);
        mBook.setLastestReadPage(currentChapterPage);
        mBook.setLatestReadChapter(chapterId);
        mBook.setBookName(bookTilte);
        BaseDB.getInstance(mContext).updateBook(mBook);

        LogUtil.showLog("msg---退出时保存book：" + mBook.toString());
    }

    private void setReadTheme(int posiiton) {
        //设置当前主题色
        curTheme = posiiton;
        //保存选择的主题颜色
        ThemeManager.cacheThemeBg(posiiton);
        pageView.setPageBackground(ThemeManager.setReaderBgColor(posiiton));
        pageView.refreshPage();
    }

    /**
     * 日夜间模式切换
     */
    private void changeMode() {
        isDayNight = !isDayNight;
        int theme = isDayNight ? ThemeManager.NIGHT : curTheme;
        LogUtil.showLog("msg---theme:" + theme);
        Drawable drawable = ContextCompat.getDrawable(this, isDayNight ? R.mipmap.icon_read_day : R.mipmap.icon_read_night);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        btn_datnight.setCompoundDrawables(null, drawable, null, null);
        btn_datnight.setText(isDayNight ? R.string.day_mode : R.string.night_mode);
        pageView.setPageBackground(ThemeManager.setReaderBgColor(theme));
        pageView.refreshPage();
        ThemeManager.cacheThemeBg(theme);
    }

    /**
     * 设置翻页效果
     *
     * @param pageMode
     */
    private void setPageMode(int pageMode) {
        View selectedView = null;
        switch (pageMode) {
            case PageView.PAGE_MODE_COVER:
                read_tv_flip_over_cover.setSelected(true);
                selectedView = read_tv_flip_over_cover;
                break;
            case PageView.PAGE_MODE_SIMULATION:
                read_tv_flip_over_simulation.setSelected(true);
                selectedView = read_tv_flip_over_simulation;
                break;
            case PageView.PAGE_MODE_SLIDE:
                read_tv_flip_over_slide.setSelected(true);
                selectedView = read_tv_flip_over_slide;
                break;
            case PageView.PAGE_MODE_NONE:
                read_tv_flip_over_none.setSelected(true);
                selectedView = read_tv_flip_over_none;
                break;
        }
        for (View view : flipOverViews) {
            if (view != selectedView && view.isSelected()) {
                view.setSelected(false);
            }
        }
        if (pageMode != pageView.getPageMode()) {
            LogUtil.showLog("msg---pageModel:" + pageMode);
            pageView.setPageAnimMode(pageMode);
            ThemeManager.cachePageModel(pageMode);
        }


    }

    /**
     * 设置字体大小
     *
     * @param progress
     */
    private void calcFontSize(int progress) {

        if (progress >= 0 && progress <= 10) {
            seek_font_bar.setProgress(progress);
            int fontSize = CommonUtils.dip2px(mContext, 12 + 2.0f * progress);
            LogUtil.showLog("msg---字体大小:" + fontSize + "==---滚动条位置:" + progress);

            pageView.setTextSize(fontSize);
            ThemeManager.cacheFontSize(fontSize);
            ThemeManager.cacheSeekBarProgress(progress);
        }
    }

    /**
     * 字体-1
     */
    public void fontSizeReduce() {
        calcFontSize(ThemeManager.getReadSeekBarPorgress() == -1 ? 5 - 1 : ThemeManager.getReadSeekBarPorgress() - 1);
    }

    /**
     * 字体+1
     */
    public void fontSizePlus() {
        calcFontSize(ThemeManager.getReadSeekBarPorgress() == -1 ? 5 + 1 : ThemeManager.getReadSeekBarPorgress() + 1);
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

    private class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (seekBar.getId() == seekBar.getId() && fromUser) {
                calcFontSize(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

    }


    private void showBackDialog() {
        mDialog.setDialogMsg(String.format(mContext.getResources().getString(R.string.add_bookstore), bookTilte));
    }

    @Override
    public void onBackPressed() {
        if (top_view.getVisibility() == VISIBLE) {
            toggleMenu(true);
            return;
        }

        if (bookIsInStore) {
            //书架已经存在该书籍
            super.onBackPressed();
        } else {
            //书架没有该书籍
            showBackDialog();
        }

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
            LoginActivity.toThis(mContext, false, false);
        } else if (code == DataCallBack.INTERNAL_SERVER_ERROR) {
            pageView.setStatus(3);
        }

        loading.setVisibility(View.GONE);
        ToastUtils.showShort(mContext, msg);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && resultCode == 1000) {
            mPresenter.onBookChapterPay();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveReadLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvents(String str) {
        if (str.equals(Constant.PAY_CHAPTER)) {
            isRefreshCateLog = true;
            loading.setVisibility(View.VISIBLE);
            chapterId = currentChapterId;
            //当前章节购买
            mPresenter.onBookChapterPay();
        }
    }

    /**
     * 阅读统计
     */
    private void bookStatistics() {
        MobclickAgent.onEvent(mContext, UMengUtils.BOOK_READ);
        MobclickAgent.onEvent(mContext, UMengUtils.BOOK_READ, "book_read");
    }
}
