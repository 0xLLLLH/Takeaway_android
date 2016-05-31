package com.xllllh.android.takeaway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by 0xLLLLH on 16-5-30.
 *
 */
public class StickListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<JSONObject> dishList = new ArrayList<>();
    private HashMap<String,String> headerList = new HashMap<>();
    private LayoutInflater inflater;

    StickListAdapter(Context context,List<JSONObject> list,HashMap<String,String> header) {
        inflater = LayoutInflater.from(context);
        dishList = list;
        headerList = header;
    }

    @Override
    public int getCount() {
        return dishList.size();
    }

    @Override
    public Object getItem(int position) {
        return dishList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.dish_list_item_layout, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.dish_list_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(dishList.get(position).toString());

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.dish_list_title_layout, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.dish_list_title);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        String headerText =headerList.get(Utils.getValueFromJSONObject(dishList.get(position),"type_id","0"));
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        long type_id = 0;
        type_id = Long.parseLong(Utils.getValueFromJSONObject(dishList.get(position),"type_id","0"));
        return type_id;
    }
    private class HeaderViewHolder {
        TextView text;
    }

    private class ViewHolder {
        TextView text;
    }
}
