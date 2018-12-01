package com.homesorderconsumer.trackorder.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.databinding.FragmentTrackOrderBinding;
import com.homesorderconsumer.trackorder.TrackOrder;
import com.homesorderconsumer.trackorder.model.TrackOrderResponse;
import com.homesorderconsumer.user.myorder.MyOrderDetailActivity;
import com.homesorderconsumer.user.myorder.NavigationEnum;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;
import com.homesorderconsumer.util.Util;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 08/05/18.
 */

public class TrackOrderVM {

    Activity activity;
    FragmentTrackOrderBinding binding;
    MyProgressDialog myProgressDialog;
    public TrackOrderVM(@NonNull Activity activity,FragmentTrackOrderBinding binding){
        this.activity=activity;
        this.binding=binding;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickSubmit(View view, TrackOrder trackOrder){
        Util.getInstance().hideKeyboard(activity);
        clearError();
        validation(trackOrder);
    }

    private void validation(TrackOrder trackOrder){
        if (trackOrder.getOrderIncrementId().trim().length()==0){
            binding.orderIdLayout.setError(activity.getString(R.string.valid_order_id));
        }else if (trackOrder.getLastName().trim().length()==0){
            binding.lastNameLayout.setError(activity.getString(R.string.valid_billing_last_name));
        }else if (trackOrder.getEmail().trim().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(trackOrder
                .getEmail().trim()).matches()) {
            binding.emailLayout.setError(activity.getString(R.string.valid_email_address_error));
        }else {
            trackOrderAPICall(trackOrder);
        }
    }

    private void clearError(){
        binding.orderIdLayout.setError(null);
        binding.lastNameLayout.setError(null);
        binding.emailLayout.setError(null);
    }

    private void trackOrderAPICall(TrackOrder trackOrder){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<TrackOrderResponse>> observable = api.trackOrder(trackOrder);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            Intent intent=new Intent(activity, MyOrderDetailActivity.class);
                            intent.putExtra("ordersItem",responses.body().getOrders());
                            intent.putExtra("trackOrder",trackOrder);
                            intent.putExtra("navigationFrom", NavigationEnum.TRACK_ORDER
                                    .getValue());
                            activity.startActivity(intent);
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
