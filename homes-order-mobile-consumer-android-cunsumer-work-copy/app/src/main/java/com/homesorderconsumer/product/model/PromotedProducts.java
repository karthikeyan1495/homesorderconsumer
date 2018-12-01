package com.homesorderconsumer.product.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 25/05/18.
 */

public class PromotedProducts implements Serializable{

    @SerializedName("food")
    @Expose
    private List<Products> food=new ArrayList<>();

    @SerializedName("fashion")
    @Expose
    private List<Products> fashion=new ArrayList<>();

    public List<Products> getFood() {
        return food;
    }

    public void setFood(List<Products> food) {
        this.food = food;
    }

    public List<Products> getFashion() {
        return fashion;
    }

    public void setFashion(List<Products> fashion) {
        this.fashion = fashion;
    }
}
