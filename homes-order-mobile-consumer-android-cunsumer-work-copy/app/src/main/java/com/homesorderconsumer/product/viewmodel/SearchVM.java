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
import com.homesorderconsumer.product.model.ProductRequest;
import com.homesorderconsumer.product.model.ProductResponse;
import com.homesorderconsumer.product.model.Products;
import com.homesorderconsumer.product.model.SearchKey;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;
import com.homesorderconsumer.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 26/03/18.
 */

public class SearchVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;
    List<Products> products=new ArrayList<>();


    public ObservableBoolean isNoData=new ObservableBoolean(false);

    public SearchVM(@NonNull Activity activity){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
    }

    public void onClickBack(View view){
        activity.finish();
    }

    public void onClickSearch(View view, SearchKey searchKey){
        if (searchKey.getSearchKey().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .please_enter_one_char));
        }else {
            Util.getInstance().hideKeyboard(activity);
            searchKey.setDisplayText(searchKey.getSearchKey());
            productSearchAPICall(APIUtil.getInstance().productSearch(searchKey.getSearchKey(),1)
                    ,true);
        }
    }
    public List<Products> getProducts(){
        return products;
    }


    public void productSearchAPICall(ProductRequest productRequest,boolean isClear) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<ProductResponse>> observable;
            if (MySession.getInstance(activity).isLogin()) {
                observable = api.products(APIUtil.getInstance()
                        .getUserToken(), productRequest);
            }else {
                observable = api.products(productRequest);
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            products= APIUtil.getInstance().parseFeaturedProduct(responses.body());
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
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
                    });
        } else {
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
