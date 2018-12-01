package com.homesorderconsumer.product;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivityProductDetailBinding;
import com.homesorderconsumer.home.viewmodel.CartAndSearchVM;
import com.homesorderconsumer.product.adapter.ImagePreviewAdapter;
import com.homesorderconsumer.product.model.ProductDataCollection;
import com.homesorderconsumer.product.model.ProductDetail;
import com.homesorderconsumer.product.model.Products;
import com.homesorderconsumer.product.model.SelectedDeliverySlot;
import com.homesorderconsumer.product.viewmodel.ProductDetailVM;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.ReaderViewPagerTransformer;
import com.homesorderconsumer.util.StatusBarUtil;
import com.homesorderconsumer.util.Util;

import java.util.List;
import java.util.Locale;
import java.util.Observer;

public class ProductDetailActivity extends AppCompatActivity implements Observer {

    ActivityProductDetailBinding binding;
    ProductDetailVM productDetailVM;
    Products products=new Products();

    public SelectedDeliverySlot selectedDeliverySlot=new SelectedDeliverySlot();

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setLightMode(this);
        getBundleData();
        bindView();
        setUpObserver(productDetailVM);
    }
    @Override
    protected void onResume() {
        super.onResume();
        binding.setCartCount(String.valueOf(MySession.getInstance(this).getCartCount()));
    }
    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof ProductDetailVM) {
            binding.setProductDetail(productDetailVM.getProductDetail());
            setProductDataCollectionObject(productDetailVM.getProductDetail());
            setImageViewPager(productDetailVM.getProductDetail().getMedia_gallery());
        }
    }

    private void getBundleData(){
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            products=(Products)bundle.getSerializable("products");
        }
    }

    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_product_detail);
        productDetailVM=new ProductDetailVM(this,products,binding);
        binding.setProductDetailVM(productDetailVM);
        binding.setCartAndSearchVM(new CartAndSearchVM(this));
        selectedDeliverySlot.setShowText(getString(R.string.select_delivery_slot));
        binding.setSelectedDeliverySlot(selectedDeliverySlot);
        setSupportActionBar(binding.toolbar);

    }

    private void setImageViewPager(List<String> list) {
        binding.imageViewPager.setAdapter(new ImagePreviewAdapter(this,list));
        binding.imageViewPager.setPageTransformer(true, new ReaderViewPagerTransformer
                (ReaderViewPagerTransformer.TransformType.DEPTH));
        binding.sliderIndicator.setViewPager(binding.imageViewPager);
    }

    private void setProductDataCollectionObject(ProductDetail productDetail){
        ProductDataCollection productDataCollection=new ProductDataCollection();
        productDataCollection.setQuantity("1");
        if (productDetail.getOption()!=null){
            if (productDetail.getOption().getColor()!=null&&productDetail.getOption().getColor()
                    .size()!=0){
                productDataCollection.setColorName(productDetail.getOption().getColor().get(0).getName());
                productDataCollection.setColorValue(productDetail.getOption().getColor().get(0).getValue());
            }
            if (productDetail.getOption().getSize()!=null&&productDetail.getOption().getSize()
                    .size()!=0){
                productDataCollection.setSizeName(productDetail.getOption().getSize().get(0).getName());
                productDataCollection.setSizeValue(productDetail.getOption().getSize().get(0).getValue());
            }
            if (productDetail.getOption().getWeight()!=null&&productDetail.getOption().getWeight()
                    .size()!=0){
                productDataCollection.setWeightName(productDetail.getOption().getWeight().get(0).getName
                        ());
                productDataCollection.setWeightValue(productDetail.getOption().getWeight().get(0).getValue
                        ());
            }
        }
        binding.setProductDataCollection(productDataCollection);
    }
}
