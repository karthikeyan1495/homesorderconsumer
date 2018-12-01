package com.homesorderconsumer.user.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 21/03/18.
 */

public class Login {

    @SerializedName("username")
    @Expose
    private String username="";

    @SerializedName("password")
    @Expose
    private String password="";

    @SerializedName("device_token")
    @Expose
    private String device_token="";

    @SerializedName("device_type")
    @Expose
    private String device_type="";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
