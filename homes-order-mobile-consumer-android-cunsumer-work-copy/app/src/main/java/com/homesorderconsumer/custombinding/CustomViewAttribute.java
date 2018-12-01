package com.homesorderconsumer.custombinding;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by innoppl on 23/03/18.
 */

public class CustomViewAttribute {

    @BindingAdapter({"bind:marginBottom"})
    public static void setBottomMargin(View view, float bottomMargin) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin,
                layoutParams.rightMargin, Math.round(bottomMargin));
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter({"bind:height"})
    public static void setHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();
        layoutParams.height=height;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter({"bind:width"})
    public static void setWidth(View view, int width) {
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();
        layoutParams.width=width;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter({"bind:statusColorLine"})
    public static void setStatusColorLine(View view, String status) {
        if (status!=null){
            if (status.trim().equals("0")){
                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }else if(status.trim().equals("1")){
                view.setBackgroundColor(Color.parseColor("#f8e71c"));
            }else if(status.trim().equals("2")){
                view.setBackgroundColor(Color.parseColor("#f8e71c"));
            }else if(status.trim().equals("3")){
                view.setBackgroundColor(Color.parseColor("#7ed321"));
            }else if(status.trim().equals("4")){
                view.setBackgroundColor(Color.parseColor("#F2253E"));
            }
        }else {
            view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }


}
