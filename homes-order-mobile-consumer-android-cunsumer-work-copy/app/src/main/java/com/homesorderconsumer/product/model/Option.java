package com.homesorderconsumer.product.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 30/03/18.
 */

public class Option implements Serializable {

    @SerializedName("color")
    @Expose
    private List<Color> color=new ArrayList<>();

    @SerializedName("size")
    @Expose
    private List<Size> size=new ArrayList<>();

    @SerializedName("weight")
    @Expose
    private List<Weight> weight=new ArrayList<>();


    public List<Color> getColor() {
        return color;
    }

    public void setColor(List<Color> color) {
        this.color = color;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public List<Weight> getWeight() {
        return weight;
    }

    public void setWeight(List<Weight> weight) {
        this.weight = weight;
    }
}
