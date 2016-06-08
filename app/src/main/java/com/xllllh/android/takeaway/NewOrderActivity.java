package com.xllllh.android.takeaway;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewOrderActivity extends Activity {

    private HashMap<String,Integer> dishCount;
    private HashMap<String,String> dishes;
    private LinearLayout order_dish_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        dishCount = (HashMap<String,Integer>) getIntent().getExtras().get("dish_count");
        dishes = (HashMap<String, String>) getIntent().getExtras().get("dish_json");

        init();
        LayoutInflater inflater = LayoutInflater.from(NewOrderActivity.this);
        for (HashMap.Entry<String, Integer> entry : dishCount.entrySet()) {
            try {
                View view = inflater.inflate(R.layout.order_dish_list_layout, null);
                order_dish_list.addView(view);
                String id = entry.getKey();
                String name = Utils.getValueFromJSONObject(
                        new JSONObject(dishes.get(id)), "dish_name", "菜品名");
                Float price = Float.parseFloat(Utils.getValueFromJSONObject(
                        new JSONObject(dishes.get(id)), "price", "0"))
                        * entry.getValue();

                ((TextView) view.findViewById(R.id.order_dish_name)).setText(name);
                ((TextView) view.findViewById(R.id.order_dish_count)).setText(
                        String.format("x%d", entry.getValue())
                );
                ((TextView) view.findViewById(R.id.order_dish_price)).setText(
                        String.format("￥%.2f", price)
                );
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    void init() {
        order_dish_list = (LinearLayout) findViewById(R.id.order_dish_list);
    }
}
