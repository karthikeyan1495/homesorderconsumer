package com.homesorderconsumer.user.cart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 03/04/18.
 */

public class CartAddResponse {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("cart_id")
    @Expose
    private String cart_id;

    @SerializedName("message")
    @Expose
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
