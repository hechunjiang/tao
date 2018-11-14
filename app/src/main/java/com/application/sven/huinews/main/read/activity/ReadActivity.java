/*
package com.application.sven.huinews.main.read.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.entity.request.BookChapterPayRequest;
import com.application.sven.huinews.entity.request.BookChapterRequest;
import com.application.sven.huinews.entity.request.BookContentRequest;
import com.application.sven.huinews.entity.response.BookChapterInfoResponse;
import com.application.sven.huinews.entity.response.BookChapterItem;
import com.application.sven.huinews.entity.response.BookChapters;
import com.application.sven.huinews.main.read.adapter.ReadBgAdapter;
import com.application.sven.huinews.main.read.contract.BookReadContract;
import com.application.sven.huinews.main.read.model.BookReadModel;
import com.application.sven.huinews.main.read.presenter.BookReadPresenter;
import com.application.sven.huinews.main.read.test.TestConfig;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ScreensUitls;
import com.application.sven.huinews.utils.statusbar.Eyes;
import com.application.sven.huinews.view.newread.PageView;
import com.application.sven.huinews.view.read.OnReadStateChangeListener;
import com.application.sven.huinews.view.read.ThemeManager;
import com.application.sven.huinews.view.read.cache.CacheBook;
import com.application.sven.huinews.view.read.widght.PageWidget;


*/
/**
 * auther: sunfuyi
 * data: 2018/7/5
 * effect: 阅读界面
 *//*

public class ReadActivity extends BaseActivity<BookReadPresenter, BookReadModel> implements BookReadContract.View {
    private View decodeView;
    private RelativeLayout root_view;
    private FrameLayout read_layout;
    private LinearLayout top_view, bottom_view, pop_window, ll_set;
    private ImageButton btn_back, btn_more;
    private PageWidget mReadView;
    private TextView btn_catalog, btn_datnight, btn_set;
    private TextView read_tv_flip_over_cover, read_tv_flip_over_simulation, read_tv_flip_over_slide, read_tv_flip_over_none;
    private RecyclerView bg_rv;
    private SeekBar seek_font_bar;
    private ImageView btn_font_reduce, btn_font_plus;
    private PageView mPageView;
    private int currentChapter = 1;
    private String bookId;
    private int curTheme = -1;
    private boolean isShowPop = false;
    private boolean isDayNight;

    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, ReadActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        Eyes.setStatusBarLightMode(this, getResources().getColor(R.color.c_ff222222));
        return R.layout.activity_read;
    }

    @Override
    public void initView() {
       // root_view = findViewById(R.id.root_view);
        read_layout = findViewById(R.id.read_layout);
        top_view = findViewById(R.id.top_view);
        bottom_view = findViewById(R.id.bottom_view);
        pop_window = findViewById(R.id.pop_window);
        btn_back = findViewById(R.id.btn_back);
        btn_more = findViewById(R.id.btn_more);
        btn_datnight = findViewById(R.id.btn_datnight);
        btn_catalog = findViewById(R.id.btn_catalog);
        bg_rv = findViewById(R.id.bg_rv);
        seek_font_bar = findViewById(R.id.seek_font_bar);
        btn_font_reduce = findViewById(R.id.btn_font_reduce);
        btn_font_plus = findViewById(R.id.btn_font_plus);
        ll_set = findViewById(R.id.ll_set);
        btn_set = findViewById(R.id.btn_set);

    }

    @Override
    public void initEvents() {
        btn_back.setOnClickListener(this);
        btn_more.setOnClickListener(this);
        btn_datnight.setOnClickListener(this);
        btn_catalog.setOnClickListener(this);
        btn_font_plus.setOnClickListener(this);
        btn_font_reduce.setOnClickListener(this);
        btn_set.setOnClickListener(this);
        ll_set.setOnClickListener(this);

    }

    @Override
    public void onClickEvent(View v) {
        if (v == btn_back) {
            finish();
        } else if (v == btn_more) {
            showPop();
        } else if (v == btn_datnight) {
            changeMode();
        } else if (v == btn_catalog) {
           // CatalogActivity.toThis(mContext, 1);
        } else if (v == btn_font_plus) {
            fontSizePlus();
        } else if (v == btn_font_reduce) {
            fontSizeReduce();
        } else if (v == btn_set) {
            showSet();
        } else if (v == ll_set) {

        }
    }


    @Override
    public void initObject() {
        */
/*RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.bottomMargin = 200;*//*

        decodeView = getWindow().getDecorView();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) top_view.getLayoutParams();
        params.topMargin = ScreensUitls.getStatusBarHeight(this) - 2;
        top_view.setLayoutParams(params);
        bookId = "test_boo_id";
        mReadView = new PageWidget(mContext, bookId, new ReadListener());

        read_layout.removeAllViews();
        read_layout.addView(mReadView);

        //   read_layout.addView(payInfoView(), -1, params);


        TestConfig config = new TestConfig();
        config.getTestChapter();

        if (config.getTestChapter() != null) {
            CacheBook.getInstance().saveChapterFile(bookId, 1, config.getTestChapter());
        }

        setDefSet();
    }


    */
/**
     * 默认设置
     *//*

    private void setDefSet() {
        //获取当前主题色
        curTheme = ThemeManager.getCacheThemeBg();
        //设置字体信息
        seek_font_bar.setMax(10);
        int fontSizePx = ThemeManager.getReadFontSize();
        int progress = (int) ((CommonUtils.dip2px(mContext, fontSizePx) - 12) / 1.7f);
        seek_font_bar.setProgress(progress);
        seek_font_bar.setOnSeekBarChangeListener(new SeekBarChangeListener());
        mReadView.init(curTheme);
        setBgList();


    }

    @Override
    public BookContentRequest getBookConentRequest() {
        BookContentRequest request = new BookContentRequest();
        // request.setBookId(b);
        return null;
    }

    @Override
    public BookChapterRequest getBookChapterRequest() {
        return null;
    }

    @Override
    public BookChapterPayRequest getBookChapterPayRequest() {
        return null;
    }

    @Override
    public void setBookChapters(BookChapters mBookChapters) {

    }

    @Override
    public void setBookChapterInfo(BookChapterItem mBookChapterInfo, boolean isOpen) {

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

    private class ReadListener implements OnReadStateChangeListener {
        @Override
        public void onChapterChanged(int chapter) {
            LogUtil.showLog("onChapterChanged:" + chapter);

        }

        @Override
        public void onPageChanged(int chapter, int page) {
            LogUtil.showLog("onPageChanged:" + chapter + "-" + page);
        }

        @Override
        public void onLoadChapterFailure(int chapter) {
            LogUtil.showLog("onLoadChapterFailure:" + chapter);

        }

        @Override
        public void onCenterClick() {
            LogUtil.showLog("onCenterClick");
            toggleReadBar();
        }

        @Override
        public void onFlip() {
            hideReadBar();
        }
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

    */
/**
     * 切换隐藏显示
     *//*

    private synchronized void toggleReadBar() {
        if (top_view.getVisibility() == View.VISIBLE) {
            hideReadBar();
        } else {
            showReadBar();
        }
    }


    */
/**
     * 隐藏工具栏
     *//*

    private synchronized void hideReadBar() {
        top_view.setVisibility(View.GONE);
        bottom_view.setVisibility(View.GONE);
        pop_window.setVisibility(View.GONE);
        isShowPop = false;
        hideStatusBar();
        decodeView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    Handler handler = new Handler();

    */
/**
     * 显示工具栏
     *//*

    private synchronized void showReadBar() { // 显示工具栏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                top_view.setVisibility(View.VISIBLE);
                bottom_view.setVisibility(View.VISIBLE);
            }
        }, 100);

        showStatusBar();
        decodeView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void showPop() {
        isShowPop = !isShowPop;
        if (isShowPop) {
            pop_window.setVisibility(View.VISIBLE);
        } else {
            pop_window.setVisibility(View.GONE);
        }
    }

    */
/**
     * 日夜间模式切换
     *//*

    private void changeMode() {
        isDayNight = !isDayNight;
        int theme = isDayNight ? ThemeManager.NIGHT : curTheme;
        mReadView.setTheme(theme);
        btn_datnight.setText(isDayNight ? R.string.day_mode : R.string.night_mode);
        Drawable drawable = ContextCompat.getDrawable(this, isDayNight ? R.mipmap.icon_read_day : R.mipmap.icon_read_night);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        btn_datnight.setCompoundDrawables(null, drawable, null, null);
        ThemeManager.setReaderTheme(theme, root_view);
    }

    */
/**
     * 显示隐藏设置
     *//*

    private void showSet() {
        ll_set.setVisibility(ll_set.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
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
                //设置当前主题色
                mReadView.setTheme(posiiton);
                ThemeManager.setReaderTheme(posiiton, root_view);
                //保存选择的主题颜色
                ThemeManager.cacheThemeBg(posiiton);

                curTheme = posiiton;

            }
        });
        mReadBgAdapter.setSelected(curTheme);
    }


    */
/**
     * 设置字体大小
     *
     * @param progress
     *//*

    private void calcFontSize(int progress) {
        // progress range 1 - 10
        if (progress >= 0 && progress <= 10) {
            seek_font_bar.setProgress(progress);
            mReadView.setFontSize(CommonUtils.dip2px(mContext, 12 + 1.7f * progress));
        }
    }

    */
/**
     * 字体-1
     *//*

    public void fontSizeReduce() {
        calcFontSize(seek_font_bar.getProgress() - 1);
    }

    */
/**
     * 字体+1
     *//*

    public void fontSizePlus() {
        calcFontSize(seek_font_bar.getProgress() + 1);
    }


    private View payInfoView() {
        View payView = LayoutInflater.from(mContext).inflate(R.layout.read_pay_layout, null);
        return payView;
    }

}
*/
