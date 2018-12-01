package com.homesorderconsumer.home.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.category.CategoryActivity;
import com.homesorderconsumer.category.model.Categories;
import com.homesorderconsumer.category.model.CategoryResponse;
import com.homesorderconsumer.user.preference.PreferenceActivity;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 24/03/18.
 */

public class HomeVM extends java.util.Observable{

    Activity activity;

    MyProgressDialog myProgressDialog;

    Categories categories=new Categories();

    public HomeVM(@NonNull Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
        categoryAPICall();
    }

    public void onClickFood(View view){
        activity.startActivity(new Intent(activity, CategoryActivity.class));
    }
    public void onClickFashion(View view){
        activity.startActivity(new Intent(activity, CategoryActivity.class));
    }
    public void onClickSelectedArea(View view){
        activity.startActivity(new Intent(activity, PreferenceActivity.class));
    }

    public Categories getCategories(){
        return categories;
    }

    private void categoryAPICall() {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<CategoryResponse>> observable = api.category();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            categories=responses.body().getCategories();
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
