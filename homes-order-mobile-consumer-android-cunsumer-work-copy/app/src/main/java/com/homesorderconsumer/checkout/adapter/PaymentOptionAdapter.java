package com.homesorderconsumer.checkout.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.checkout.model.PaymentMethodItem;
import com.homesorderconsumer.checkout.viewmodel.PaymentOptionItemVM;
import com.homesorderconsumer.databinding.PaymentOptionItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by innoppl on 06/04/18.
 */

public class PaymentOptionAdapter extends RecyclerView.Adapter<PaymentOptionAdapter.ViewHolder> implements Observer {

    Activity activity;
    public List<PaymentMethodItem> list = new ArrayList<>();

    public PaymentOptionAdapter(Activity activity, List<PaymentMethodItem> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public PaymentOptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PaymentOptionItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.payment_option_item,
                parent,
                false);
        return new PaymentOptionAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PaymentOptionAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
        setUpObserver(holder.paymentOptionItemVM);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof PaymentOptionItemVM) {
            PaymentMethodItem paymentMethodItem=(PaymentMethodItem)object;
            for (int i=0;i<list.size();i++){
                if (list.get(i).getCode().equals(paymentMethodItem.getCode())){
                    list.get(i).setSelected(true);
                }else {
                    list.get(i).setSelected(false);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private PaymentOptionItemBinding binding;
        PaymentOptionItemVM paymentOptionItemVM;

        public ViewHolder(PaymentOptionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PaymentMethodItem paymentMethodItem) {
            paymentOptionItemVM = new PaymentOptionItemVM(activity);
            binding.setPaymentOptionItemVM(paymentOptionItemVM);
            binding.setPaymentMethodItem(paymentMethodItem);
            binding.executePendingBindings();
        }
    }
}