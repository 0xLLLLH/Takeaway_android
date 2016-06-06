package com.xllllh.android.takeaway;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
                .inflate(R.layout.shop_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.commentJSON.setText(holder.mItem.toString());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView commentJSON;
        public JSONObject mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            commentJSON = (TextView) view.findViewById(R.id.comment_json);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + commentJSON.getText() + "'";
        }
    }
}
