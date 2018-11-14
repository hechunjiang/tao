package com.application.sven.huinews.main.search.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.db.BaseDB;
import com.application.sven.huinews.entity.request.BookStoreRequest;
import com.application.sven.huinews.entity.response.BookStoreCate;
import com.application.sven.huinews.entity.response.BookStoreList;
import com.application.sven.huinews.main.read.adapter.BookStoreListAdapter;
import com.application.sven.huinews.main.read.contract.BookStoreContract;
import com.application.sven.huinews.main.read.model.BookStoreModel;
import com.application.sven.huinews.main.read.presenter.BookStorePresenter;
import com.application.sven.huinews.main.search.adapter.HistorySearchAdapter;
import com.application.sven.huinews.utils.KeyBoradUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.RefreshLayout;
import com.application.sven.huinews.view.dialog.SearchDelDialog;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/13
 * effect: 搜索书籍
 */
public class SearchBookActivity extends BaseActivity<BookStorePresenter, BookStoreModel> implements BookStoreContract.View {
    private RefreshLayout refresh_view;
    private RecyclerView search_rv, history_rv;
    private RelativeLayout rl_data;
    private LinearLayout ll_history;
    private ImageButton btn_clear;
    private BookStoreListAdapter mBookAdapter;
    private HistorySearchAdapter mSearchAdapter;
    private EditText et_input;
    private ImageButton btn_back;
    private TextView tv_search;
    private String keyword;
    private BaseDB mBaseDB;
    private List<String> mSearchHistory;
    private SearchDelDialog mDialog;

    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, SearchBookActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_search;
    }

    @Override
    public void initView() {
        search_rv = findViewById(R.id.search_rv);
        et_input = findViewById(R.id.et_input);
        btn_back = findViewById(R.id.btn_back);
        tv_search = findViewById(R.id.tv_search);
        mEmptyLayout = findViewById(R.id.mEmptyLayout);
        refresh_view = findViewById(R.id.refresh_view);
        history_rv = findViewById(R.id.history_rv);
        rl_data = findViewById(R.id.rl_data);
        ll_history = findViewById(R.id.ll_history);
        btn_clear = findViewById(R.id.btn_clear);


        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = true;
                PAGE = 1;
                onSearch();
            }
        });
        refresh_view.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                isRefresh = false;
                PAGE += 1;
                onSearch();
            }
        });

        mEmptyLayout.setOnEmptyRefreshLisenter(new EmptyLayout.onEmptyRefreshLisenter() {
            @Override
            public void onEmptyRefresh() {
                isRefresh = true;
                PAGE = 1;
                showLoading();
                onSearch();
            }
        });
    }

    @Override
    public void initEvents() {
        btn_back.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        btn_clear.setOnClickListener(this);

        et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keyword = et_input.getText().toString();
                    mBaseDB.insertInBookSearchKeyWord(keyword);
                    isRefresh = true;
                    onSearch();
                }
                return true;
            }
        });

        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    ll_history.setVisibility(View.VISIBLE);
                    rl_data.setVisibility(View.GONE);
                    mSearchHistory = mBaseDB.getAllBookHistroySearchList();
                    mSearchAdapter.setData(mSearchHistory);
                }
            }
        });

    }

    @Override
    public void onClickEvent(View v) {
        if (v == btn_back) {
            finish();
        } else if (v == tv_search) {
            onSearch();
        } else if (v == btn_clear) {
            mDialog.setSearchMsg(getString(R.string.del_all), 1, -1);
        }
    }

    @Override
    public void initObject() {
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        mBaseDB = BaseDB.getInstance(mContext);
        setMVP();
        initRecycler();
        initDialog();
    }

    private void initRecycler() {
        mBookAdapter = new BookStoreListAdapter(mContext);
        search_rv.setLayoutManager(new LinearLayoutManager(mContext));
        search_rv.setAdapter(mBookAdapter);

        mSearchAdapter = new HistorySearchAdapter(mContext);
        history_rv.setLayoutManager(new LinearLayoutManager(mContext));
        history_rv.setAdapter(mSearchAdapter);

        mSearchHistory = mBaseDB.getAllBookHistroySearchList();
        mSearchAdapter.setData(mSearchHistory);


        mSearchAdapter.setOnLongClickLisenter(new HistorySearchAdapter.onLongClickLisenter() {
            @Override
            public void longClick(final int posiiton) {
                mDialog.setSearchMsg(getString(R.string.del_one), 2, posiiton);
            }

            @Override
            public void itemClick(int position) {
                LogUtil.showLog("msg---itemClick:" + mSearchHistory.toString());
                keyword = mSearchHistory.get(position);
                et_input.setText(keyword);
                et_input.setSelection(keyword.length());
                mBaseDB.delInBookSearchKeyWord(keyword);
                mBaseDB.insertInBookSearchKeyWord(keyword);
                isRefresh = true;
                mBookAdapter.clearData();
                onSearch();
            }
        });
    }

    private void onSearch() {
        keyword = et_input.getText().toString();
        if (TextUtils.isEmpty(keyword)) {
            ToastUtils.showLong(mContext, getString(R.string.tip_input_keyword));
            return;
        }

        rl_data.setVisibility(View.VISIBLE);
        ll_history.setVisibility(View.GONE);


        mSearchAdapter.setData(mBaseDB.getAllBookHistroySearchList());
        mPresenter.onBookStore();
    }

    @Override
    public BookStoreRequest getBookStoreRequest() {
        BookStoreRequest request = new BookStoreRequest();
        request.setKeywords(keyword);
        request.setPage(PAGE);
        request.setLimit(LIMIT);
        return request;
    }

    @Override
    public void setCategroy(List<BookStoreCate> mBookStoreCates) {

    }

    @Override
    public void setBookList(BookStoreList bookStoreList) {
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        if (isRefresh) {
            refresh_view.finishRefresh();
            refresh_view.setEnableLoadmore(true);
            if (bookStoreList.getLists().size() <= 0) {
                mEmptyLayout.setErrorType(EmptyLayout.NO_DATA, EmptyLayout.NO_BOOK);
            } else {
                mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
            }
        } else {
            if (!bookStoreList.isIs_more()) {
                ToastUtils.showLong(mContext, "没有更多数据了");
            }
            refresh_view.finishLoadmore();
        }

        mBookAdapter.setDatas(bookStoreList.getLists(), isRefresh);
        KeyBoradUtils.HideKeyboard(et_input);
    }

    @Override
    public void showLoading() {
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorTip(int code, String msg) {
        if (code == DataCallBack.NET_TIME_OUT) {
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

    private void initDialog() {
        if (mDialog == null) {
            mDialog = new SearchDelDialog(mContext);
        }

        mDialog.setDialogClickLisenter(new SearchDelDialog.dialogClickLisenter() {
            @Override
            public void onCancel() {
                mDialog.dismiss();
            }

            @Override
            public void onEnter(int type, int posiiton) {
                if (type == 1) {
                    mBaseDB.emptyBookSearchDb();
                    mSearchAdapter.clearAll();
                    mDialog.dismiss();
                } else {
                    if (mSearchHistory != null && mSearchHistory.size() > 0) {
                        mBaseDB.delInBookSearchKeyWord(mSearchHistory.get(posiiton));
                        mSearchAdapter.clearData(posiiton);
                        mDialog.dismiss();
                    }
                }
            }
        });
    }
}
