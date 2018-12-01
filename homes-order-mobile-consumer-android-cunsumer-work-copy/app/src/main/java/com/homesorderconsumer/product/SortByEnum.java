package com.homesorderconsumer.product;

/**
 * Created by innoppl on 27/03/18.
 */

public enum SortByEnum {
    PRICE_LOW_HIGH("price_low_high"),
    PRICE_HIGH_LOW("price_high_low"),
    MOST_RECENT("most_recent"),
    CATEGORY_BASED("category_based"),
    ASC("asc"),
    DESC("desc");
    private String value;
    public String getValue() {
        return value;
    }
    private SortByEnum(String value) {
        this.value = value;
    }
}
