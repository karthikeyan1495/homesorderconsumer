package com.homesorderconsumer.user.myorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 09/04/18.
 */

public class ReviewPost {

    @SerializedName("productId")
    @Expose
    private String productId;

    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("title")
    @Expose
    private String title="";

    @SerializedName("detail")
    @Expose
    private String detail="";

    @SerializedName("ratingValue")
    @Expose
    private float ratingValue=0;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }
}
