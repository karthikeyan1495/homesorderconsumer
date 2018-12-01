package com.homesorderconsumer.product.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by innoppl on 07/05/18.
 */

public class Weight implements Serializable {
    @SerializedName("name")
    @Expose
    private String name="";

    @SerializedName("value")
    @Expose
    private String value="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
