package com.homesorderconsumer.checkout.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.homesorderconsumer.BR;

/**
 * Created by innoppl on 06/04/18.
 */

public class CheckoutUI extends BaseObservable {

    boolean isAddressOpen=true;
    boolean isPaymentMethodOpen=false;
    boolean isAddressSaved=false;

    @Bindable
    public boolean isAddressOpen() {
        return isAddressOpen;
    }

    public void setAddressOpen(boolean addressOpen) {
        isAddressOpen = addressOpen;
        notifyPropertyChanged(BR.addressOpen);

    }

    @Bindable
    public boolean isPaymentMethodOpen() {
        return isPaymentMethodOpen;
    }

    public void setPaymentMethodOpen(boolean paymentMethodOpen) {
        isPaymentMethodOpen = paymentMethodOpen;
        notifyPropertyChanged(BR.paymentMethodOpen);
    }

    @Bindable
    public boolean isAddressSaved() {
        return isAddressSaved;
    }

    public void setAddressSaved(boolean addressSaved) {
        isAddressSaved = addressSaved;
        notifyPropertyChanged(BR.addressSaved);
    }
}
