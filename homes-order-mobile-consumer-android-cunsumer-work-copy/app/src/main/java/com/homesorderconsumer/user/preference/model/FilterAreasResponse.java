package com.homesorderconsumer.user.preference.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by innoppl on 27/03/18.
 */

public class FilterAreasResponse {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message="";

    @SerializedName("filterArea")
    @Expose
    private List<Country> filterArea;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Country> getFilterArea() {
        return filterArea;
    }

    public void setFilterArea(List<Country> filterArea) {
        this.filterArea = filterArea;
    }
}
