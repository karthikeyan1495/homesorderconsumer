package com.homesorderconsumer.home.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.product.SearchActivity;
import com.homesorderconsumer.user.cart.CartActivity;

/**
 * Created by innoppl on 25/03/18.
 */

public class CartAndSearchVM {

    Activity activity;
    public CartAndSearchVM(@NonNull Activity activity){
        this.activity=activity;
    }

    public void onClickSearch(View view){
        activity.startActivity(new Intent(activity, SearchActivity.class));
    }
    public void onClickCart(View view){
        if (activity instanceof CartActivity){
        }else {
            activity.startActivity(new Intent(activity, CartActivity.class));
        }
    }
}
