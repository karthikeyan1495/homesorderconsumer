package com.homesorderconsumer.user.cart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 03/04/18.
 */

public class AddToCartOption {

    @SerializedName("size")
    @Expose
    private String size;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("weight")
    @Expose
    private String weight;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
