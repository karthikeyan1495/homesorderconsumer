package com.homesorderconsumer.user.myorder.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.api.APIUtil;
import com.homesorderconsumer.api.GeneralResponse;
import com.homesorderconsumer.user.myorder.CancelOrderFragment;
import com.homesorderconsumer.user.myorder.model.CancelOrder;
import com.homesorderconsumer.user.myorder.model.OrdersItem;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 18/04/18.
 */

public class CancelOrderVM {

    Activity activity;
    CancelOrderFragment cancelOrderFragment;
    OrdersItem ordersItem;
    MyProgressDialog myProgressDialog;
    public CancelOrderVM(@NonNull Activity activity,CancelOrderFragment cancelOrderFragment,OrdersItem ordersItem){
        this.activity=activity;
        this.cancelOrderFragment=cancelOrderFragment;
        this.ordersItem=ordersItem;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickClose(View view){
        cancelOrderFragment.dismiss();
    }

    public void onClickSubmit(View view, CancelOrder cancelOrder){
        cancelOrdersAPICall(cancelOrder);
    }

    private void cancelOrdersAPICall(CancelOrder cancelOrder) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.cancelOrder(ordersItem
                    .getOrderId(), APIUtil
                    .getInstance()
                    .getUserToken(),cancelOrder);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        cancelOrderFragment.dismiss();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses
                                    .code(), responses.body().getMessage());
                            if (cancelOrderFragment.cancelOrderListener!=null){
                                cancelOrderFragment.cancelOrderListener.onCanceled();
                            }
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