package com.homesorderconsumer.trackorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesorderconsumer.user.myorder.model.OrdersItem;

/**
 * Created by innoppl on 09/05/18.
 */

public class TrackOrderResponse {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message="";

    @SerializedName("orders")
    @Expose
    private OrdersItem orders;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OrdersItem getOrders() {
        return orders;
    }

    public void setOrders(OrdersItem orders) {
        this.orders = orders;
    }
}
