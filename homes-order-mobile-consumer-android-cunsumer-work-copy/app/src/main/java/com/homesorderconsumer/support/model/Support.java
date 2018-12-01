package com.homesorderconsumer.support.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 10/04/18.
 */

public class Support {

    @SerializedName("email")
    @Expose
    private String email="";

    @SerializedName("name")
    @Expose
    private String name="";

    @SerializedName("phone")
    @Expose
    private String phone="";

    @SerializedName("message")
    @Expose
    private String message="";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
