package com.homesorderconsumer.product;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.DeliverySlotItemBinding;
import com.homesorderconsumer.product.adapter.DeliverySlotAdapter;
import com.homesorderconsumer.product.model.DeliverySlots;
import com.homesorderconsumer.product.model.SelectedDeliverySlot;
import com.homesorderconsumer.product.model.Slots;
import com.homesorderconsumer.product.viewmodel.DeliverySlotItemVM;

/**
 * Created by innoppl on 28/03/18.
 */

public class DeliverySlotItemFragment extends Fragment {

    DeliverySlotItemBinding binding;
    DeliverySlotItemVM deliverySlotItemVM;
    DeliverySlots deliverySlots=new DeliverySlots();

    DeliverySlotAdapter.OnDeliverySlotSelected onDeliverySlotSelected;
    int position;

    public DeliverySlotItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getIntentData();
        bindView(inflater, container);
        return binding.getRoot();
    }

    private void getIntentData(){
        Bundle bundle=getArguments();
        if (bundle!=null) {
            deliverySlots = (DeliverySlots)bundle.getSerializable("slots");
            position=bundle.getInt("position");
        }
    }

    public void setOnDeliverySlotSelected(DeliverySlotAdapter.OnDeliverySlotSelected
                                                  onDeliverySlotSelected){
        this.onDeliverySlotSelected=onDeliverySlotSelected;
    }


    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater, R.layout.delivery_slot_item, container, false);
        deliverySlotItemVM=new DeliverySlotItemVM(getActivity(),onDeliverySlotSelected,position);
        binding.setDeliverySlotItemVM(deliverySlotItemVM);
        binding.setSlots(getSlots());
        binding.setDeliverySlots(deliverySlots);
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
