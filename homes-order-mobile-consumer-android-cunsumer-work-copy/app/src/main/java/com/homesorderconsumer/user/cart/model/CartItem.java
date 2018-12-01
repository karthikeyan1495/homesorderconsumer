package com.homesorderconsumer.user.cart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 03/04/18.
 */

public class CartItem {

    @SerializedName("item_id")
    @Expose
    private int item_id;

    @SerializedName("product")
    @Expose
    private CartProduct product;

    //Remove or update the Item in recyclerView adapter - this field only used for UI updates
    boolean isRemove=false;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public CartProduct getProduct() {
        return product;
    }

    public void setProduct(CartProduct product) {
        this.product = product;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }
}
