package com.homesorderconsumer.product.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 27/03/18.
 */

public class Sortby {

    @SerializedName("field")
    @Expose
    private String field;

    @SerializedName("order")
    @Expose
    private String order;

    public Sortby(String field,String order){
        this.field=field;
        this.order=order;
    }

    public Sortby(){}

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
