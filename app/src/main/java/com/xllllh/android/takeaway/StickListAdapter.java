package com.xllllh.android.takeaway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
    private ButtonOnClickListener buttonOnClickListener;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.dish_list_item_layout, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.dish_title);
            holder.minus = (TextView) convertView.findViewById(R.id.dish_minus);
            holder.num = (TextView) convertView.findViewById(R.id.dish_num);
            holder.plus = (TextView) convertView.findViewById(R.id.dish_plus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(Utils.getValueFromJSONObject(dishList.get(position),"dish_name","dish_name"));
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonOnClickListener!=null)
                    buttonOnClickListener.plus(holder, dishList.get(position));
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonOnClickListener!=null)
                    buttonOnClickListener.minus(holder, dishList.get(position));
            }
        });
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

    public void setButtonOnClickListener(ButtonOnClickListener buttonOnClickListener) {
        this.buttonOnClickListener = buttonOnClickListener;
    }

    public class HeaderViewHolder {
        TextView text;
    }

    public class ViewHolder {
        TextView title;
        TextView plus;
        TextView num;
        TextView minus;
    }

    public interface ButtonOnClickListener{
        void plus(ViewHolder holder, JSONObject dish);
        void minus(ViewHolder holder, JSONObject dish);
    }
}
