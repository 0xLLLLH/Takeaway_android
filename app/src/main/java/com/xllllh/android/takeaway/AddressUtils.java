package com.xllllh.android.takeaway;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 0xLLLLH on 16-6-9.
 */
public class AddressUtils {

    static ArrayList <JSONObject> addressList;

    static void Init() {
        addressList = new ArrayList<>();
        addressList.addAll(getAddress(UserUtils.getUsername()));
    }

    static List<JSONObject> getAddress(String username){
        return Utils.connectAndGetJSONList("http://dirtytao.com/androidAPI/Address",
                "POST",String.format("username=%s",username));
    }
}
