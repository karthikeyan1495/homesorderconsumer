package com.homesorderconsumer.user.myaccount;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivityMyAccountBinding;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.myaccount.viewmodel.MyAccountVM;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.Util;

import java.util.Locale;

public class MyAccountActivity extends AppCompatActivity {

    ActivityMyAccountBinding binding;
    MyAccountVM myAccountVM;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_account);
        myAccountVM=new MyAccountVM(this);
        binding.setMyAccountVM(myAccountVM);
        if (MySession.getInstance(this).getLanguageKey().equals(getString(R.string.ar))){
            binding.setIsAr(true);
        }else {
            binding.setIsAr(false);
        }
        setSupportActionBar(binding.toolbar);
    }
}
