package com.homesorderconsumer.product.deliveryslot.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.product.deliveryslot.DeliverySlotDialog;
import com.homesorderconsumer.product.model.DeliverySlots;
import com.homesorderconsumer.product.model.SelectedDeliverySlot;

/**
 * Created by innoppl on 16/05/18.
 */

public class DeliverySlotDialogVM {

    Activity activity;
    DeliverySlotDialog deliverySlotDialog;
    public DeliverySlotDialogVM(@NonNull Activity activity, DeliverySlotDialog deliverySlotDialog){
        this.activity=activity;
        this.deliverySlotDialog=deliverySlotDialog;
    }

    public void onClickClose(View view){
        deliverySlotDialog.dismiss();
    }

    public void onClickDeliveryOption(View view, DeliverySlots deliverySlots,
                                      SelectedDeliverySlot selectedDeliverySlot){
        selectedDeliverySlot.setDate(deliverySlots.getDate());
        selectedDeliverySlot.setOrderCount(deliverySlots.getOrderCount());
        if (Integer.valueOf(view.getTag().toString())==1){
            selectedDeliverySlot.setSelectedsSlot("M");
            selectedDeliverySlot.setShowText(selectedDeliverySlot.getDate()+" "+activity
                    .getString(R.string.morning));
        }else  if (Integer.valueOf(view.getTag().toString())==2){
            selectedDeliverySlot.setSelectedsSlot("A");
            selectedDeliverySlot.setShowText(selectedDeliverySlot.getDate()+" "+activity
                    .getString(R.string.afternoon));
        }else  if (Integer.valueOf(view.getTag().toString())==3){
            selectedDeliverySlot.setSelectedsSlot("E");
            selectedDeliverySlot.setShowText(selectedDeliverySlot.getDate()+" "+activity
                    .getString(R.string.evening));

        }
        deliverySlotDialog.dismiss();
    }
}
