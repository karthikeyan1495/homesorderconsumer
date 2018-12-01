package com.homesorderconsumer.product.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.homesorderconsumer.BR;

/**
 * Created by innoppl on 30/03/18.
 */

public class ProductDataCollection extends BaseObservable {

    String quantity="";
    String colorName="";
    String colorValue="";
    String sizeName="";
    String sizeValue="";
    String weightName="";
    String weightValue="";



    @Bindable
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
        notifyPropertyChanged(BR.quantity);
    }

    @Bindable
    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
        notifyPropertyChanged(BR.colorName);
    }

    @Bindable
    public String getColorValue() {
        return colorValue;
    }

    public void setColorValue(String colorValue) {
        this.colorValue = colorValue;
        notifyPropertyChanged(BR.colorValue);
    }

    @Bindable
    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
        notifyPropertyChanged(BR.sizeName);
    }

    @Bindable
    public String getSizeValue() {
        return sizeValue;
    }

    public void setSizeValue(String sizeValue) {
        this.sizeValue = sizeValue;
        notifyPropertyChanged(BR.sizeValue);
    }

    @Bindable
    public String getWeightName() {
        return weightName;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
        notifyPropertyChanged(BR.weightName);

    }

    @Bindable
    public String getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(String weightValue) {
        this.weightValue = weightValue;
        notifyPropertyChanged(BR.weightValue);

    }
}
