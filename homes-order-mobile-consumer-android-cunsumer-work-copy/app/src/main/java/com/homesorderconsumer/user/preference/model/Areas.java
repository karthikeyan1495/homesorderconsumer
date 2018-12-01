package com.homesorderconsumer.user.preference.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 27/03/18.
 */

public class Areas {

    @SerializedName("areaNameEN")
    @Expose
    private String areaNameEN="";

    @SerializedName("areaNameAR")
    @Expose
    private String areaNameAR="";

    @SerializedName("value")
    @Expose
    private String value="";

    public String getAreaNameEN() {
        return areaNameEN;
    }

    public void setAreaNameEN(String areaNameEN) {
        this.areaNameEN = areaNameEN;
    }

    public String getAreaNameAR() {
        return areaNameAR;
    }

    public void setAreaNameAR(String areaNameAR) {
        this.areaNameAR = areaNameAR;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
