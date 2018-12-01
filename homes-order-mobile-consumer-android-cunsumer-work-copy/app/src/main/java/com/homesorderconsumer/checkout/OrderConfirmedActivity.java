package com.homesorderconsumer.checkout;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesorderconsumer.R;
import com.homesorderconsumer.checkout.viewmodel.OrderConfirmedVM;
import com.homesorderconsumer.databinding.ActivityOrderConfirmedBinding;
import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.StatusBarUtil;
import com.homesorderconsumer.util.Util;

import java.util.Locale;

public class OrderConfirmedActivity extends AppCompatActivity {
    ActivityOrderConfirmedBinding binding;
    OrderConfirmedVM orderConfirmedVM;
    String increment_id="";

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataIntent();
        StatusBarUtil.setLightMode(this);
        bindView();
    }

    @Override
    public void onBackPressed() {
       finishAffinity();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void getDataIntent(){
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            increment_id=bundle.getString("increment_id");
        }
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_order_confirmed);
        orderConfirmedVM=new OrderConfirmedVM(this);
        binding.setOrderConfirmedVM(orderConfirmedVM);
        binding.setOrderId(increment_id);
    }
}
