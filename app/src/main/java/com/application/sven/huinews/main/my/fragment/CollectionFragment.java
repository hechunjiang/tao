package com.application.sven.huinews.main.my.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseFragment;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.http.DataCallBack;
import com.application.sven.huinews.entity.CollectionList;
import com.application.sven.huinews.entity.request.CollectionRequest;
import com.application.sven.huinews.entity.request.VideoCollectionCancelRequest;
import com.application.sven.huinews.main.home.activity.VideoDetailsActivity;
import com.application.sven.huinews.main.my.activity.CollectionActivity;
import com.application.sven.huinews.main.my.adapter.CollectionAdapter;
import com.application.sven.huinews.main.my.contract.CollectionContract;
import com.application.sven.huinews.main.my.model.CollectionModel;
import com.application.sven.huinews.main.my.presenter.CollectionPresenter;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ToastUtils;
import com.application.sven.huinews.view.EmptyLayout;
import com.application.sven.huinews.view.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class CollectionFragment extends BaseFragment<CollectionPresenter, CollectionModel> implements CollectionContract.View {
    private RecyclerView rv;
    private RefreshLayout refresh_view;
    private EmptyLayout emptyLayout;
    private CollectionAdapter mAdapter;
    private List<CollectionList.ListsBean> mCListsBeans;
    private List<CollectionList.ListsBean> mSelectedPos;
    private boolean isAll;
    private boolean isEdit;
    private int type;

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;


    public static CollectionFragment getInstance(int pos) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.COLLECTION_TAB_PAGE_INDEX, pos);
        CollectionFragment fragment = new CollectionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }


    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            // loadData();
            mPresenter.onCollection();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;

            //  printLog(mTextviewContent+"可见,加载数据");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onSelectItemListener) {
            mOnSelectItemListener = (onSelectItemListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    public void initObject() {
        //type== 1 /small video  ==2/ movie
        emptyLayout.setErrorType(EmptyLayout.LOADING, -1);
        type = getArguments().getInt(Constant.COLLECTION_TAB_PAGE_INDEX) + 1;
        setMVP();
        setCollection();

    }

    @Override
    protected void initView(View v) {
        rv = v.findViewById(R.id.rv);
        refresh_view = v.findViewById(R.id.refresh_view);
        emptyLayout = v.findViewById(R.id.mEmptyLayout);
    }

    @Override
    public void initEvents() {
        mAdapter.setOnSelectedLisenter(new CollectionAdapter.onSelectedLisenter() {
            @Override
            public void setOnSelected(List<CollectionList.ListsBean> mList) {
                mSelectedPos = mList;
                mCListsBeans = mAdapter.getData();
                isAll = (mCListsBeans.size() == mList.size()) ? true : false;
                if (mOnSelectItemListener != null) {
                    mOnSelectItemListener.onSelectItem(type - 1, isAll);
                }
            }
        });

        mAdapter.setOnItemViewClickListener(new CollectionAdapter.OnItemViewClickListener() {
            @Override
            public void onItemClick(CollectionList.ListsBean mData) {
                LogUtil.showLog("msg---收藏点击:" + mData.getTitle());
                if (type == 1) {
                    VideoDetailsActivity.toThis(mContext, mData.getId());
                } else if (type == 2) {
                    MovieDetailsActivity.toThis(mContext, mData.getId(), 0, 0);
                }
            }
        });

        refresh_view.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                PAGE = 1;
                isRefresh = true;
                mPresenter.onCollection();
            }
        });

        refresh_view.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(com.scwang.smartrefresh.layout.api.RefreshLayout refreshlayout) {
                PAGE += 1;
                isRefresh = false;
                mPresenter.onCollection();
            }
        });
        emptyLayout.setOnEmptyRefreshLisenter(new EmptyLayout.onEmptyRefreshLisenter() {
            @Override
            public void onEmptyRefresh() {
                PAGE = 1;
                isRefresh = true;
                mPresenter.onCollection();
            }
        });
    }

    @Override
    public void OnClickEvents(View v) {

    }

    private void setCollection() {
        mAdapter = new CollectionAdapter(mContext);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(mAdapter);
    }

    @Override
    public CollectionRequest getCollectionRequest() {
        CollectionRequest request = new CollectionRequest();
        request.setType(type);
        request.setLimit(LIMIT);
        request.setPage(PAGE);
        return request;
    }

    @Override
    public VideoCollectionCancelRequest getVideoCollectionCancelRequest() {
        VideoCollectionCancelRequest request = new VideoCollectionCancelRequest();
        request.setDelType(isAll ? "all" : "");
        request.setType(type == 1 ? Constant.CHANNEL_TYPE_VIDEO : Constant.CHANNEL_TYPE_MOVIE);
        request.setrId(selectIdS());
        return request;
    }

    @Override
    public void setCollectionList(CollectionList mCollectionList) {
        emptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT, -1);
        this.mCListsBeans = mCollectionList.getLists();
        if (isRefresh) {
            refresh_view.finishRefresh();
            refresh_view.setEnableLoadmore(true);
        } else {
            refresh_view.finishLoadmore();
        }
        if (mCollectionList.getLists().size() <= 0) {
            if (isRefresh) {
                emptyLayout.setErrorType(EmptyLayout.NO_DATA, EmptyLayout.NO_COLLETCION);
            }
            if (mOnSelectItemListener != null) {
                mOnSelectItemListener.onDelAllCollOk(type - 1, false);
            }
        } else {
            if (mOnSelectItemListener != null) {
                mOnSelectItemListener.onDelAllCollOk(type - 1, true);
            }

        }
        mAdapter.setData(mCollectionList.getLists(), isRefresh);
    }

    @Override
    public void delCollectionOk() {
        //接口调用成功后，再删除本地数据，再获取数据
        /*if (isAll) {
            delAll();
        } else {
            delAt();
        }*/
        mPresenter.onCollection();
    }


    /**
     * 是否为编辑状态
     *
     * @param isEdit
     */
    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
        mAdapter.setIsEdit(isEdit);

    }

    /**
     * 全选
     *
     * @param isAll
     */
    public void setAllSelect(boolean isAll) {
        this.isAll = isAll;
        mAdapter.setIsAll(isAll);
    }


    /**
     * 删除
     */
    public void del() {
        selectedItem();
        if (mSelectedPos.size() <= 0) {
            ToastUtils.showLong(mContext, "请至少选择一个视频或者影视");
            return;
        }
        mPresenter.onDelCollection();
    }

    private void selectedItem() {
        mSelectedPos = new ArrayList<>();
        Map<CollectionList.ListsBean, Boolean> selectedMap = mAdapter.getSelectedMap();
        for (Map.Entry<CollectionList.ListsBean, Boolean> entry : selectedMap.entrySet()) {
            if (entry.getValue()) {
                mSelectedPos.add(entry.getKey());
            }
        }
    }

    private void delAll() {
        mAdapter.removeAll();
    }

    private void delAt() {
        mAdapter.removeAt(mSelectedPos);
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

    public interface onSelectItemListener {
        void onSelectItem(int position, boolean isAll);

        void onDelAllCollOk(int position, boolean b);
    }

    onSelectItemListener mOnSelectItemListener;

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
            emptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.INTERNAL_SERVER_ERROR) {
            if (!isRefresh) {
                ToastUtils.showLong(mContext, msg);
                refresh_view.finishLoadmore();
                return;
            }
            emptyLayout.setErrorType(EmptyLayout.LOAD_ERROR, EmptyLayout.LOAD_ERROR);
        } else if (code == DataCallBack.NETWORK_ERROR) {
            if (!isRefresh) {
                ToastUtils.showLong(mContext, msg);
                refresh_view.finishLoadmore();
                return;
            }
            emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR, EmptyLayout.NETWORK_ERROR);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //页面销毁,恢复标记
        isViewCreated = false;
        isUIVisible = false;

    }
}
