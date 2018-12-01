package com.homesorderconsumer.user.resetpassword;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivityResetPasswordBinding;
import com.homesorderconsumer.user.resetpassword.model.ResetPassword;
import com.homesorderconsumer.user.resetpassword.viewmodel.ResetPasswordVM;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.StatusBarUtil;
import com.homesorderconsumer.util.Util;

import java.util.Locale;


public class ResetPasswordActivity extends AppCompatActivity {

    ActivityResetPasswordBinding binding;
    ResetPasswordVM resetPasswordVM;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        bindView();
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        resetPasswordVM=new ResetPasswordVM(this,binding);
        binding.setResetPasswordVM(resetPasswordVM);
        binding.setResetPassword(new ResetPassword());
    }
}
