package com.xllllh.android.takeaway;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class UsercenterActivity extends Activity {

    Button mWalletButton,mRedPocketButton,mVoucherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercenter);

        mWalletButton = (Button) findViewById(R.id.button_wallet);
        mRedPocketButton = (Button) findViewById(R.id.button_red_pocket);
        mVoucherButton = (Button) findViewById(R.id.button_voucher);
    }
}
