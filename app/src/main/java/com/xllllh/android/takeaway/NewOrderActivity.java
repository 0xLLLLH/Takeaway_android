package com.xllllh.android.takeaway;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NewOrderActivity extends Activity {

    private HashMap<String,Integer> dishCount;
    private HashMap<String,String> dishes;
    private String discount;
    private LinearLayout order_dish_list;
    private TopBar topBar;
    private float priceSum;
    private TextView orderPriceSum,orderDiscount,orderAddress,orderRemarks;
    private Button orderButton;
    private Spinner spinner;
    private CheckedTextView payment0,payment1;
    private String paymentType,address_id, dish_string,remarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        init();

        topBar.setOnTopbarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void titleClick() {
                finish();
            }

            @Override
            public void menuClick() {

            }
        });

        dishCount = (HashMap<String,Integer>) getIntent().getExtras().get("dish_count");
        dishes = (HashMap<String, String>) getIntent().getExtras().get("dish_json");
        discount = getIntent().getStringExtra("discount");
        priceSum = 0;

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
                priceSum += price;

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
        View view = inflater.inflate(R.layout.order_dish_list_layout, null);
        ((TextView) view.findViewById(R.id.order_dish_name)).setText("满减优惠");
        ((TextView) view.findViewById(R.id.order_dish_count)).setText(" ");
        if (discount.equals("不满足优惠要求")) {
            orderDiscount.setVisibility(View.INVISIBLE);
            ((TextView) view.findViewById(R.id.order_dish_price)).setText("不满足优惠要求");
        } else {
            orderDiscount.setVisibility(View.VISIBLE);
            orderDiscount.setText(String.format("已优惠￥%s",discount));
            priceSum-= Float.parseFloat(discount);
            ((TextView) view.findViewById(R.id.order_dish_price)).setText(
                    String.format("-￥%s", discount)
            );
        }
        order_dish_list.addView(view);

        orderPriceSum.setText(String.format("￥%.2f",priceSum));
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NewOrderTask().execute();
            }
        });


        payment0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment0.setCheckMarkDrawable(R.mipmap.mg);
                payment1.setCheckMarkDrawable(R.mipmap.mh);
                paymentType = "0";
            }
        });
        payment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment0.setCheckMarkDrawable(R.mipmap.mh);
                payment1.setCheckMarkDrawable(R.mipmap.mg);
                paymentType = "1";
            }
        });

        Date date = new Date();
        ArrayList<String> timeList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        long dateTime = date.getTime();
        dateTime-=dateTime%(20*60*1000);
        dateTime+=20*60*1000;
        for (int i=0;i<8;i++)
        {
            timeList.add(format.format(new Date(dateTime+i*20*60*1000)));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,timeList);
        spinner.setAdapter(adapter);

        if (AddressUtils.addressList.size()>0)
        {
            orderAddress.setText(AddressUtils.addressList.get(0).toString());
            address_id=Utils.getValueFromJSONObject(AddressUtils.addressList.get(0),"id","0");
        }
        else
            orderAddress.setText("暂无地址");
    }

    void init() {
        order_dish_list = (LinearLayout) findViewById(R.id.order_dish_list);
        topBar = (TopBar) findViewById(R.id.topbar);
        orderDiscount = (TextView) findViewById(R.id.order_discount);
        orderPriceSum = (TextView) findViewById(R.id.order_price_sum);
        orderButton = (Button) findViewById(R.id.button_order);
        spinner = (Spinner) findViewById(R.id.spinner);
        orderAddress = (TextView) findViewById(R.id.order_address);
        payment0 = (CheckedTextView) findViewById(R.id.payment_type_0);
        payment1 = (CheckedTextView) findViewById(R.id.payment_type_1);
        orderRemarks = (TextView) findViewById(R.id.remark);
    }

    class NewOrderTask extends AsyncTask<Void,Void,Boolean> {

        private JSONObject result;

        @Override
        protected Boolean doInBackground(Void... params) {
            dish_string = "";
            for (HashMap.Entry<String,Integer> entry:dishCount.entrySet()) {
                if (dish_string.equals(""))
                    dish_string +="-";
                dish_string +=String.format("%s:%s",entry.getKey(),entry.getValue().toString());
            }
            result = OrderUtils.createNewOrder(UserUtils.getUsername(),address_id,
                    Utils.getValueFromJSONObject(ShopUtils.Shop.getShop_json(),"id","0"),
                    dish_string,remarks,paymentType,discount,String.format("%.2f", priceSum));
            return Utils.getValueFromJSONObject(result,"status","fail").equals("success");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Intent intent = new Intent(NewOrderActivity.this,MainActivity.class);
                //intent.putExtra("order_id",result.toString());
                intent.putExtra("index",1);
                startActivity(intent);
            }
            else {
                Toast.makeText(NewOrderActivity.this,"创建订单失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
