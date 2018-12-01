package com.homesorderconsumer.user.myaccount.profile.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.api.GeneralResponse;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.login.model.User;
import com.homesorderconsumer.user.myaccount.profile.model.ProfileUpdate;
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

public class ProfileVM {

    Activity activity;
    MyProgressDialog myProgressDialog;

    public ProfileVM(@NonNull Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickBack(View view){
        activity.finish();
    }

    public void onClickSave(View view, ProfileUpdate profileUpdate){
        validation(profileUpdate);
    }

    private void validation(ProfileUpdate profileUpdate){
        if (profileUpdate.getFirstname().trim().length() <4 ) {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.valid_first_name));
        } else if (profileUpdate.getLastname().trim().length() <4 ) {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.valid_last_name));
        }else {
            profileUpdateAPICall(profileUpdate);
        }
    }

    private void profileUpdateAPICall(ProfileUpdate profileUpdate) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable = api.profileUpdate(MySession
                    .getInstance(activity).getUser().getToken(),profileUpdate);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.body().getMessage());
                            User user=MySession.getInstance(activity).getUser();
                            user.getProfile().getUserdetails().setFirstname(profileUpdate.getFirstname());
                            user.getProfile().getUserdetails().setLastname(profileUpdate.getLastname());
                            MySession.getInstance(activity).saveUser(user);
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
