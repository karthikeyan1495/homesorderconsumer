package com.homesorderconsumer.product.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by innoppl on 06/04/18.
 */

public class ReviewItem implements Serializable {

    @SerializedName("name")
    @Expose
    private String name="";

    @SerializedName("title")
    @Expose
    private String title="";

    @SerializedName("detail")
    @Expose
    private String detail="";

    @SerializedName("ratingPercentage")
    @Expose
    private String ratingPercentage="";

    @SerializedName("ratingValue")
    @Expose
    private String ratingValue="";

    @SerializedName("created_at")
    @Expose
    private String created_at="";




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRatingPercentage() {
        return ratingPercentage;
    }

    public void setRatingPercentage(String ratingPercentage) {
        this.ratingPercentage = ratingPercentage;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
