package com.homesorderconsumer.product.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.DatePicker;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.api.APIUtil;
import com.homesorderconsumer.api.GeneralResponse;
import com.homesorderconsumer.databinding.ActivityProductDetailBinding;
import com.homesorderconsumer.product.DeliverySlotFragment;
import com.homesorderconsumer.product.ProductDetailActivity;
import com.homesorderconsumer.product.ReviewListActivity;
import com.homesorderconsumer.product.deliveryslot.DeliverySlotDialog;
import com.homesorderconsumer.product.model.Color;
import com.homesorderconsumer.product.model.DeliverySlots;
import com.homesorderconsumer.product.model.ProductDataCollection;
import com.homesorderconsumer.product.model.ProductDetail;
import com.homesorderconsumer.product.model.ProductDetailResponse;
import com.homesorderconsumer.product.model.Products;
import com.homesorderconsumer.product.model.Size;
import com.homesorderconsumer.product.model.Weight;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.cart.model.CartAddResponse;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;
import com.homesorderconsumer.util.StringUtil;
import com.homesorderconsumer.util.Util;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 25/03/18.
 */

public class ProductDetailVM extends java.util.Observable{


    Activity activity;
    MyProgressDialog myProgressDialog;
    Products products;
    ProductDetail productDetail=new ProductDetail();
    ActivityProductDetailBinding binding;

    public ProductDetailVM(@NonNull Activity activity,Products products,ActivityProductDetailBinding binding){
        this.activity=activity;
        this.products=products;
        myProgressDialog=new MyProgressDialog();
        this.binding=binding;
        productDetailAPICall();
    }

    public void onClickBack(View view){
        activity.finish();
    }

    public void onClickReviewAndRating(View view,ProductDetail productDetail){
        if (StringUtil.showRatingStar(productDetail.getRatingValueAverage())) {
            Intent intent = new Intent(activity, ReviewListActivity.class);
            intent.putExtra("productDetail", productDetail);
            activity.startActivity(intent);
        }
    }

    public void onClickDeliverySlot(View view,ProductDetail productDetail){
        if (productDetail!=null) {
            DeliverySlotFragment deliverySlotFragment=new DeliverySlotFragment();
            deliverySlotFragment.setDeliverySlotList(productDetail.getDeliverySlots());
            ProductDetailActivity productDetailActivity=(ProductDetailActivity)activity;
            deliverySlotFragment.show(productDetailActivity.getSupportFragmentManager(), "dialog");
            //datePicker(productDetail);
        }else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
        }
    }

    public void onClickColor(View view, ProductDetail productDetail, ProductDataCollection
            productDataCollection){
        colorPicker(productDetail.getOption().getColor(),productDataCollection);
    }
    public void onClickWeight(View view, ProductDetail productDetail, ProductDataCollection
            productDataCollection){
        weightPicker(productDetail.getOption().getWeight(),productDataCollection);
    }
    public void onClickSize(View view, ProductDetail productDetail, ProductDataCollection
            productDataCollection){
        sizePicker(productDetail.getOption().getSize(),productDataCollection);
    }

    public void onClickQuantity(View view, ProductDetail productDetail, ProductDataCollection
            productDataCollection){
        quantityPicker(productDataCollection);
    }

    public void onClickAddToCart(View view,ProductDetail productDetail, ProductDataCollection
            productDataCollection){
        validation(productDetail,productDataCollection);

    }

    public void validation(ProductDetail productDetail, ProductDataCollection
            productDataCollection){
            if (Util.getInstance().getSelectedDeliverySlot(activity).getSelectedsSlot().trim()
                    .length()==0){
                MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R
                        .string.please_select_your_delivery_slot));
            }else {
                if (MySession.getInstance(activity).isLogin()){
                    addToCartAPICall(productDetail,productDataCollection);
                }else {
                    addToCartGuestAPICall(productDetail,productDataCollection);
                }
            }
    }


    public ProductDetail getProductDetail(){
        return productDetail;
    }

    private void productDetailAPICall() {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<ProductDetailResponse>> observable = api.productByID(products.getProductID());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            productDetail=responses.body().getProduct();
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

    private void addToCartGuestAPICall(ProductDetail product, ProductDataCollection
            productDataCollection){

        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<CartAddResponse>> observable;
            if (MySession.getInstance(activity).getGuestCartID().trim().length()==0) {
                observable = api.guestAddtocart(APIUtil.getInstance().addToCart(product,
                        productDataCollection, Util.getInstance().getSelectedDeliverySlot(activity)));
            }else {
                observable = api.guestAddtocart(MySession.getInstance
                        (activity).getGuestCartID(), APIUtil.getInstance().addToCart(product,
                        productDataCollection, Util.getInstance().getSelectedDeliverySlot(activity)));
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            MySession.getInstance(activity).saveCartCount(MySession.getInstance
                                    (activity).getCartCount()+1);
                            binding.setCartCount(String.valueOf(MySession.getInstance(activity)
                                    .getCartCount()));
                            MySession.getInstance(activity).saveGuestCartID(responses.body().getCart_id());
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

    private void addToCartAPICall(ProductDetail product, ProductDataCollection
            productDataCollection){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);

            Observable<Response<GeneralResponse>> observable = api.addToCart(MySession.getInstance
                        (activity).getUser().getToken(), APIUtil.getInstance().addToCart(product,
                        productDataCollection, Util.getInstance().getSelectedDeliverySlot(activity)));

            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            MySession.getInstance(activity).saveCartCount(MySession.getInstance
                                    (activity).getCartCount()+1);
                            binding.setCartCount(String.valueOf(MySession.getInstance(activity)
                                    .getCartCount()));
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


    private void weightPicker(List<Weight> list, ProductDataCollection productDataCollection) {
        if (list!=null&&list.size() != 0) {
            CharSequence[] items = new CharSequence[list.size()];
            for (int i = 0; i < list.size(); i++) {
                items[i] = list.get(i).getName();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int position) {
                    productDataCollection.setWeightName(list.get(position).getName());
                    productDataCollection.setWeightValue(list.get(position).getValue());
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }


    private void colorPicker(List<Color> list,ProductDataCollection productDataCollection) {
        if (list!=null&&list.size() != 0) {
            CharSequence[] items = new CharSequence[list.size()];
            for (int i = 0; i < list.size(); i++) {
                items[i] = list.get(i).getName();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int position) {
                    productDataCollection.setColorName(list.get(position).getName());
                    productDataCollection.setColorValue(list.get(position).getValue());
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void sizePicker(List<Size> list, ProductDataCollection productDataCollection) {
        if (list!=null&&list.size() != 0) {
            CharSequence[] items = new CharSequence[list.size()];
            for (int i = 0; i < list.size(); i++) {
                items[i] = list.get(i).getName();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int position) {
                    productDataCollection.setSizeName(list.get(position).getName());
                    productDataCollection.setSizeValue(list.get(position).getValue());
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void quantityPicker(ProductDataCollection productDataCollection) {
            CharSequence[] items = new CharSequence[10];
            for (int i = 0; i <10; i++) {
                items[i] = String.valueOf(i+1);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int position) {
                    productDataCollection.setQuantity(String .valueOf(items[position]));
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
    }


    private void datePicker(ProductDetail productDetail) {
        if (activity instanceof ProductDetailActivity) {
            ProductDetailActivity productDetailActivity = (ProductDetailActivity) activity;
            DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                    String dateString = "";
                    if (month + 1 <= 9) {
                        dateString += "0" + String.valueOf(month + 1) + "/";
                    } else {
                        dateString += String.valueOf(month + 1) + "/";
                    }
                    if (date <= 9) {
                        dateString += "0" + String.valueOf(date) + "/";
                    } else {
                        dateString += String.valueOf(date) + "/";
                    }

                    dateString += String.valueOf(year);
                    checkDateAvailability(productDetail, dateString);
                }
            }, StringUtil.getYear(productDetailActivity.selectedDeliverySlot.getDate()), StringUtil
                    .getMonth(productDetailActivity.selectedDeliverySlot.getDate()), StringUtil.getDay
                    (productDetailActivity.selectedDeliverySlot.getDate()));
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            datePickerDialog.show();
        }
    }

    private void checkDateAvailability(ProductDetail productDetail,String selectedDate){
        List<DeliverySlots> list=productDetail.getDeliverySlots();
        DeliverySlots deliverySlots=new DeliverySlots()
                ;
        boolean isDataAvailable=false;
        for (DeliverySlots slots:list){
            if (slots.getDate().trim().equals(selectedDate.trim())){
                isDataAvailable=true;
                deliverySlots=slots;
                break;
            }
        }
        if (isDataAvailable) {
            DeliverySlotDialog deliverySlotDialog = new DeliverySlotDialog();
            deliverySlotDialog.setDeliverySlot(deliverySlots);
            ProductDetailActivity productDetailActivity = (ProductDetailActivity) activity;
            deliverySlotDialog.show(productDetailActivity.getSupportFragmentManager(),
                    "dialog");
        }else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .delivery_slot_not_available));
        }
    }

}