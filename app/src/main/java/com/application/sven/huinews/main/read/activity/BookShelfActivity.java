package com.application.sven.huinews.main.read.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.entity.CollectionList;
import com.application.sven.huinews.entity.request.BookListRequest;
import com.application.sven.huinews.entity.request.BookShelfDelRequest;
import com.application.sven.huinews.entity.request.BookShelfListRequest;
import com.application.sven.huinews.entity.response.BookShelfList;
import com.application.sven.huinews.main.login.activity.LoginActivity;
import com.application.sven.huinews.main.my.activity.PayInfoActivity;
import com.application.sven.huinews.main.read.adapter.BookShelfAdapter;
import com.application.sven.huinews.main.read.contract.BookShelfContract;
import com.application.sven.huinews.main.read.model.BookShelfModel;
import com.application.sven.huinews.main.read.presenter.BookShelfPresenter;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.itemDecoration.BookItemDecoration;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * auther: sunfuyi
 * data: 2018/7/12
 * effect: 书架
 */
public class BookShelfActivity extends BaseActivity<BookShelfPresenter, BookShelfModel> implements BookShelfContract.View {
    private RefreshLayout refresh_view;
    private RecyclerView rv_book;
    private BookShelfAdapter mBookShelfAdapter;
    private RelativeLayout top_view;
    private LinearLayout ll_edit, ll_all, ll_login, ll_data, btn_pay;
    private ImageView iv_check;
    private TextView btn_all;
    private TextView btn_del;
    private TextView btn_login;
    private List<BookShelfList.ListsBean> mSelectedPos;
    private List<BookShelfList.ListsBean> mBooks;
    private boolean isEditing, isAll;


    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, BookShelfActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.activity_book_shelf;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.title_bar);
        mTitleBar.setTitle("书架");
        mTitleBar.hideRightTv();
        mTitleBar.setRightTv("管理");
        rv_book = findViewById(R.id.rv_book);
        ll_edit = findViewById(R.id.ll_edit);
        ll_all = findViewById(R.id.ll_all);
        iv_check = findViewById(R.id.iv_check);
        btn_all = findViewById(R.id.btn_all);
        btn_del = findViewById(R.id.btn_del);
        ll_login = findViewById(R.id.ll_login);
        btn_login = findViewById(R.id.btn_login);
        ll_data = findViewById(R.id.ll_data);
        mEmptyLayout = findViewById(R.id.mEmptyLayout);
        refresh_view = findViewById(R.id.refresh_view);
        btn_pay = findViewById(R.id.btn_pay);
        top_view = findViewById(R.id.top_view);
    }

    @Override
    public void initEvents() {
        mTitleBar.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBar.setRightTvOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });
        ll_all.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_pay.setOnClickListener(this);

        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = true;
                PAGE = 1;
                getBookShelf();
            }
        });
        refresh_view.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = false;
                PAGE += 1;
                getBookShelf();
            }
        });

        mEmptyLayout.setOnEmptyRefreshLisenter(new EmptyLayout.onEmptyRefreshLisenter() {
            @Override
            public void onEmptyRefresh() {
                isRefresh = true;
                PAGE = 1;
                showLoading();
                getBookShelf();
            }
        });
    }

    @Override
    public void onClickEvent(View v) {
        if (v == ll_all) {
            selectAll();
        } else if (v == btn_del) {
            del();
        } else if (v == btn_login) {
            LoginActivity.toThis(mContext, false, false);
        } else if (v == btn_pay) {
            PayInfoActivity.toThis(mContext, 1);
        }
    }

    @Override
    public void initObject() {
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        setMVP();
        initRecycler();
        getBookShelf();
    }

    private void getBookShelf() {
        mPresenter.onBookShelfList();
    }

    private void initRecycler() {
        mBookShelfAdapter = new BookShelfAdapter(mContext);
        rv_book.addItemDecoration(new BookItemDecoration(mContext));
        rv_book.setLayoutManager(new GridLayoutManager(mContext, 3));
        rv_book.setAdapter(mBookShelfAdapter);

        mBookShelfAdapter.setOnSelectedLisenter(new BookShelfAdapter.OnSelectedLisenter() {
            @Override
            public void setOnSelected(List<BookShelfList.ListsBean> mList) {
                LogUtil.showLog("msg----mlist:" + mList.size());
                mBooks = mBookShelfAdapter.getmBookList();
                isAll = (mBooks.size() == mList.size()) ? true : false;
                iv_check.setImageResource(isAll ? R.mipmap.gouxuan_wdsc_cli : R.mipmap.gouxuan_wdsc_nor);
                btn_all.setText(isAll ? getString(R.string.reverse) : getString(R.string.all));
            }
        });

    }

    private void edit() {
        isEditing = !isEditing;
        if (mBookShelfAdapter != null) {
            mBookShelfAdapter.setEdit(isEditing);
        }

        ll_edit.setVisibility(isEditing ? View.VISIBLE : View.GONE);
        mTitleBar.setRightTv(isEditing ? "完成" : "管理");
        // top_view.setVisibility(isEditing ? View.GONE : View.VISIBLE);
    }

    private void selectAll() {
        isAll = !isAll;
        btn_all.setText(isAll ? getString(R.string.reverse) : getString(R.string.all));
        iv_check.setImageResource(isAll ? R.mipmap.gouxuan_wdsc_cli : R.mipmap.gouxuan_wdsc_nor);
        if (mBookShelfAdapter != null) {
            mBookShelfAdapter.setIsAll(isAll);
        }
    }


    public void del() {
        selectedItem();
        if (mSelectedPos.size() <= 0) {
            ToastUtils.showLong(mContext, "请至少选择一本书籍");
            return;
        }
        mPresenter.onBookShelfDel();
    }


    private void selectedItem() {
        mSelectedPos = new ArrayList<>();
        Map<BookShelfList.ListsBean, Boolean> selectedMap = mBookShelfAdapter.getSelectedMap();
        for (Map.Entry<BookShelfList.ListsBean, Boolean> entry : selectedMap.entrySet()) {
            if (entry.getValue()) {
                mSelectedPos.add(entry.getKey());
            }
        }
    }

    /**
     * 集合转字符串 ,隔开
     *
     * @return
     */
    private String selectIdS() {
        List<String> mIds = new ArrayList<>();
        String ids = "";
        for (int i = 0; i < mSelectedPos.size(); i++) {
            mIds.add(mSelectedPos.get(i).getId() + "");
        }
        return CommonUtils.listToStr(mIds, ",");
    }


    @Override
    public BookShelfListRequest getBookShelfListRequest() {
        BookShelfListRequest request = new BookShelfListRequest();
        request.setLimit(LIMIT);
        request.setPage(PAGE);
        return request;
    }

    @Override
    public BookShelfDelRequest getBookShelfDelRequest() {
        BookShelfDelRequest request = new BookShelfDelRequest();
        request.setBookId(selectIdS());
        request.setType(isAll ? 2 : 1);
        return request;
    }

    @Override
    public void setBookShelfList(BookShelfList bookShelfList) {
        // ll_data.setVisibility(View.GONE);
        this.mBooks = bookShelfList.getLists();
        if (isRefresh) {
            if (bookShelfList.getLists().size() > 0) {
                mTitleBar.showRightTv();
                mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
            } else {
                ll_edit.setVisibility(View.GONE);
                mEmptyLayout.setErrorType(EmptyLayout.NO_DATA, EmptyLayout.NO_BOOK);
            }
            refresh_view.finishRefresh();
        } else {
            if (!bookShelfList.isIs_more()) {
                ToastUtils.showLong(mContext, "没有更多数据了");
            }
            refresh_view.finishLoadmore();
        }
        mBookShelfAdapter.setDatas(bookShelfList.getLists(), isRefresh);

    }

    @Override
    public void bookShelfDelOk() {
        getBookShelf();
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
            //  ll_data.setVisibility(View.GONE);
            ll_login.setVisibility(View.VISIBLE);
        } else if (code == DataCallBack.NET_TIME_OUT) {
            if (!isRefresh) {
                ToastUtils.showLong(mContext, msg);
                refresh_view.finishLoadmore();
                return;
            }
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.INTERNAL_SERVER_ERROR) {
            if (!isRefresh) {
                ToastUtils.showLong(mContext, msg);
                refresh_view.finishLoadmore();
                return;
            }
            mEmptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.NETWORK_ERROR) {
            if (!isRefresh) {
                ToastUtils.showLong(mContext, msg);
                refresh_view.finishLoadmore();
                return;
            }
            mEmptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR, EmptyLayout.NETWORK_ERROR);
        }


    }

    @Subscribe
    public void onEvents(String str) {
        if (str.equals(Constant.LOGIN_OK)) {
            ll_login.setVisibility(View.GONE);
            getBookShelf();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
