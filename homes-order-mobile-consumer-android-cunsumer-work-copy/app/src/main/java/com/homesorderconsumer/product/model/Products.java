package com.homesorderconsumer.product.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesorderconsumer.BR;

import java.io.Serializable;
import java.util.List;

/**
 * Created by innoppl on 28/03/18.
 */

public class Products extends BaseObservable implements Serializable {

    @SerializedName("productID")
    @Expose
    private int productID;

    @SerializedName("type")
    @Expose
    private String type="";

    @SerializedName("productNameEN")
    @Expose
    private String productNameEN="";

    @SerializedName("productNameAR")
    @Expose
    private String productNameAR="";

    @SerializedName("price")
    @Expose
    private String price="";

    @SerializedName("short_description")
    @Expose
    private String short_description="";

    @SerializedName("descriptionEN")
    @Expose
    private String descriptionEN="";

    @SerializedName("descriptionAR")
    @Expose
    private String descriptionAR="";

    @SerializedName("special_price")
    @Expose
    private String special_price="";

    @SerializedName("special_from_date")
    @Expose
    private String special_from_date="";

    @SerializedName("special_to_date")
    @Expose
    private String special_to_date="";

    @SerializedName("finalPrice")
    @Expose
    private String finalPrice="";

    @SerializedName("media_gallery")
    @Expose
    private List<String> media_gallery;

    @SerializedName("available_in_cities")
    @Expose
    private String available_in_cities="";

    @SerializedName("category_ids")
    @Expose
    private String category_ids="";

    @SerializedName("category_names")
    @Expose
    private String category_names="";

    @SerializedName("created_at")
    @Expose
    private String created_at="";

    @SerializedName("updated_at")
    @Expose
    private String updated_at="";

    @SerializedName("isPromotion")
    @Expose
    private String isPromotion="";

    @SerializedName("isFeatured")
    @Expose
    private String isFeatured="";

    @SerializedName("isInWishlist")
    @Expose
    private boolean isInWishlist=false;

    @SerializedName("ratingValueAverage")
    @Expose
    private String ratingValueAverage="";

    @SerializedName("ratingPercentageAverage")
    @Expose
    private String ratingPercentageAverage="";

    @SerializedName("featureStartDate")
    @Expose
    private String featureStartDate="";

    @SerializedName("featureEndDate")
    @Expose
    private String featureEndDate="";

    @SerializedName("wishlistItemId")
    @Expose
    private String wishlistItemId="";

    @SerializedName("brand")
    @Expose
    private String brand="";

    @SerializedName("priceInSAR")
    @Expose
    private String priceInSAR="";

    @SerializedName("special_priceInSAR")
    @Expose
    private String special_priceInSAR="";

    @SerializedName("finalPriceInSAR")
    @Expose
    private String finalPriceInSAR="";

    private boolean isFeaturedProduct=false;


    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
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

    public String getAvailable_in_cities() {
        return available_in_cities;
    }

    public void setAvailable_in_cities(String available_in_cities) {
        this.available_in_cities = available_in_cities;
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

    public String getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(String isPromotion) {
        this.isPromotion = isPromotion;
    }

    public String getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(String isFeatured) {
        this.isFeatured = isFeatured;
    }

    @Bindable
    public boolean isInWishlist() {
        return isInWishlist;
    }

    public void setInWishlist(boolean inWishlist) {
        isInWishlist = inWishlist;
        notifyPropertyChanged(BR.inWishlist);
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

    public String getFeatureStartDate() {
        return featureStartDate;
    }

    public void setFeatureStartDate(String featureStartDate) {
        this.featureStartDate = featureStartDate;
    }

    public String getFeatureEndDate() {
        return featureEndDate;
    }

    public void setFeatureEndDate(String featureEndDate) {
        this.featureEndDate = featureEndDate;
    }

    public boolean isFeaturedProduct() {
        return isFeaturedProduct;
    }

    public void setFeaturedProduct(boolean featuredProduct) {
        isFeaturedProduct = featuredProduct;
    }

    public String getWishlistItemId() {
        return wishlistItemId;
    }

    public void setWishlistItemId(String wishlistItemId) {
        this.wishlistItemId = wishlistItemId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
}
