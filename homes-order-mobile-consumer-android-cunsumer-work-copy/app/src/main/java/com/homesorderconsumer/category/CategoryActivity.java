package com.homesorderconsumer.category;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.homesorderconsumer.R;
import com.homesorderconsumer.ads.adapter.AdsAdapter;
import com.homesorderconsumer.category.adapter.CategoryAdapter;
import com.homesorderconsumer.category.model.Adds;
import com.homesorderconsumer.category.model.Child;
import com.homesorderconsumer.category.viewmodel.CategoryVM;
import com.homesorderconsumer.databinding.ActivityCategoryBinding;
import com.homesorderconsumer.home.viewmodel.CartAndSearchVM;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.ReaderViewPagerTransformer;
import com.homesorderconsumer.util.UIUtil;
import com.homesorderconsumer.util.Util;

import java.util.List;
import java.util.Locale;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoryActivity extends AppCompatActivity implements Observer {

    ActivityCategoryBinding binding;
    CategoryVM categoryVM;
    CategoryAdapter categoryAdapter;
    AdsAdapter adsAdapter;
    Child child;

    ProductType productType=ProductType.FOOD;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        setUpObserver(categoryVM);
        getBundleData();
        //showFragment(new AdsFragment());
    }
    @Override
    protected void onResume() {
        super.onResume();
        binding.setCartCount(String.valueOf(MySession.getInstance(this).getCartCount()));
    }
    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof CategoryVM) {

            if (child==null) {

                if (productType == ProductType.FOOD) {

                    if (categoryVM.getCategories().getCategory().size() > 0) {

                        setupCategoryRecyclerView(categoryVM.getCategories()
                                .getCategory().get(0).getChild());

                        setAdsViewPager(categoryVM.getCategories()
                                .getCategory().get(0).getAdds());

                    }

                } else if (productType == ProductType.FASHION) {

                    if (categoryVM.getCategories().getCategory().size() > 1) {

                        setupCategoryRecyclerView(categoryVM.getCategories()
                                .getCategory().get(1).getChild());

                        setAdsViewPager(categoryVM.getCategories()
                                .getCategory().get(1).getAdds());

                    }
                }
            }

        }
    }

    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }


    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            child = (Child) bundle.getSerializable("child");
            productType = (ProductType) bundle.getSerializable("productType");
            if (child!=null) {
                setAdsViewPager(child.getAdds());
                setupCategoryRecyclerView(child.getChild());
            }else {
                categoryVM.categoryAPICall();
            }
        }
    }

    private void bindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        categoryVM = new CategoryVM(this);
        binding.setCategoryVM(categoryVM);
        binding.setCartAndSearchVM(new CartAndSearchVM(this));
        setSupportActionBar(binding.toolbar);
    }

    private void setupCategoryRecyclerView(List<Child> list) {
        int screenWidth = Util.getInstance().getScreenWidth(this) - (int) UIUtil.convertDpToPixel
                (25.0f);
        categoryAdapter = new CategoryAdapter(this, screenWidth / 2, list);
        binding.categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.categoryRecyclerView.setAdapter(categoryAdapter);
    }


    private void setAdsViewPager(List<Adds> list) {
        adsAdapter = new AdsAdapter(getSupportFragmentManager(),
                this, list);
        binding.adsViewPager.setAdapter(adsAdapter);
        binding.adsViewPager.setPageTransformer(true, new ReaderViewPagerTransformer
                (ReaderViewPagerTransformer.TransformType.DEPTH));
        changeAds();
    }

    private void changeAds() {
        Observable.interval(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    if (adsAdapter.getCount() == binding.adsViewPager.getCurrentItem() + 1) {
                        binding.adsViewPager.setCurrentItem(0);
                    } else {
                        binding.adsViewPager.setCurrentItem(binding.adsViewPager.getCurrentItem() + 1);
                    }
                });
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_layout, fragment)
                .commit();
    }


    public enum ProductType{
        FOOD("food"),
        FASHION("fashion");
        private String value;
        public String getValue() {
            return value;
        }
        private ProductType(String value) {
            this.value = value;
        }
    }
}
