package com.homesorderconsumer.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.homesorderconsumer.MyApp;
import com.homesorderconsumer.product.ProductDetailActivity;
import com.homesorderconsumer.product.model.SelectedDeliverySlot;
import com.homesorderconsumer.sharedpreferences.MySession;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mac on 2/1/18.
 */

public class Util {
    private static final Util ourInstance = new Util();

    public static Util getInstance() {
        return ourInstance;
    }

    private Util() {
    }

    public SelectedDeliverySlot getSelectedDeliverySlot(Activity activity){
        SelectedDeliverySlot selectedDeliverySlot=new SelectedDeliverySlot();
        if (activity instanceof ProductDetailActivity){
            ProductDetailActivity productDetailActivity=(ProductDetailActivity)activity;
            selectedDeliverySlot=productDetailActivity.selectedDeliverySlot;
        }
        return selectedDeliverySlot;
    }

    public void setTransparentBackground(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
           // window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                   WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.getDecorView().setSystemUiVisibility(0);
            }
        }

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            window.setStatusBarColor(getResources()
                    .getColor(R.color.themeToolbarColor));
        }*/
    }

    public void changeStatusBarColor(Activity activity,int color,boolean lightStatus){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
            if (lightStatus){
                window.getDecorView().setSystemUiVisibility(0);
            }else{
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    public Configuration setLanguage(){
        Locale locale = new Locale(MySession.getInstance(MyApp.getContext()).getLanguageKey());
        Resources resources = MyApp.getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        // 参考 https://developer.android.com/reference/android/content/res/Configuration.html

        /* if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
            resources.updateConfiguration(config, dm);
        }*/

        config.locale = locale;
        resources.updateConfiguration(config, dm);
        return config;

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Configuration configuration = activity.getBaseContext().getResources().getConfiguration();
            LocaleList localeList = new LocaleList(locale);
            localeList.setDefault(localeList);
            configuration.setLocales(localeList);
            activity.getBaseContext().createConfigurationContext(configuration);
            return configuration;
        }else {
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
            return config;
       }*/
    }

    public static Locale getLanguageType(Context context) {
        /*Log.i("=======", "context = " + context);
//        Resources resources = context.getResources();
        Resources resources = MyApp.getContext().getResources();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return config.getLocales().get(0);
        } else {
            return config.locale;
        }*/

        return new Locale(MySession.getInstance(MyApp.getContext()).getLanguageKey());

    }

    public int getScreenWidth(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return  metrics.widthPixels;
    }

    public void hideKeyboard(Activity activity){
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void openBrowser(Activity activity,String url){
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);

    }

    public boolean isTablet(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=7){
            return true;
        }else{
            return false;
        }
    }



    public boolean passwordValidator(String password)
    {
        final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=" +
                ".*?[#?!@$%^&*-_]).{8,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
