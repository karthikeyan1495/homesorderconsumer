package com.homesorderconsumer.product.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.product.DeliverySlotFragment;

/**
 * Created by innoppl on 28/03/18.
 */

public class DeliverySlotVM {
    Activity activity;
    DeliverySlotFragment deliverySlotFragment;
    public DeliverySlotVM(@NonNull Activity activity,DeliverySlotFragment deliverySlotFragment){
        this.activity=activity;
        this.deliverySlotFragment=deliverySlotFragment;
    }

    public void onClickClose(View view){
        deliverySlotFragment.dismiss();
    }
}
