package com.homesorderconsumer.user.myorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesorderconsumer.product.model.ReviewItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 07/04/18.
 */

public class OrderProductItem implements Serializable{

    @SerializedName("item_id")
    @Expose
    private String item_id;

    @SerializedName("parent_item_id")
    @Expose
    private String parent_item_id;

    @SerializedName("product_id")
    @Expose
    private String product_id;

    @SerializedName("productID")
    @Expose
    private String productID;

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

    /*@SerializedName("product_options")
    @Expose
    private Object product_options;*/

    @SerializedName("qty_ordered")
    @Expose
    private String qty_ordered;

    @SerializedName("created_at")
    @Expose
    private String updated_at;

    @SerializedName("deliveryDate")
    @Expose
    private String deliveryDate;

    @SerializedName("delivery_slot")
    @Expose
    private String delivery_slot;

    @SerializedName("displaytext")
    @Expose
    private String displaytext;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("statusKey")
    @Expose
    private String statusKey;

    @SerializedName("ratingValueAverage")
    @Expose
    private String ratingValueAverage;

    @SerializedName("ratingPercentageAverage")
    @Expose
    private String ratingPercentageAverage;

    @SerializedName("review")
    @Expose
    private List<ReviewItem> review=new ArrayList<>();

    @SerializedName("option")
    @Expose
    private OrderProductOption option;

    @SerializedName("priceInSAR")
    @Expose
    private String priceInSAR;

    @SerializedName("special_priceInSAR")
    @Expose
    private String special_priceInSAR;

    @SerializedName("finalPriceInSAR")
    @Expose
    private String finalPriceInSAR;

    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("delivery_price")
    @Expose
    private String delivery_price;

    @SerializedName("delivery_priceInSAR")
    @Expose
    private String delivery_priceInSAR;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getParent_item_id() {
        return parent_item_id;
    }

    public void setParent_item_id(String parent_item_id) {
        this.parent_item_id = parent_item_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
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

   /* public Object getProduct_options() {
        return product_options;
    }

    public void setProduct_options(Object product_options) {
        this.product_options = product_options;
    }*/

    public String getQty_ordered() {
        return qty_ordered;
    }

    public void setQty_ordered(String qty_ordered) {
        this.qty_ordered = qty_ordered;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public OrderProductOption getOption() {
        return option;
    }

    public void setOption(OrderProductOption option) {
        this.option = option;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }

    public String getRatingValueAverage() {
        return ratingValueAverage;
    }

    public void setRatingValueAverage(String ratingValueAverage) {
        this.ratingValueAverage = ratingValueAverage;
    }

    public String getRatingPercentageAverage() {
        return ratingPercentageAverage;
    }

    public void setRatingPercentageAverage(String ratingPercentageAverage) {
        this.ratingPercentageAverage = ratingPercentageAverage;
    }

    public List<ReviewItem> getReview() {
        return review;
    }

    public void setReview(List<ReviewItem> review) {
        this.review = review;
    }

    public String getDisplaytext() {
        return displaytext;
    }

    public void setDisplaytext(String displaytext) {
        this.displaytext = displaytext;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDelivery_slot() {
        return delivery_slot;
    }

    public void setDelivery_slot(String delivery_slot) {
        this.delivery_slot = delivery_slot;
    }

    public String getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(String delivery_price) {
        this.delivery_price = delivery_price;
    }

    public String getDelivery_priceInSAR() {
        return delivery_priceInSAR;
    }

    public void setDelivery_priceInSAR(String delivery_priceInSAR) {
        this.delivery_priceInSAR = delivery_priceInSAR;
    }
}
