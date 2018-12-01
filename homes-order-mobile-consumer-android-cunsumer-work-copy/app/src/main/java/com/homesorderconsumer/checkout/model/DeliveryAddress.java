package com.homesorderconsumer.checkout.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.homesorderconsumer.BR;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 06/04/18.
 */

public class DeliveryAddress extends BaseObservable {

    @SerializedName("paymentMethod")
    @Expose
    private String paymentMethod;

    @SerializedName("region")
    @Expose
    private String region="";

    @SerializedName("country_id")
    @Expose
    private String country_id="";

    @SerializedName("country_name")
    @Expose
    private String country_name="";

    @SerializedName("postcode")
    @Expose
    private String postcode="";

    @SerializedName("city")
    @Expose
    private String city="";

    @SerializedName("firstname")
    @Expose
    private String firstname="";

    @SerializedName("lastname")
    @Expose
    private String lastname="";

    @SerializedName("email")
    @Expose
    private String email="";

    @SerializedName("telephone")
    @Expose
    private String telephone="";

    @SerializedName("address_line1")
    @Expose
    private String address_line1="";

    @SerializedName("address_line2")
    @Expose
    private String address_line2="";

    @SerializedName("area")
    @Expose
    private String area="";

    @SerializedName("street")
    @Expose
    private List<String> street=new ArrayList<>();

    @SerializedName("refId")
    @Expose
    private String refId="";

    @Bindable
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
        notifyPropertyChanged(BR.region);
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<String> getStreet() {
        return street;
    }

    public void setStreet(List<String> street) {
        this.street = street;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Bindable
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
        notifyPropertyChanged(BR.area);

    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
}
