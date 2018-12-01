package com.homesorderconsumer.product.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 30/03/18.
 */

public class DeliverySlots implements Serializable {

    @SerializedName("orderCount")
    @Expose
    private String orderCount;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("slots")
    @Expose
    private List<String> slots=new ArrayList<>();




    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getSlots() {
        return slots;
    }

    public void setSlots(List<String> slots) {
        this.slots = slots;
    }

}
