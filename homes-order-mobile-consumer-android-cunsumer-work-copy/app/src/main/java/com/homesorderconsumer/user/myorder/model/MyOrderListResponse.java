package com.homesorderconsumer.user.myorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 07/04/18.
 */

public class MyOrderListResponse {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message="";

    @SerializedName("orders")
    @Expose
    private List<OrdersItem> orders=new ArrayList<>();

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

    public List<OrdersItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersItem> orders) {
        this.orders = orders;
    }
}
