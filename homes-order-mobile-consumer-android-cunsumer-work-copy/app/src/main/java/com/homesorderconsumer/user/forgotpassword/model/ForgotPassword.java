package com.homesorderconsumer.user.forgotpassword.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2/21/18.
 */

public class ForgotPassword {

    @SerializedName("email")
    @Expose
    private String email="";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
