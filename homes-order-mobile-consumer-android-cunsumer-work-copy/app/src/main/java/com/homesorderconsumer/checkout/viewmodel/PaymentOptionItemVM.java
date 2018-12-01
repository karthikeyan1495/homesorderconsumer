package com.homesorderconsumer.checkout.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.checkout.model.PaymentMethodItem;

/**
 * Created by innoppl on 06/04/18.
 */

public class PaymentOptionItemVM extends java.util.Observable{
    Activity activity;
    public PaymentOptionItemVM(@NonNull Activity activity){
        this.activity=activity;
    }

    public void onClickItem(View view, PaymentMethodItem paymentMethodItem){
        setChanged();
        notifyObservers(paymentMethodItem);
    }
}
