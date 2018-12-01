package com.homesorderconsumer.user.resetpassword.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac on 3/8/18.
 */

public class ResetPassword {

    @SerializedName("currentPassword")
    @Expose
    private String currentPassword="";

    @SerializedName("newPassword")
    @Expose
    private String newPassword="";

    @SerializedName("confirmpassword")
    @Expose
    private String confirmpassword="";

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }
}
