package com.homesorderconsumer.initialscreen.splash;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivitySplashBinding;
import com.homesorderconsumer.initialscreen.languageselection.LanguageSelectionActivity;
import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.preference.PreferenceActivity;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.StatusBarUtil;
import com.homesorderconsumer.util.Util;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    ActivitySplashBinding binding;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Util.getInstance().setLanguage();
        StatusBarUtil.setTransparent(this);
        bindView();
        waitingTime();
        //slideToTop();
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splash);
    }

    private void waitingTime() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToNextScreen();
            }
        }, SPLASH_TIME_OUT);
    }

    public void slideToTop(){
        Animation bottomUp = AnimationUtils.loadAnimation(this,
                R.anim.slide_up_animation);
        bottomUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                waitingTime();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        binding.customerAppView.startAnimation(bottomUp);
        binding.customerAppView.setVisibility(View.VISIBLE);
    }

    private void moveToNextScreen() {

        if (MySession.getInstance(this).isAppFirstTimeLoad()){
            Intent intent=new Intent(this, LanguageSelectionActivity.class);
            intent.putExtra("changelanguagevia", LanguageSelectionActivity.ChangeLanguageVia.INITIAL);
            startActivity(intent);
            finish();
        }else {
            if (MySession.getInstance(this).isPreferenceStatus())
            {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }else {
                startActivity(new Intent(SplashActivity.this, PreferenceActivity.class));
                finish();
            }
        }



        /*if (MySession.getInstance(this).isLogin()) {
            if (StringUtil.accountComplitionStatus(MySession.getInstance(this).getUser())) {
                Intent i = new Intent(SplashActivity.this, NavigationActivity.class);
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(SplashActivity.this, AdditionalInformationActivity.class);
                startActivity(i);
                finish();
            }
        } else {
            if (MySession.getInstance(this).isAppFirstTimeLoad()){
                startActivity(new Intent(SplashActivity.this, LanguageSelectionActivity.class));
                finish();
            }else {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }*/
    }
}
