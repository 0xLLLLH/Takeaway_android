package com.xllllh.android.takeaway;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 0xLLLLH on 16-5-22.
 *
 */
public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<JSONObject> mValues = new ArrayList<>();
    private boolean mIsStagger;

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
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        position--;
        if (mIsStagger) {
            StaggerViewHolder staggerViewHolder = (StaggerViewHolder) holder;
            staggerViewHolder.iconView.setVisibility(View.VISIBLE);
            staggerViewHolder.mContentView.setText(mValues.get(position).toString());
        } else {
            ViewHolder mHolder = (ViewHolder) holder;
            mHolder.mItem = mValues.get(position);
            mHolder.mContentView.setText(mValues.get(position).toString());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class StaggerViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public View iconView;
        public TextView mContentView;

        public StaggerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            iconView = itemView.findViewById(R.id.icon);
            mContentView = (TextView) itemView.findViewById(R.id.content);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public JSONObject mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
