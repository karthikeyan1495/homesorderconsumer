package com.homesorderconsumer.category.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by innoppl on 27/03/18.
 */

public class Adds implements Serializable{

    @SerializedName("id")
    @Expose
    private String id="";

    @SerializedName("name")
    @Expose
    private String name="";

    @SerializedName("percentage")
    @Expose
    private String percentage="";

    @SerializedName("valid")
    @Expose
    private String valid="";

    @SerializedName("category")
    @Expose
    private String category="";

    @SerializedName("image")
    @Expose
    private String image="";

    @SerializedName("url")
    @Expose
    private String url="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
