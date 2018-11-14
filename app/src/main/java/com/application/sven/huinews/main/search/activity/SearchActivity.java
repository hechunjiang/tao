package com.application.sven.huinews.main.search.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.ChannelConfig;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.db.BaseDB;
import com.application.sven.huinews.entity.request.SearchRequest;
import com.application.sven.huinews.entity.response.SearchHotResponse;
import com.application.sven.huinews.entity.response.SearchResponse;
import com.application.sven.huinews.main.search.adapter.HistorySearchAdapter;
import com.application.sven.huinews.main.search.adapter.SearchAdapter;
import com.application.sven.huinews.main.search.adapter.SearchMovieAdapter;
import com.application.sven.huinews.main.search.contract.SearchContract;
import com.application.sven.huinews.main.search.model.SearchModel;
import com.application.sven.huinews.main.search.presenter.SearchPresenter;
import com.application.sven.huinews.utils.KeyBoradUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.utils.umeng.UMengUtils;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.RefreshLayout;
import com.application.sven.huinews.view.dialog.SearchDelDialog;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/12
 * effect:搜索
 */
public class SearchActivity extends BaseActivity<SearchPresenter, SearchModel> implements SearchContract.View {
    private final int TAB_INDEX_SMALL_VIDEO = 0;
    private final int TAB_INDEX_MOVIES = 1;
    private final int TAB_INDEX_TVPLAY = 2;
    private int mType;
    private NestedScrollView search_def_ns;
    private RefreshLayout refresh_view;
    private LinearLayout search_result_ll;
    private ImageButton btn_back;
    private ImageButton btn_clear;
    private RecyclerView history_rv;
    private RecyclerView search_rv;
    private TagFlowLayout flowlayout;
    private HistorySearchAdapter mSearchAdapter;
    private SearchAdapter mAdapter;
    private SearchMovieAdapter mMovieAdapter;
    private SearchDelDialog mDialog;
    private TextView tv_search;
    private EditText et_input;
    private LinearLayout ll_small_video, ll_search_movie, ll_tv_play;
    private TextView tv_small_video, tv_search_movie, tv_search_tvplay;
    private View indicator_small_video, indicator_view_movie, indicator_view_tvplay;
    private EmptyLayout mEmptyLayout;
    private BaseDB mBaseDB;
    private List<String> mSearchHistory;
    private String keyword;

    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, SearchActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        btn_back = findViewById(R.id.btn_back);
        history_rv = findViewById(R.id.history_rv);
        search_rv = findViewById(R.id.search_rv);
        flowlayout = findViewById(R.id.taglayout);
        btn_clear = findViewById(R.id.btn_clear);
        et_input = findViewById(R.id.et_input);
        tv_search = findViewById(R.id.tv_search);
        search_def_ns = findViewById(R.id.search_def_ns);
        search_result_ll = findViewById(R.id.search_result_ll);
        ll_small_video = findViewById(R.id.ll_small_video);
        ll_search_movie = findViewById(R.id.ll_search_movie);
        ll_tv_play = findViewById(R.id.ll_tv_play);
        tv_small_video = findViewById(R.id.tv_small_video);
        tv_search_movie = findViewById(R.id.tv_search_movie);
        tv_search_tvplay = findViewById(R.id.tv_search_tvplay);
        indicator_small_video = findViewById(R.id.indicator_small_video);
        indicator_view_movie = findViewById(R.id.indicator_view_movie);
        indicator_view_tvplay = findViewById(R.id.indicator_view_tvplay);
        mEmptyLayout = findViewById(R.id.mEmptyLayout);
        refresh_view = findViewById(R.id.refresh_view);

    }

    @Override
    public void initEvents() {
        btn_back.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        ll_small_video.setOnClickListener(this);
        ll_search_movie.setOnClickListener(this);
        ll_tv_play.setOnClickListener(this);

        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                PAGE = 1;
                isRefresh = true;
                searchKeyWords();
            }
        });


        refresh_view.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                PAGE += 1;
                isRefresh = false;
                searchKeyWords();
            }
        });

        et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keyword = et_input.getText().toString();
                    mBaseDB.insertInSearchKeyWord(keyword);
                    setSearchResult(true, keyword);
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

                    setSearchResult(false, null);
                }
            }
        });

        mEmptyLayout.setOnEmptyRefreshLisenter(new EmptyLayout.onEmptyRefreshLisenter() {
            @Override
            public void onEmptyRefresh() {
                mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
                setSearchTabStatus(mType);
            }
        });
    }

    @Override
    public void onClickEvent(View v) {
        if (v == btn_back) {
            finish();
        } else if (v == btn_clear) {
            mDialog.setSearchMsg(getString(R.string.del_all), 1, -1);
        } else if (v == tv_search) {
            keyword = et_input.getText().toString();
            mBaseDB.insertInSearchKeyWord(keyword);
            setSearchResult(true, keyword);


        } else if (v == ll_small_video) {
            mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
            setSearchTabStatus(TAB_INDEX_SMALL_VIDEO);
        } else if (v == ll_search_movie) {
            mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
            setSearchTabStatus(TAB_INDEX_MOVIES);
        } else if (v == ll_tv_play) {
            mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
            setSearchTabStatus(TAB_INDEX_TVPLAY);
        }
    }

    @Override
    public void initObject() {
        mBaseDB = BaseDB.getInstance(mContext);
        setMVP();
        getHotSearch();
        initDialog();
        setHistorySearch();
        initTab();

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
                    mBaseDB.emptySearchDb();
                    mSearchAdapter.clearAll();
                    mDialog.dismiss();
                } else {
                    if (mSearchHistory != null && mSearchHistory.size() > 0) {
                        mBaseDB.delInSearchKeyWord(mSearchHistory.get(posiiton));
                        mSearchAdapter.clearData(posiiton);
                        mDialog.dismiss();
                    }
                }
            }
        });
    }

    private void setHistorySearch() {
        mSearchHistory = mBaseDB.getAllHistroySearchList();
        mSearchAdapter = new HistorySearchAdapter(mContext);
        history_rv.setLayoutManager(new LinearLayoutManager(mContext));
        history_rv.setAdapter(mSearchAdapter);
        history_rv.setNestedScrollingEnabled(false);
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
                mBaseDB.delInSearchKeyWord(keyword);
                mBaseDB.insertInSearchKeyWord(keyword);
                setSearchResult(true, keyword);
            }
        });
    }

    /**
     * 设置热门搜索
     *
     * @param searchList
     */
    private void setHotSearchInfo(final List<SearchHotResponse.SearchList> searchList) {
        flowlayout.setAdapter(new TagAdapter(searchList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.hot_search_list_item, flowlayout, false);
                tv.setText(searchList.get(position).getKeyword());
                return tv;
            }
        });

        flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                keyword = searchList.get(position).getKeyword();
                et_input.setText(keyword);
                et_input.setSelection(keyword.length());
                mBaseDB.insertInSearchKeyWord(keyword);
                setSearchResult(true, keyword);
                return false;
            }
        });
    }

    private void getHotSearch() {
        mPresenter.onSearchHot();
    }


    private void initTab() {
        mAdapter = new SearchAdapter(mContext);
        mMovieAdapter = new SearchMovieAdapter(mContext);
        search_rv.setLayoutManager(new LinearLayoutManager(mContext));
        mEmptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        setSearchTabStatus(TAB_INDEX_SMALL_VIDEO);
    }


    private void setSearchTabStatus(int index) {
        mType = index;
        switch (index) {
            case TAB_INDEX_SMALL_VIDEO:
                tv_small_video.setTextColor(getResources().getColor(R.color.color_def));
                tv_search_movie.setTextColor(getResources().getColor(R.color.c_333333));
                tv_search_tvplay.setTextColor(getResources().getColor(R.color.c_333333));
                indicator_small_video.setVisibility(View.VISIBLE);
                indicator_view_movie.setVisibility(View.INVISIBLE);
                indicator_view_tvplay.setVisibility(View.INVISIBLE);
                isRefresh = true;
                PAGE = 1;
                searchKeyWords();
                search_rv.setAdapter(mAdapter);
                break;
            case TAB_INDEX_MOVIES:
                tv_small_video.setTextColor(getResources().getColor(R.color.c_333333));
                tv_search_movie.setTextColor(getResources().getColor(R.color.color_def));
                tv_search_tvplay.setTextColor(getResources().getColor(R.color.c_333333));
                indicator_small_video.setVisibility(View.INVISIBLE);
                indicator_view_movie.setVisibility(View.VISIBLE);
                indicator_view_tvplay.setVisibility(View.INVISIBLE);
                isRefresh = true;
                PAGE = 1;
                search_rv.setAdapter(mMovieAdapter);
                searchKeyWords();
                break;
            case TAB_INDEX_TVPLAY:
                tv_small_video.setTextColor(getResources().getColor(R.color.c_333333));
                tv_search_movie.setTextColor(getResources().getColor(R.color.c_333333));
                tv_search_tvplay.setTextColor(getResources().getColor(R.color.color_def));
                indicator_small_video.setVisibility(View.INVISIBLE);
                indicator_view_movie.setVisibility(View.INVISIBLE);
                indicator_view_tvplay.setVisibility(View.VISIBLE);
                isRefresh = true;
                PAGE = 1;
                search_rv.setAdapter(mMovieAdapter);
                searchKeyWords();
                break;
            default:
                break;
        }
    }

    private void setSearchResult(boolean b, String keyword) {
        if (b) {
            if (TextUtils.isEmpty(keyword)) {
                ToastUtils.showLong(mContext, getString(R.string.tip_input_keyword));
                return;
            }
            search_result_ll.setVisibility(View.VISIBLE);
            search_def_ns.setVisibility(View.GONE);
            searchKeyWords();

            mSearchAdapter.setData(mBaseDB.getAllHistroySearchList());

            MobclickAgent.onEvent(mContext, UMengUtils.SEARCH);
            MobclickAgent.onEvent(mContext, UMengUtils.SEARCH, UMengUtils.SEARCH);
        } else {
            mSearchHistory = mBaseDB.getAllHistroySearchList();
            setSearchTabStatus(TAB_INDEX_SMALL_VIDEO);
            search_result_ll.setVisibility(View.GONE);
            search_def_ns.setVisibility(View.VISIBLE);
            mMovieAdapter.clearData();
            mAdapter.clearData();
        }


    }

    private void searchKeyWords() {
        mPresenter.onSearch();
    }

    @Override
    public SearchRequest getSearchRequest() {
        SearchRequest request = new SearchRequest();
        request.setPage(PAGE);
        request.setLimit(LIMIT);
        request.setKeywords(keyword);
        request.setType(ChannelConfig.getSearchType(mType));
        return request;
    }

    @Override
    public void setHotSearch(List<SearchHotResponse.SearchList> hotSearch) {
        setHotSearchInfo(hotSearch);
    }

    @Override
    public void setSearchList(SearchResponse.Search mSearch) {
        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        if (isRefresh) {
            refresh_view.finishRefresh();
            refresh_view.setEnableLoadmore(true);
        } else {
            refresh_view.finishLoadmore();
        }
        if (mType == TAB_INDEX_SMALL_VIDEO) {
            if (mSearch.getLists().size() <= 0) {
                if (isRefresh) {
                    mEmptyLayout.setErrorType(EmptyLayout.NO_DATA, EmptyLayout.NO_MOVIE);
                }
                return;
            } else {
                mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
            }
            mAdapter.setData(mSearch.getLists(), isRefresh);
        } else {
            if (mSearch.getLists().size() <= 0) {
                if (isRefresh) {
                    mEmptyLayout.setErrorType(EmptyLayout.NO_DATA, EmptyLayout.NO_MOVIE);
                }
                return;
            } else {
                mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
            }
            mMovieAdapter.setData(mSearch.getLists(), isRefresh);
        }
        KeyBoradUtils.HideKeyboard(et_input);
    }

    @Override
    public void showLoading() {

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
}
