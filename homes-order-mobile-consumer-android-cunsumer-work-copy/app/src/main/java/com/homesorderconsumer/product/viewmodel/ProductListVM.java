package com.homesorderconsumer.product.viewmodel;

import android.app.Activity;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.api.APIUtil;
import com.homesorderconsumer.category.model.Child;
import com.homesorderconsumer.product.ProductListActivity;
import com.homesorderconsumer.product.SortByEnum;
import com.homesorderconsumer.product.SortingOptionFragment;
import com.homesorderconsumer.product.model.ProductRequest;
import com.homesorderconsumer.product.model.ProductResponse;
import com.homesorderconsumer.product.model.Products;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 25/03/18.
 */

public class ProductListVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;
    Child child;
    List<Products> products=new ArrayList<>();
    public ObservableBoolean isNoData=new ObservableBoolean(false);
    SortByEnum sortByEnum=SortByEnum.CATEGORY_BASED;

    public ProductListVM(@NonNull Activity activity,Child child){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
        this.child=child;
        paginationAPICall(1,true);
       /* productAPICall(APIUtil.getInstance()
                .productBasedOnCategory(child.getId(),1),true);*/
    }

    public void onClickBack(View view){
        activity.finish();
    }

    public void onClickSort(View view){
        SortingOptionFragment sortingOptionFragment=new SortingOptionFragment();
        sortingOptionFragment.setSortDialogListener(sortByEnum,tempSortByEnum -> {
            sortByEnum=tempSortByEnum;
            paginationAPICall(1,true);
        });

        ProductListActivity productListActivity=(ProductListActivity)activity;
        sortingOptionFragment.show(productListActivity.getSupportFragmentManager(), "dialog");
    }

    public void paginationAPICall(int page,boolean isClear){
        if (sortByEnum== SortByEnum.MOST_RECENT) {
            productAPICall(APIUtil.getInstance()
                    .productMostRecent(child.getId(), page),isClear);
        }else if (sortByEnum==SortByEnum.PRICE_LOW_HIGH){
            productAPICall(APIUtil.getInstance()
                    .productPriceLowToHigh(child.getId(), page),isClear);
        }else if (sortByEnum==SortByEnum.PRICE_HIGH_LOW){
            productAPICall(APIUtil.getInstance()
                    .productPriceHighToLow(child.getId(), page),isClear);
        }else if(sortByEnum==SortByEnum.CATEGORY_BASED){
            productAPICall(APIUtil.getInstance()
                    .productBasedOnCategory(child.getId(),page),isClear);
        }
    }

    public List<Products> getProducts(){
        return products;
    }

    public void productAPICall(ProductRequest productRequest,boolean isClear) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<ProductResponse>> observable;
            if (MySession.getInstance(activity).isLogin()) {
                observable = api.products(APIUtil.getInstance()
                        .getUserToken(), productRequest);
            }else{
                observable= api.products(productRequest);
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            products=APIUtil.getInstance().parseFeaturedProduct(responses.body());
                            setChanged();
                            notifyObservers(isClear);
                        } else {
                            if (responses.body() != null) {
                                APIErrorHandler.getInstance().errorHandler(activity, responses
                                        .code(), responses.body().getMessage());
                            } else {
                                APIErrorHandler.getInstance().errorHandler(activity, responses.code(), responses.errorBody().string());
                            }
                        }
                        setNoData(isClear);
                    }, throwable -> {
                        myProgressDialog.dismissDialog();
                        setNoData(isClear);
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));

                    });
        } else {
            setNoData(isClear);
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.no_internet));
        }
    }

    private void setNoData(boolean isClear){
        if (isClear){
            if (products.size()==0){
                isNoData.set(true);
            }else {
                isNoData.set(false);
            }
        }
    }

}
