package com.homesorderconsumer.user.myorder.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.trackorder.TrackOrder;
import com.homesorderconsumer.user.myorder.MyOrderDetailActivity;
import com.homesorderconsumer.user.myorder.RateAndReviewFragment;
import com.homesorderconsumer.user.myorder.model.OrderProductItem;
import com.homesorderconsumer.user.myorder.model.OrdersItem;

/**
 * Created by innoppl on 07/04/18.
 */

public class OrderProductItemVM {

    Activity activity;
    TrackOrder trackOrder;

    public OrderProductItemVM(@NonNull Activity activity,TrackOrder trackOrder){
        this.activity=activity;
        this.trackOrder=trackOrder;
    }

    public void onClickRatingAndReview(View view, OrdersItem ordersItem, OrderProductItem orderProductItem){
        RateAndReviewFragment rateAndReviewFragment=new RateAndReviewFragment();
        rateAndReviewFragment.setOrderProductItem(orderProductItem,ordersItem,trackOrder);
        MyOrderDetailActivity myOrderDetailActivity=(MyOrderDetailActivity)activity;
        rateAndReviewFragment.show(myOrderDetailActivity.getSupportFragmentManager(), "dialog");
    }

}
