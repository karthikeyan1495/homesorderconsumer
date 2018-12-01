package com.homesorderconsumer.user.myorder;


import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.FragmentRateAndReviewBinding;
import com.homesorderconsumer.product.model.ReviewItem;
import com.homesorderconsumer.trackorder.TrackOrder;
import com.homesorderconsumer.user.myorder.model.OrderProductItem;
import com.homesorderconsumer.user.myorder.model.OrdersItem;
import com.homesorderconsumer.user.myorder.model.ReviewPost;
import com.homesorderconsumer.user.myorder.viewmodel.ProductRateAndReviewVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateAndReviewFragment extends DialogFragment {

    FragmentRateAndReviewBinding binding;
    ProductRateAndReviewVM productRateAndReviewVM;

    OrderProductItem orderProductItem=new OrderProductItem();
    ReviewItem reviewItem=new ReviewItem();
    OrdersItem ordersItem=new OrdersItem();

    TrackOrder trackOrder=new TrackOrder();

    public RateAndReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        bindView(inflater, container);
        return binding.getRoot();
    }

    public void setOrderProductItem(OrderProductItem orderProductItem,OrdersItem ordersItem,TrackOrder trackOrder){
        this.ordersItem=ordersItem;
        this.orderProductItem=orderProductItem;
        this.trackOrder=trackOrder;
        if (orderProductItem.getReview().size()!=0){
            reviewItem=orderProductItem.getReview().get(0);
        }
    }
    private void bindView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rate_and_review, container, false);
        productRateAndReviewVM = new ProductRateAndReviewVM(getActivity(),this,reviewItem,trackOrder);
        binding.setProductRateAndReviewVM(productRateAndReviewVM);
        binding.setOrderProductItem(orderProductItem);
        binding.setOrdersItem(ordersItem);
        binding.setReviewPost(getReviewPost());
    }

    private ReviewPost getReviewPost(){
        ReviewPost reviewPost=new ReviewPost();
        reviewPost.setTitle(reviewItem.getTitle());
        reviewPost.setDetail(reviewItem.getDetail());
        try {
            reviewPost.setRatingValue(Float.valueOf(reviewItem.getRatingValue()));
        }catch (Exception e){
            reviewPost.setRatingValue(0);
        }
        return reviewPost;
    }
}
