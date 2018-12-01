package com.homesorderconsumer.user.cart.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.api.APIUtil;
import com.homesorderconsumer.api.GeneralResponse;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.cart.model.CartItem;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 26/03/18.
 */

public class CartItemVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;

    public CartItemVM(@NonNull Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickItem(View view, CartItem cartItem){
    }

    public void onClickQuantity(View view, CartItem cartItem){
        quantityPicker(cartItem);
    }

    public void onClickRemove(View view, CartItem cartItem){
        confirmationAlert(cartItem);
    }


    private void confirmationAlert(CartItem cartItem) {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support
                .v7.app.AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(activity.getString(R.string.cart_item_remove_alert));
        alertDialogBuilder.setPositiveButton(activity.getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        APICall(cartItem,true,null);
                    }
                });

        alertDialogBuilder.setNegativeButton(activity.getString(R.string.no), new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void APICall(CartItem cartItem,boolean isRemove,String qty){
        if (MySession.getInstance(activity).isLogin()){
            if (isRemove) {
                cartRemoveAPICall(cartItem,false);
            }else{
                if (qty!=null){
                    if (!cartItem.getProduct().getQty().equals(qty))
                        cartUpdateAPICall(cartItem,qty,false);
                }
            }
        }else {
            if (MySession.getInstance(activity).getGuestCartID().trim().length()!=0){
                if (isRemove) {
                    cartRemoveAPICall(cartItem,true);
                }else{
                    if (qty!=null){
                        if (!cartItem.getProduct().getQty().equals(qty))
                            cartUpdateAPICall(cartItem,qty,true);
                    }
                }
            }
        }
    }

    private void quantityPicker(CartItem cartItem) {
        CharSequence[] items = new CharSequence[10];
        for (int i = 0; i <10; i++) {
            items[i] = String.valueOf(i+1);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                APICall(cartItem,false,String.valueOf(items[position]));
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void cartUpdateAPICall(CartItem cartItem,String qty,boolean isGuest) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable;

            if (isGuest) {
                observable = api.guestUpdateCart
                        (MySession
                                .getInstance(activity).getGuestCartID().trim(), cartItem.getItem_id(), APIUtil
                                .getInstance().updateCart(cartItem, qty));
            }else {
                observable = api.updateCart(cartItem.getItem_id(), MySession.getInstance
                        (activity).getUser().getToken(),APIUtil
                                .getInstance().updateCart(cartItem, qty));
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses
                                    .code(), responses.body().getMessage());
                            cartItem.setRemove(false);
                            cartItem.getProduct().setQty(qty);
                            setChanged();
                            notifyObservers(cartItem);
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

    private void cartRemoveAPICall(CartItem cartItem,boolean isGuest) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable;
            if (isGuest) {
                observable = api.guestDeleteCartItem(MySession
                        .getInstance(activity).getGuestCartID().trim(), cartItem.getItem_id());
            }else{
                observable = api.deleteCartItem(cartItem.getItem_id(),MySession
                        .getInstance(activity).getUser().getToken());
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses
                                    .code(), responses.body().getMessage());
                            cartItem.setRemove(true);
                            setChanged();
                            notifyObservers(cartItem);
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
