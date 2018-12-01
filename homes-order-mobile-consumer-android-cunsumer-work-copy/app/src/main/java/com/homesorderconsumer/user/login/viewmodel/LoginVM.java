package com.homesorderconsumer.user.login.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.databinding.ActivityLoginBinding;
import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.forgotpassword.ForgotPasswordActivity;
import com.homesorderconsumer.user.login.model.Login;
import com.homesorderconsumer.user.login.model.User;
import com.homesorderconsumer.user.registration.RegistrationActivity;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;
import com.homesorderconsumer.util.Util;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 21/03/18.
 */

public class LoginVM {

    Activity activity;
    MyProgressDialog myProgressDialog;
    ActivityLoginBinding binding;

    public LoginVM(@NonNull Activity activity, ActivityLoginBinding binding) {
        this.activity = activity;
        myProgressDialog = new MyProgressDialog();
        this.binding = binding;
    }

    public void onClickRootView(View view) {
        Util.getInstance().hideKeyboard(activity);
    }

    public void onClickBack(View view) {
        activity.finish();
    }

    public void onClickLogin(View view,Login login) {
        Util.getInstance().hideKeyboard(activity);
        removeErrorMessage();
        validation(login);
    }

    public void onClickSignup(View view) {
        activity.startActivity(new Intent(activity, RegistrationActivity.class));
    }

    public void onClickForgotPassword(View view) {
        activity.startActivity(new Intent(activity, ForgotPasswordActivity.class));
    }

    public void onEmailTextChanged(CharSequence s, int start, int before, int count, Login login) {
        if (s.toString().trim().length() != 0) {
            binding.signInUsernameLayout.setError(null);
        }
    }

    public void onPasswordTextChanged(CharSequence s, int start, int before, int count, Login
            login) {
        if (s.toString().trim().length() != 0) {
            binding.signInPasswordLayout.setError(null);
        }
    }

    private void validation(Login login) {
        if (login.getUsername().trim().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(login.getUsername().trim()).matches()) {
            binding.signInUsernameLayout.setError(activity.getString(R.string.valid_email_address_error));
        } else if (login.getPassword().length() == 0) {
            binding.signInPasswordLayout.setError(activity.getString(R.string.please_enter_valid_password));
        } else {
            loginAPICall(login);
        }
    }

    private void removeErrorMessage() {
        binding.signInUsernameLayout.setError(null);
        binding.signInPasswordLayout.setError(null);
    }

    private void loginAPICall(Login login) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            login.setDevice_type("0");// 0 Means Android
            login.setDevice_token(FirebaseInstanceId.getInstance().getToken());
            Observable<Response<User>> observable = api.login(login);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            MySession.getInstance(activity).saveLoginStatus(true);
                            MySession.getInstance(activity).saveUser(responses.body());
                            MySession.getInstance(activity).saveCartCount(0);
                            MySession.getInstance(activity).saveGuestCartID("");
                            activity.startActivity(new Intent(activity, MainActivity.class));
                            activity.finishAffinity();
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
