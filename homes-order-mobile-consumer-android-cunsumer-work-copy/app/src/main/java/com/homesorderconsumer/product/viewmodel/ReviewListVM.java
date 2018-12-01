package com.homesorderconsumer.product.viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.product.model.ProductDetail;
import com.homesorderconsumer.product.model.ReviewItemsResponse;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 25/03/18.
 */

public class ReviewListVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;
    ProductDetail productDetail;
    ReviewItemsResponse reviewItemsResponse=new ReviewItemsResponse();
    public ReviewListVM(@NonNull Activity activity,ProductDetail productDetail){
        this.activity=activity;
        this.productDetail=productDetail;
        myProgressDialog=new MyProgressDialog();
        productReviewItemsAPICall(productDetail.getProductID(),1);
    }

    public void onClickBack(View view){
        activity.finish();
    }

    public ReviewItemsResponse getReviewItemsResponse(){
        return reviewItemsResponse;
    }

    public void productReviewItemsAPICall(int productId,int page) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<ReviewItemsResponse>> observable = api.productReviewItems
                    (productId,page);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            reviewItemsResponse=responses.body();
                            setChanged();
                            notifyObservers();
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
