package com.xllllh.android.takeaway;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONObject;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class ShopActivity extends FragmentActivity {

    private LinearLayout shopLoading,shopView;
    private ImageView image_load;
    private StickyListHeadersListView stickyList;
    private String shopId;
    private JSONObject shopJSON;
    private ProgressDialog dialog;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private static int defaultTabIndex = 0;

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

        image_load = (ImageView) findViewById(R.id.image_load);

        viewPager = (ViewPager) findViewById(R.id.shopPager);
        tabLayout = (TabLayout) findViewById(R.id.shopTab);
        ShopFragmentPagerAdapter adapter = new ShopFragmentPagerAdapter(getSupportFragmentManager(),
                this,shopId);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(defaultTabIndex);
        //LoadShopDetailTask detailTask = new LoadShopDetailTask(shopId);
        //detailTask.execute();
    }
}
