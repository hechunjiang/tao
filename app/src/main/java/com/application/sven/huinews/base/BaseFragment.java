package com.application.sven.huinews.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.sven.huinews.utils.TUtil;

/**
 * Created by sfy. on 2018/5/7 0007.
 */

public abstract class BaseFragment<P extends BasePresenter, M extends BaseModel> extends Fragment implements View.OnClickListener {

    public Context mContext;
    public P mPresenter;
    public M mModel;
    protected View mView;
    protected int PAGE = 1;
    protected int LIMIT = 20;
    protected boolean isRefresh = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getLayoutResource(), container, false);
        }
        this.mContext = getActivity();
        initView(mView);
        initObject();
        initEvents();

        return mView;
    }

    /**
     * 初始化MVP
     */
    public void setMVP() {
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        mPresenter.setVM(this, mModel);
    }


    /**
     * 获取布局文件
     */

    protected abstract int getLayoutResource();

    /**
     * 初始化事物
     */

    public abstract void initObject();

    /**
     * 初始化view
     */
    protected abstract void initView(View v);

    /**
     * 事件监听
     */
    public abstract void initEvents();

    /**
     * 处理监听
     *
     * @param v
     */
    public abstract void OnClickEvents(View v);

    @Override
    public void onClick(View v) {
        OnClickEvents(v);
    }

/*********************跳转相关**********************************/
    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
