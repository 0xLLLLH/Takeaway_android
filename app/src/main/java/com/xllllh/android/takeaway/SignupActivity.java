package com.xllllh.android.takeaway;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {

    TopBar mTopBar;
    EditText mUsername, mPassword,mConfirm;
    Button mSignUp;
    private ProgressDialog dialog;
    private static int minimumPasswordLength =6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mTopBar = (TopBar) findViewById(R.id.topbar);
        mTopBar.setOnTopbarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void titleClick() {
                finish();
            }

            @Override
            public void menuClick() {
                //Toast.makeText(SignupActivity.this,"You clicked the menu button",Toast.LENGTH_SHORT).show();
            }
        });
        mTopBar.setMenuButtonVisible(false);

        mUsername = (EditText) findViewById(R.id.username);
        Drawable ic_user = getResources().getDrawable(R.mipmap.ic_user,null);
        if (ic_user!=null)
            ic_user.setBounds(0,0,50,50);
        mUsername.setCompoundDrawables(ic_user,null,null,null);

        mPassword = (EditText) findViewById(R.id.password);
        Drawable ic_pass = getResources().getDrawable(R.mipmap.ic_pass,null);
        if (ic_pass!=null)
            ic_pass.setBounds(0,0,50,50);
        mPassword.setCompoundDrawables(ic_pass,null,null,null);

        mConfirm = (EditText) findViewById(R.id.password_confirm);
        Drawable ic_confirm = getResources().getDrawable(R.mipmap.ic_pass,null);
        if (ic_confirm!=null)
            ic_confirm.setBounds(0,0,50,50);
        mConfirm.setCompoundDrawables(ic_confirm,null,null,null);

        mSignUp = (Button) findViewById(R.id.sign_up_button);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SignupActivity","Attempt Sign up");
                attemptSignup();
            }
        });
    }

    /**
     * Attempt to signup.
     * Before send request to server,check the fields. if fields not valid,cancel the signup process.
     */

    private void attemptSignup() {

        boolean cancel=false;
        View focusView=null;

        //reset error
        mUsername.setError(null);
        mPassword.setError(null);
        mConfirm.setError(null);

        // Store values at the time of the login attempt.
        String account = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String confirm = mConfirm.getText().toString();

        //check account
        if (TextUtils.isEmpty(account)){
            mUsername.setError(getString(R.string.error_field_required));
            focusView=mUsername;
            cancel=true;
        }
        else if (!isAccountValid(account))
        {
            mUsername.setError(getString(R.string.error_invalid_account));
            focusView=mUsername;
            cancel=true;
        }

        //check password

        if (TextUtils.isEmpty(password)){
            mPassword.setError(getString(R.string.error_field_required));
            focusView=mPassword;
            cancel=true;
        }
        else if (!isPasswordValid(password)){
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView=mPassword;
            cancel=true;
        }

        //check confirm
        if (TextUtils.isEmpty(confirm)){
            mConfirm.setError(getString(R.string.error_field_required));
            focusView=mConfirm;
            cancel=true;
        }
        else if (!password.equals(confirm)){
            mConfirm.setError(getString(R.string.error_password_different));
            focusView=mConfirm;
            cancel=true;
        }

        if (cancel){
            focusView.requestFocus();
        }
        else {
            showProgress(true);
            //start login progress
            //TODO:start login thread here, AsyncTask is advisable.
            Log.d("SignupActivity", "Start signup");


            showProgress(false);
            Intent intent = new Intent(SignupActivity.this,MainActivity.class);
            startActivity(intent);

        }

    }

    private void showProgress(boolean show){
        //自己的progressbar
        //mFormView.setVisibility(show?View.GONE:View.VISIBLE);
        //mProgressView.setVisibility(show?View.VISIBLE:View.GONE);

        //TODO：此处ProgressDialog太丑，需要改为自己的progressbar
        if (show) {
            dialog=new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setTitle("登录中...");
            dialog.setMessage("请稍等");
            dialog.show();
        }
        else {
            dialog.hide();
        }
    }
    private boolean isAccountValid(String account){
        if (Character.isDigit(account.charAt(0))||account.length()< minimumPasswordLength)
            return false;
        return true;
    }
    private boolean isPasswordValid(String password) {
        if (TextUtils.isEmpty(password)||password.length()< minimumPasswordLength)
            return false;
        return true;
    }
}
