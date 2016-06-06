package com.xllllh.android.takeaway;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

public class ShopActivity extends FragmentActivity {

    private LinearLayout shopLoading,shopView;
    private ImageView image_load;
    private TextView shopTitle, shopNotice,shopDiscount;
    private String shopId;
    private JSONObject shopJSON;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TopBar topBar;

    private static int defaultTabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        initComponents();

        try {
            shopJSON = new JSONObject(getIntent().getExtras().getString("json"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        shopId = Utils.getValueFromJSONObject(shopJSON,"id","0");

        ShopFragmentPagerAdapter adapter = new ShopFragmentPagerAdapter(getSupportFragmentManager(),
                this,shopId,Utils.getValueFromJSONObject(shopJSON,"price_tosend","1"));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(defaultTabIndex);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        topBar.setOnTopbarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void titleClick() {
                finish();
            }

            @Override
            public void menuClick() {

            }
        });
        shopTitle.setText(Utils.getValueFromJSONObject(shopJSON,"shop_name","店铺名称"));
        shopNotice.setText(Utils.getValueFromJSONObject(shopJSON,"shop_notice","店铺公告"));

        String[] discount = Utils.getValueFromJSONObject(shopJSON,"discount","10-0").split("-");
        if (discount.length==2)
            shopDiscount.setText(String.format("满%s减%s",discount[0],discount[1]));
        else
            shopDiscount.setVisibility(View.GONE);
    }

    void initComponents()
    {
        shopLoading = (LinearLayout) findViewById(R.id.shop_loading);
        shopView = (LinearLayout) findViewById(R.id.shop_view);
        topBar = (TopBar) findViewById(R.id.topbar);
        image_load = (ImageView) findViewById(R.id.image_load);
        viewPager = (ViewPager) findViewById(R.id.shopPager);
        tabLayout = (TabLayout) findViewById(R.id.shopTab);
        shopTitle = (TextView) findViewById(R.id.shop_name);
        shopNotice = (TextView) findViewById(R.id.shop_notice);
        shopDiscount = (TextView) findViewById(R.id.shop_discount);
    }
}
