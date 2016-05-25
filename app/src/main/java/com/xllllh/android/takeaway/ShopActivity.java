package com.xllllh.android.takeaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShopActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        textView = (TextView) findViewById(R.id.shop_json);
        textView.setText(getIntent().getExtras().getString("json"));
    }
}
