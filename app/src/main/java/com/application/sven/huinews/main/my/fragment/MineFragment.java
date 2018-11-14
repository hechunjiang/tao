package com.application.sven.huinews.main.my.fragment;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.base.BaseFragment;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.config.api.Api;
import com.application.sven.huinews.db.UserSpCache;
import com.application.sven.huinews.entity.MovieHistory;
import com.application.sven.huinews.entity.UserInfo;
import com.application.sven.huinews.entity.request.MovieWatchHistoryRequest;
import com.application.sven.huinews.entity.response.ActiavtePageResponse;
import com.application.sven.huinews.entity.response.UserFlagResponse;
import com.application.sven.huinews.entity.response.UserHitsResponse;
import com.application.sven.huinews.main.login.activity.LoginActivity;
import com.application.sven.huinews.main.my.activity.CollectionActivity;
import com.application.sven.huinews.main.my.activity.DownloadActivity;
import com.application.sven.huinews.main.my.activity.FollowActivity;
import com.application.sven.huinews.main.my.activity.HistoryVideoActivity;
import com.application.sven.huinews.main.my.activity.PayInfoActivity;
import com.application.sven.huinews.main.my.activity.SettingActivity;
import com.application.sven.huinews.main.my.adapter.ActiavtePagerAdapter;
import com.application.sven.huinews.main.my.adapter.PersonInfoRecordAdapter;
import com.application.sven.huinews.main.my.contract.UserIndexContract;
import com.application.sven.huinews.main.my.model.UserIndexModel;
import com.application.sven.huinews.main.my.presenter.UserIndexPresenter;
import com.application.sven.huinews.main.web.activity.WebActivity;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.LogUtil;
import com.application.sven.huinews.utils.ads.AdsConfig;
import com.application.sven.huinews.utils.itemDecoration.VideoRecordItem;
import com.application.sven.huinews.view.dialog.LoginTipDialog;
import com.application.sven.huinews.view.dialog.PersonLoginDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfy. on 2018/5/8 0008.
 * 个人中心
 */

public class MineFragment extends BaseFragment<UserIndexPresenter, UserIndexModel> implements UserIndexContract.View {
    private SwipeRefreshLayout refresh_view;
    private ViewPager mViewPager;
    private RecyclerView recordRv;
    private SimpleDraweeView userHead;
    private RelativeLayout btn_msg, rl_task, btn_yy_withdraw, ad_layout;
    private TextView tvMoreHistory, user_flag, btn_tips, user_code, btn_msg_center, user_gold_count, user_balance_count, user_name, tv_qq, btn_pay_center, user_zs_count;
    private View msg_tip;
    private LinearLayout ll_collect, ll_follow, ll_download, ll_zs, ll_setting, ll_income, ll_balance, ll_mingxi, ll_input_invitation, ll_tixian, ll_help, ll_xiaofei, ll_history, ll_qq;
    private PersonInfoRecordAdapter mAdapter;
    private ActiavtePagerAdapter mActiavtePagerAdapter;
    private LoginTipDialog mTipDialog;
    private View task_dot;
    private PersonLoginDialog mPersonLoginDialog; //去登陆弹窗

    private boolean isLoginFlag; //临时判断用户状态
    private boolean isGetOneRed; //是否领取一元红包
    private boolean isWithDraw; //是否提现一元红包
    private boolean isOneRed; //是否为一元新手红包 ture是，flase 不是

    private List<MovieHistory.ListsBean> mMovieHistorys = new ArrayList<>();

    private AdsConfig adsConfig;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initObject() {
        EventBus.getDefault().register(this);
        setMVP();
        setRecord();
        refresh_view.setRefreshing(true);
        getUserInfo();
        setBannerAd();
    }

    @Override
    protected void initView(View v) {
        refresh_view = v.findViewById(R.id.refresh_view);
        refresh_view.setColorSchemeColors(getResources().getColor(R.color.color_def));
        userHead = v.findViewById(R.id.user_head);
        mViewPager = v.findViewById(R.id.viewpager);
        recordRv = v.findViewById(R.id.record_rv);
        tvMoreHistory = v.findViewById(R.id.tv_more_history);
        ll_collect = v.findViewById(R.id.ll_collect);
        ll_follow = v.findViewById(R.id.ll_follow);
        ll_download = v.findViewById(R.id.ll_download);
        ll_setting = v.findViewById(R.id.ll_setting);
        user_flag = v.findViewById(R.id.user_flag);
        btn_tips = v.findViewById(R.id.btn_tips);
        user_code = v.findViewById(R.id.user_code);
        btn_msg = v.findViewById(R.id.btn_msg);
        ll_income = v.findViewById(R.id.ll_income);
        ll_balance = v.findViewById(R.id.ll_balance);
        rl_task = v.findViewById(R.id.rl_task);
        btn_msg_center = v.findViewById(R.id.btn_msg_center);
        btn_yy_withdraw = v.findViewById(R.id.btn_yy_withdraw);
        ll_mingxi = v.findViewById(R.id.ll_mingxi);
        ll_input_invitation = v.findViewById(R.id.ll_input_invitation);
        ll_tixian = v.findViewById(R.id.ll_tixian);
        ll_help = v.findViewById(R.id.ll_help);
        user_gold_count = v.findViewById(R.id.user_gold_count);
        user_balance_count = v.findViewById(R.id.user_balance_count);
        user_name = v.findViewById(R.id.user_name);
        ll_history = v.findViewById(R.id.ll_history);
        ll_qq = v.findViewById(R.id.ll_qq);
        tv_qq = v.findViewById(R.id.tv_qq);
        task_dot = v.findViewById(R.id.task_dot);
        ad_layout = v.findViewById(R.id.ad_layout);
        msg_tip = v.findViewById(R.id.msg_tip);
        ll_zs = v.findViewById(R.id.ll_zs);
        btn_pay_center = v.findViewById(R.id.btn_pay_center);
        ll_xiaofei = v.findViewById(R.id.ll_xiaofei);
        user_zs_count = v.findViewById(R.id.user_zs_count);
    }

    @Override
    public void initEvents() {
        tvMoreHistory.setOnClickListener(this);
        ll_collect.setOnClickListener(this);
        ll_follow.setOnClickListener(this);
        ll_download.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        user_flag.setOnClickListener(this);
        btn_tips.setOnClickListener(this);
        user_code.setOnClickListener(this);
        btn_msg.setOnClickListener(this);
        ll_balance.setOnClickListener(this);
        ll_income.setOnClickListener(this);
        rl_task.setOnClickListener(this);
        userHead.setOnClickListener(this);
        btn_yy_withdraw.setOnClickListener(this);
        btn_msg_center.setOnClickListener(this);
        ll_mingxi.setOnClickListener(this);
        ll_input_invitation.setOnClickListener(this);
        ll_tixian.setOnClickListener(this);
        ll_help.setOnClickListener(this);
        user_name.setOnClickListener(this);
        ll_qq.setOnClickListener(this);
        ll_zs.setOnClickListener(this);
        btn_pay_center.setOnClickListener(this);
        ll_xiaofei.setOnClickListener(this);

        refresh_view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserInfo();
            }
        });

        recordRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void OnClickEvents(View v) {
        if (v == tvMoreHistory) {
            HistoryVideoActivity.toThis(mContext);
        } else if (v == ll_collect) {
            CollectionActivity.toThis(mContext);
        } else if (v == ll_follow) {
            FollowActivity.toThis(mContext);
        } else if (v == ll_download) {
            DownloadActivity.toThis(mContext);
        } else if (v == ll_setting) {
            SettingActivity.toThis(mContext);
        } else if (v == user_flag) {
            toWeb(Api.MASTERLEVEL);
        } else if (v == btn_tips) {
            toWeb(Api.TIPS);
        } else if (v == btn_msg) {
            toWeb(Api.MESSAGE_CENTER);
        } else if (v == ll_mingxi || v == btn_msg_center) {
            toWeb(Api.MY_INCOME_TYPE1);
        } else if (v == ll_balance) {
            toWeb(Api.MY_INCOME_TYPE2);
        } else if (v == ll_income) {
            toWeb(Api.MY_INCOME_TYPE1);
        } else if (v == rl_task) {
            toWeb(Api.TASK_HALL);
        } else if (v == btn_yy_withdraw) {
            toWeb(Api.MAKEMONEY);
        } else if (v == ll_input_invitation) {
            showPicToLoginDialog();
        } else if (v == ll_tixian) {
            jumpLogic();
        } else if (v == ll_help) {
            toWeb(Api.HELP_FEEDBACK);
        } else if (v == user_name || v == userHead) {
            if (!isLoginFlag) {
                LoginActivity.toThis(mContext, false, false);
            }
        } else if (v == ll_qq) {
            mPresenter.toQQ();
        } else if (v == ll_zs) {
            toWeb(Api.MY_DIAMOND);
        } else if (v == btn_pay_center) {
            if (!isLoginFlag) {
                LoginActivity.toThis(mContext, false, false);
                return;
            }
            PayInfoActivity.toThis(mContext, 1);
        } else if (v == ll_xiaofei) {
            toWeb(Api.MY_DIAMOND);
        }
    }


    private void getUserInfo() {
        mPresenter.onUserInfo();
        mPresenter.onUserFlag();
        mPresenter.onDotInfo();
        mPresenter.onActiavtePage();
        mPresenter.onMovieHistory();
    }

    private void setActivePage(final List<ActiavtePageResponse.Data> response) {
        mActiavtePagerAdapter = new ActiavtePagerAdapter(getActivity(), response);
        mViewPager.setAdapter(mActiavtePagerAdapter);
    }


    private void setRecord() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recordRv.setLayoutManager(manager);
        recordRv.setNestedScrollingEnabled(false);
        recordRv.addItemDecoration(new VideoRecordItem(getActivity()));
        mAdapter = new PersonInfoRecordAdapter(getActivity());
        recordRv.setAdapter(mAdapter);
    }

    private void toWeb(String s) {
        // if (isLoginFlag) {
        WebActivity.toThis(mContext, s);
        // } else {
        //   showLoginTipDialog();
        // }
    }

    private void setBannerAd() {
        adsConfig = new AdsConfig(mContext);
        // adsConfig.getBaiduBannerAd(getActivity(), ad_layout);
        adsConfig.tencentAdBanner(getActivity(), ad_layout);
    }

    @Override
    public void setUserInfo(UserInfo mUserInfo) {
        //临时用户判定
        isLoginFlag = mUserInfo.isLogin_flag() ? true : false;
        refresh_view.setRefreshing(false);
        FrescoUtil.loadDefImg(userHead, mUserInfo.getUserMsg().getHeadimg());
        user_code.setText(getString(R.string.mine_invitation_code) + ":" + mUserInfo.getUserMsg().getInvitation_code());
        user_gold_count.setText(mUserInfo.getUserMsg().getGold_flag() + "");
        user_balance_count.setText("￥" + mUserInfo.getUserMsg().getBalance() + "");
        user_flag.setVisibility(mUserInfo.isLogin_flag() ? View.VISIBLE : View.GONE);
        user_name.setText(isLoginFlag ? mUserInfo.getUserMsg().getNickname() : getString(R.string.tmep_login));

        user_code.setVisibility(isLoginFlag ? View.VISIBLE : View.GONE);
        tv_qq.setText("QQ:" + UserSpCache.getInstance(mContext).getInt(UserSpCache.QQ));
        user_zs_count.setText(mUserInfo.getUserMsg().getDiamond() + "");


        //是否领取过一元红包
        isGetOneRed = mUserInfo.getUserMsg().isOred_status() ? true : false;
        //是否提现一元红包
        isWithDraw = mUserInfo.getUserMsg().isRedcash_status() ? true : false;

        //是否提现的界面处理
        // ivNew.setVisibility(isWithDraw ? View.GONE : View.VISIBLE);
        ll_tixian.setVisibility(mUserInfo.getUserMsg().isRedcash_status() ? View.GONE : View.VISIBLE);
        //是否输入过邀请码
        ll_input_invitation.setVisibility(mUserInfo.getUserMsg().isShare_code_status() ? View.GONE : View.VISIBLE);

        if (!isLoginFlag) {
            mPersonLoginDialog = new PersonLoginDialog(getActivity());
            mTipDialog = new LoginTipDialog(getActivity());
        }

        if (UserSpCache.getInstance(mContext).getBoolean(UserSpCache.RED_OPEN)) {
            ll_tixian.setVisibility(View.VISIBLE);
        } else {
            ll_tixian.setVisibility(View.GONE);
        }


        boolean isAutoPay = mUserInfo.getUserMsg().getIs_deduction() == 1 ? false : true;
        UserSpCache.getInstance(mContext).putBoolean(UserSpCache.SAVE_AUTO_PAY, isAutoPay);
    }


    @Override
    public void setUserFlag(UserFlagResponse.UserFlag mUserFlag) {
        for (int i = 0; i < mUserFlag.getGrade().size(); i++) {
            UserFlagResponse.UserFlag.GradeBean grade = mUserFlag.getGrade().get(i);
            if (mUserFlag.getNow_grade() == grade.getId()) {
                user_flag.setText(grade.getName());
            }
        }
    }

    @Override
    public MovieWatchHistoryRequest getMovieWatchHistoryRequest() {
        MovieWatchHistoryRequest request = new MovieWatchHistoryRequest();
        request.setType(Constant.MOVIE_HISTORY_LIST);
        request.setLimit(1000);
        request.setPage(PAGE);
        return request;
    }


    @Override
    public void setMovieHistory(MovieHistory movieHistory) {
        if (movieHistory.getLists().size() <= 0) {
            recordRv.setVisibility(View.GONE);
            ll_history.setVisibility(View.GONE);
        } else {
            recordRv.setVisibility(View.VISIBLE);
            ll_history.setVisibility(View.VISIBLE);
            //  mMovieHistorys.addAll(movieHistory.getLists());
            mAdapter.setData(movieHistory.getLists());
        }
    }

    @Override
    public void setActivatePage(List<ActiavtePageResponse.Data> mActivates) {
        mCount = mActivates.size();
        setActivePage(mActivates);
        mHandler.removeCallbacks(runnableForViewPager);
        mHandler.postDelayed(runnableForViewPager, TIME);
    }

    @Override
    public void setUserHits(UserHitsResponse response) {
        task_dot.setVisibility(response.getData().isUnTisk() ? View.VISIBLE : View.INVISIBLE);
        msg_tip.setVisibility(response.getData().isNewNews() ? View.VISIBLE : View.INVISIBLE);
        if (response.getData().getReadType().equals(Constant.RED_TYPE_YY)) {
            //一元新手红包
            isOneRed = true;
        } else if (response.getData().getReadType().equals(Constant.RED_TYPE_GOLD)) {
            //金币红包
            isOneRed = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        adsConfig.destoryBdAd();
        adsConfig.destroyTencentAdBanner();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            getUserInfo();
        }
    }

    @Subscribe
    public void onRegOkEvents(String str) {
        if (str.equals(Constant.REGISTER_OK) || str.equals(Constant.LOGIN_OK) || str.equals(Constant.LOGOUT_OK)) {
            refresh_view.setRefreshing(true);
            getUserInfo();
        } else if (str.equals(Constant.REFRESH_HOME)) {
            mPresenter.onMovieHistory();
        }
    }

    Handler mHandler = new Handler();
    int TIME = 3000;
    int itemPosition;
    int mCount;
    /**
     * ViewPager的定时器
     */
    Runnable runnableForViewPager = new Runnable() {
        @Override
        public void run() {
            try {
                itemPosition++;
                mHandler.postDelayed(this, TIME);
                mViewPager.setCurrentItem(itemPosition % mCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    private void jumpLogic() {
        //是否临时用户
        if (isLoginFlag) {
            if (isOneRed && UserSpCache.getInstance(mContext).getBoolean(UserSpCache.RED_OPEN)) {

                // EventBus.getDefault().post(new RedBagHideEvent(true));
            } else {
                //判断是否领取一元红包，已领取，不弹出一元红包
                if (isGetOneRed) {
                    //已经领取没有提现一元红包，点击跳转 一元提现任务界面
                    if (isWithDraw) {
                        //是否提现一元红包，已经提现跳转明细
                        toWeb(Api.MY_INCOME_TYPE2);
                    } else {
                        //未提现跳转一元提现任务
                        toWeb(Api.WITHDRAW);
                    }
                } else {
                    //
                    toWeb(Api.MY_INCOME_TYPE2);
                }
            }
        } else {
            if (UserSpCache.getInstance(mContext).getBoolean(UserSpCache.RED_OPEN)) {
                showToLoginDialog();
            } else {
                toWeb(Api.MY_INCOME_TYPE2);
            }

        }
    }

    /**
     * 去注册
     */
    private void showPicToLoginDialog() {
        if (isLoginFlag) {
            toWeb(Api.ENTERCODE);
        } else {
            if (mTipDialog == null) {
                return;
            }
            mTipDialog.show();
        }
    }

    /**
     * 临时用户去登录弹窗
     */
    private void showToLoginDialog() {
        if (mPersonLoginDialog == null) {
            return;
        }
        mPersonLoginDialog.show();
        mPersonLoginDialog.setOnLoginLisenter(new PersonLoginDialog.OnLoginLisenter() {
            @Override
            public void toLogin() {
                mPersonLoginDialog.dismiss();
                LoginActivity.toThis(mContext, false, false);
            }
        });
    }
}
