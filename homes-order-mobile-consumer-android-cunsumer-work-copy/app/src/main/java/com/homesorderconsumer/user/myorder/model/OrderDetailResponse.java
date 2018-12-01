package com.homesorderconsumer.user.myorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 11/05/18.
 */

public class OrderDetailResponse {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message="";

    @SerializedName("orders")
    @Expose
    private OrdersItem ordersItem;

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

    public OrdersItem getOrdersItem() {
        return ordersItem;
    }

    public void setOrdersItem(OrdersItem ordersItem) {
        this.ordersItem = ordersItem;
    }
}
