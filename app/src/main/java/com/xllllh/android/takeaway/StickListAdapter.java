package com.xllllh.android.takeaway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Context context;
    private List<Integer> count;

    StickListAdapter(Context context,List<JSONObject> list,HashMap<String,String> header,List<Integer> count) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.dishList = list;
        this.headerList = header;
        this.count = count;
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
        return dishList.get(position).optInt("id");
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.dish_list_item_layout, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.dish_title);
            holder.sold = (TextView) convertView.findViewById(R.id.dish_sold);
            holder.price = (TextView) convertView.findViewById(R.id.dish_price);
            holder.minus = (TextView) convertView.findViewById(R.id.dish_minus);
            holder.num = (TextView) convertView.findViewById(R.id.dish_num);
            holder.plus = (TextView) convertView.findViewById(R.id.dish_plus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(Utils.getValueFromJSONObject(dishList.get(position),"dish_name","dish_name"));
        holder.sold.setText(String.format("月售%s单",
                Utils.getValueFromJSONObject(dishList.get(position),"sell_num","sell_num")));
        holder.price.setText(String.format("￥%s",
                Utils.getValueFromJSONObject(dishList.get(position),"price","price")));
        holder.minus.setVisibility(View.GONE);
        holder.num.setVisibility(View.GONE);
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonOnClickListener != null)
                    buttonOnClickListener.plus(position,holder, dishList.get(position));
            }
        });
        if (count.get(position)>0) {
            holder.minus.setVisibility(View.VISIBLE);
            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonOnClickListener != null)
                        buttonOnClickListener.minus(position ,holder, dishList.get(position));
                }
            });

            holder.num.setVisibility(View.VISIBLE);
            holder.num.setText(String.format("%d",count.get(position)));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,""+holder.toString(),Toast.LENGTH_SHORT).show();
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
        TextView title, sold, plus, num, minus, price;
    }

    public interface ButtonOnClickListener{
        void plus(int position, ViewHolder holder, JSONObject dish);
        void minus(int position, ViewHolder holder, JSONObject dish);
    }
}
