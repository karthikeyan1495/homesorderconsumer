package com.homesorderconsumer.user.registration.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 2/21/18.
 */

public class Register {

    @SerializedName("firstname")
    @Expose
    private String firstname="";

    @SerializedName("lastname")
    @Expose
    private String lastname="";

    @SerializedName("email")
    @Expose
    private String email="";

    @SerializedName("password")
    @Expose
    private String password="";

    @SerializedName("confirm_password")
    @Expose
    private String confirm_password="";

    @SerializedName("device_token")
    @Expose
    private String device_token="";

    @SerializedName("device_type")
    @Expose
    private String device_type="";



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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }
}
