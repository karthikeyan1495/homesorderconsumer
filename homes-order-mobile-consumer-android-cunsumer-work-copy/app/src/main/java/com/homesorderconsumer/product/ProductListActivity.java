package com.homesorderconsumer.product;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.homesorderconsumer.R;
import com.homesorderconsumer.category.model.Child;
import com.homesorderconsumer.databinding.ActivityProductListBinding;
import com.homesorderconsumer.home.viewmodel.CartAndSearchVM;
import com.homesorderconsumer.product.adapter.ProductListAdapter;
import com.homesorderconsumer.product.viewmodel.ProductListVM;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.util.EndlessRecyclerOnScrollListener;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.Util;

import java.util.Locale;
import java.util.Observer;

public class ProductListActivity extends AppCompatActivity implements Observer {

    ActivityProductListBinding binding;
    ProductListVM productListVM;
    ProductListAdapter productListAdapter;
    LinearLayoutManager linearLayoutManager;

    Child child=new Child();

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundleData();
        bindView();
        setUpObserver(productListVM);
        setupProductRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.setCartCount(String.valueOf(MySession.getInstance(this).getCartCount()));
    }

    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof ProductListVM) {
            if (productListAdapter!=null){
                boolean isClear=(boolean)object;
                if (isClear){
                    setupProductRecyclerView();
                }
                productListAdapter.setData(productListVM.getProducts(),isClear);
            }
        }
    }

    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }
    private void getBundleData(){
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            child=(Child)bundle.getSerializable("child");
        }
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_product_list);
        productListVM=new ProductListVM(this,child);
        binding.setProductListVM(productListVM);
        binding.setChild(child);
        binding.setCartAndSearchVM(new CartAndSearchVM(this));
        setSupportActionBar(binding.toolbar);
    }

    private void setupProductRecyclerView(){
        linearLayoutManager=new LinearLayoutManager(this);
        productListAdapter=new ProductListAdapter(this);
        binding.productRecyclerView.setLayoutManager(linearLayoutManager);
        binding.productRecyclerView.setAdapter(productListAdapter);
        binding.productRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                productListVM.paginationAPICall(current_page,false);
            }
        });
    }
}
