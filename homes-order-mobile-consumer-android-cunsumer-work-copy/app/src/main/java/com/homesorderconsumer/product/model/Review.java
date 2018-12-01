package com.homesorderconsumer.product.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 06/04/18.
 */

public class Review {

    @SerializedName("reviews")
    @Expose
    private List<ReviewItem> reviews=new ArrayList<>();

    @SerializedName("ratingValueaverage")
    @Expose
    private String ratingValueaverage="";

    @SerializedName("ratingPercentageaverage")
    @Expose
    private String ratingPercentageaverage="";

    public List<ReviewItem> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewItem> reviews) {
        this.reviews = reviews;
    }

    public String getRatingValueaverage() {
        return ratingValueaverage;
    }

    public void setRatingValueaverage(String ratingValueaverage) {
        this.ratingValueaverage = ratingValueaverage;
    }

    public String getRatingPercentageaverage() {
        return ratingPercentageaverage;
    }

    public void setRatingPercentageaverage(String ratingPercentageaverage) {
        this.ratingPercentageaverage = ratingPercentageaverage;
    }
}
