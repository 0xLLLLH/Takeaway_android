package com.xllllh.android.takeaway;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 0xLLLLH on 16-5-22.
 *
 */
public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
implements View.OnClickListener{
    private List<JSONObject> mValues = new ArrayList<>();
    private boolean mIsStagger;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public ItemRecyclerViewAdapter(List<JSONObject> items) {
        mValues = items;
    }

    public void switchMode(boolean mIsStagger) {
        this.mIsStagger = mIsStagger;
    }

    public void setData(List<JSONObject> data) {
        mValues = data;
    }

    public void addData(List<JSONObject> data) {
        mValues.addAll(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LoadMoreRecyclerView.TYPE_STAGGER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shop_list_item, parent, false);
            return new StaggerViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shop_list_item, parent, false);
            view.setOnClickListener(this);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        position--;
        if (mIsStagger) {
            //StaggerViewHolder staggerViewHolder = (StaggerViewHolder) holder;
        } else {
            ViewHolder mHolder = (ViewHolder) holder;
            mHolder.mItem = mValues.get(position);
            mHolder.mView.setTag(mHolder.mItem);
            try {
                mHolder.mShopName.setText(mHolder.mItem.getString("shop_name"));
                mHolder.mShopRating.setRating((float) mHolder.mItem.getDouble("score"));
                mHolder.mShopSold.setText(String.format("月售%s单", mHolder.mItem.getString("sell_num")));
                int sendTime = mHolder.mItem.getInt("ave_sendtime");
                StringBuilder builder = new StringBuilder();
                if (sendTime/60 > 0)
                    builder.append(String.format("%d小时",sendTime/60));
                builder.append(String.format("%d分钟",sendTime%60));
                mHolder.mShopSendTime.setText(builder.toString());
                mHolder.mShopPriceToSend.setText(String.format("起送价￥%d", mHolder.mItem.getInt("price_tosend")));
                mHolder.mShopSendFee.setText(String.format("配送费￥0"));
                String[] discount = mHolder.mItem.getString("discount").split("-");
                if (discount.length == 2) {
                    mHolder.mShopDiscount.setText(String.format("满%s减%s", discount[0], discount[1]));
                } else {
                    mHolder.mShopDiscount.setVisibility(View.GONE);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(JSONObject) v.getTag());
        }
    }

    public class StaggerViewHolder extends RecyclerView.ViewHolder {
        public View mView;

        public StaggerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mShopName,mShopSold,mShopSendTime,mShopPriceToSend,mShopSendFee,mShopDiscount;
        public final RatingBar mShopRating;
        public JSONObject mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mShopName = (TextView) view.findViewById(R.id.shop_name);
            mShopRating = (RatingBar) view.findViewById(R.id.shop_rating);
            mShopSold = (TextView) view.findViewById(R.id.shop_sold);
            mShopSendTime = (TextView) view.findViewById(R.id.shop_send_time);
            mShopPriceToSend = (TextView) view.findViewById(R.id.shop_price_to_send);
            mShopSendFee = (TextView) view.findViewById(R.id.shop_send_fee);
            mShopDiscount = (TextView) view.findViewById(R.id.shop_discount);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.toString() + "'";
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , JSONObject data);
    }


}
