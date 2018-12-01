package com.homesorderconsumer.user.cart.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.checkout.CheckoutActivity;
import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.cart.model.CartItem;
import com.homesorderconsumer.user.cart.model.CartListResponse;
import com.homesorderconsumer.user.cart.model.CartPrice;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;
import com.homesorderconsumer.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 26/03/18.
 */

public class CartVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;
    List<CartItem> cartItems=new ArrayList<>();
    public ObservableBoolean isNoData = new ObservableBoolean(false);

    public CartVM(@NonNull Activity activity) {
        this.activity = activity;
        this.myProgressDialog=new MyProgressDialog();
        APICall();
    }

    public void onClickBack(View view) {
        activity.finishAffinity();
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    public void onClickCheckout(View view, CartPrice cartPrice){
        if (checkoutValidation()) {
            Intent intent=new Intent(activity, CheckoutActivity.class);
            intent.putExtra("cartPrice",cartPrice);
            activity.startActivity(intent);
        }else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .delete_unavailable_product));
        }
    }


    private void APICall(){
        if (MySession.getInstance(activity).isLogin()){
            cartListAPICall(false);
        }else {
            if (MySession.getInstance(activity).getGuestCartID().trim().length()!=0){
                cartListAPICall(true);
            }else {
                setNoData();
            }
        }
    }


    public List<CartItem> getCartItems(){
        return cartItems;
    }

    private void cartListAPICall(boolean isGuest) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);

            Observable<Response<CartListResponse>> observable;
            if (isGuest) {
                observable = api.guestCartItems(StringUtil
                        .selectedStateName(), MySession
                        .getInstance
                                (activity).getGuestCartID().trim());
            }else {
                observable = api.cartItems(StringUtil
                        .selectedStateName(), MySession
                        .getInstance
                                (activity).getUser().getToken());
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            cartItems=responses.body().getItems();
                            setChanged();
                            notifyObservers();
                        } else {
                            if (responses.body() != null) {
                                APIErrorHandler.getInstance().errorHandler(activity, responses
                                        .code(), responses.body().getMessage());
                            } else {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.errorBody().string());
                            }
                        }
                        setNoData();
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        setNoData();
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
                    });
        } else {
            setNoData();
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.no_internet));
        }
    }


    private void setNoData() {
        if (cartItems.size() == 0) {
            isNoData.set(true);
        } else {
            isNoData.set(false);
        }
    }

    private boolean checkoutValidation(){
        for (CartItem cartItem:cartItems){
            if (!cartItem.getProduct().isAvailable()){
                return false;
            }
        }

        return true;
    }
}
