package com.homesorderconsumer.user.myorder;

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
import com.homesorderconsumer.databinding.FragmentCancelOrderBinding;
import com.homesorderconsumer.user.myorder.delegate.CancelOrderListener;
import com.homesorderconsumer.user.myorder.model.CancelOrder;
import com.homesorderconsumer.user.myorder.model.OrdersItem;
import com.homesorderconsumer.user.myorder.viewmodel.CancelOrderVM;

/**
 * Created by innoppl on 18/04/18.
 */

public class CancelOrderFragment extends DialogFragment {

    FragmentCancelOrderBinding binding;
    CancelOrderVM cancelOrderVM;
    OrdersItem ordersItem=new OrdersItem();

    public CancelOrderListener cancelOrderListener;

    public CancelOrderFragment() {
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

    public void setOrderItem(OrdersItem ordersItem){
        this.ordersItem=ordersItem;
    }

    public void setCancelOrderListener(CancelOrderListener cancelOrderListener){
        this.cancelOrderListener=cancelOrderListener;
    }

    private void bindView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cancel_order, container, false);
        cancelOrderVM=new CancelOrderVM(getActivity(),this,ordersItem);
        binding.setCancelOrderVM(cancelOrderVM);
        binding.setCancelOrder(new CancelOrder());
    }

}

