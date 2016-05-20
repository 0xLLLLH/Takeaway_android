package com.xllllh.android.takeaway;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private UserLoginTask mAuthTask = null;
    TopBar mTopBar;
    EditText mUsername, mPassword;
    Button mLogin;
    private ProgressDialog dialog;
    private static int minimumPasswordLength =6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        Utils.setStatusBarColor(this,Color.rgb(0,0,0),true);

        mTopBar = (TopBar) findViewById(R.id.topbar);
        mTopBar.setOnTopbarClickListener(new TopBar.topbarClickListener() {
            @Override
            public void titleClick() {
                finish();
            }

            @Override
            public void menuClick() {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

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

        mLogin = (Button) findViewById(R.id.login_button);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LoginActivity","Attempt login");
                attemptLogin();
            }
        });
    }


    /**
     * Attempt to login.
     * Before send request to server,check the fields. if fields not valid,cancel the login process.
     */

    private void attemptLogin() {

        boolean cancel=false;
        View    focusView=null;

        //reset error
        mUsername.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String account = mUsername.getText().toString();
        String password = mPassword.getText().toString();

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

        //check mPassword
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

        if (cancel){
            focusView.requestFocus();
        }
        else {
            //start login progress
            //TODO:start login thread here, AsyncTask is advisable.
            Log.d("LoginActivity", "Start login");

            mAuthTask = new UserLoginTask(account,password);
            mAuthTask.execute();
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
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String email;
        private final String password;

        UserLoginTask(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result=false;
            try {
                result = UserUtils.getInstance().login(email,password);
            } catch (Exception e) {
                return false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
            } else {
                mPassword.setError(getString(R.string.error_invalid_password));
                mPassword.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
