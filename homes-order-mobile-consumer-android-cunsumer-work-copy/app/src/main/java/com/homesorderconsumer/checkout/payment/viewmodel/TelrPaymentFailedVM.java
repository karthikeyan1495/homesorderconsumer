package com.homesorderconsumer.checkout.payment.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.checkout.payment.PaymentUtil;
import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.cart.CartActivity;
import com.homesorderconsumer.util.MySnackBar;

/**
 * Created by innoppl on 14/06/18.
 */

public class TelrPaymentFailedVM {
    Activity activity;

    public TelrPaymentFailedVM(@NonNull Activity activity){
        this.activity=activity;
    }

    public void onClickBack(View view){
        activity.finishAffinity();
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    public void onClickRetry(View view){
        activity.finishAffinity();
        activity.startActivity(new Intent(activity, CartActivity.class));
        /*if (MySession.getInstance(activity).getCartAmount()!=null&&MySession.getInstance
                (activity).getDeliveryAddress()!=null) {
            PaymentUtil.telrPaymentGatewayInitialization(activity, MySession.getInstance(activity).getCartAmount(),
                    MySession.getInstance
                            (activity).getDeliveryAddress());
        }else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
        }*/
    }
}
