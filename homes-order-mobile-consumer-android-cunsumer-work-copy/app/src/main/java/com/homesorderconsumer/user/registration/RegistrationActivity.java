package com.homesorderconsumer.user.registration;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivityRegistrationBinding;
import com.homesorderconsumer.user.registration.model.Register;
import com.homesorderconsumer.user.registration.viewmodel.RegisterVM;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.StatusBarUtil;
import com.homesorderconsumer.util.Util;

import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {

    ActivityRegistrationBinding binding;
    RegisterVM registerVM;

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
        binding= DataBindingUtil.setContentView(this,R.layout.activity_registration);
        registerVM=new RegisterVM(this,binding);
        binding.setRegisterVM(registerVM);
        binding.setRegister(new Register());
    }

}
