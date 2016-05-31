package com.xllllh.android.takeaway;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class ShopActivity extends Activity {

    private LinearLayout shopLoading,shopView;
    private TextView textView;
    private StickyListHeadersListView stickyList;
    private String shopId;
    private JSONObject shopJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        try {
            shopJSON = new JSONObject(getIntent().getExtras().getString("json"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        shopId = Utils.getValueFromJSONObject(shopJSON,"id","0");
        shopLoading = (LinearLayout) findViewById(R.id.shop_loading);
        shopView = (LinearLayout) findViewById(R.id.shop_view);

        textView = (TextView) findViewById(R.id.shop_json);
        textView.setText(getIntent().getExtras().getString("json"));

        LoadShopDetailTask detailTask = new LoadShopDetailTask(shopId);
        detailTask.execute();
    }

    class LoadShopDetailTask extends AsyncTask<Void,Void,Boolean> {

        String shopId;
        JSONObject shopDetail;
        List<JSONObject> dishList;
        HashMap<String,String> dishType;

        LoadShopDetailTask(String shopId) {
            this.shopId = shopId;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                shopDetail = ShopUtils.Shop.getShopDetail(shopId);
                dishType = ShopUtils.Shop.getDishTypeList(shopDetail.getJSONArray("dishtype"));
                dishList = ShopUtils.Shop.getDishList(shopId);
                return true;
            }catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            shopLoading.setVisibility(View.VISIBLE);
            shopView.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                stickyList = (StickyListHeadersListView) findViewById(R.id.dish_list);
                StickListAdapter adapter = new StickListAdapter(ShopActivity.this, dishList, dishType);
                stickyList.setAdapter(adapter);

                shopLoading.setVisibility(View.GONE);
                shopView.setVisibility(View.VISIBLE);
            }
        }
    }
}
