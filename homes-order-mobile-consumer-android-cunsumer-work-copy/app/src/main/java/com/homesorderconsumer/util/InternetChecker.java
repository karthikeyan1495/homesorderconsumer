package com.homesorderconsumer.util;

import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mac on 2/1/18.
 */

public class InternetChecker {

    private boolean isReachable=false;

    private static final InternetChecker ourInstance = new InternetChecker();

    public static InternetChecker getInstance() {
        return ourInstance;
    }

    private InternetChecker() {
    }

    public void init(){
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responses -> {
                    Log.d("Internet",""+responses);
                    isReachable=responses;
                });
    }

    public boolean isReachable(){
        return isReachable;
    }
}
