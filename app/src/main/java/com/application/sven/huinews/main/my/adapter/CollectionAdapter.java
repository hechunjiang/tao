package com.application.sven.huinews.main.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.CollectionList;
import com.application.sven.huinews.utils.CommonUtils;
import com.application.sven.huinews.utils.FrescoUtil;
import com.application.sven.huinews.utils.LogUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * auther: sunfuyi
 * data: 2018/5/10
 * effect:我的收藏
 */
public class CollectionAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private boolean isEdit;
    private boolean isAll;
    private List<CollectionList.ListsBean> mCollectionDatas = new ArrayList<>();
    private Map<CollectionList.ListsBean, Boolean> selectedMap = new HashMap<>();
    private List<CollectionList.ListsBean> collPos = new ArrayList<>();


    public CollectionAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public Map<CollectionList.ListsBean, Boolean> getSelectedMap() {
        return selectedMap;
    }

    public List<CollectionList.ListsBean> getData() {
        return mCollectionDatas;
    }

    /**
     * 是否编辑
     *
     * @param b
     */
    public void setIsEdit(boolean b) {
        this.isEdit = b;
        notifyDataSetChanged();
    }

    /**
     * 全选
     *
     * @param b
     */
    public void setIsAll(boolean b) {
        collPos.clear();
        if (mCollectionDatas == null && mCollectionDatas.size() <= 0) {
            return;
        }
        this.isAll = b;
        LogUtil.showLog("msg--- 收藏个数:" + mCollectionDatas.size());
        for (int i = 0; i < mCollectionDatas.size(); i++) {
            selectedMap.put(mCollectionDatas.get(i), isAll);
            collPos.add(mCollectionDatas.get(i));
        }
        notifyDataSetChanged();
    }


    /**
     * 批量删除
     *
     * @param selectPos
     */
    public void removeAt(List<CollectionList.ListsBean> selectPos) {
        if (selectPos != null && selectPos.size() > 0) {
            mCollectionDatas.removeAll(selectPos);
            collPos.removeAll(selectPos);
        }
        notifyDataSetChanged();
    }

    /**
     * 删除全部
     */
    public void removeAll() {
        collPos.clear();
        mCollectionDatas.clear();
        selectedMap.clear();
        notifyDataSetChanged();
    }

    /**
     * 初始化设置数据
     *
     * @param mCollectionDatas
     */
    public void setData(List<CollectionList.ListsBean> mCollectionDatas, boolean isRefresh) {
        if (mCollectionDatas == null && mCollectionDatas.size() <= 0) {
            return;
        }
        if (isRefresh) {
            this.mCollectionDatas.clear();
            selectedMap.clear();
        }

        this.mCollectionDatas.addAll(mCollectionDatas);
        for (int i = 0; i < mCollectionDatas.size(); i++) {
            selectedMap.put(mCollectionDatas.get(i), false);
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.edit_video_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        vh.iv_checkbox.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        final CollectionList.ListsBean mCollectionBean = mCollectionDatas.get(position);
        final boolean isSelected = selectedMap.get(mCollectionBean);
        vh.iv_checkbox.setImageResource(isSelected ? R.mipmap.gouxuan_wdsc_cli : R.mipmap.gouxuan_wdsc_nor);

        if (null != mCollectionBean.getVideo_cover()) {
            FrescoUtil.loadDefImg(vh.thumb, mCollectionBean.getVideo_cover());
            vh.tv_name.setText(mCollectionBean.getA_star());
            vh.tv_display.setText(mCollectionBean.getPlay_count().toString());
            vh.tv_display.setVisibility(View.VISIBLE);
            vh.tv_duration.setVisibility(View.GONE);
        } else if (null != mCollectionBean.getM_img()) {
            FrescoUtil.loadDefImg(vh.thumb, mCollectionBean.getM_img());
            vh.tv_name.setText("主演：" + mCollectionBean.getA_star());
            vh.tv_display.setVisibility(View.GONE);
            vh.tv_duration.setVisibility(View.GONE);
        }

        vh.tv_content.setText(mCollectionBean.getTitle());

        vh.iv_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelected(isSelected, mCollectionBean);
            }
        });

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemViewClickListener != null) {
                    mOnItemViewClickListener.onItemClick(mCollectionBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCollectionDatas.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView iv_checkbox;
        SimpleDraweeView thumb;
        TextView tv_duration;
        TextView tv_content;
        TextView tv_name;
        TextView tv_display;

        public VH(View v) {
            super(v);
            iv_checkbox = v.findViewById(R.id.iv_checkbox);
            thumb = v.findViewById(R.id.iv_thumb);
            tv_duration = v.findViewById(R.id.tv_duration);
            tv_content = v.findViewById(R.id.tv_content);
            tv_name = v.findViewById(R.id.tv_name);
            tv_display = v.findViewById(R.id.tv_display);
        }
    }

    /**
     * 勾选
     *
     * @param isSelected
     * @param itemId
     */
    private void setSelected(boolean isSelected, CollectionList.ListsBean itemId) {
        if (isSelected) {
            collPos.remove(itemId);
            selectedMap.put(itemId, false);
        } else {
            collPos.add(itemId);
            selectedMap.put(itemId, true);
        }
        if (mOnSelectedLisenter != null) {
            mOnSelectedLisenter.setOnSelected(collPos);
        }
        notifyDataSetChanged();
    }


    private onSelectedLisenter mOnSelectedLisenter;


    public interface onSelectedLisenter {
        void setOnSelected(List<CollectionList.ListsBean> mList);
    }

    public void setOnSelectedLisenter(onSelectedLisenter mOnSelectedLisenter) {
        this.mOnSelectedLisenter = mOnSelectedLisenter;
    }

    private OnItemViewClickListener mOnItemViewClickListener;

    public interface OnItemViewClickListener {
        void onItemClick(CollectionList.ListsBean mData);
    }

    public void setOnItemViewClickListener(OnItemViewClickListener l) {
        this.mOnItemViewClickListener = l;
    }
}
