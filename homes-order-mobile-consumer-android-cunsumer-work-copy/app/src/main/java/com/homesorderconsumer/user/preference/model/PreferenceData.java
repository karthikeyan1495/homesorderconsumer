package com.homesorderconsumer.user.preference.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.homesorderconsumer.BR;

/**
 * Created by innoppl on 27/03/18.
 */

public class PreferenceData extends BaseObservable {

    String countryName="";
    String stateName="";
    String areaName="";

    @Bindable
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
        notifyPropertyChanged(BR.countryName);
    }

    @Bindable
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
        notifyPropertyChanged(BR.stateName);

    }

    @Bindable
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
        notifyPropertyChanged(BR.areaName);

    }
}
