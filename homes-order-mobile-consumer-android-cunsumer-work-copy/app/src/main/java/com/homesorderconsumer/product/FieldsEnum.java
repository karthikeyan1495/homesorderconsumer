package com.homesorderconsumer.product;

/**
 * Created by innoppl on 27/03/18.
 */

public enum FieldsEnum {
    NAME("name"),
    PRICE("finalPrice"),
    MOST_RECENT("created_at"),
    AREA("available_in_cities"),
    BRANDS("brands");
    private String value;
    public String getValue() {
        return value;
    }
    private FieldsEnum(String value) {
        this.value = value;
    }

    /**
     * Created by innoppl on 28/03/18.
     */

}
