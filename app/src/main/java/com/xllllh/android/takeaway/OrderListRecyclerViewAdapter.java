package com.xllllh.android.takeaway;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

public class OrderListRecyclerViewAdapter extends RecyclerView.Adapter<OrderListRecyclerViewAdapter.ViewHolder> {

    private final List<JSONObject> mValues;
    private Context context;

    public OrderListRecyclerViewAdapter(Context context, List<JSONObject> items) {
        mValues = items;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        final String orderId = Utils.getValueFromJSONObject(holder.mItem,"id","0");
        //TODO:利用store_id查找店铺名后显示
        //holder.mShop.setText(Utils.getValueFromJSONObject(holder.mItem,"store_id","0"));
        holder.mShop.setText("店铺名");
        String stateCode = Utils.getValueFromJSONObject(holder.mItem,"state",Order.STATE_FINISH);
        String stateString="stateString未设置";

        holder.mCancel.setVisibility(View.GONE);
        holder.mPay.setVisibility(View.GONE);
        holder.mConfirm.setVisibility(View.GONE);
        switch (stateCode) {
            case Order.STATE_UNPAY:
                stateString="未付款";
                holder.mCancel.setVisibility(View.VISIBLE);
                holder.mPay.setVisibility(View.VISIBLE);
                break;
            case Order.STATE_CANCEL:
                stateString="已取消";
                break;
            case Order.STATE_PAYED:
                stateString="等待接单";
                holder.mCancel.setVisibility(View.VISIBLE);
                break;
            case Order.STATE_PAYED_ACCEPT:
                stateString="商家已接单";
                holder.mConfirm.setVisibility(View.VISIBLE);
                break;
            case Order.STATE_PAYED_UNACCEPT:
                stateString="商家拒绝接单";
                break;
            case Order.STATE_FINISH:
                stateString="订单完成";
                break;
        }
        holder.mStatus.setText(stateString);
        holder.mTime.setText(Utils.getValueFromJSONObject(holder.mItem,"setorder_time","2016-05-25 20:05:52"));
        holder.mPrice.setText(
                String.format(
                        "￥%s",Utils.getValueFromJSONObject(holder.mItem,"total_price","0")
                )
        );
        holder.mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeOrderStateTask().execute(orderId,Order.STATE_CANCEL);
                try {
                    holder.mItem.remove("state");
                    holder.mItem.put("state", Order.STATE_CANCEL);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                holder.mStatus.setText("订单已取消");
                holder.mCancel.setVisibility(View.GONE);
                holder.mPay.setVisibility(View.GONE);
                holder.mConfirm.setVisibility(View.GONE);
            }
        });
        holder.mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeOrderStateTask().execute(orderId,Order.STATE_PAYED_ACCEPT);
                try {
                    holder.mItem.remove("state");
                    holder.mItem.put("state", Order.STATE_PAYED_ACCEPT);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                holder.mStatus.setText("商家已接单");
                holder.mCancel.setVisibility(View.GONE);
                holder.mPay.setVisibility(View.GONE);
                holder.mConfirm.setVisibility(View.VISIBLE);
            }
        });
        holder.mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeOrderStateTask().execute(orderId,Order.STATE_FINISH);
                try {
                    holder.mItem.remove("state");
                    holder.mItem.put("state", Order.STATE_FINISH);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                holder.mStatus.setText("订单完成");
                holder.mCancel.setVisibility(View.GONE);
                holder.mPay.setVisibility(View.GONE);
                holder.mConfirm.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public JSONObject mItem;
        public TextView mShop,mPrice,mStatus,mTime;
        public Button mConfirm,mPay,mCancel;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mShop = (TextView) view.findViewById(R.id.order_shop);
            mStatus = (TextView) view.findViewById(R.id.order_status);
            mPrice = (TextView) view.findViewById(R.id.order_price);
            mTime = (TextView) view.findViewById(R.id.order_time);
            mConfirm = (Button) view.findViewById(R.id.order_confirm);
            mPay = (Button) view.findViewById(R.id.order_pay);
            mCancel = (Button) view.findViewById(R.id.order_cancel);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.toString() + "'";
        }
    }

    class ChangeOrderStateTask extends AsyncTask<String,Void,Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            return OrderUtils.setOrderState(params[0],params[1]);
        }
    }
}
