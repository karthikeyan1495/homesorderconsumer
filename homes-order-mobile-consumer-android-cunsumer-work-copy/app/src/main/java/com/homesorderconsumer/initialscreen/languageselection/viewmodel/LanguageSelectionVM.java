package com.homesorderconsumer.initialscreen.languageselection.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.initialscreen.languageselection.LanguageSelectionActivity;
import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.preference.PreferenceActivity;
import com.homesorderconsumer.util.Util;

/**
 * Created by innoppl on 21/03/18.
 */

public class LanguageSelectionVM {

    Activity activity;
    LanguageSelectionActivity.ChangeLanguageVia changeLanguageVia;

    public LanguageSelectionVM(@NonNull Activity activity,LanguageSelectionActivity.ChangeLanguageVia changeLanguageVia){
        this.activity=activity;
        this.changeLanguageVia=changeLanguageVia;
    }

    public void onClickEnglishLanguage(View view){
        changeLanguage(activity.getString(R.string.en));
    }

    public void onClickArabicLanguage(View view){
        changeLanguage(activity.getString(R.string.ar));
    }


    private void changeLanguage(String key){
        if (LanguageSelectionActivity.ChangeLanguageVia.INITIAL==changeLanguageVia){
            MySession.getInstance(activity).saveLanguageKey(key);
            Util.getInstance().setLanguage();
            MySession.getInstance(activity).saveAppFirstTimeLoad(false);
            activity.startActivity(new Intent(activity, PreferenceActivity.class));
             activity.finish();
            //activity.startActivity(new Intent(activity, SplashActivity.class));
            //quit();

        }else if(LanguageSelectionActivity.ChangeLanguageVia.PROFILE==changeLanguageVia){
            if (MySession.getInstance(activity).getLanguageKey().equals(key)){
                activity.finish();
            }else {
                MySession.getInstance(activity).saveLanguageKey(key);
                Util.getInstance().setLanguage();
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finishAffinity();
                //activity.startActivity(new Intent(activity, SplashActivity.class));
                //quit();
            }
        }
    }

    public void quit() {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);
    }
}
