package com.homesorderconsumer.user.wishlist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesorderconsumer.product.model.Products;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 30/03/18.
 */

public class WishListResponse {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("wishlist")
    @Expose
    private List<Products> wishlist=new ArrayList<>();

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

    public List<Products> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<Products> wishlist) {
        this.wishlist = wishlist;
    }
}
