package com.homesorderconsumer.product.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.product.ProductDetailActivity;
import com.homesorderconsumer.product.model.Products;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.login.LoginActivity;
import com.homesorderconsumer.user.wishlist.model.WishListAddResponse;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 25/03/18.
 */

public class ProductItemVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;

    public ProductItemVM(@NonNull Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickItem(View view, Products products){
        Intent intent=new Intent(activity, ProductDetailActivity.class);
        intent.putExtra("products",products);
        activity.startActivity(intent);
    }

    public void onClickFavourite(View view, Products products){
        if (MySession.getInstance(activity).isLogin()) {
            if (products.isInWishlist()) {
                wishListAPICall(products,false);
                //products.setInWishlist(false);
            } else {
                wishListAPICall(products,true);
                //products.setInWishlist(true);
            }
        }else {
            activity.startActivity(new Intent(activity, LoginActivity.class));
        }
    }

    public void onClickAddToCart(View view, Products products){
        if (MySession.getInstance(activity).isLogin()){

        }else {

        }
    }

    private void wishListAPICall(Products products,boolean isWishlist) {
        if (InternetChecker.getInstance().isReachable()) {

            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<WishListAddResponse>> observable;

            if (isWishlist) {
                observable = api.wishListAdd(products
                        .getProductID(), MySession.getInstance(activity).getUser().getToken());
            }else{
                observable = api.wishListDelete(products
                        .getWishlistItemId(), MySession.getInstance(activity).getUser().getToken());
            }


            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses
                                    .code(), responses.body().getMessage());

                            if (products.isInWishlist()) {
                                products.setInWishlist(false);
                            } else {
                                products.setWishlistItemId(responses.body().getWishlistItemId());
                                products.setInWishlist(true);
                            }
                            setChanged();
                            notifyObservers(products);
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