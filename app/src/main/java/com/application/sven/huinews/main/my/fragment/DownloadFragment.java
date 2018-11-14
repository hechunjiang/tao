package com.application.sven.huinews.main.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.base.BaseFragment;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.db.BaseDB;
import com.application.sven.huinews.main.my.adapter.DownloadAdapter;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.download.DownloadData;
import com.application.sven.huinews.view.EmptyLayout;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/11
 * effect: 我的下载
 */
public class DownloadFragment extends BaseFragment {
    private RecyclerView rv;
    private DownloadAdapter mAdapter;
    private int index;
    private EmptyLayout emptyLayout;
    private BaseDB mBaseDB;

    public static DownloadFragment getInstance(int pos) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.DOWNLOAD_TAB_PAGE_INDEX, pos);
        DownloadFragment fragment = new DownloadFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        index = getArguments().getInt(Constant.DOWNLOAD_TAB_PAGE_INDEX);
        return R.layout.fragment_list;
    }

    @Override
    public void initObject() {
        emptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        mBaseDB = BaseDB.getInstance(mContext);
        setDownloadInfo();
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

    private void setDownloadInfo() {
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new DownloadAdapter(mContext, index);
        rv.setAdapter(mAdapter);

        List<DownloadData> mDatas = mBaseDB.getAllDownloadMovie();
        if (mDatas.size() > 0) {
            mAdapter.setData(mBaseDB.getAllDownloadMovie());
        } else {
            emptyLayout.setErrorType(EmptyLayout.NO_DATA, EmptyLayout.NO_DOWNLOAD);
        }
    }


    public void clearAll() {
        mBaseDB.deleteMovie();
        mAdapter.clearAll();
        emptyLayout.setErrorType(EmptyLayout.NO_DATA, EmptyLayout.NO_DOWNLOAD);
    }

}