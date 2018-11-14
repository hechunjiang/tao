package com.application.sven.huinews.main.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseFragment;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.main.my.adapter.HistoryVideoAdapter;
import com.application.sven.huinews.main.preemption.adapter.RecommedAdapter;
import com.application.sven.huinews.main.preemption.fragment.PreeTabFragment;

/**
 * auther: sunfuyi
 * data: 2018/5/10
 * effect:  观看历史
 */
public class HistoryVideoFragment extends BaseFragment {
    private RecyclerView rv;
    private HistoryVideoAdapter mAdapter;

    public static HistoryVideoFragment getInstance(int pos) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.HISTORY_TAB_PAGE_INDEX, pos);
        HistoryVideoFragment fragment = new HistoryVideoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    public void initObject() {

    }

    @Override
    protected void initView(View v) {
        rv = v.findViewById(R.id.rv);
    }

    @Override
    public void initEvents() {

    }

    @Override
    public void OnClickEvents(View v) {

    }
}
