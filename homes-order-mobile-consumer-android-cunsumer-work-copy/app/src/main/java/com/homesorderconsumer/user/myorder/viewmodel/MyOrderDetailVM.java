package com.homesorderconsumer.user.myorder.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.api.APIUtil;
import com.homesorderconsumer.trackorder.TrackOrder;
import com.homesorderconsumer.trackorder.model.TrackOrderResponse;
import com.homesorderconsumer.user.myorder.CancelOrderFragment;
import com.homesorderconsumer.user.myorder.MyOrderDetailActivity;
import com.homesorderconsumer.user.myorder.NavigationEnum;
import com.homesorderconsumer.user.myorder.model.OrderDetailResponse;
import com.homesorderconsumer.user.myorder.model.OrdersItem;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 07/04/18.
 */

public class MyOrderDetailVM extends java.util.Observable{
    Activity activity;
    MyProgressDialog myProgressDialog;

    OrdersItem ordersItem;

    public MyOrderDetailVM(@NonNull Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickBack(View view){
        activity.finish();
    }

    public void onClickStatus(View view, OrdersItem ordersItem) {
        MyOrderDetailActivity myOrderDetailActivity=(MyOrderDetailActivity) activity;
        CancelOrderFragment cancelOrderFragment=new CancelOrderFragment();
        cancelOrderFragment.setOrderItem(ordersItem);
        cancelOrderFragment.setCancelOrderListener(() -> {
            if (myOrderDetailActivity.navigationFrom!=null&&myOrderDetailActivity.navigationFrom
                    .equals(NavigationEnum
                    .ORDER_LIST.getValue())) {
                myOrdersDetailAPICall(ordersItem.getOrderId());
            }else if(myOrderDetailActivity.navigationFrom!=null&&myOrderDetailActivity
                    .navigationFrom.equals
                    (NavigationEnum.TRACK_ORDER.getValue())) {
                trackOrderAPICall(myOrderDetailActivity.trackOrder);
            }else {
                myOrdersDetailAPICall(myOrderDetailActivity.orderId);
            }
        });
        cancelOrderFragment.show(myOrderDetailActivity.getSupportFragmentManager(), "dialog");
    }

    public OrdersItem getOrdersItem(){
        return ordersItem;
    }

    public void myOrdersDetailAPICall(String orderId) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<OrderDetailResponse>> observable = api.ordersById(orderId,APIUtil.getInstance()
                    .getUserToken());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            ordersItem=responses.body().getOrdersItem();
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
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
                    });
        } else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.no_internet));
        }
    }

    public void trackOrderAPICall(TrackOrder trackOrder){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<TrackOrderResponse>> observable = api.trackOrder(trackOrder);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            ordersItem=responses.body().getOrders();
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
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
                    });
        } else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.no_internet));
        }
    }



}
