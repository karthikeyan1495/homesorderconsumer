package com.homesorderconsumer.user.staticcontent.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.util.Util;

/**
 * Created by innoppl on 23/03/18.
 */

public class StaticContentVM {

    Activity activity;

    public StaticContentVM(@NonNull Activity activity){
        this.activity=activity;
    }

    public void onClickBack(View view){
        Util.getInstance().setLanguage();
        activity.finish();
    }

}
