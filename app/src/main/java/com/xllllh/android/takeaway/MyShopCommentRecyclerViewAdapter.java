package com.xllllh.android.takeaway;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

public class MyShopCommentRecyclerViewAdapter extends RecyclerView.Adapter<MyShopCommentRecyclerViewAdapter.ViewHolder> {

    private final List<JSONObject> mValues;

    public MyShopCommentRecyclerViewAdapter(List<JSONObject> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shopcomment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.username.setText(Utils.getValueFromJSONObject(holder.mItem,"username","用户名"));
        holder.time.setText(
                Utils.getValueFromJSONObject(holder.mItem,"time","2016-6-7 0:0:0").split(" ")[0]);
        holder.score.setRating(Float.parseFloat(
                Utils.getValueFromJSONObject(holder.mItem,"score","0.0")
        ));
        holder.score.refreshDrawableState();
        holder.comment.setText(
                Utils.getValueFromJSONObject(holder.mItem, "comments", "")
        );
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView username,time,comment;
        public final RatingBar score;
        public JSONObject mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            username = (TextView) view.findViewById(R.id.comment_username);
            time = (TextView) view.findViewById(R.id.comment_time);
            comment = (TextView) view.findViewById(R.id.comment_content);
            score = (RatingBar) view.findViewById(R.id.comment_score);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.toString() + "'";
        }
    }
}
