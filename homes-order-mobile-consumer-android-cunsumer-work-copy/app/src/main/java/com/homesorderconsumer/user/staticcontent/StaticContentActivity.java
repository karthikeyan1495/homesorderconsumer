package com.homesorderconsumer.user.staticcontent;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivityStaticContentBinding;
import com.homesorderconsumer.user.staticcontent.viewmodel.StaticContentVM;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.StatusBarUtil;
import com.homesorderconsumer.util.Util;

import java.util.Locale;

public class StaticContentActivity extends AppCompatActivity {

    ActivityStaticContentBinding binding;
    String title="";
    String url="";

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setLightMode(this);
        getIntentData();
        bindView();
        loadWebview();
    }

    @Override
    public void onBackPressed() {
        Util.getInstance().setLanguage();
        super.onBackPressed();
    }

    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            title=bundle.getString("title");
            url=bundle.getString("url");
        }
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_static_content);
        binding.setStaticContentVM(new StaticContentVM(this));
        binding.setTitle(title);
    }

    private void loadWebview(){
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.postUrl(url,null);
        binding.webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}
