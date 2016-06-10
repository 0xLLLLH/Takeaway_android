package com.xllllh.android.takeaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        ((TextView) findViewById(R.id.order_id)).setText(getIntent().getExtras().getString("order_id"));
    }
}
