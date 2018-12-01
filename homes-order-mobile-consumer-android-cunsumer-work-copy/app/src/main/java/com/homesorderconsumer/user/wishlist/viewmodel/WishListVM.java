package com.homesorderconsumer.user.wishlist.viewmodel;

import android.app.Activity;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.api.APIUtil;
import com.homesorderconsumer.product.model.Products;
import com.homesorderconsumer.user.wishlist.model.WishListResponse;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 26/03/18.
 */

public class WishListVM extends java.util.Observable {
    Activity activity;
    MyProgressDialog myProgressDialog;
    List<Products> wishList = new ArrayList<>();
    public ObservableBoolean isNoData = new ObservableBoolean(false);


    public WishListVM(@NonNull Activity activity) {
        this.activity = activity;
        myProgressDialog = new MyProgressDialog();
        isNoData.set(true);
        wishListAPICall();
    }

    public List<Products> getWishList() {
        return wishList;
    }

    private void wishListAPICall() {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<WishListResponse>> observable = api.wishList(APIUtil.getInstance()
                    .getUserToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            wishList = responses.body().getWishlist();
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
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));

                    });
        } else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.no_internet));
        }
    }

    private void setNoData() {
        if (wishList.size() == 0) {
            isNoData.set(true);
        } else {
            isNoData.set(false);
        }
    }
}
