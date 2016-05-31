package com.xllllh.android.takeaway;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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
    public static List<JSONObject> connectAndGetJSONList(String urlString, String method, String params) {
        List<JSONObject> jsonList=new ArrayList<JSONObject>();
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
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i=0;i<jsonArray.length();i++) {
                    jsonList.add(jsonArray.getJSONObject(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return jsonList;
    }
    /*
     * 从网络上获取图片，如果图片在本地存在的话就直接拿，如果不存在再去服务器上下载图片
     * 这里的path是图片的地址
     */
    public Uri getImageURI(String path, File cache) throws Exception {
        String name = getMD5(path) + path.substring(path.lastIndexOf("."));
        File file = new File(cache, name);
        // 如果图片存在本地缓存目录，则不去服务器下载
        if (file.exists()) {
            return Uri.fromFile(file);//Uri.fromFile(path)这个方法能得到文件的URI
        } else {
            // 从网络上获取图片
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {

                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
                // 返回一个URI对象
                return Uri.fromFile(file);
            }
        }
        return null;
    }

    public static String getMD5(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return new String(m);
    }

    /**
     * This method get value of corresponding key in JSON object and return as String.
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return value of corresponding key,can be a json string
     */
    public static String getValueFromJSONObject(JSONObject jsonObject,String key,String defaultValue) {
        String ret = defaultValue;
        try {
            ret = jsonObject.get(key).toString();
        }catch (Exception e) {
            Log.d("Utils",String.format("Unable to get %s from JSON object",key));
            e.printStackTrace();
        }
        return ret;
    }
}