package com.xllllh.android.takeaway;

import android.app.Application;

/**
 * Created by 0xLLLLH on 16-5-21.
 *
 * This Class contains common configurations of App, meanwhile, it do some init works on app create.
 */
public class Config extends Application {

    private static Config instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //init util classes need context
        UserUtils.init(getApplicationContext());
    }

    public static Config getInstance() {
        return instance;
    }
}
