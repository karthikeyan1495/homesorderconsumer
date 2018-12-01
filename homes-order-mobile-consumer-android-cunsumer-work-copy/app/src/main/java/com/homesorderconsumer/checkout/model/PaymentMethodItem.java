package com.homesorderconsumer.checkout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 06/04/18.
 */

public class PaymentMethodItem {

    @SerializedName("code")
    @Expose
    private String code="";

    @SerializedName("title")
    @Expose
    private String title="";

    boolean isSelected=false;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
