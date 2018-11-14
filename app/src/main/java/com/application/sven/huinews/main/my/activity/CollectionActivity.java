package com.application.sven.huinews.main.my.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseActivity;
import com.application.sven.huinews.config.ChannelBean;
import com.application.sven.huinews.config.ChannelConfig;
import com.application.sven.huinews.entity.CollectionList;
import com.application.sven.huinews.main.my.adapter.MineTabPagerAdapter;
import com.application.sven.huinews.main.my.fragment.CollectionFragment;
import com.application.sven.huinews.utils.LogUtil;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/10
 * effect:我的收藏
 */
public class CollectionActivity extends BaseActivity implements CollectionFragment.onSelectItemListener {
    private SlidingTabLayout tablayout;

    private ViewPager mViewPager;
    private MineTabPagerAdapter mineTabPagerAdapter;
    private CollectionFragment fragment;
    private LinearLayout ll_edit, ll_all;
    private ImageView iv_check;
    private List<Boolean> mBooleans = new ArrayList<>();  //记录每个fragment的编辑状态
    private List<Boolean> isNoDatas = new ArrayList<>(); //记录每个fragment的数据有无状态
    private List<Boolean> isSelected = new ArrayList<>(); //记录每个fragment是否全选状态
    private TextView btn_del, btn_all;
    private int mCurrentPisition = 0;
    private boolean isEdit; //是否编辑
    private boolean isAll; //是否全选
    private boolean selected;// 是否选中


    public static void toThis(Context mContext) {
        Intent intent = new Intent(mContext, CollectionActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.titlebar);
        tablayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);
        ll_edit = findViewById(R.id.ll_edit);
        ll_all = findViewById(R.id.ll_all);
        btn_del = findViewById(R.id.btn_del);
        btn_all = findViewById(R.id.btn_all);
        iv_check = findViewById(R.id.iv_check);
        mTitleBar.setTitle(getString(R.string.home_collect));
        mTitleBar.setRightTv(getString(R.string.edit));
    }

    @Override
    public void initEvents() {
        btn_del.setOnClickListener(this);
        ll_all.setOnClickListener(this);
        mTitleBar.setBackOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitleBar.setRightTvOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEdit();
            }
        });

    }

    @Override
    public void onClickEvent(View v) {
        if (v == ll_all) {
            setAll();
        } else if (v == btn_del) {
            del();
        }
    }

    @Override
    public void initObject() {
        setTabInfo();
    }

    private void setTabInfo() {
        ArrayList<ChannelBean> arrayList = ChannelConfig.getHistoryChanel();
        for (int i = 0; i < arrayList.size(); i++) {
            mBooleans.add(false);
            isNoDatas.add(false);
            isSelected.add(false);
        }
        mineTabPagerAdapter = new MineTabPagerAdapter(getSupportFragmentManager(), arrayList, MineTabPagerAdapter.TYPE_COLLECTION);
        mViewPager.setAdapter(mineTabPagerAdapter);
        tablayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /**
                 * 记录滑动的位置
                 * 判断当前是否是编辑状态
                 * 判断当前是否有数据
                 * 如果没数据隐藏右边编辑按钮反之显示
                 * 如果当前不是编辑状态 隐藏底部按钮 反之显示
                 * 判断当前是否为全选状态
                 */
                mCurrentPisition = position;
                boolean isEdit = mBooleans.get(mCurrentPisition);
                boolean isNoData = isNoDatas.get(mCurrentPisition);
                boolean isAll = isSelected.get(mCurrentPisition);
                ll_edit.setVisibility(isEdit ? View.VISIBLE : View.GONE);

                if (isNoData) {
                    mTitleBar.setRightTv(isEdit ? getString(R.string.edit_over) : getString(R.string.edit));
                } else {
                    mTitleBar.hideRightTv();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 启用编辑
     */
    private void setEdit() {
        isEdit = !mBooleans.get(mCurrentPisition);
        ll_edit.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        mTitleBar.setRightTv(isEdit ? getString(R.string.edit_over) : getString(R.string.edit));
        // mTitleBar.hsBack(isEdit);
        //设置fragment为编辑状态，adapter 刷新界面
        mineTabPagerAdapter.getFragment().setEdit(isEdit);
        //记录当前fragment为编辑状态
        mBooleans.set(mCurrentPisition, isEdit);


    }


    private void setAll() {
        if (!isEdit) {
            return;
        }
        isAll = !isAll;

        onSelectItem(mCurrentPisition, isAll);
        mineTabPagerAdapter.getFragment().setAllSelect(isAll);
    }

    @Override
    public void onSelectItem(int position, boolean isAll) {
        mCurrentPisition = position;
        isSelected.set(mCurrentPisition, isAll);
        iv_check.setImageResource(isAll ? R.mipmap.gouxuan_wdsc_cli : R.mipmap.gouxuan_wdsc_nor);
        btn_all.setText(isAll ? getString(R.string.reverse) : getString(R.string.all));
    }

    private void del() {
        mineTabPagerAdapter.getFragment().del();
    }


    @Override
    public void onDelAllCollOk(int position, boolean b) {
        //无数据 和 删除全部数据后的处理
        mCurrentPisition = position;
        LogUtil.showLog("msg----collection  index:" + position);
        if (b) {
            //如果有数据，记录
            // ll_edit.setVisibility(View.VISIBLE);
            mTitleBar.showRightTv();
            isNoDatas.set(mCurrentPisition, true);
        } else {
            //如果没数据，隐藏底部按钮，隐藏编辑按钮，记录当前fragment 为不能编辑状态和无数据状态
            ll_edit.setVisibility(View.GONE);
            mTitleBar.hideRightTv();
            mBooleans.set(mCurrentPisition, false);
            isNoDatas.set(mCurrentPisition, false);
        }
    }

}
