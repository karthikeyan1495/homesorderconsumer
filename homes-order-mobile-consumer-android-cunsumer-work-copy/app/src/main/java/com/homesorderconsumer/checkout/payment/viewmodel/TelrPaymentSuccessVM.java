package com.homesorderconsumer.checkout.payment.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.checkout.model.CheckoutResponse;
import com.homesorderconsumer.checkout.model.DeliveryAddress;
import com.homesorderconsumer.databinding.ActivityTelrPaymentSuccessBinding;
import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 14/06/18.
 */

public class TelrPaymentSuccessVM {

    Activity activity;
    MyProgressDialog myProgressDialog;
    ActivityTelrPaymentSuccessBinding binding;
    String transactionId="";


    public TelrPaymentSuccessVM(@NonNull Activity activity, ActivityTelrPaymentSuccessBinding binding,String transactionId){
        this.activity=activity;
        this.binding=binding;
        this.transactionId=transactionId;
        myProgressDialog=new MyProgressDialog();
        if (MySession.getInstance(activity).isLogin()){
            checkoutAPICall(MySession.getInstance(activity).getDeliveryAddress(),false);
        }else {
            checkoutAPICall(MySession.getInstance(activity).getDeliveryAddress(),true);
        }
    }

    public void onClickBack(View view){
        activity.finishAffinity();
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    private void checkoutAPICall(DeliveryAddress deliveryAddress,
                                 boolean isGuest) {
        deliveryAddress.setRefId(transactionId);
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<CheckoutResponse>> observable;
            if (isGuest) {
                observable = api.guestOrderCheckout(MySession
                        .getInstance(activity).getGuestCartID().trim(), deliveryAddress);
            }else{
                observable = api.orderCheckout(MySession
                        .getInstance(activity).getUser().getToken(),deliveryAddress);
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            MySession.getInstance(activity).saveGuestCartID("");
                            MySession.getInstance(activity).saveCartCount(0);
                            binding.setOrderId(responses.body().getOrders().getIncrement_id());
                        } else {
                            if (responses.body() != null) {
                                APIErrorHandler.getInstance().errorHandler(activity, responses
                                        .code(), responses.body().getMessage());
                            } else {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.errorBody().string());
                            }
                        }
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
                    });
        } else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.no_internet));
        }
    }
}
