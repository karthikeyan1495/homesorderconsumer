package com.homesorderconsumer.product;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivityReviewListBinding;
import com.homesorderconsumer.product.adapter.ReviewListAdapter;
import com.homesorderconsumer.product.model.ProductDetail;
import com.homesorderconsumer.product.model.ReviewItem;
import com.homesorderconsumer.product.viewmodel.ReviewListVM;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.StatusBarUtil;
import com.homesorderconsumer.util.StringUtil;
import com.homesorderconsumer.util.Util;

import java.util.List;
import java.util.Locale;
import java.util.Observer;

public class ReviewListActivity extends AppCompatActivity implements Observer {

    ActivityReviewListBinding binding;
    ReviewListVM reviewListVM;
    ReviewListAdapter reviewListAdapter;
    ProductDetail productDetail=new ProductDetail();

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setLightMode(this);
       // Util.getInstance().setLanguage();
        getIntentData();
        bindView();
        setUpObserver(reviewListVM);
        //setupProductRecyclerView();
    }
    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof ReviewListVM) {
            setupProductReviewRecyclerView(reviewListVM.getReviewItemsResponse().getReview().getReviews());
        }
    }
    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }

    private void getIntentData(){
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            productDetail=(ProductDetail) bundle.getSerializable("productDetail");
        }
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_review_list);
        reviewListVM=new ReviewListVM(this,productDetail);
        binding.setReviewListVM(reviewListVM);
        binding.setTitle(StringUtil.getLanguageString(productDetail.getProductNameEN(),
                productDetail.getProductNameAR()));
    }

    private void setupProductReviewRecyclerView(List<ReviewItem> list){
        reviewListAdapter=new ReviewListAdapter(this,list);
        binding.reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.reviewRecyclerView.setAdapter(reviewListAdapter);
    }
}
