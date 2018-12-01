package com.homesorderconsumer.user.myorder.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.user.myorder.CancelOrderFragment;
import com.homesorderconsumer.user.myorder.MyOrderDetailActivity;
import com.homesorderconsumer.user.myorder.NavigationEnum;
import com.homesorderconsumer.user.myorder.model.OrdersItem;
import com.homesorderconsumer.util.MyProgressDialog;

/**
 * Created by innoppl on 07/04/18.
 */

public class MyOrderItemVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;



    public MyOrderItemVM(@NonNull Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickItem(View view, OrdersItem ordersItem){
        Intent intent=new Intent(activity, MyOrderDetailActivity.class);
        intent.putExtra("ordersItem",ordersItem);
        intent.putExtra("navigationFrom", NavigationEnum.ORDER_LIST
                .getValue());
        activity.startActivity(intent);
    }

    public void onClickStatus(View view, OrdersItem ordersItem){
        if (ordersItem.getRemainingTime()>0){
            CancelOrderFragment cancelOrderFragment=new CancelOrderFragment();
            cancelOrderFragment.setOrderItem(ordersItem);
            cancelOrderFragment.setCancelOrderListener(() -> {
                setChanged();
                notifyObservers();
            });
            MainActivity mainActivity=(MainActivity) activity;
            cancelOrderFragment.show(mainActivity.getSupportFragmentManager(), "dialog");
        }else{
        }
    }

}
