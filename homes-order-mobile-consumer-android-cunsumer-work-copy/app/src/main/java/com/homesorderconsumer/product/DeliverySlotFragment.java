package com.homesorderconsumer.product;


import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.FragmentDeliverySlotBinding;
import com.homesorderconsumer.product.adapter.DeliverySlotAdapter;
import com.homesorderconsumer.product.model.DeliverySlots;
import com.homesorderconsumer.product.model.SelectedDeliverySlot;
import com.homesorderconsumer.product.viewmodel.DeliverySlotVM;
import com.homesorderconsumer.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliverySlotFragment extends DialogFragment {

    FragmentDeliverySlotBinding binding;
    DeliverySlotVM deliverySlotVM;

    List<DeliverySlots> list=new ArrayList<>();


    public DeliverySlotFragment() {
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
        setupViewPager(list);
        return binding.getRoot();
    }

    public void setDeliverySlotList(List<DeliverySlots> list){
        this.list=list;
    }


    private void bindView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_delivery_slot, container, false);
        deliverySlotVM = new DeliverySlotVM(getActivity(),this);
        binding.setDeliverySlotVM(deliverySlotVM);
    }

    private void setupViewPager(List<DeliverySlots> list) {
        DeliverySlotAdapter adapter = new DeliverySlotAdapter(getActivity(),
                getChildFragmentManager(),list,this);
        binding.viewpager.setAdapter(adapter);
        binding.deliverySlotTabs.setupWithViewPager(binding.viewpager);
        SelectedDeliverySlot selectedDeliverySlot= Util.getInstance().getSelectedDeliverySlot(getActivity());
        binding.viewpager.setCurrentItem(selectedDeliverySlot.getTabPosition());
    }
}
