package com.homesorderconsumer.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.homesorderconsumer.MyApp;
import com.homesorderconsumer.R;
import com.homesorderconsumer.checkout.model.DeliveryAddress;
import com.homesorderconsumer.user.login.model.User;
import com.homesorderconsumer.user.preference.model.Country;


/**
 * Created by mac on 11/15/17.
 */

public class MySession extends SessionConstant{

    private final String FILE_NAME = "homesorderconsumer-Preferences";

    private static MySession MySession;
    SharedPreferences preferences;

    private MySession() {
    }

    public static MySession getInstance(Context context) {
        if (MySession == null) {
            MySession = new MySession();
        }
        MySession.getPreferenceObject(context);
        return MySession;
    }

    private void getPreferenceObject(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
    }

    public void saveLoginStatus(boolean status){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(IS_LOGIN,status);
        editor.commit();
    }
    public boolean isLogin(){
        return preferences.getBoolean(IS_LOGIN,false);
    }


    public void saveAppFirstTimeLoad(boolean status){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(APP_FIRST_TIME_LOAD,status);
        editor.commit();
    }
    public boolean isAppFirstTimeLoad(){
        return preferences.getBoolean(APP_FIRST_TIME_LOAD,true);
    }

    public void savePreferenceStatus(boolean status){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(IS_PREFERENCE,status);
        editor.commit();
    }
    public boolean isPreferenceStatus(){
        return preferences.getBoolean(IS_PREFERENCE,false);
    }

    public void savePreference(Country preference){
        Gson gson = new Gson();
        String json = gson.toJson(preference);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(PREFERENCE, json);
        editor.commit();
    }

    public Country getPreference(){
        String json = preferences.getString(PREFERENCE, null);
        Gson gson = new Gson();
        return  gson.fromJson(json, Country.class);
    }


      public void saveUser(User user){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(USER_INFO, json);
        editor.commit();
    }

    public User getUser(){
        String json = preferences.getString(USER_INFO, null);
        Gson gson = new Gson();
        return  gson.fromJson(json, User.class);
    }

    public void saveLanguageKey(String key){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(LANGUAGE_KEY, key);
        editor.commit();
    }

    public String getLanguageKey(){
        return preferences.getString(LANGUAGE_KEY, MyApp.getContext().getString(R.string.en));
    }

    public void saveCartCount(int count){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt(CART_COUNT, count);
        editor.commit();
    }

    public int getCartCount(){
        return preferences.getInt(CART_COUNT, 0);
    }

    public void saveGuestCartID(String guestCartID){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(GUEST_CART_ID, guestCartID);
        editor.commit();
    }

    public String getGuestCartID(){
        return preferences.getString(GUEST_CART_ID, "");
    }

    public void saveCurrency(String key){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(CURRENCY, key);
        editor.commit();
    }

    public String getCurrency(){
        return preferences.getString(CURRENCY, MyApp.getContext().getString(R.string.aed));
    }


    public void saveDeliveryAddress(DeliveryAddress deliveryAddress){
        Gson gson = new Gson();
        String json = gson.toJson(deliveryAddress);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(DELIVERY_ADDRESS, json);
        editor.commit();
    }

    public DeliveryAddress getDeliveryAddress(){
        String json = preferences.getString(DELIVERY_ADDRESS, null);
        Gson gson = new Gson();
        return  gson.fromJson(json, DeliveryAddress.class);
    }

    public void saveCartAmount(String amount){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(CART_AMOUNT, amount);
        editor.commit();
    }

    public String getCartAmount(){
        return  preferences.getString(CART_AMOUNT, null);
    }

    public void clearLoginSession(){
        saveLoginStatus(false);
        saveCartCount(0);
        saveGuestCartID("");
    }

    public void clearUserData() {
        preferences.edit().clear().commit();
    }
}
