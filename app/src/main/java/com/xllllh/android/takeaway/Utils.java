package com.xllllh.android.takeaway;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 0xLLLLH on 16-5-19.
 *
 * Some useful function
 */


public class Utils {

    /**
     * Set color of status bar.
     * @param activity Activity.
     * @param color Color to set.
     * @param fitsSystemWindows determine if reserve room for system window.
     */
    public static void setStatusBarColor(Activity activity, int color, boolean fitsSystemWindows){
        Window window = activity.getWindow();
        if (fitsSystemWindows){
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            //设置透明状态栏,这样才能让 ContentView 向上
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(color);

        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, fitsSystemWindows);
        }
    }

    /**
     * Send HTTP request to get JSONObject
     * @param urlString url to connect
     * @param method method of connection,value should be "GET" or "POST"
     * @param params params of POST request, useless for GET
     * @return JSON Object
     */
    public static JSONObject connectAndGetJSONObject(String urlString, String method, String params) {
        JSONObject jsonObject=null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            if (method.equals("POST")) {
                //Post data
                connection.setDoOutput(true);
                PrintWriter pw = new PrintWriter(connection.getOutputStream());
                pw.print(params);
                pw.flush();
                pw.close();
            }

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine())!=null) {
                response.append(line);
            }
            String jsonString = response.toString();
            Log.d("Utils",jsonString);
            //Parse JSON
            try {
                jsonObject = new JSONObject(jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return jsonObject;
    }

}
