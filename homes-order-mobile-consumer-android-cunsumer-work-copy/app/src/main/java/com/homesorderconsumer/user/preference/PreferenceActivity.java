package com.homesorderconsumer.user.preference;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivityPreferenceBinding;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.preference.model.Country;
import com.homesorderconsumer.user.preference.model.PreferenceData;
import com.homesorderconsumer.user.preference.viewmodel.PreferenceVM;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.StatusBarUtil;
import com.homesorderconsumer.util.StringUtil;
import com.homesorderconsumer.util.Util;

import java.util.Locale;

public class PreferenceActivity extends AppCompatActivity {

    ActivityPreferenceBinding binding;
    PreferenceVM preferenceVM;
    Country country;

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
        binding= DataBindingUtil.setContentView(this,R.layout.activity_preference);
        country=MySession.getInstance(this).getPreference();
        preferenceVM=new PreferenceVM(this, country);
        binding.setPreferenceVM(preferenceVM);
        binding.setPreferenceData(getPreferenceData());
    }
    private PreferenceData getPreferenceData(){
        PreferenceData preferenceData=new PreferenceData();
        if (country!=null){
            preferenceData.setCountryName(StringUtil.getLanguageString(country.getCountryNameEN()
                    ,country.getCountryNameAR()));
            if (country.getStates()!=null&&country.getStates().size()!=0)
            {
                preferenceData.setStateName(StringUtil.getLanguageString(country
                                .getStates().get(0).getStateNameEN()
                    ,country.getStates().get(0).getStateNameAR()));
                if (country.getStates().get(0).getAreas()!=null&&country.getStates().get(0)
                        .getAreas().size()!=0){
                    preferenceData.setAreaName(StringUtil.getLanguageString(country
                                    .getStates().get(0).getAreas().get(0).getAreaNameEN()
                            ,country.getStates().get(0).getAreas().get(0).getAreaNameAR()));
                }
            }
        }
        return preferenceData;
    }
}
