package com.homesorderconsumer.product.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 27/03/18.
 */

public class SearchCriteria {

    @SerializedName("field")
    @Expose
    private String field;

    @SerializedName("value")
    @Expose
    private String value;

    public SearchCriteria(String field,String value){
        this.field=field;
        this.value=value;
    }

    public SearchCriteria(){}

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
