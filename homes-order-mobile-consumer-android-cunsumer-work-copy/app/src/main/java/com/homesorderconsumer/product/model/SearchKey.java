package com.homesorderconsumer.product.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.homesorderconsumer.BR;

/**
 * Created by innoppl on 28/03/18.
 */

public class SearchKey extends BaseObservable {

    String searchKey="";

    String displayText="";

    @Bindable
    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
        notifyPropertyChanged(BR.searchKey);
    }

    @Bindable
    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
        notifyPropertyChanged(BR.displayText);
    }
}
