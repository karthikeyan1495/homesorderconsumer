package com.homesorderconsumer.user.myorder.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.OrderProductItemBinding;
import com.homesorderconsumer.trackorder.TrackOrder;
import com.homesorderconsumer.user.myorder.model.OrderProductItem;
import com.homesorderconsumer.user.myorder.model.OrdersItem;
import com.homesorderconsumer.user.myorder.viewmodel.OrderProductItemVM;

import java.util.List;

/**
 * Created by innoppl on 07/04/18.
 */

public class MyOrderProductItemAdapter extends RecyclerView.Adapter<MyOrderProductItemAdapter.ViewHolder> {

    Activity activity;
    OrdersItem ordersItem;
    List<OrderProductItem> list;

    TrackOrder trackOrder;



    public MyOrderProductItemAdapter(Activity activity,OrdersItem ordersItem,List<OrderProductItem> list,TrackOrder trackOrder) {
        this.activity = activity;
        this.ordersItem=ordersItem;
        this.list=list;
        this.trackOrder=trackOrder;
    }

    @Override
    public MyOrderProductItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        OrderProductItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.order_product_item,
                parent,
                false);
        return new MyOrderProductItemAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyOrderProductItemAdapter.ViewHolder holder, int position) {
        holder.bind(ordersItem,list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private OrderProductItemBinding binding;

        public ViewHolder(OrderProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(OrdersItem ordersItem,OrderProductItem orderProductItem) {
            binding.setOrderProductItemVM(new OrderProductItemVM(activity,trackOrder));
            binding.setOrdersItem(ordersItem);
            binding.setOrderProductItem(orderProductItem);
            binding.executePendingBindings();
        }
    }
}