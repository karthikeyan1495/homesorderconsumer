package com.homesorderconsumer.user.preference.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by innoppl on 27/03/18.
 */

public class Country {

    @SerializedName("countryNameEN")
    @Expose
    private String countryNameEN="";

    @SerializedName("countryNameAR")
    @Expose
    private String countryNameAR="";

    @SerializedName("value")
    @Expose
    private String value="";

    @SerializedName("states")
    @Expose
    private List<States> states;

    public String getCountryNameEN() {
        return countryNameEN;
    }

    public void setCountryNameEN(String countryNameEN) {
        this.countryNameEN = countryNameEN;
    }

    public String getCountryNameAR() {
        return countryNameAR;
    }

    public void setCountryNameAR(String countryNameAR) {
        this.countryNameAR = countryNameAR;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<States> getStates() {
        return states;
    }

    public void setStates(List<States> states) {
        this.states = states;
    }
}
