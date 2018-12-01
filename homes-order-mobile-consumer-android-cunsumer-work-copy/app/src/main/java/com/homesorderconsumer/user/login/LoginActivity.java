package com.homesorderconsumer.user.login;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivityLoginBinding;
import com.homesorderconsumer.user.login.model.Login;
import com.homesorderconsumer.user.login.viewmodel.LoginVM;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.StatusBarUtil;
import com.homesorderconsumer.util.Util;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    LoginVM loginVM;

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
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginVM=new LoginVM(this,binding);
        binding.setLoginVM(loginVM);
        binding.setLogin(new Login());
    }
}


