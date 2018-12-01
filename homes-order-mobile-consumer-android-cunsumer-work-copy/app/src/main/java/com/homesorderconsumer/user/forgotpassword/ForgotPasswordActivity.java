package com.homesorderconsumer.user.forgotpassword;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivityForgotPasswordBinding;
import com.homesorderconsumer.user.forgotpassword.model.ForgotPassword;
import com.homesorderconsumer.user.forgotpassword.viewmodel.ForgotPasswordVM;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.StatusBarUtil;
import com.homesorderconsumer.util.Util;

import java.util.Locale;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;
    ForgotPasswordVM forgotPasswordVM;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        bindingView();
    }

    private void bindingView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_forgot_password);
        forgotPasswordVM=new ForgotPasswordVM(this,binding);
        binding.setForgotPasswordVM(forgotPasswordVM);
        binding.setForgotPassword(new ForgotPassword());
    }
}
