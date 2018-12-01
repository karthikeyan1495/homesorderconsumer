package com.homesorderconsumer.user.cart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 03/04/18.
 */

public class AddToCartDeliverySlots {

    @SerializedName("slots")
    @Expose
    private String slots;

    @SerializedName("date")
    @Expose
    private String date;

    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
