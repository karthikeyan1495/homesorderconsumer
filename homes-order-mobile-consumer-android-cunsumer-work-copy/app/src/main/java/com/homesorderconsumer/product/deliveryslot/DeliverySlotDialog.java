package com.homesorderconsumer.product.deliveryslot;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.DeliverySlotDialogBinding;
import com.homesorderconsumer.product.ProductDetailActivity;
import com.homesorderconsumer.product.deliveryslot.viewmodel.DeliverySlotDialogVM;
import com.homesorderconsumer.product.model.DeliverySlots;
import com.homesorderconsumer.product.model.SelectedDeliverySlot;
import com.homesorderconsumer.product.model.Slots;

/**
 * Created by innoppl on 16/05/18.
 */

public class DeliverySlotDialog extends DialogFragment {

    DeliverySlotDialogBinding binding;
    DeliverySlotDialogVM deliverySlotDialogVM;
    DeliverySlots deliverySlots = new DeliverySlots();

    public DeliverySlotDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        bindView(inflater, container);
        return binding.getRoot();
    }

    public void setDeliverySlot(DeliverySlots deliverySlots){
        this.deliverySlots=deliverySlots;
    }

    private void bindView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.delivery_slot_dialog, container, false);
        deliverySlotDialogVM = new DeliverySlotDialogVM(getActivity(),this);
        binding.setDeliverySlotDialogVM(deliverySlotDialogVM);
        binding.setDeliverySlots(deliverySlots);
        binding.setSlots(getSlots());
        binding.setSelectedDeliverySlot(getSelectedDeliverySlot());
    }

    private Slots getSlots(){
        Slots slots=new Slots();
        for (int i=0;i<deliverySlots.getSlots().size();i++){
            if (deliverySlots.getSlots().get(i).trim().toLowerCase().equals("m")){
                slots.setMorning(true);
            }else if (deliverySlots.getSlots().get(i).trim().toLowerCase().equals("a")){
                slots.setAfternoon(true);
            }else if (deliverySlots.getSlots().get(i).trim().toLowerCase().equals("e")){
                slots.setEvening(true);
            }
        }
        return slots;
    }

    private SelectedDeliverySlot getSelectedDeliverySlot(){
        SelectedDeliverySlot selectedDeliverySlot=new SelectedDeliverySlot();
        if (getActivity() instanceof ProductDetailActivity){
            ProductDetailActivity productDetailActivity=(ProductDetailActivity)getActivity();
            selectedDeliverySlot=productDetailActivity.selectedDeliverySlot;
        }
        return selectedDeliverySlot;
    }
}
