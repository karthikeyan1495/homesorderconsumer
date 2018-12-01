package com.homesorderconsumer.user.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 24/03/18.
 */

public class Profile {

    @SerializedName("userdetails")
    @Expose
    private UserDetails userdetails;

    @SerializedName("email")
    @Expose
    private String email="";

    @SerializedName("addresses")
    @Expose
    private Object addresses;

    public UserDetails getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(UserDetails userdetails) {
        this.userdetails = userdetails;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getAddresses() {
        return addresses;
    }

    public void setAddresses(Object addresses) {
        this.addresses = addresses;
    }
}
