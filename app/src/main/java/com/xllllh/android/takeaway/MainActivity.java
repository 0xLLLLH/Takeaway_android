package com.xllllh.android.takeaway;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.viewpagerindicator.TitlePageIndicator;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set the pager with an adapter
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        //pager.setAdapter(new TestAdapter(getSupportFragmentManager()));

        //Bind the title indicator to the adapter
        TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.titles);
        titleIndicator.setViewPager(pager);
    }
}
