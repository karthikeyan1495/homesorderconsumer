package com.homesorderconsumer.font;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/**
 * Created by mac on 11/10/17.
 */

public class CustomTypefaceSpan extends TypefaceSpan {

    private final Typeface mTypeface;

    public CustomTypefaceSpan(String family, Typeface typeface) {
        super(family);
        mTypeface = typeface;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        applyCustomTypeFace(textPaint, mTypeface);
    }

    @Override
    public void updateMeasureState(TextPaint textPaint) {
        applyCustomTypeFace(textPaint, mTypeface);
    }

    private static void applyCustomTypeFace(Paint paint, Typeface typeface) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~typeface.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(typeface);
    }
}