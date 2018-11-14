package com.application.sven.huinews.main.preemption.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.MovieDetailResponse;
import com.application.sven.huinews.main.preemption.activity.MovieDetailsActivity;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressADView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sfy. on 2018/5/9 0009.
 * 为你推荐
 */

public class RecommedAdapter extends RecyclerView.Adapter {

    static final int TYPE_DATA = 0;  //原始数据 （包含原始视频，点冠广告，瑞狮广告）
    static final int TYPE_AD = 1; //腾讯广告
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MovieDetailResponse.MovieDetailData.RecommenBean> mRecommenBeanList = new ArrayList<>();
    private List<Object> mDats = new ArrayList<>();
    private HashMap<NativeExpressADView, Integer> mAdViewPositionMap = new HashMap<NativeExpressADView, Integer>();


    public RecommedAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public int getDataList() {
        return mDats.size();
    }

    public List<Object> datas() {
        return mDats;
    }

    public void setData(List list) {
        if (list == null && list.size() == 0) {
            return;
        }
        mDats.addAll(list);
        notifyDataSetChanged();
    }

    /* public void setData(List<MovieDetailResponse.MovieDetailData.RecommenBean> mRecommenBeanList) {
         this.mRecommenBeanList = mRecommenBeanList;
         notifyDataSetChanged();
     }*/
    // 移除NativeExpressADView的时候是一条一条移除的
    public void removeADView(int position, NativeExpressADView adView) {
        mDats.remove(position);
        notifyItemRemoved(position); // position为adView在当前列表中的位置
        notifyItemRangeChanged(0, mDats.size() - 1);
    }

    // 把返回的NativeExpressADView添加到数据集里面去
    public void addADViewToPosition(int position, NativeExpressADView adView) {
        if (position >= 0 && position < mDats.size() && adView != null) {
            mDats.add(position, adView);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mDats.get(position) instanceof NativeExpressADView ? TYPE_AD : TYPE_DATA;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = (viewType == TYPE_AD) ? R.layout.item_left_img_ad_ad : R.layout.recommend_list_item;
        View v = mInflater.inflate(layoutId, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        int type = getItemViewType(position);
        if (type == TYPE_DATA) {
            final MovieDetailResponse.MovieDetailData.RecommenBean data =
                    (MovieDetailResponse.MovieDetailData.RecommenBean) mDats.get(position);
            FrescoUtil.loadDefImg(vh.iv_thumb, data.getM_img());
            vh.tv_content.setText(data.getM_name());
            vh.tv_name.setText(data.getA_star());
            vh.tv_name.setTextColor(mContext.getResources().getColor(R.color.c_999999));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickLisenter != null) {
                        mOnItemClickLisenter.onItemClick(data.getId());
                    }
                }
            });
            vh.tv_play.setVisibility(View.GONE);
            vh.tv_duration.setVisibility(View.GONE);
        } else if (type == TYPE_AD) {

            final NativeExpressADView adView = (NativeExpressADView) mDats.get(position);
            mAdViewPositionMap.put(adView, position); // 广告在列表中的位置是可以被更新的
            if (vh.container.getChildCount() > 0 && vh.container.getChildAt(0) == adView) {
                return;
            }
            if (vh.container.getChildCount() > 0) {
                vh.container.removeAllViews();
            }
            if (adView.getParent() != null) {
                ((ViewGroup) adView.getParent()).removeView(adView);
            }

            vh.tv_name.setText("广告");
            vh.tv_name.setTextColor(mContext.getResources().getColor(R.color.c_e4e4e4));
            vh.tv_content.setText(adView.getBoundData().getTitle());
            vh.container.addView(adView);
            adView.render(); // 调用render方法后sdk才会开始展示广告*/
        }
    }

    @Override
    public int getItemCount() {
        return mDats.size();
    }

    class VH extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_thumb;
        TextView tv_duration;
        TextView tv_content;
        TextView tv_name;
        TextView tv_play;

        ViewGroup container;

        public VH(View v) {
            super(v);
            iv_thumb = v.findViewById(R.id.iv_thumb);
            tv_duration = v.findViewById(R.id.tv_duration);
            tv_content = v.findViewById(R.id.tv_content);
            tv_name = v.findViewById(R.id.tv_name);
            tv_play = v.findViewById(R.id.tv_play);
            container = itemView.findViewById(R.id.express_ad_container);

        }
    }

    public interface OnItemClickLisenter {
        void onItemClick(int movieId);
    }

    private OnItemClickLisenter mOnItemClickLisenter;

    public void setmOnItemClickLisenter(OnItemClickLisenter m) {
        this.mOnItemClickLisenter = m;
    }


}
