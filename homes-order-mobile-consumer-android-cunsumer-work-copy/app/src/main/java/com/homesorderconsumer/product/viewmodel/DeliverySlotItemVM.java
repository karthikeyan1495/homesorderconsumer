package com.homesorderconsumer.product.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.product.adapter.DeliverySlotAdapter;
import com.homesorderconsumer.product.model.DeliverySlots;
import com.homesorderconsumer.product.model.SelectedDeliverySlot;

/**
 * Created by innoppl on 28/03/18.
 */

public class DeliverySlotItemVM {

    Activity activity;
    DeliverySlotAdapter.OnDeliverySlotSelected onDeliverySlotSelected;
    int position;

    public DeliverySlotItemVM(@NonNull Activity activity, DeliverySlotAdapter
            .OnDeliverySlotSelected onDeliverySlotSelected,int position){
        this.activity=activity;
        this.onDeliverySlotSelected=onDeliverySlotSelected;
        this.position=position;
    }

    public void onClickDeliveryOption(View view, DeliverySlots deliverySlots,
                                      SelectedDeliverySlot selectedDeliverySlot){
        selectedDeliverySlot.setDate(deliverySlots.getDate());
        selectedDeliverySlot.setOrderCount(deliverySlots.getOrderCount());
        if (Integer.valueOf(view.getTag().toString())==1){
            selectedDeliverySlot.setSelectedsSlot("M");
        }else  if (Integer.valueOf(view.getTag().toString())==2){
            selectedDeliverySlot.setSelectedsSlot("A");
        }else  if (Integer.valueOf(view.getTag().toString())==3){
            selectedDeliverySlot.setSelectedsSlot("E");
        }
        if (onDeliverySlotSelected!=null){
            onDeliverySlotSelected.onSlotSelected(position);
        }
    }

}
