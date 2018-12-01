package com.homesorderconsumer.product.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.homesorderconsumer.BR;

/**
 * Created by innoppl on 02/04/18.
 */

public class SelectedDeliverySlot extends BaseObservable {

    private String orderCount="";
    private String date="";
    private String selectedsSlot="";
    private int tabPosition=0;

    private String showText="";

    @Bindable
    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
        notifyPropertyChanged(BR.orderCount);

    }

    @Bindable
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        notifyPropertyChanged(BR.date);

    }

    @Bindable
    public String getSelectedsSlot() {
        return selectedsSlot;
    }

    public void setSelectedsSlot(String selectedsSlot) {
        this.selectedsSlot = selectedsSlot;
        notifyPropertyChanged(BR.selectedsSlot);
    }

    public int getTabPosition() {
        return tabPosition;
    }

    public void setTabPosition(int tabPosition) {
        this.tabPosition = tabPosition;
    }

    @Bindable
    public String getShowText() {
        return showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
        notifyPropertyChanged(BR.showText);

    }
}
