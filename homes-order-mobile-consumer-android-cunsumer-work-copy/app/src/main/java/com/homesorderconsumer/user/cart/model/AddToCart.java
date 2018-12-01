package com.homesorderconsumer.user.cart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 03/04/18.
 */

public class AddToCart {

    @SerializedName("productId")
    @Expose
    private int productID;

    @SerializedName("qty")
    @Expose
    private String qty;

    @SerializedName("option")
    @Expose
    private AddToCartOption option;

    @SerializedName("deliverySlots")
    @Expose
    private AddToCartDeliverySlots deliverySlots;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public AddToCartOption getOption() {
        return option;
    }

    public void setOption(AddToCartOption option) {
        this.option = option;
    }

    public AddToCartDeliverySlots getDeliverySlots() {
        return deliverySlots;
    }

    public void setDeliverySlots(AddToCartDeliverySlots deliverySlots) {
        this.deliverySlots = deliverySlots;
    }
}
