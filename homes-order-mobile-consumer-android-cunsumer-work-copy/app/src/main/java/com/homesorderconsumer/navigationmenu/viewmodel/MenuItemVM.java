package com.homesorderconsumer.navigationmenu.viewmodel;

import android.app.Activity;
import android.view.View;

import com.homesorderconsumer.navigationmenu.OnNavigationMenuListener;
import com.homesorderconsumer.navigationmenu.model.AppMenu;

/**
 * Created by mac on 3/1/18.
 */

public class MenuItemVM {
    Activity activity;

    OnNavigationMenuListener listener;
    public MenuItemVM(Activity activity, OnNavigationMenuListener listener){
        this.activity=activity;
        this.listener=listener;
    }

    public void onClickMenuItem(View view, AppMenu appMenu){
        if (listener!=null){
            listener.onClickNavigationItem(appMenu);
        }
    }

}
