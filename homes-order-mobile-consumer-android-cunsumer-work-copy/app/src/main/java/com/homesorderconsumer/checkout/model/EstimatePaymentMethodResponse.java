package com.homesorderconsumer.checkout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 06/04/18.
 */

public class EstimatePaymentMethodResponse {

    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message="";

    @SerializedName("paymentMethod")
    @Expose
    private List<PaymentMethodItem> paymentMethod=new ArrayList<>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PaymentMethodItem> getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(List<PaymentMethodItem> paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
