package com.xllllh.android.takeaway;


import android.app.Application;

/**
 * Created by 0xLLLLH on 16-5-19.
 *
 * This file contains account relate methods, such as login, logout and register.
 * If user had login, variety isLogin would set to true, and information of user would be saved in shared preference.
 */
public class UserUtils extends Application{

    public boolean isLogin = false;

    public UserUtils() {
        isLogin = false;
    }

    public boolean login(String username, String password){
        return false;
    }

    public boolean logout(String username){
        return false;
    }

    public int signup(String username, String password){
        return 0;
    }
}