package com.homesorderconsumer.user.registration.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.databinding.ActivityRegistrationBinding;
import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.login.model.User;
import com.homesorderconsumer.user.registration.model.Register;
import com.homesorderconsumer.user.staticcontent.StaticContentActivity;
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

public class RegisterVM {

    Activity activity;
    ActivityRegistrationBinding binding;

    MyProgressDialog myProgressDialog;

    public RegisterVM(Activity activity,ActivityRegistrationBinding binding){
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

    public void onClickRegister(View view,Register register){
        Util.getInstance().hideKeyboard(activity);
        removeErrorMessage();
        validation(register);

    }

    public void onClickTermsAndCondition(View view){
        Intent intent=new Intent(activity, StaticContentActivity.class);
        intent.putExtra("title",activity.getString(R.string.terms_and_condtions));
        if (MySession.getInstance(activity).getLanguageKey().equals(activity.getString(R.string
                .ar))) {
            intent.putExtra("url",activity.getString(R.string.terms_and_condition_url_ar));
        }else{
            intent.putExtra("url",activity.getString(R.string.terms_and_condition_url_en));
        }
        activity.startActivity(intent);
    }

    public void onFirstNameTextChanged(CharSequence s, int start, int before, int count, Register
            register) {
        if (s.toString().trim().length()!=0){
            binding.firstNameLayout.setError(null);
        }
    }

    public void onLastNameTextChanged(CharSequence s, int start, int before, int count, Register
            register) {
        if (s.toString().trim().length()!=0){
            binding.lastNameLayout.setError(null);
        }
    }


    public void onEmailTextChanged(CharSequence s, int start, int before, int count, Register register) {
        if (s.toString().trim().length()!=0){
            binding.emailLayout.setError(null);
        }
    }

    public void onPasswordTextChanged(CharSequence s, int start, int before, int count, Register register) {
        if (s.toString().trim().length()!=0){
            binding.passwordLayout.setError(null);
        }
    }

    public void onConfirmPasswordTextChanged(CharSequence s, int start, int before, int count, Register register) {
        if (s.toString().trim().length()!=0){
            binding.retypePasswordLayout.setError(null);
        }
    }




    private void validation(Register register){
        if (register.getFirstname().trim().length() <3 ) {
            binding.firstNameLayout.setError(activity.getString(R.string.valid_first_name));
        } else if (register.getLastname().trim().length() <3 ) {
            binding.lastNameLayout.setError(activity.getString(R.string.valid_last_name));
        } else if (register.getEmail().trim().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(register.getEmail().trim()).matches()) {
            binding.emailLayout.setError(activity.getString(R.string.valid_email_address_error));
        } else if(! Util.getInstance().passwordValidator(register.getPassword())) {
            binding.passwordLayout.setError(activity.getString(R.string.invalid_password_error));
        } else if(!register.getPassword().equals(register.getConfirm_password())){
            binding.retypePasswordLayout.setError(activity.getString(R.string.passwords_match_error));
        } else if(!binding.termsAndConditionCheckbox.isChecked()){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.terms_and_condition_accept_error));
        } else {
            registerAPICall(register);
        }
    }

    private void removeErrorMessage() {
        binding.firstNameLayout.setError(null);
        binding.lastNameLayout.setError(null);
        binding.emailLayout.setError(null);
        binding.passwordLayout.setError(null);
        binding.retypePasswordLayout.setError(null);
    }

    private void registerAPICall(Register register) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            register.setDevice_type("0");// 0 Means Android
            register.setDevice_token(FirebaseInstanceId.getInstance().getToken());
            Observable<Response<User>> observable = api.register(register);
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
