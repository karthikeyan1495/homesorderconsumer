package com.homesorderconsumer.font;

import android.databinding.BindingAdapter;
import android.widget.TextView;

/**
 * Created by mac on 11/10/17.
 */

public class FontBinding {

    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(CustomFontFamily.getInstance().getFont(fontName));
    }
}
