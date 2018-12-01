package com.homesorderconsumer.user.cart.model;

import java.io.Serializable;

/**
 * Created by innoppl on 05/04/18.
 */

public class CartPrice implements Serializable{

    String productTotal="";
    String shippingTotal="";
    String overAllTotal="";

    public String getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(String productTotal) {
        this.productTotal = productTotal;
    }

    public String getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(String shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public String getOverAllTotal() {
        return overAllTotal;
    }

    public void setOverAllTotal(String overAllTotal) {
        this.overAllTotal = overAllTotal;
    }
}
