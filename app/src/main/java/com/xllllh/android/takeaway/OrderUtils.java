package com.xllllh.android.takeaway;

import org.json.JSONObject;

/**
 * Created by 0xLLLLH on 16-6-9.
 *
 */
public class OrderUtils {

    static JSONObject createNewOrder(String username, String address_id, String store_id,
                                     String dish_id_string, String remark, String payment_type,
                                     String discount_result, String total_price) {
            return Utils.connectAndGetJSONObject("http://dirtytao.com/androidAPI/Order/add",
                    "POST",
                    String.format("username=%s&address_id=%s&store_id=%s&dish_id_string=%s" +
                            "&remark=%s&payment_type=%s&discount_result=%s&total_price=%s"
                            ,username,address_id,store_id,dish_id_string,remark,payment_type,
                            discount_result,total_price));
    }
}
