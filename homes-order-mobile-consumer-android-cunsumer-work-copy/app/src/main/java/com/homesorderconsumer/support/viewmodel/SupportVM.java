package com.homesorderconsumer.support.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.api.GeneralResponse;
import com.homesorderconsumer.databinding.FragmentSupportBinding;
import com.homesorderconsumer.home.HomeFragment;
import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.support.model.Support;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 10/04/18.
 */

public class SupportVM {
    Activity activity;
    FragmentSupportBinding binding;
    MyProgressDialog myProgressDialog;
    public SupportVM(@NonNull Activity activity, FragmentSupportBinding binding){
        this.activity=activity;
        this.binding=binding;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickSubmit(View view, Support support){
        clear();
        validation(support);
    }

    private void clear(){
        binding.nameLayout.setError(null);
        binding.emailLayout.setError(null);
        binding.phoneNumberLayout.setError(null);
        binding.yourMessageLayout.setError(null);
    }
    private void validation(Support support){
        if (support.getName().trim().length() <4 ) {
            binding.nameLayout.setError(activity.getString(R.string.valid_name));
        }  else if (support.getEmail().trim().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(support
                .getEmail().trim()).matches()) {
            binding.emailLayout.setError(activity.getString(R.string.valid_email_address_error));
        } else if(support.getPhone().trim().length()< 8) {
            binding.phoneNumberLayout.setError(activity.getString(R.string.valid_phone_number));
        } else if(support.getMessage().trim().length()==0){
            binding.yourMessageLayout.setError(activity.getString(R.string.valid_your_message));
        } else {
            supportAPICall(support);
        }
    }

    private void supportAPICall(Support support){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.contactUs(support);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses
                                    .code(), responses.body().getMessage());
                            if (activity instanceof MainActivity){
                                MainActivity mainActivity=(MainActivity)activity;
                                mainActivity.showFragment(new HomeFragment());
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
