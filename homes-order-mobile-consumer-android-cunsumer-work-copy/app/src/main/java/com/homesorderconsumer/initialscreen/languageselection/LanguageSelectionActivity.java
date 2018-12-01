package com.homesorderconsumer.initialscreen.languageselection;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivityLanguageSelectionBinding;
import com.homesorderconsumer.initialscreen.languageselection.viewmodel.LanguageSelectionVM;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.StatusBarUtil;
import com.homesorderconsumer.util.Util;

import java.util.Locale;

public class LanguageSelectionActivity extends AppCompatActivity {

    ActivityLanguageSelectionBinding binding;
    LanguageSelectionVM languageSelectionVM;
    ChangeLanguageVia changeLanguageVia=ChangeLanguageVia.PROFILE;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        getBundleData();
        bindView();
    }

    private void getBundleData(){
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            changeLanguageVia=(ChangeLanguageVia)bundle.getSerializable("changelanguagevia");
        }
    }
    private void bindView(){
     binding= DataBindingUtil.setContentView(this,R.layout.activity_language_selection);
     languageSelectionVM=new LanguageSelectionVM(this,changeLanguageVia);
     binding.setLanguageSelectionVM(languageSelectionVM);
    }

    public enum ChangeLanguageVia{
        PROFILE("profile"),
        INITIAL("initial");
        private String value;
        public String getValue() {
            return value;
        }
        private ChangeLanguageVia(String value) {
            this.value = value;
        }
    }
}
