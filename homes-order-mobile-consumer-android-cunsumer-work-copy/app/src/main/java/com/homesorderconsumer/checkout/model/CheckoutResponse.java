package com.homesorderconsumer.checkout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesorderconsumer.user.myorder.model.OrdersItem;

/**
 * Created by innoppl on 07/04/18.
 */

public class CheckoutResponse {
    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("orderId")
    @Expose
    private String orderId;


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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrdersItem getOrders() {
        return orders;
    }

    public void setOrders(OrdersItem orders) {
        this.orders = orders;
    }
}
