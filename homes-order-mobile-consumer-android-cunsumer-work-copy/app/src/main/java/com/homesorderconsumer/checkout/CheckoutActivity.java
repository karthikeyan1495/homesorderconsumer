package com.homesorderconsumer.checkout;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.homesorderconsumer.R;
import com.homesorderconsumer.checkout.adapter.PaymentOptionAdapter;
import com.homesorderconsumer.checkout.model.CheckoutUI;
import com.homesorderconsumer.checkout.model.DeliveryAddress;
import com.homesorderconsumer.checkout.model.PaymentMethodItem;
import com.homesorderconsumer.checkout.viewmodel.CheckoutVM;
import com.homesorderconsumer.databinding.ActivityCheckoutBinding;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.cart.model.CartPrice;
import com.homesorderconsumer.user.login.model.User;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.StatusBarUtil;
import com.homesorderconsumer.util.StringUtil;
import com.homesorderconsumer.util.Util;

import java.util.List;
import java.util.Locale;
import java.util.Observer;

public class CheckoutActivity extends AppCompatActivity implements Observer {

    ActivityCheckoutBinding binding;
    CheckoutVM checkoutVM;
    public PaymentOptionAdapter paymentOptionAdapter;
    DeliveryAddress deliveryAddress;
    CartPrice cartPrice=new CartPrice();

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData();
        StatusBarUtil.setLightMode(this);
        bindView();
        setUpObserver(checkoutVM);
    }
    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof CheckoutVM) {
            setupProductReviewRecyclerView(checkoutVM.getPaymentMethodItems());
        }
    }
    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }


    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_checkout);
        deliveryAddress=getDeliveryAddress();
        checkoutVM=new CheckoutVM(this,deliveryAddress,cartPrice);
        binding.setCheckoutVM(checkoutVM);
        binding.setDeliveryAddress(deliveryAddress);
        binding.setCheckoutUI(new CheckoutUI());
    }

    private void setupProductReviewRecyclerView(List<PaymentMethodItem> list){
        paymentOptionAdapter=new PaymentOptionAdapter(this,list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(paymentOptionAdapter);
    }
    private DeliveryAddress getDeliveryAddress(){
        DeliveryAddress address=new DeliveryAddress();
        if (MySession.getInstance(this).isLogin()){
            User user=MySession.getInstance(this).getUser();
            address.setFirstname(user.getProfile().getUserdetails().getFirstname());
            address.setLastname(user.getProfile().getUserdetails().getLastname());
            address.setEmail(user.getProfile().getEmail());
        }
        address.setRegion(StringUtil.selectedAreaName());
        address.setCity(StringUtil.selectedStateNameLanguageBased());
        address.setCountry_name(StringUtil.selectedCountryName());
        address.setCountry_id(StringUtil.selectedCountryID());
        return address;
    }

    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            cartPrice=(CartPrice)bundle.getSerializable("cartPrice");
        }
    }
}
