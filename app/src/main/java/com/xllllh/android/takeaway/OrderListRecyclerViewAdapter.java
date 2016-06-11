package com.xllllh.android.takeaway;

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

    public OrderListRecyclerViewAdapter(List<JSONObject> items) {
        mValues = items;
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
        holder.mShop.setText(Utils.getValueFromJSONObject(holder.mItem,"store_id",""));
        holder.mStatus.setText(Utils.getValueFromJSONObject(holder.mItem,"state","1111"));
        holder.mTime.setText(Utils.getValueFromJSONObject(holder.mItem,"setorder_time","2016-05-25 20:05:52"));
        holder.mPrice.setText(Utils.getValueFromJSONObject(holder.mItem,"total_price","0"));
        holder.mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OrderListAdapter","click");
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
        public Button mConfirm;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mShop = (TextView) view.findViewById(R.id.order_shop);
            mStatus = (TextView) view.findViewById(R.id.order_status);
            mPrice = (TextView) view.findViewById(R.id.order_price);
            mTime = (TextView) view.findViewById(R.id.order_time);
            mConfirm = (Button) view.findViewById(R.id.order_confirm);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.toString() + "'";
        }
    }
}
