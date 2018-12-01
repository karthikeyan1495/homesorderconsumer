package com.homesorderconsumer.category.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesorderconsumer.product.model.PromotedProducts;

import java.io.Serializable;
import java.util.List;

/**
 * Created by innoppl on 27/03/18.
 */

public class Categories implements Serializable {

    @SerializedName("category")
    @Expose
    private List<Category> category;

    @SerializedName("adds")
    @Expose
    private List<Adds> adds;

    @SerializedName("promotedProducts")
    @Expose
    private PromotedProducts promotedProducts;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Adds> getAdds() {
        return adds;
    }

    public void setAdds(List<Adds> adds) {
        this.adds = adds;
    }

    public PromotedProducts getPromotedProducts() {
        return promotedProducts;
    }

    public void setPromotedProducts(PromotedProducts promotedProducts) {
        this.promotedProducts = promotedProducts;
    }
}
