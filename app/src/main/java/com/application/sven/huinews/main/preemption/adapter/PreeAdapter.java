package com.application.sven.huinews.main.preemption.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.sven.huinews.R;
import com.application.sven.huinews.config.Constant;
import com.application.sven.huinews.entity.response.MovieListResponse;
import com.application.sven.huinews.main.home.adapter.viewholder.HomeAdOneViewHolder;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.main.preemption.adapter.viewholder.AlbumViewHolder;
import com.application.sven.huinews.main.preemption.adapter.viewholder.BGridViewHolder;
import com.application.sven.huinews.main.preemption.adapter.viewholder.BannerViewHolder;
import com.application.sven.huinews.main.preemption.adapter.viewholder.ContinueViewHolder;
import com.application.sven.huinews.main.preemption.adapter.viewholder.MountainViewHolder;
import com.application.sven.huinews.main.preemption.adapter.viewholder.SGridViewHodler;
import com.application.sven.huinews.main.preemption.adapter.viewholder.TopBannerViewHolder;
import com.application.sven.huinews.config.PreeBean;
import com.application.sven.huinews.utils.LogUtil;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sfy. on 2018/5/8 0008.
 * 抢先 的适配器
 */

public class PreeAdapter extends RecyclerView.Adapter {

    public static final int TYPE_B_GRID = 1;  //大栅格布局（4图,2图横排）
    public static final int TYPE_S_GRID = 2;  //小栅格布局（6图,3图竖排）
    public static final int TYPE_ALBUM = 3;  //相册布局
    public static final int TYPE_BANNER = 4;  //横幅布局（轮播）
    public static final int TYPE_MOUNTAIN = 5;  //倒山布局
    public static final int TYPE_TOP_BANNER = 6;  //顶部轮播
    public static final int TYPE_CONTINUE = 7;  //续播
    public static final int TYPE_AD_ONE_IMG = 8; //广告大图

    private Context mContext;
    // private List<MovieListResponse.MovieData> mMovieList = new ArrayList<>();
    private List<Object> mDatas = new ArrayList<>();

    public PreeAdapter(Context mContext) {
        this.mContext = mContext;
    }

  /*  public void setData(List<MovieListResponse.MovieData> mDataList, boolean isRefresh) {
        if (mDataList == null && mDataList.size() == 0) {
            return;
        }
        if (isRefresh) {
            mMovieList.clear();
        }
        this.mMovieList.addAll(mDataList);
        notifyDataSetChanged();
    }*/

    public int getDataList() {
        return mDatas.size();
    }

    public List<Object> datas() {
        return mDatas;
    }

    public void setList(List list) {
        if (list == null && list.size() == 0) {
            return;
        }

        this.mDatas = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view;
        switch (viewType) {
            case TYPE_B_GRID:
                view = LayoutInflater.from(mContext).inflate(R.layout.b_grid_item, parent, false);
                holder = new BGridViewHolder(mContext, view);
                break;
            case TYPE_S_GRID:
                view = LayoutInflater.from(mContext).inflate(R.layout.s_grid_item, parent, false);
                holder = new SGridViewHodler(mContext, view);
                break;
            case TYPE_ALBUM:
                view = LayoutInflater.from(mContext).inflate(R.layout.album_item, parent, false);
                holder = new AlbumViewHolder(mContext, view);
                break;
            case TYPE_BANNER:
                view = LayoutInflater.from(mContext).inflate(R.layout.banner_item, parent, false);
                holder = new BannerViewHolder(mContext, view);
                break;
            case TYPE_MOUNTAIN:
                view = LayoutInflater.from(mContext).inflate(R.layout.mountain_list_item, parent, false);
                holder = new MountainViewHolder(mContext, view);
                break;
            case TYPE_TOP_BANNER:
                view = LayoutInflater.from(mContext).inflate(R.layout.top_view_pager_list_item, parent, false);
                holder = new TopBannerViewHolder(mContext, view);
                break;
            case TYPE_CONTINUE:
                view = LayoutInflater.from(mContext).inflate(R.layout.type_continue_play_list_item, parent, false);
                holder = new ContinueViewHolder(mContext, view);
                break;
            case TYPE_AD_ONE_IMG:
                view = LayoutInflater.from(mContext).inflate(R.layout.type_ad_big_img_list_item, parent, false);
                holder = new HomeAdOneViewHolder(mContext, view);
                break;
        }
        return holder;
    }

    TopBannerViewHolder mTopBannerVh;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case TYPE_B_GRID:
                BGridViewHolder mBGridViewHolder = (BGridViewHolder) holder;
                mBGridViewHolder.setBGridInfo((MovieListResponse.MovieData) mDatas.get(position));
                break;
            case TYPE_S_GRID:
                SGridViewHodler sGridViewHodler = (SGridViewHodler) holder;
                sGridViewHodler.setSGridInfo((MovieListResponse.MovieData) mDatas.get(position));
                break;
            case TYPE_ALBUM:
                AlbumViewHolder mAlbumViewHolder = (AlbumViewHolder) holder;
                mAlbumViewHolder.setAlbumInfo((MovieListResponse.MovieData) mDatas.get(position));
                break;
            case TYPE_BANNER:
                BannerViewHolder mBannerViewHolder = (BannerViewHolder) holder;
                mBannerViewHolder.setBannerInfo((MovieListResponse.MovieData) mDatas.get(position));
                break;
            case TYPE_MOUNTAIN:
                MountainViewHolder mMountainViewHolder = (MountainViewHolder) holder;
                mMountainViewHolder.setMountain((MovieListResponse.MovieData) mDatas.get(position));
                break;
            case TYPE_TOP_BANNER:
                if (mTopBannerVh == null) {
                    mTopBannerVh = (TopBannerViewHolder) holder;
                }

                mTopBannerVh.setTopBannerInfo((MovieListResponse.MovieData) mDatas.get(position));
                break;
            case TYPE_CONTINUE:
                ContinueViewHolder mContinueViewHolder = (ContinueViewHolder) holder;
                mContinueViewHolder.setName((MovieListResponse.MovieData) mDatas.get(position));
                break;
            case TYPE_AD_ONE_IMG:
                //处理腾讯广告
                HomeAdOneViewHolder oneViewHolder = (HomeAdOneViewHolder) holder;
                oneViewHolder.setTencentAds((NativeExpressADView) mDatas.get(position));
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        if (mDatas.get(position) instanceof NativeExpressADView) {
            type = TYPE_AD_ONE_IMG;
        } else {
            switch (getItemDataType(((MovieListResponse.MovieData) mDatas.get(position)).getLayout())) {
                case TYPE_B_GRID:
                    type = TYPE_B_GRID;
                    break;
                case TYPE_S_GRID:
                    type = TYPE_S_GRID;
                    break;
                case TYPE_ALBUM:
                    type = TYPE_ALBUM;
                    break;
                case TYPE_BANNER:
                    type = TYPE_BANNER;
                    break;
                case TYPE_MOUNTAIN:
                    type = TYPE_MOUNTAIN;
                    break;
                case TYPE_TOP_BANNER:
                    type = TYPE_TOP_BANNER;
                    break;
                case TYPE_CONTINUE:
                    type = TYPE_CONTINUE;
                    break;
            }
        }

        return type;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    /**
     * 获取布局信息
     *
     * @param str
     * @return
     */
    private int getItemDataType(String str) {
        int type = 0;
        if (str.equals(Constant.TYPE_B_GRID)) {
            type = TYPE_B_GRID;
        } else if (str.equals(Constant.TYPE_ALBUM)) {
            type = TYPE_ALBUM;
        } else if (str.equals(Constant.TYPE_BANNER)) {
            type = TYPE_BANNER;
        } else if (str.equals(Constant.TYPE_CONTINUE)) {
            type = TYPE_CONTINUE;
        } else if (str.equals(Constant.TYPE_MOUNTAIN)) {
            type = TYPE_MOUNTAIN;
        } else if (str.equals(Constant.TYPE_S_GRID)) {
            type = TYPE_S_GRID;
        } else if (str.equals(Constant.TYPE_TOP_BANNER)) {
            type = TYPE_TOP_BANNER;
        }
        return type;
    }

    public void onDestoryHandler() {
        if (mTopBannerVh != null) {
            mTopBannerVh.onDestroyHandler();
        }
    }

    public void onStartHandler(){
        if (mTopBannerVh!=null){
            mTopBannerVh.onStartHandler();
        }
    }
}
