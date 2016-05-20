package com.xllllh.android.takeaway;


import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import org.json.JSONObject;

/**
 * Created by 0xLLLLH on 16-5-19.
 *
 * This file contains account relate methods, such as login, logout and register.
 * If user had login, variety isLogin would set to true, and information of user would be saved in shared preference.
 */
public class UserUtils extends Application{

    public String mUsername = null;
    private static UserUtils instance;

    public static UserUtils getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mUsername = getUsername();
    }

    public boolean isLogin() {
        return mUsername!=null;
    }

    public boolean login(String username, String password){
        JSONObject jsonResponse = Utils.connectAndGetJSONObject("http://dirtytao.com/demo/index.php/Home/User",
                "POST",String.format("op=signin&username=%s&password=%s",username,password));
        try {
            String status = jsonResponse.getString("status");
            Log.d("UserUtils_login:",status);
            boolean success = status.equals("success");
            if (success)
                setUsername(username);
            return success;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean logout(String username){
        setUsername("");
        return true;
    }

    public boolean signup(String username, String password){
        JSONObject jsonResponse = Utils.connectAndGetJSONObject("http://dirtytao.com/demo/index.php/Home/User",
                "POST",String.format("op=signup&username=%s&password=%s",username,password));
        try {
            String status = jsonResponse.getString("status");
            Log.d("UserUtils_login",status);
            return status.equals("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setUsername(String username) {
        SharedPreferences.Editor editor = getSharedPreferences("AccountInfo", MODE_PRIVATE).edit();
        editor.clear();
        if (!username.equals(""))
            editor.putString("username",username);
        editor.apply();
    }

    private String getUsername() {
        SharedPreferences preferences = getSharedPreferences("AccountInfo", MODE_PRIVATE);
        return preferences.getString("username",null);
    }
}