package com.xllllh.android.takeaway;

import org.json.JSONObject;

import java.util.ArrayList;
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

        public static void init() {
            page = 0;
            initialContents = getShops();
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
}
