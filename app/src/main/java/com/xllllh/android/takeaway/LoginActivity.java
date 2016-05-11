package com.xllllh.android.takeaway;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    TopBar topBar;
    EditText username,password;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        topBar = (TopBar) findViewById(R.id.topbar);
        topBar.setOnTopbarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void titleClick() {
                finish();
            }

            @Override
            public void menuClick() {
                Toast.makeText(LoginActivity.this,"You clicked the menu button",Toast.LENGTH_SHORT).show();
            }
        });

        username = (EditText) findViewById(R.id.username);
        Drawable ic_user = getResources().getDrawable(R.mipmap.ic_user,null);
        if (ic_user!=null)
            ic_user.setBounds(0,0,50,50);
        username.setCompoundDrawables(ic_user,null,null,null);

        password = (EditText) findViewById(R.id.password);
        Drawable ic_pass = getResources().getDrawable(R.mipmap.ic_pass,null);
        if (ic_pass!=null)
            ic_pass.setBounds(0,0,50,50);
        password.setCompoundDrawables(ic_pass,null,null,null);

        btnLogin = (Button) findViewById(R.id.login);
    }
}
