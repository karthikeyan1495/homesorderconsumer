package com.homesorderconsumer.user.myorder.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.MyOrderItemBinding;
import com.homesorderconsumer.user.myorder.delegate.CancelOrderListener;
import com.homesorderconsumer.user.myorder.model.OrdersItem;
import com.homesorderconsumer.user.myorder.viewmodel.MyOrderItemVM;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by innoppl on 07/04/18.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> implements Observer {

    Activity activity;
    List<OrdersItem> list=new ArrayList<>();
    CancelOrderListener cancelOrderListener;
    public MyOrderAdapter(Activity activity,List<OrdersItem> list) {
        this.activity = activity;
        this.list=list;
    }

    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MyOrderItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.my_order_item,
                parent,
                false);
        return new MyOrderAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyOrderAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
        setUpObserver(holder.myOrderItemVM);
        try {
            runCountDownTimer(holder.binding.timer,Long.valueOf(list.get(position).getRemainingTimeInsec()));
        }catch (Exception e){
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof MyOrderItemVM) {
            if (cancelOrderListener!=null){
                cancelOrderListener.onCanceled();
            }
        }
    }

    private void runCountDownTimer(TextView textView,long seconds){
        if (seconds!=0) {
            long milliSeconds = seconds * 1000;
            new CountDownTimer(milliSeconds, 1000) {
                public void onTick(long millisUntilFinished) {
                    String time = "00:";
                    long minutes = (millisUntilFinished / 1000) / 60;
                    long seconds = (millisUntilFinished / 1000) % 60;
                    if (minutes != 0) {
                        if (minutes > 9) {
                            time = String.valueOf(minutes) + ":";
                        } else {
                            time = "0" + String.valueOf(minutes) + ":";
                        }
                    }
                    if (seconds > 9) {
                        time = time + String.valueOf(seconds);
                    } else {
                        time = time + "0" + String.valueOf(seconds);
                    }
                    textView.setText(String.format(activity.getString(R.string.cancel_time_message), time));
                }
                public void onFinish() {
                    OrdersItem ordersItem = (OrdersItem) textView.getTag();
                    ordersItem.setRemainingTime(0);
                    ordersItem.setRemainingTimeInsec(0);
                    notifyDataSetChanged();
                    //textView.setVisibility(View.GONE);
                }
            }.start();
        }
    }

    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }

    public void setCancelOrderListener(CancelOrderListener cancelOrderListener){
        this.cancelOrderListener=cancelOrderListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public MyOrderItemBinding binding;
        MyOrderItemVM myOrderItemVM;

        public ViewHolder(MyOrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(OrdersItem ordersItem) {
            myOrderItemVM=new MyOrderItemVM(activity);
            binding.setMyOrderItemVM(myOrderItemVM);
            binding.setOrdersItem(ordersItem);
            binding.executePendingBindings();
        }
    }
}