package com.homesorderconsumer.fcm;

/**
 * Created by innoppl on 22/05/18.
 */

public enum NotificationTypeEnum {
    ORDER("ORDER");
    private String value;
    public String getValue() {
        return value;
    }
    NotificationTypeEnum(String value) {
        this.value = value;
    }
}
