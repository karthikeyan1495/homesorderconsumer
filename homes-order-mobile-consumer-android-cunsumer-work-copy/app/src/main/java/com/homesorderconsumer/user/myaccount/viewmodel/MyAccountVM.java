package com.homesorderconsumer.user.myaccount.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.initialscreen.languageselection.LanguageSelectionActivity;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.myaccount.profile.ProfileActivity;
import com.homesorderconsumer.user.resetpassword.ResetPasswordActivity;

/**
 * Created by innoppl on 29/03/18.
 */

public class MyAccountVM {

    Activity activity;
    public ObservableField<String> currency = new ObservableField<>("");

    public MyAccountVM(@NonNull Activity activity){
        this.activity=activity;
        currency.set(MySession.getInstance(activity).getCurrency());

    }

    public void onClickBack(View view){
        activity.finish();
    }

    public void onClickChangeProfileInfo(View view){
        activity.startActivity(new Intent(activity, ProfileActivity.class));
    }

    public void onClickChangeLanguage(View view){
        Intent intent=new Intent(activity, LanguageSelectionActivity.class);
        intent.putExtra("changelanguagevia", LanguageSelectionActivity.ChangeLanguageVia.PROFILE);
        activity.startActivity(intent);
    }
    public void onClickChangePassword(View view){
        activity.startActivity(new Intent(activity, ResetPasswordActivity.class));
    }
    public void onClickCurrency(View view) {
        currencyPicker();
    }

    private void currencyPicker() {
        final CharSequence[] currencyName = activity.getResources().getStringArray(R.array
                .currency_array);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(currencyName, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
                MySession.getInstance(activity).saveCurrency(String.valueOf(currencyName[item]));
                currency.set(String.valueOf(currencyName[item]));

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
