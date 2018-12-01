package com.homesorderconsumer.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import com.homesorderconsumer.MyApp;
import com.homesorderconsumer.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mac on 11/10/17.
 */

public class CustomFontFamily {

    static CustomFontFamily customFontFamily;
    HashMap<String, String> fontMap = new HashMap<>();
    HashMap<String, Typeface> fontCache = new HashMap<>();

    public static CustomFontFamily getInstance() {
        if (customFontFamily == null)
            customFontFamily = new CustomFontFamily();
        return customFontFamily;
    }

    private void addFont(String alias, String fontName) {
        fontMap.put(alias, fontName);
    }

    public Typeface getFont(String alias) {
        String fontFilename = fontMap.get(alias);
        if (fontFilename == null) {
            Log.e("", "Font not available with name " + alias);
            return null;
        }
        if (fontCache.containsKey(alias))
            return fontCache.get(alias);
        else {
            Typeface typeface = Typeface.createFromAsset(MyApp.getContext().getAssets(), "fonts/" + fontFilename);
            fontCache.put(fontFilename, typeface);
            return typeface;
        }
    }

    public void addAllFont(Context context)
    {
        List<String> fileNames = Arrays.asList(context.getResources().getStringArray(R.array.font_file_name));
        List<String> aliesNames = Arrays.asList(context.getResources().getStringArray(R.array.font_alies_name));
        if (fileNames.size()==aliesNames.size()) {
            for (int i = 0; i < fileNames.size();i++)
                addFont(aliesNames.get(i),fileNames.get(i));
        }
    }
}

