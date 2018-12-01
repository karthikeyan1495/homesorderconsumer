package com.homesorderconsumer.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.Window;
import android.widget.ProgressBar;

import com.homesorderconsumer.R;


/**
 * Created by mac on 2/21/18.
 */

public class MyProgressDialog {

    private ProgressDialog progress;
    private String TAG = MyProgressDialog.this.getClass().getSimpleName();

    public void dismissDialog() {
        try {
            if ((progress != null) && progress.isShowing())
                progress.dismiss();
            progress = null;
        } catch (Exception e) {
        }
    }

    public ProgressDialog getProgress() {
        return progress;
    }

    public void showDialog(Context context) {
        try {

            if ((progress != null) && progress.isShowing()) {
                return;
            }
            progress = null;
            progress = new ProgressDialog(context);
            progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progress.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);

            if (!((Activity) context).isFinishing()) {
                progress.show();
            }

            ProgressBar spinner = new ProgressBar(context);
            spinner.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            progress.setContentView(spinner);
            // progress.setMessage("Loading...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            // progress.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } catch (Exception e) {
        }
    }
}

