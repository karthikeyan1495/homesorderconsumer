package com.homesorderconsumer.user.cart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesorderconsumer.product.model.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 03/04/18.
 */

public class CartProduct {

    @SerializedName("productID")
    @Expose
    private int productID;

    @SerializedName("sku")
    @Expose
    private String sku;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("productNameEN")
    @Expose
    private String productNameEN;

    @SerializedName("productNameAR")
    @Expose
    private String productNameAR;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("special_price")
    @Expose
    private String special_price;

    @SerializedName("special_from_date")
    @Expose
    private String special_from_date;

    @SerializedName("special_to_date")
    @Expose
    private String special_to_date;

    @SerializedName("short_description")
    @Expose
    private String short_description;

    @SerializedName("descriptionEN")
    @Expose
    private String descriptionEN;

    @SerializedName("descriptionAR")
    @Expose
    private String descriptionAR;

    @SerializedName("productLimitPerDay")
    @Expose
    private String productLimitPerDay;

    @SerializedName("preparingTime")
    @Expose
    private String preparingTime;

    @SerializedName("finalPrice")
    @Expose
    private String finalPrice;

    @SerializedName("media_gallery")
    @Expose
    private List<String> media_gallery=new ArrayList<>();

    @SerializedName("category_ids")
    @Expose
    private String category_ids;

    @SerializedName("category_names")
    @Expose
    private String category_names;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("deliveryCost")
    @Expose
    private String deliveryCost;

    @SerializedName("option")
    @Expose
    private Option option;

    @SerializedName("qty")
    @Expose
    private String qty;

    @SerializedName("priceInSAR")
    @Expose
    private String priceInSAR;

    @SerializedName("special_priceInSAR")
    @Expose
    private String special_priceInSAR;

    @SerializedName("finalPriceInSAR")
    @Expose
    private String finalPriceInSAR;

    @SerializedName("delivery_date")
    @Expose
    private String delivery_date;

    @SerializedName("delivery_slot")
    @Expose
    private String delivery_slot;

    @SerializedName("deliveryCostInSAR")
    @Expose
    private String deliveryCostInSAR;

    @SerializedName("isAvailable")
    @Expose
    private boolean isAvailable=true;


    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductNameEN() {
        return productNameEN;
    }

    public void setProductNameEN(String productNameEN) {
        this.productNameEN = productNameEN;
    }

    public String getProductNameAR() {
        return productNameAR;
    }

    public void setProductNameAR(String productNameAR) {
        this.productNameAR = productNameAR;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecial_price() {
        return special_price;
    }

    public void setSpecial_price(String special_price) {
        this.special_price = special_price;
    }

    public String getSpecial_from_date() {
        return special_from_date;
    }

    public void setSpecial_from_date(String special_from_date) {
        this.special_from_date = special_from_date;
    }

    public String getSpecial_to_date() {
        return special_to_date;
    }

    public void setSpecial_to_date(String special_to_date) {
        this.special_to_date = special_to_date;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getDescriptionAR() {
        return descriptionAR;
    }

    public void setDescriptionAR(String descriptionAR) {
        this.descriptionAR = descriptionAR;
    }

    public String getProductLimitPerDay() {
        return productLimitPerDay;
    }

    public void setProductLimitPerDay(String productLimitPerDay) {
        this.productLimitPerDay = productLimitPerDay;
    }

    public String getPreparingTime() {
        return preparingTime;
    }

    public void setPreparingTime(String preparingTime) {
        this.preparingTime = preparingTime;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public List<String> getMedia_gallery() {
        return media_gallery;
    }

    public void setMedia_gallery(List<String> media_gallery) {
        this.media_gallery = media_gallery;
    }

    public String getCategory_ids() {
        return category_ids;
    }

    public void setCategory_ids(String category_ids) {
        this.category_ids = category_ids;
    }

    public String getCategory_names() {
        return category_names;
    }

    public void setCategory_names(String category_names) {
        this.category_names = category_names;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPriceInSAR() {
        return priceInSAR;
    }

    public void setPriceInSAR(String priceInSAR) {
        this.priceInSAR = priceInSAR;
    }

    public String getSpecial_priceInSAR() {
        return special_priceInSAR;
    }

    public void setSpecial_priceInSAR(String special_priceInSAR) {
        this.special_priceInSAR = special_priceInSAR;
    }

    public String getFinalPriceInSAR() {
        return finalPriceInSAR;
    }

    public void setFinalPriceInSAR(String finalPriceInSAR) {
        this.finalPriceInSAR = finalPriceInSAR;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDelivery_slot() {
        return delivery_slot;
    }

    public void setDelivery_slot(String delivery_slot) {
        this.delivery_slot = delivery_slot;
    }

    public String getDeliveryCostInSAR() {
        return deliveryCostInSAR;
    }

    public void setDeliveryCostInSAR(String deliveryCostInSAR) {
        this.deliveryCostInSAR = deliveryCostInSAR;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
