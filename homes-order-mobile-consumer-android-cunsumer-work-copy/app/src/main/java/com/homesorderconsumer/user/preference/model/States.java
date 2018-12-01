package com.homesorderconsumer.user.preference.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by innoppl on 27/03/18.
 */

public class States {

    @SerializedName("stateNameEN")
    @Expose
    private String stateNameEN="";

    @SerializedName("stateNameAR")
    @Expose
    private String stateNameAR="";

    @SerializedName("value")
    @Expose
    private String value="";

    @SerializedName("areas")
    @Expose
    private List<Areas> areas;

    public String getStateNameEN() {
        return stateNameEN;
    }

    public void setStateNameEN(String stateNameEN) {
        this.stateNameEN = stateNameEN;
    }

    public String getStateNameAR() {
        return stateNameAR;
    }

    public void setStateNameAR(String stateNameAR) {
        this.stateNameAR = stateNameAR;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Areas>  getAreas() {
        return areas;
    }

    public void setAreas(List<Areas>  areas) {
        this.areas = areas;
    }
}
