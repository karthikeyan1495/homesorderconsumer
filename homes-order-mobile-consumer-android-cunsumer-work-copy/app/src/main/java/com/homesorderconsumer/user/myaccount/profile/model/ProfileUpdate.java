package com.homesorderconsumer.user.myaccount.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 18/04/18.
 */

public class ProfileUpdate {

    @SerializedName("email")
    @Expose
    private String email="";

    @SerializedName("firstname")
    @Expose
    private String firstname="";

    @SerializedName("lastname")
    @Expose
    private String lastname="";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
