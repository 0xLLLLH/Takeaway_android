package com.xllllh.android.takeaway;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int defaultTabIndex = 0;
    ViewPager viewPager;
    TabLayout tabLayout;
    private int imageDefaultId[] = new int[]{R.mipmap.tab_home,R.mipmap.tab_order,R.mipmap.tab_my};
    private int imageActiveId[] = new int[]{R.mipmap.tab_home_active,R.mipmap.tab_order_active,R.mipmap.tab_my_active};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("debug","status:"+UserUtils.isLogined());

        initComponents();
    }

    void initComponents(){
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        SampleFragmentPagerAdapter pagerAdapter =
                new SampleFragmentPagerAdapter(getSupportFragmentManager(), this);

        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                View view = pagerAdapter.getTabView(i);
                ImageView imageView = null;
                if (view != null && i==defaultTabIndex){
                    imageView = (ImageView) view.findViewById(R.id.imageView);
                    imageView.setImageResource(imageActiveId[i]);
                }
                tab.setCustomView(view);
            }
        }
        viewPager.setCurrentItem(defaultTabIndex);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    if (tab != null) {
                        View view = tab.getCustomView();
                        ImageView imageView = null;
                        if (view != null) {
                            imageView = (ImageView) view.findViewById(R.id.imageView);
                            if (i==position)
                                imageView.setImageResource(imageActiveId[i]);
                            else
                                imageView.setImageResource(imageDefaultId[i]);
                        }
                        tab.setCustomView(view);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
