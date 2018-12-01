package com.homesorderconsumer.checkout.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.api.GeneralResponse;
import com.homesorderconsumer.checkout.CheckoutActivity;
import com.homesorderconsumer.checkout.OrderConfirmedActivity;
import com.homesorderconsumer.checkout.model.CheckoutResponse;
import com.homesorderconsumer.checkout.model.CheckoutUI;
import com.homesorderconsumer.checkout.model.DeliveryAddress;
import com.homesorderconsumer.checkout.model.EstimatePaymentMethodResponse;
import com.homesorderconsumer.checkout.model.PaymentMethodItem;
import com.homesorderconsumer.checkout.payment.PaymentType;
import com.homesorderconsumer.checkout.payment.PaymentUtil;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.cart.model.CartPrice;
import com.homesorderconsumer.user.preference.model.Areas;
import com.homesorderconsumer.user.preference.model.Country;
import com.homesorderconsumer.user.preference.model.FilterAreasResponse;
import com.homesorderconsumer.user.preference.model.States;
import com.homesorderconsumer.util.InternetChecker;
import com.homesorderconsumer.util.MyProgressDialog;
import com.homesorderconsumer.util.MySnackBar;
import com.homesorderconsumer.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by innoppl on 06/04/18.
 */

public class CheckoutVM extends java.util.Observable{

    Activity activity;
    MyProgressDialog myProgressDialog;
    List<PaymentMethodItem> paymentMethodItems=new ArrayList<>();
    List<Country> countryList=new ArrayList<>();
    List<Areas> areasList=new ArrayList<>();
    DeliveryAddress deliveryAddress;

    CartPrice cartPrice;

    public CheckoutVM(@NonNull Activity activity,DeliveryAddress deliveryAddress,CartPrice cartPrice){
        this.activity=activity;
        this.deliveryAddress=deliveryAddress;
        this.cartPrice=cartPrice;
        myProgressDialog=new MyProgressDialog();
        //filterAreasAPICall();
        areasListAPICall();
    }

    public void onClickBack(View view){
        activity.finish();
    }

    public void onClickSaveAndContinue(View view, CheckoutUI checkoutUI, DeliveryAddress
            deliveryAddress){
        validation(checkoutUI,deliveryAddress);
    }

    public void onClickCheckout(View view, CheckoutUI checkoutUI, DeliveryAddress
            deliveryAddress){
        if (activity instanceof CheckoutActivity) {
            CheckoutActivity checkoutActivity = (CheckoutActivity) activity;
            List<PaymentMethodItem> items = checkoutActivity.paymentOptionAdapter.list;
            PaymentMethodItem paymentMethodItem = null;
            for (PaymentMethodItem item : items) {
                if (item.isSelected()) {
                    paymentMethodItem = item;
                    break;
                }
            }
            if (paymentMethodItem == null) {
                MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                        .please_select_payment_method));
            } else {
                deliveryAddress.setPaymentMethod(paymentMethodItem.getCode());
                if (paymentMethodItem.getCode().equals(PaymentType.TELR_PAYMENT.getValue())) {
                    MySession.getInstance(activity).saveDeliveryAddress(deliveryAddress);
                    if (cartPrice.getOverAllTotal()!=null&&!cartPrice.getOverAllTotal().trim()
                            .equals("")) {
                        MySession.getInstance(activity).saveCartAmount(cartPrice.getOverAllTotal());
                        PaymentUtil.telrPaymentGatewayInitialization(activity, cartPrice.getOverAllTotal(),
                                deliveryAddress);
                    }else {
                        MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
                    }
                }else if (paymentMethodItem.getCode().equals(PaymentType.CASH_ON_DELIVERY.getValue())){
                    if (MySession.getInstance(activity).isLogin()) {
                        checkoutAPICall(deliveryAddress, false);
                    } else {
                        if (MySession.getInstance(activity).getGuestCartID().trim().length() != 0) {
                            checkoutAPICall(deliveryAddress, true);
                        }
                    }
                }
            }
        }
    }

    public void onClickDeliveryAddress(View view,CheckoutUI checkoutUI, DeliveryAddress
            deliveryAddress){
        checkoutUI.setPaymentMethodOpen(false);
        checkoutUI.setAddressOpen(true);
    }

    public void onClickPaymentMethod(View view,CheckoutUI checkoutUI, DeliveryAddress
            deliveryAddress){
        if (checkoutUI.isAddressSaved()) {
            checkoutUI.setAddressOpen(false);
            checkoutUI.setPaymentMethodOpen(true);
        }
    }

    public void onClickArea(View view,DeliveryAddress deliveryAddress){
        areaPicker(deliveryAddress);
    }

    public List<PaymentMethodItem> getPaymentMethodItems(){
        return paymentMethodItems;
    }

    private void validation(CheckoutUI checkoutUI,DeliveryAddress deliveryAddress){
        if (deliveryAddress.getFirstname().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .valid_first_name));
        }else if (deliveryAddress.getLastname().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .valid_last_name));
        }else if (deliveryAddress.getEmail().trim().length() == 0 || !Patterns.EMAIL_ADDRESS.matcher
                (deliveryAddress.getEmail().trim()).matches()){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .valid_email_address_error));
        }else if (deliveryAddress.getTelephone().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .valid_phone_number));
        }else if (deliveryAddress.getAddress_line1().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .valid_address));
        }else if (deliveryAddress.getArea().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .valid_area));
        }
        /*else if (deliveryAddress.getPostcode().trim().length()==0){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .valid_post_code));
        }*/else
        {
            List<String> streets=new ArrayList<>();
            streets.add(deliveryAddress.getAddress_line1());
            if (deliveryAddress.getAddress_line2().trim().length()!=0){
                streets.add(deliveryAddress.getAddress_line2());
            }
            streets.add(deliveryAddress.getArea());

            deliveryAddress.setStreet(streets);
            if (MySession.getInstance(activity).isLogin()){
                addDeliveryAddressAPICall(deliveryAddress,checkoutUI,false);
            }else {
                if (MySession.getInstance(activity).getGuestCartID().trim().length()!=0){
                    addDeliveryAddressAPICall(deliveryAddress,checkoutUI,true);
                }
            }
        }
    }

    private void filterAreasAPICall() {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<FilterAreasResponse>> observable = api.filterAreas();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            countryList=responses.body().getFilterArea();
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

    private void areasListAPICall() {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<List<Country>>> observable = api.areasList(activity
                    .getString(R.string.area_list_url));
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            areaListParsing(responses.body());
                        } else {
                            if (responses.body() != null) {
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


    private void addDeliveryAddressAPICall(DeliveryAddress deliveryAddress,CheckoutUI checkoutUI,
                                           boolean isGuest) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<GeneralResponse>> observable;
            if (isGuest) {
                observable = api.guestAddShippingAddress(MySession
                        .getInstance(activity).getGuestCartID().trim(), deliveryAddress);
            }else{
                observable = api.addShippingAddress(MySession
                        .getInstance(activity).getUser().getToken(),deliveryAddress);
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            checkoutUI.setAddressSaved(true);
                            estimatePaymentMethodAPICall(deliveryAddress,checkoutUI,isGuest);
                            /*APIErrorHandler.getInstance().errorHandler(activity, responses
                                    .code(), responses.body().getMessage());*/
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

    private void estimatePaymentMethodAPICall(DeliveryAddress deliveryAddress,CheckoutUI checkoutUI,
                                              boolean isGuest){
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<EstimatePaymentMethodResponse>> observable;
            if (isGuest) {
                observable = api.guestEstimatePaymentMethod(MySession
                        .getInstance(activity).getGuestCartID().trim(), deliveryAddress);
            }else{
                observable = api.estimatePaymentMethod(MySession
                        .getInstance(activity).getUser().getToken(),deliveryAddress);
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            checkoutUI.setAddressOpen(false);
                            checkoutUI.setPaymentMethodOpen(true);
                            paymentMethodItems=responses.body().getPaymentMethod();
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

    private void checkoutAPICall(DeliveryAddress deliveryAddress,
                                           boolean isGuest) {
        if (InternetChecker.getInstance().isReachable()) {
            myProgressDialog.showDialog(activity);
            APICall api = APIConfiguration.getInstance().createService(APICall.class);
            Observable<Response<CheckoutResponse>> observable;
            if (isGuest) {
                observable = api.guestOrderCheckout(MySession
                        .getInstance(activity).getGuestCartID().trim(), deliveryAddress);
            }else{
                observable = api.orderCheckout(MySession
                        .getInstance(activity).getUser().getToken(),deliveryAddress);
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responses -> {
                        myProgressDialog.dismissDialog();
                        if (responses.code() == 200) {
                            MySession.getInstance(activity).saveGuestCartID("");
                            MySession.getInstance(activity).saveCartCount(0);

                            Intent intent=new Intent(activity, OrderConfirmedActivity.class);
                            intent.putExtra("increment_id",responses.body().getOrders().getIncrement_id());
                            activity.startActivity(intent);
                            /*APIErrorHandler.getInstance().errorHandler(activity, responses
                                    .code(), responses.body().getMessage());*/
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

    private void areaPicker(DeliveryAddress deliveryAddress) {
        if (areasList!=null&&areasList.size()!=0){
            CharSequence[] items = new CharSequence[areasList.size()];
            for (int i = 0; i < areasList.size(); i++) {
                items[i] = StringUtil.getLanguageString(areasList.get(i).getAreaNameEN(),
                        areasList.get(i)
                                .getAreaNameAR());
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int position) {
                    deliveryAddress.setArea(String.valueOf(items[position]));
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
        }

        /*if (countryList!=null&&countryList.size() != 0) {
            Country country=null;
            for (Country temp:countryList){
                if (temp.getValue().equals(deliveryAddress.getCountry_id())){
                    country=temp;
                    break;
                }
            }
            if (country!=null) {
                States states=null;
                for (States temp : country.getStates()){
                    if (StringUtil.getLanguageString(temp.getStateNameEN(),temp.getStateNameAR())
                            .equals(deliveryAddress.getCity())){
                        states=temp;
                    }
                }

                if (states!=null){
                    CharSequence[] items = new CharSequence[states.getAreas().size()];
                    for (int i = 0; i < states.getAreas().size(); i++) {
                        items[i] = StringUtil.getLanguageString(states.getAreas().get(i).getAreaNameEN(),
                                states.getAreas().get(i)
                                .getAreaNameAR());
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                           deliveryAddress.setRegion(String.valueOf(items[position]));
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        }*/
    }

    private void areaListParsing(List<Country> list){
        for (Country country:list){
            for (States states:country.getStates()){
                if (StringUtil.getLanguageString(states.getStateNameEN(),states.getStateNameAR())
                        .equals(deliveryAddress.getCity())){
                    areasList.addAll(states.getAreas());
                    break;
                }
            }
            if (areasList.size()!=0){
                break;
            }
        }
    }

}
