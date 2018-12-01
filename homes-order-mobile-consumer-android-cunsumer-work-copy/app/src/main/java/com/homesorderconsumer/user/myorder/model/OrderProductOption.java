package com.homesorderconsumer.user.myorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by innoppl on 07/04/18.
 */

public class OrderProductOption implements Serializable{

    @SerializedName("size")
    @Expose
    private String size;

    @SerializedName("color")
    @Expose
    private String color;

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
}
