package com.homesorderconsumer.product.viewmodel;

import android.app.Activity;
import android.view.View;

import com.homesorderconsumer.product.SortByEnum;
import com.homesorderconsumer.product.SortingOptionFragment;

/**
 * Created by innoppl on 27/03/18.
 */

public class SortingOptionVM {
    Activity activity;
    SortingOptionFragment sortingOptionFragment;
    SortingOptionFragment.SortDialogListener sortDialogListener;
    public SortingOptionVM(Activity activity,SortingOptionFragment sortingOptionFragment,SortingOptionFragment.SortDialogListener sortDialogListener){
        this.activity=activity;
        this.sortingOptionFragment=sortingOptionFragment;
        this.sortDialogListener=sortDialogListener;
    }
    public void onClickClose(View view){
        sortingOptionFragment.dismiss();
    }
    public void onClickSortOption(View view){
        sortingOptionFragment.dismiss();
        if (sortDialogListener!=null){
            String tag=view.getTag().toString();
            if (tag.equals("1")){
                sortDialogListener.sortOption(SortByEnum.MOST_RECENT);
            }else if(tag.equals("2")) {
                sortDialogListener.sortOption(SortByEnum.PRICE_LOW_HIGH);
            }else if(tag.equals("3")) {
                sortDialogListener.sortOption(SortByEnum.PRICE_HIGH_LOW);
            }
        }

    }
}
