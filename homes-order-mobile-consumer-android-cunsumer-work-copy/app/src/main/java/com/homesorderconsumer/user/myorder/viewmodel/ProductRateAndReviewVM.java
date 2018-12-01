package com.homesorderconsumer.user.myorder.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.api.APIUtil;
import com.homesorderconsumer.api.GeneralResponse;
import com.homesorderconsumer.product.model.ReviewItem;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.trackorder.TrackOrder;
import com.homesorderconsumer.user.myorder.RateAndReviewFragment;
import com.homesorderconsumer.user.myorder.model.OrderProductItem;
import com.homesorderconsumer.user.myorder.model.ReviewPost;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 09/04/18.
 */

public class ProductRateAndReviewVM {
    Activity activity;
    RateAndReviewFragment rateAndReviewFragment;
    MyProgressDialog myProgressDialog;
    ReviewItem reviewItem;
    TrackOrder trackOrder;
    public ProductRateAndReviewVM (@NonNull Activity activity, RateAndReviewFragment
            rateAndReviewFragment, ReviewItem reviewItem,TrackOrder trackOrder){
        this.activity=activity;
        this.rateAndReviewFragment=rateAndReviewFragment;
        this.reviewItem=reviewItem;
        this.trackOrder=trackOrder;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickClose(View view){
        rateAndReviewFragment.dismiss();
    }

    public void onClickSubmit(View view, OrderProductItem
            orderProductItem, ReviewPost reviewPost){

        if (orderProductItem!=null&&orderProductItem.getProductID()!=null&&reviewPost!=null) {
            reviewPost.setProductId(orderProductItem.getProductID());

            if (MySession.getInstance(activity).isLogin()) {
                reviewPost.setNickname(MySession.getInstance(activity).getUser().getProfile()
                        .getUserdetails().getFirstname() + " " + MySession.getInstance(activity).getUser()
                        .getProfile()
                        .getUserdetails().getLastname());
                validation(orderProductItem, reviewPost);
            }else {
                //Guest User
                reviewPost.setNickname(trackOrder.getLastName());
                validation(orderProductItem, reviewPost);
            }
        }else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .something_went_wrong_while_retrieving_information));
        }
    }

    private void validation(OrderProductItem orderProductItem,ReviewPost reviewPost){
        if (reviewPost.getRatingValue()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .please_select_stars));
        }else if(reviewPost.getTitle().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .please_enter_title));
        }else if(reviewPost.getDetail().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .please_enter_comments));
        }else {
            reviewPostAPICall(orderProductItem,reviewPost);
        }
    }

    private void reviewPostAPICall(OrderProductItem orderProductItem,ReviewPost reviewPost) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);

            Observable<Response<GeneralResponse>> observable;

            if (MySession.getInstance(activity).isLogin()) {
                observable = api.reviewPost(APIUtil.getInstance()
                        .getUserToken(), reviewPost);
            }else {
                //Guest User
                observable = api.guestReviewPost(reviewPost);
            }

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            reviewItem.setName(reviewPost.getNickname());
                            reviewItem.setTitle(reviewPost.getTitle());
                            reviewItem.setDetail(reviewPost.getDetail());
                            reviewItem.setRatingValue(String.valueOf(reviewPost.getRatingValue()));
                            orderProductItem.getReview().add(0,reviewItem);
                            rateAndReviewFragment.dismiss();
                            APIErrorHandler.getInstance().errorHandler(activity, responses
                                    .code(), responses.body().getMessage());
                        } else {
                            if (responses.body() != null) {
                                APIErrorHandler.getInstance().errorHandler(activity, responses
                                        .code(), responses.body().getMessage());
                            } else {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.errorBody().string());
                            }
                        }
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));

                    });
        } else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.no_internet));
        }
    }

}
