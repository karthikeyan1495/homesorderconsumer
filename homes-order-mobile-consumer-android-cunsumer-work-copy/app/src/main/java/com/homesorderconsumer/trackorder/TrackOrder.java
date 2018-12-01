package com.homesorderconsumer.trackorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by innoppl on 08/05/18.
 */

public class TrackOrder implements Serializable{

    @SerializedName("orderIncrementId")
    @Expose
    private String orderIncrementId="";

    @SerializedName("lastName")
    @Expose
    private String lastName="";

    @SerializedName("email")
    @Expose
    private String email="";

    public String getOrderIncrementId() {
        return orderIncrementId;
    }

    public void setOrderIncrementId(String orderIncrementId) {
        this.orderIncrementId = orderIncrementId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
