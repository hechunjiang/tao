package com.application.sven.huinews.main.my.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.sven.huinews.R;
import com.application.sven.huinews.entity.response.PayMsgResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/12
 * effect:
 */
public class PayInfoAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private int curPos = -1;
    private List<PayMsgResponse.PayMsg.PaymsgBean> mPayMsg = new ArrayList<>();

    public PayInfoAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setDatas(List<PayMsgResponse.PayMsg.PaymsgBean> mPayMsg) {
        if (mPayMsg.size() <= 0 && mPayMsg == null) {
            return;
        }
        this.mPayMsg = mPayMsg;
        notifyDataSetChanged();
    }

    public void setCurrPosition(int position) {
        this.curPos = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.pay_info_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        final PayMsgResponse.PayMsg.PaymsgBean payMsg = mPayMsg.get(position);
        vh.tv_pay_masonry.setText(payMsg.getBase_diamond() + "钻石");
        vh.tv_pay_money.setText(payMsg.getName());
        vh.tv_give_masonry.setText("（送" + payMsg.getAdd_diamond() + "钻石）");
        if (curPos == position) {
            vh.ll.setSelected(true);
            vh.tv_pay_masonry.setSelected(true);
            vh.tv_pay_masonry.setSelected(true);
        } else {
            vh.ll.setSelected(false);
            vh.tv_pay_masonry.setSelected(false);
            vh.tv_pay_masonry.setSelected(false);
        }

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(payMsg, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPayMsg.size();
    }

    class VH extends RecyclerView.ViewHolder {
        LinearLayout ll;
        TextView tv_pay_money;
        TextView tv_pay_masonry, tv_give_masonry;

        public VH(View itemView) {
            super(itemView);
            ll = itemView.findViewById(R.id.ll);
            tv_pay_money = itemView.findViewById(R.id.tv_pay_money);
            tv_pay_masonry = itemView.findViewById(R.id.tv_pay_masonry);
            tv_give_masonry = itemView.findViewById(R.id.tv_give_masonry);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(PayMsgResponse.PayMsg.PaymsgBean paymsgBean, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mOnItemClickListener = l;
    }
}
