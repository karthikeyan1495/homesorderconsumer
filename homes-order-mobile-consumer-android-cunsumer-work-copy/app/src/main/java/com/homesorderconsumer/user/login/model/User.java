package com.homesorderconsumer.user.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 24/03/18.
 */

public class User {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("token")
    @Expose
    private String token="";

    @SerializedName("message")
    @Expose
    private String message="";

    @SerializedName("profile")
    @Expose
    private Profile profile;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
