package com.application.sven.huinews.main.search.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseFragment;
import com.application.sven.huinews.config.ChannelConfig;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.entity.events.SearchEvent;
import com.application.sven.huinews.entity.request.SearchRequest;
import com.application.sven.huinews.entity.response.SearchResponse;
import com.application.sven.huinews.main.my.fragment.HistoryVideoFragment;
import com.application.sven.huinews.main.search.adapter.SearchAdapter;
import com.application.sven.huinews.main.search.contract.SearchContract;
import com.application.sven.huinews.main.search.model.SearchModel;
import com.application.sven.huinews.main.search.presenter.SearchPresenter;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.view.EmptyLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:搜索
 */
public class SearchFragment extends BaseFragment {
    private EmptyLayout emptyLayout;
    private RecyclerView rv;
    private SearchAdapter mSearchAdapter;
    private String mType;
    private String keyWords;

    public static SearchFragment getInstance(int pos) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.SEARCH_TAB_PAGE_INDEX, pos);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    protected int getLayoutResource() {
        int pos = getArguments().getInt(Constant.SEARCH_TAB_PAGE_INDEX);
        mType = ChannelConfig.getSearchType(pos);

        return R.layout.fragment_list;
    }

    @Override
    public void initObject() {
        setMVP();
        initRecycler();


    }

    @Override
    protected void initView(View v) {
        rv = v.findViewById(R.id.rv);
        emptyLayout = v.findViewById(R.id.mEmptyLayout);
    }

    @Override
    public void initEvents() {

    }

    @Override
    public void OnClickEvents(View v) {

    }

    private void initRecycler() {

    }


}
