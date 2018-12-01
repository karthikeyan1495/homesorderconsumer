package com.homesorderconsumer.product.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by innoppl on 28/03/18.
 */

public class ProductResponse {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message="";

    @SerializedName("products")
    @Expose
    private List<Products> products;

    @SerializedName("featuredProducts")
    @Expose
    private List<Products> featuredProducts;

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

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public List<Products> getFeaturedProducts() {
        return featuredProducts;
    }

    public void setFeaturedProducts(List<Products> featuredProducts) {
        this.featuredProducts = featuredProducts;
    }

}
