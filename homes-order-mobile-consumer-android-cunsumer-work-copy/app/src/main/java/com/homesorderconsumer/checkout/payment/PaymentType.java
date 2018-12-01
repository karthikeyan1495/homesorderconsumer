package com.homesorderconsumer.checkout.payment;

/**
 * Created by innoppl on 14/06/18.
 */

public enum PaymentType {

    CASH_ON_DELIVERY("cashondelivery"),
    TELR_PAYMENT("telr_telrpayments");

    private String value;

    public String getValue() {
        return value;
    }

    private PaymentType(String value) {
        this.value = value;
    }
}

