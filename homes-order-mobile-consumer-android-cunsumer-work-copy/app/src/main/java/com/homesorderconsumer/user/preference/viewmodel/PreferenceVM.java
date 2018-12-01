package com.homesorderconsumer.user.preference.viewmodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APICall;
import com.homesorderconsumer.api.APIConfiguration;
import com.homesorderconsumer.api.APIErrorHandler;
import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.preference.model.Areas;
import com.homesorderconsumer.user.preference.model.Country;
import com.homesorderconsumer.user.preference.model.FilterAreasResponse;
import com.homesorderconsumer.user.preference.model.PreferenceData;
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
 * Created by innoppl on 24/03/18.
 */

public class PreferenceVM {

    Activity activity;
    MyProgressDialog myProgressDialog;
    Country preferenceCountry;
    List<Country> countryList=new ArrayList<>();

    int countryPosition=0;
    int statePosition=0;
    int areaPosition=0;

    public PreferenceVM(@NonNull Activity activity,Country preferenceCountry){
        this.activity=activity;
        myProgressDialog=new MyProgressDialog();
        this.preferenceCountry=preferenceCountry;
        filterAreasAPICall();
    }

    public void onClickCountry(View view, PreferenceData preferenceData){
        countryPicker(preferenceData);
    }

    public void onClickCity(View view,PreferenceData preferenceData){
        if (preferenceData.getCountryName().trim().equals("")){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .valid_country));
        }else {
            statePicker(preferenceData);
        }
    }

    public void onClickArea(View view,PreferenceData preferenceData){
        if (preferenceData.getCountryName().trim().equals("")){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .valid_country));
        }else if(preferenceData.getStateName().trim().equals
                ("")){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                            .valid_region
                    ));
        }else {
            areaPicker(preferenceData);
        }
    }

    public void onClickSave(View view,PreferenceData preferenceData){
        validation(preferenceData);
    }

    private void validation(PreferenceData preferenceData){
        if (preferenceData.getCountryName().trim().equals("")){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .valid_country));
        }else if(preferenceData.getStateName().trim().equals
                ("")){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.valid_region
            ));
        }else if(preferenceData.getAreaName().trim().equals
                ("")){
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string
                    .valid_city
            ));
        }else{

            List<States> states=new ArrayList<>();
            List<Areas> areas=new ArrayList<>();

            if (countryList.size()!=0) {
                states.add(countryList.get(countryPosition).getStates().get(statePosition));
                areas.add(countryList.get(countryPosition).getStates().get(statePosition).getAreas()
                        .get(areaPosition));
                preferenceCountry = countryList.get(countryPosition);
                preferenceCountry.setStates(states);
                preferenceCountry.getStates().get(0).setAreas(areas);
                MySession.getInstance(activity).savePreference(preferenceCountry);
                MySession.getInstance(activity).savePreferenceStatus(true);

                if (countryList.get(countryPosition).getValue().equals("AE")){
                    MySession.getInstance(activity).saveCurrency(activity.getString(R.string.aed));
                }else {
                    MySession.getInstance(activity).saveCurrency(activity.getString(R.string.sar));
                }
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finishAffinity();
            }else {
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finishAffinity();
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
                            getPositions();
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


    private void countryPicker(PreferenceData preferenceData) {
        if (countryList!=null&&countryList.size() != 0) {
            CharSequence[] items = new CharSequence[countryList.size()];
            for (int i = 0; i < countryList.size(); i++) {
                items[i] = StringUtil.getLanguageString(countryList.get(i).getCountryNameEN(), countryList.get(i)
                        .getCountryNameAR());
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    preferenceData.setCountryName(StringUtil.getLanguageString(countryList.get(item)
                            .getCountryNameEN(), countryList.get(item)
                            .getCountryNameAR()));
                    countryPosition=item;
                    preferenceData.setStateName("");
                    statePosition=0;
                    preferenceData.setAreaName("");
                    areaPosition=0;
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void statePicker(PreferenceData preferenceData) {
        if (countryList!=null&&countryList.size() != 0) {

            List<States> states = countryList.get(countryPosition).getStates();
            if (states != null && states.size() != 0) {
                CharSequence[] items = new CharSequence[states.size()];
                for (int i = 0; i < states.size(); i++) {
                    items[i] = StringUtil.getLanguageString(states.get(i).getStateNameEN(), states.get(i)
                            .getStateNameAR());
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        preferenceData.setStateName(StringUtil.getLanguageString(states.get(item)
                                .getStateNameEN(), states.get(item)
                                .getStateNameAR()));
                        statePosition = item;
                        preferenceData.setAreaName("");
                        areaPosition = 0;
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    private void areaPicker(PreferenceData preferenceData) {
        if (countryList!=null&&countryList.size() != 0) {

            List<Areas> areas = countryList.get(countryPosition).getStates().get(statePosition).getAreas();
            if (areas != null && areas.size() != 0) {
                CharSequence[] items = new CharSequence[areas.size()];
                for (int i = 0; i < areas.size(); i++) {
                    items[i] = StringUtil.getLanguageString(areas.get(i).getAreaNameEN(), areas.get(i)
                            .getAreaNameAR());
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        preferenceData.setAreaName(StringUtil.getLanguageString(areas.get(item)
                                .getAreaNameEN(), areas.get(item)
                                .getAreaNameAR()));
                        areaPosition = item;
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }


    private void getPositions(){
        if (preferenceCountry!=null) {
            for (int i = 0; i < countryList.size(); i++) {
                if (preferenceCountry.getValue().trim().toLowerCase().equals(countryList.get(i)
                        .getValue().trim().toLowerCase())){
                    countryPosition=i;
                    break;
                }
            }
            if (preferenceCountry.getStates()!=null&&preferenceCountry.getStates().size()!=0){
                List<States> states=countryList.get(countryPosition).getStates();
                for (int i = 0; i < states.size(); i++) {
                    if (preferenceCountry.getStates().get(0).getValue().trim().toLowerCase().equals
                            (states.get(i)
                            .getValue().trim().toLowerCase())){
                        statePosition=i;
                        break;
                    }
                }

                if (preferenceCountry.getStates().get(0).getAreas()!=null&&preferenceCountry.getStates()
                        .get(0).getAreas().size()!=0){
                    List<Areas> areas=states.get(statePosition).getAreas();
                    for (int i = 0; i < areas.size(); i++) {
                        if (preferenceCountry.getStates().get(0).getAreas().get(0).getValue().trim()
                                .toLowerCase()
                                .equals
                                (areas.get(i)
                                        .getValue().trim().toLowerCase())){
                            areaPosition=i;
                            break;
                        }
                    }
                }
            }
        }
    }

}
