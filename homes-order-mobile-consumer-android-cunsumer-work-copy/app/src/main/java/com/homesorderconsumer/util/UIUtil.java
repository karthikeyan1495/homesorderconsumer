package com.homesorderconsumer.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;

import com.homesorderconsumer.MyApp;

/**
 * Created by innoppl on 23/03/18.
 */

public class UIUtil {

    public static boolean hasNavBar()
    {
        int id = MyApp.getContext().getResources().getIdentifier("config_showNavigationBar",
            "bool",
                "android");
        return id > 0 && MyApp.getContext().getResources().getBoolean(id);

        /*boolean hasMenuKey = ViewConfiguration.get(MyApp.getContext()).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if(!hasMenuKey && !hasBackKey) {
            return true;
        }else {
            return false;
        }*/
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = MyApp.getContext().getResources().getIdentifier("status_bar_height",
                "dimen",
                "android");
        if (resourceId > 0) {
            result = MyApp.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getNavigationBarHeight()
    {
        boolean hasMenuKey = ViewConfiguration.get(MyApp.getContext()).hasPermanentMenuKey();
        int resourceId = MyApp.getContext().getResources().getIdentifier("navigation_bar_height",
                "dimen", "android");
        if (resourceId > 0 && !hasMenuKey)
        {
            return MyApp.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }


    public static int getTopSpace(){
        if (hasNavBar()) {
            int statusBarHight = getStatusBarHeight();
            return (int) convertDpToPixel(statusBarHight + 30);
        }else {
            return (int) convertDpToPixel(20);
        }
    }

    public static int getBottomSpace(){
        if (hasNavBar()) {
            int barHeight = getNavigationBarHeight();
            //return (int) convertDpToPixel(barHeight + 20);
            return barHeight+20;
        }else {
            return (int) convertDpToPixel(50);
        }
    }

    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
    public static float convertPixelsToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static int homeScreenCellHeight(){
        DisplayMetrics displayMetrics = MyApp.getContext().getResources().getDisplayMetrics();
       // int width = displayMetrics.widthPixels;
        float height = displayMetrics.heightPixels;
        height=height-(convertDpToPixel(getStatusBarHeight()+80));
        return (int)height/3;
    }

    public static int screenWidth(){
        DisplayMetrics displayMetrics = MyApp.getContext().getResources().getDisplayMetrics();
        return  displayMetrics.widthPixels;
    }
}
