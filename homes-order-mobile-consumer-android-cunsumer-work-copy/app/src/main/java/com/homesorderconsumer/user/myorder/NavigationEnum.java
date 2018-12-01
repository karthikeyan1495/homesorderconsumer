package com.homesorderconsumer.user.myorder;

/**
 * Created by innoppl on 22/05/18.
 */

public enum NavigationEnum {
    ORDER_LIST("order_list"),
    TRACK_ORDER("track_order");
    private String value;
    public String getValue() {
        return value;
    }
    NavigationEnum(String value) {
        this.value = value;
    }
}
