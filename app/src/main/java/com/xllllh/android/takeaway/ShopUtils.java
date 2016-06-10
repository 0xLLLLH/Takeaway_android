package com.xllllh.android.takeaway;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 0xLLLLH on 16-5-22.
 *
 */
public class ShopUtils {
    public static class ShopList {
        static int page =0;
        static int pageSize = 5;
        static List<JSONObject> initialContents = new ArrayList<>();
        static boolean mHasMore;

        public static void init() {
            page = 0;
            mHasMore = hasMore();
            if (mHasMore)
                initialContents .addAll( getShops());
        }

        public static void setPage(int p) {
            page = p;
        }

        public static boolean hasMore() {
            List<JSONObject> ret = new ArrayList<>();
            try {
                ret.addAll(Utils.connectAndGetJSONList("http://dirtytao.com/androidAPI/store/storelist",
                        "POST",String.format("page=%d&limit=%d",page,pageSize)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret.size() > 0;
        }

        public static List<JSONObject> getShops() {
            List<JSONObject> ret = new ArrayList<>();
            try {
                ret.addAll(Utils.connectAndGetJSONList("http://dirtytao.com/androidAPI/store/storelist",
                        "POST",String.format("page=%d&limit=%d",page++,pageSize)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ret;
        }
    }

    public static class Shop {

        static JSONObject shop_json;

        public static JSONObject getShop_json() {
            return shop_json;
        }

        public static JSONObject getShopDetail(String shop_id){
            JSONObject detail = null;
            detail = Utils.connectAndGetJSONObject("http://dirtytao.com/androidAPI/Store/storedetail",
                    "POST",String.format("sid=%s",shop_id));
            shop_json = detail;
            return detail;
        }

        public static HashMap<String, String> getDishTypeList(JSONArray jsonArray) {
            HashMap<String, String> type_list = new HashMap<>();
            for (int i=0; i< jsonArray.length();i++) {
                JSONObject obj=null;
                try {
                    obj = jsonArray.getJSONObject(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                type_list.put(Utils.getValueFromJSONObject(obj,"id","0"),Utils.getValueFromJSONObject(obj,"type",""));
            }
            return type_list;
        }

        public static List<JSONObject> getDishList(String shop_id) {
            List<JSONObject> dishList = new ArrayList<>();
            dishList = Utils.connectAndGetJSONList("http://dirtytao.com/androidAPI/Store/storedish",
                    "POST",String.format("sid=%s",shop_id));
            return dishList;
        }
    }

    public static class Comments {

        public static List<JSONObject> getCommentList(String shop_id) {
            List<JSONObject> list = new ArrayList<>();
            list = Utils.connectAndGetJSONList("http://dirtytao.com/androidAPI/Store/comments",
                    "POST",String.format("sid=%s",shop_id));
            return list;
        }
    }
}
