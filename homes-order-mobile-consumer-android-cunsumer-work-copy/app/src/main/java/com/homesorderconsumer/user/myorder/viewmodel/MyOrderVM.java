package com.homesorderconsumer.user.myorder.viewmodel;

import android.app.Activity;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.api.APIUtil;
import com.homesorderconsumer.user.myorder.model.MyOrderListResponse;
import com.homesorderconsumer.user.myorder.model.OrdersItem;
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
 * Created by innoppl on 07/04/18.
 */

public class MyOrderVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;
    List<OrdersItem> ordersItems=new ArrayList<>();
    public ObservableBoolean isNoData = new ObservableBoolean(true);

    public MyOrderVM(@NonNull Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
        myOrdersAPICall();
    }

    public List<OrdersItem> getOrdersItems(){
        return ordersItems;
    }

    public void myOrdersAPICall() {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<MyOrderListResponse>> observable = api.ordersList(APIUtil.getInstance()
                    .getUserToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            ordersItems=responses.body().getOrders();
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
        if (ordersItems.size() == 0) {
            isNoData.set(true);
        } else {
            isNoData.set(false);
        }
    }
}