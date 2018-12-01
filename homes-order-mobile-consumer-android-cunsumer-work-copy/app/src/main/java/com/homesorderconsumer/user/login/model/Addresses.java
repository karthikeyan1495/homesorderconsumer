package com.homesorderconsumer.user.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by innoppl on 24/03/18.
 */

public class Addresses {

    @SerializedName("customer_id")
    @Expose
    private String customer_id="";

    @SerializedName("firstname")
    @Expose
    private String firstname="";

    @SerializedName("lastname")
    @Expose
    private String lastname="";

    @SerializedName("telephone")
    @Expose
    private String telephone="";

    @SerializedName("Street")
    @Expose
    private String Street="";

    @SerializedName("city")
    @Expose
    private String city="";

    @SerializedName("postcode")
    @Expose
    private String postcode="";

    @SerializedName("country_id")
    @Expose
    private String country_id="";

    @SerializedName("address")
    @Expose
    private String address="";

    @SerializedName("default_shipping")
    @Expose
    private String default_shipping="";

    @SerializedName("default_billing")
    @Expose
    private String default_billing="";

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDefault_shipping() {
        return default_shipping;
    }

    public void setDefault_shipping(String default_shipping) {
        this.default_shipping = default_shipping;
    }

    public String getDefault_billing() {
        return default_billing;
    }

    public void setDefault_billing(String default_billing) {
        this.default_billing = default_billing;
    }
}
