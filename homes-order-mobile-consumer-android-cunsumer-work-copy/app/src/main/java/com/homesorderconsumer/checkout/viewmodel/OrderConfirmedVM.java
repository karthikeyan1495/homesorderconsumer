package com.homesorderconsumer.checkout.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.navigationmenu.MainActivity;

/**
 * Created by innoppl on 07/04/18.
 */

public class OrderConfirmedVM {
    Activity activity;
    public OrderConfirmedVM(@NonNull Activity activity){
        this.activity=activity;
    }
    public void onClickBack(View view){
        activity.finishAffinity();
        activity.startActivity(new Intent(activity, MainActivity.class));
    }
}
