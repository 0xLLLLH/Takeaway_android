package com.xllllh.android.takeaway;

import org.json.JSONObject;

/**
 * Created by 0xLLLLH on 16-6-13.
 *
 */
public class Order {
    private String orderId,shopId,stateCode,dishString,price,discountString,orderTime,addressId;
    private JSONObject jsonObject;

    static public final String STATE_UNPAY             = "1";
    static public final String STATE_CANCEL      = "10";
    static public final String STATE_PAYED             = "11";
    static public final String STATE_PAYED_UNACCEPT    = "110";
    static public final String STATE_PAYED_ACCEPT      = "111";
    static public final String STATE_FINISH            = "1111";


    Order(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        setAttrs(jsonObject);
    }

    private void setAttrs(JSONObject jsonObject) {
        this.orderId = Utils.getValueFromJSONObject(jsonObject,"id","0");
        this.shopId = Utils.getValueFromJSONObject(jsonObject,"store_id","0");
        this.stateCode = Utils.getValueFromJSONObject(jsonObject,"state",STATE_FINISH);
        this.dishString = Utils.getValueFromJSONObject(jsonObject,"dish_id_string","");
        this.price = Utils.getValueFromJSONObject(jsonObject,"total_price","0");
        this.discountString = Utils.getValueFromJSONObject(jsonObject,"discount_result","不满足优惠要求");
        this.orderTime = Utils.getValueFromJSONObject(jsonObject,"setorder_time","2015-07-14 19:53:35");
        this.addressId = Utils.getValueFromJSONObject(jsonObject,"address_id","1");
    }

    @Override
    public String toString() {
        return super.toString()+jsonObject.toString();
    }

    public String getOrderId() {
        return orderId;
    }

    public String getShopId() {
        return shopId;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getDishString() {
        return dishString;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscountString() {
        return discountString;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getAddressId() {
        return addressId;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        setAttrs(jsonObject);
    }
}
