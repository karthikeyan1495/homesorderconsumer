package com.homesorderconsumer.user.myaccount.profile;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivityProfileBinding;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.login.model.User;
import com.homesorderconsumer.user.myaccount.profile.model.ProfileUpdate;
import com.homesorderconsumer.user.myaccount.profile.viewmodel.ProfileVM;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.Util;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    ProfileVM profileVM;

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
        binding= DataBindingUtil.setContentView(this,R.layout.activity_profile);
        profileVM=new ProfileVM(this);
        binding.setProfileVM(profileVM);
        binding.setProfileUpdate(getProfileUpdate());
    }

    private ProfileUpdate getProfileUpdate(){
        ProfileUpdate profileUpdate=new ProfileUpdate();
        User user= MySession.getInstance(this).getUser();
        profileUpdate.setEmail(user.getProfile().getEmail());
        profileUpdate.setFirstname(user.getProfile().getUserdetails().getFirstname());
        profileUpdate.setLastname(user.getProfile().getUserdetails().getLastname());
        return profileUpdate;
    }
}
