package com.homesorderconsumer.user.resetpassword.viewmodel;

import android.app.Activity;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.api.GeneralResponse;
import com.homesorderconsumer.databinding.ActivityResetPasswordBinding;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.resetpassword.model.ResetPassword;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;
import com.homesorderconsumer.util.Util;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by mac on 3/8/18.
 */

public class ResetPasswordVM {
    Activity activity;
    ActivityResetPasswordBinding binding;
    MyProgressDialog myProgressDialog;
    public ResetPasswordVM(Activity activity,ActivityResetPasswordBinding binding){
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

    public void onClickSave(View view, ResetPassword resetPassword){
        Util.getInstance().hideKeyboard(activity);
        removeErrorMessage();
        validation(resetPassword);
    }

    private void removeErrorMessage() {
        binding.currentPasswordLayout.setError(null);
        binding.newPasswordLayout.setError(null);
        binding.confirmPasswordLayout.setError(null);
    }

    private void validation(ResetPassword resetPassword) {
        if (! Util.getInstance().passwordValidator(resetPassword.getCurrentPassword())) {
            binding.currentPasswordLayout.setError(activity.getString(R.string.invalid_password_error));
        }else if (! Util.getInstance().passwordValidator(resetPassword.getNewPassword())) {
            binding.newPasswordLayout.setError(activity.getString(R.string.invalid_password_error));
        }else if (!resetPassword.getNewPassword().equals(resetPassword.getConfirmpassword())) {
            binding.confirmPasswordLayout.setError(activity.getString(R.string.passwords_match_error));
        }else {
            resetPasswordAPICall(resetPassword);
        }
    }

    private void resetPasswordAPICall(ResetPassword resetPassword){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.passwordReset(MySession.getInstance(activity).getUser().getToken(),resetPassword);
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
