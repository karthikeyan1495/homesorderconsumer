package com.homesorderconsumer.user.forgotpassword.viewmodel;

import android.app.Activity;
import android.util.Patterns;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.api.GeneralResponse;
import com.homesorderconsumer.databinding.ActivityForgotPasswordBinding;
import com.homesorderconsumer.user.forgotpassword.model.ForgotPassword;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;
import com.homesorderconsumer.util.Util;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 2/21/18.
 */

public class ForgotPasswordVM {

    Activity activity;
    ActivityForgotPasswordBinding binding;
    MyProgressDialog myProgressDialog;

    public ForgotPasswordVM(Activity activity,ActivityForgotPasswordBinding binding){
        this.activity=activity;
        this.binding=binding;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickRootView(View view){
        Util.getInstance().hideKeyboard(activity);
    }

    public void onClickBack(View view){
        activity.finish();
    }

    public void onClickReset(View view,ForgotPassword forgotPassword){
        Util.getInstance().hideKeyboard(activity);
        removeErrorMessage();
        validation(forgotPassword);
    }
    public void onEmailTextChanged(CharSequence s, int start, int before, int count, ForgotPassword
            forgotPassword) {
        if (s.toString().trim().length()!=0){
            binding.emailLayout.setError(null);
        }
    }
    private void removeErrorMessage() {
        binding.emailLayout.setError(null);
    }

    private void validation(ForgotPassword forgotPassword) {
        if (forgotPassword.getEmail().trim().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(forgotPassword.getEmail().trim()).matches()) {
            binding.emailLayout.setError(activity.getString(R.string.valid_email_address_error));
        } else {
            forgotPasswordAPICall(forgotPassword);
        }
    }

    private void forgotPasswordAPICall(ForgotPassword forgotPassword){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.forgotPassword(forgotPassword);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                        } else {
                            if (responses.body() != null) {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
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
