package com.homesorderconsumer.user.myorder.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 07/04/18.
 */

public class OrdersItem implements Serializable{

    @SerializedName("orderId")
    @Expose
    private String orderId;

    @SerializedName("increment_id")
    @Expose
    private String increment_id;

    @SerializedName("sub_order_total")
    @Expose
    private String sub_order_total;

    @SerializedName("order_total")
    @Expose
    private String order_total;

    @SerializedName("base_shipping_amount")
    @Expose
    private String base_shipping_amount;

    @SerializedName("paymentmethod")
    @Expose
    private String paymentmethod;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("displaytext")
    @Expose
    private String displaytext;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("statusKey")
    @Expose
    private String statusKey;

    @SerializedName("customer_id")
    @Expose
    private String customer_id;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("remainingTime")
    @Expose
    private int remainingTime;

    @SerializedName("customer")
    @Expose
    private List<Customer> customer=new ArrayList<>();

    @SerializedName("items")
    @Expose
    private List<OrderProductItem> items=new ArrayList<>();

    @SerializedName("sub_order_totalInSAR")
    @Expose
    private String sub_order_totalInSAR;

    @SerializedName("order_totalInSAR")
    @Expose
    private String order_totalInSAR;

    @SerializedName("base_shipping_amountInSAR")
    @Expose
    private String base_shipping_amountInSAR;

    @SerializedName("remainingTimeInsec")
    @Expose
    private int remainingTimeInsec;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getIncrement_id() {
        return increment_id;
    }

    public void setIncrement_id(String increment_id) {
        this.increment_id = increment_id;
    }

    public String getSub_order_total() {
        return sub_order_total;
    }

    public void setSub_order_total(String sub_order_total) {
        this.sub_order_total = sub_order_total;
    }

    public String getOrder_total() {
        return order_total;
    }

    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }

    public String getBase_shipping_amount() {
        return base_shipping_amount;
    }

    public void setBase_shipping_amount(String base_shipping_amount) {
        this.base_shipping_amount = base_shipping_amount;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }

    public List<OrderProductItem> getItems() {
        return items;
    }

    public void setItems(List<OrderProductItem> items) {
        this.items = items;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }

    public String getDisplaytext() {
        return displaytext;
    }

    public void setDisplaytext(String displaytext) {
        this.displaytext = displaytext;
    }

    public String getSub_order_totalInSAR() {
        return sub_order_totalInSAR;
    }

    public void setSub_order_totalInSAR(String sub_order_totalInSAR) {
        this.sub_order_totalInSAR = sub_order_totalInSAR;
    }

    public String getOrder_totalInSAR() {
        return order_totalInSAR;
    }

    public void setOrder_totalInSAR(String order_totalInSAR) {
        this.order_totalInSAR = order_totalInSAR;
    }

    public String getBase_shipping_amountInSAR() {
        return base_shipping_amountInSAR;
    }

    public void setBase_shipping_amountInSAR(String base_shipping_amountInSAR) {
        this.base_shipping_amountInSAR = base_shipping_amountInSAR;
    }

    public int getRemainingTimeInsec() {
        return remainingTimeInsec;
    }

    public void setRemainingTimeInsec(int remainingTimeInsec) {
        this.remainingTimeInsec = remainingTimeInsec;
    }

}
