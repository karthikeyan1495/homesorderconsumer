package com.homesorderconsumer.user.cart;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivityCartBinding;
import com.homesorderconsumer.home.viewmodel.CartAndSearchVM;
import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.cart.adapter.CartAdapter;
import com.homesorderconsumer.user.cart.model.CartItem;
import com.homesorderconsumer.user.cart.model.CartPrice;
import com.homesorderconsumer.user.cart.model.CartProduct;
import com.homesorderconsumer.user.cart.viewmodel.CartVM;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.Util;

import java.util.List;
import java.util.Locale;
import java.util.Observer;

public class CartActivity extends AppCompatActivity implements Observer {

    ActivityCartBinding binding;
    CartVM cartVM;

    CartAdapter cartAdapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        setUpObserver(cartVM);
       // setupProductRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.setCartCount(String.valueOf(MySession.getInstance(this).getCartCount()));
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof CartVM) {
            updateCartPrice(cartVM.getCartItems());
            setupProductRecyclerView(cartVM.getCartItems());
        }
    }
    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }
    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_cart);
        cartVM=new CartVM(this);
        binding.setCartVM(cartVM);
        binding.setCartAndSearchVM(new CartAndSearchVM(this));
        setSupportActionBar(binding.toolbar);
    }

    private void setupProductRecyclerView(List<CartItem> list){
        cartAdapter=new CartAdapter(this,list);
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRecyclerView.setAdapter(cartAdapter);
        cartAdapter.setOnCartUpdateListener(updateList -> {
            updateCartPrice(updateList);
            if (updateList.size()==0){
                cartVM.isNoData.set(true);
            }
        });
    }

    private void updateCartPrice(List<CartItem> list){
        MySession.getInstance(this).saveCartCount(list.size());
        binding.setCartCount(String.valueOf(MySession.getInstance(this).getCartCount()));
        CartPrice cartPrice=new CartPrice();
        float productTotal=0;
        float shippingTotal=0;
        for (int i=0;i<list.size();i++){
            CartProduct cartProduct=list.get(i).getProduct();
            if (cartProduct.getQty()!=null&&cartProduct.getQty().trim().length()!=0&&cartProduct
                    .getFinalPrice()!=null&&cartProduct.getFinalPrice().trim().length()!=0){
                try {
                    float price;
                    float qty=Float.valueOf(cartProduct.getQty().trim());
                    if(MySession.getInstance(this).getCurrency().equals(getString(R.string.aed))){
                        price=Float.valueOf(cartProduct.getFinalPrice().trim());
                    }else{
                        price=Float.valueOf(cartProduct.getFinalPriceInSAR().trim());
                    }
                    productTotal=productTotal+(price*qty);

                    if (MySession.getInstance(this).getCurrency().equals(getString(R.string.aed)))
                    {
                        shippingTotal=shippingTotal+Float.valueOf(cartProduct.getDeliveryCost().trim());
                    }else {
                        shippingTotal=shippingTotal+Float.valueOf(cartProduct.getDeliveryCostInSAR().trim());
                    }
                }catch (Exception e){
                }
            }
        }
        cartPrice.setProductTotal(String.valueOf(productTotal));
        cartPrice.setShippingTotal(String.valueOf(shippingTotal));
        cartPrice.setOverAllTotal(String.valueOf(productTotal+shippingTotal));
        binding.setCartPrice(cartPrice);
    }

    public interface OnCartUpdateListener{
        void onCartUpdated(List<CartItem> list);
    }
}