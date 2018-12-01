package com.homesorderconsumer.util;

import android.util.Log;

import com.homesorderconsumer.MyApp;
import com.homesorderconsumer.R;
import com.homesorderconsumer.product.SortByEnum;
import com.homesorderconsumer.product.model.Color;
import com.homesorderconsumer.product.model.SelectedDeliverySlot;
import com.homesorderconsumer.product.model.Size;
import com.homesorderconsumer.product.model.Weight;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.user.login.model.User;
import com.homesorderconsumer.user.myorder.model.Customer;
import com.homesorderconsumer.user.myorder.model.OrderProductItem;
import com.homesorderconsumer.user.myorder.model.OrdersItem;
import com.homesorderconsumer.user.myorder.model.ReviewPost;
import com.homesorderconsumer.user.preference.model.Country;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by mac on 2/21/18.
 */

public class StringUtil {

    public static String welcomeWithName() {
        if (MySession.getInstance(MyApp.getContext()).isLogin()) {
            User user = MySession.getInstance(MyApp.getContext()).getUser();
            /*String name = user.getProfile().getUserdetails().getFirstname() + " " + user
                    .getProfile()
                    .getUserdetails().getLastname();*/
            String name = user.getProfile().getUserdetails().getLastname();
            return String.format(MyApp.getContext().getString(R.string.hi), name);
        } else {
            /*return String.format(MyApp.getContext().getString(R.string.hi), MyApp.getContext()
                    .getString(R.string.guest));*/
            return String.format(MyApp.getContext().getString(R.string.hi), "");
        }
    }

    public static String getLanguageString(String en, String ar) {
        if (MySession.getInstance(MyApp.getContext()).getLanguageKey().equals(MyApp.getContext().getString(R.string.ar))) {
            if (ar == null || ar.trim().length() == 0) {
                return en;
            } else {
                return ar;
            }
        } else {
            return en;
        }
    }

    public static String selectedArea() {
        String area = "";
        Country country = MySession.getInstance(MyApp.getContext()).getPreference();
        if (country != null) {
            if (country.getStates() != null && country.getStates().size() != 0) {
                if (country.getStates().get(0).getAreas() != null && country.getStates().get(0)
                        .getAreas().size() != 0) {
                    area = StringUtil.getLanguageString(country
                                    .getStates().get(0).getAreas().get(0).getAreaNameEN()
                            , country.getStates().get(0).getAreas().get(0).getAreaNameAR());
                }
            }
        }
        return area;
    }

    public static String selectedAreaValue() {
        String area = "";
        Country country = MySession.getInstance(MyApp.getContext()).getPreference();
        if (country != null) {
            if (country.getStates() != null && country.getStates().size() != 0) {
                if (country.getStates().get(0).getAreas() != null && country.getStates().get(0)
                        .getAreas().size() != 0) {
                    area = country
                            .getStates().get(0).getAreas().get(0).getValue();
                }
            }
        }
        return area;
    }

    public static String selectedStateName() {
        String state = "";
        Country country = MySession.getInstance(MyApp.getContext()).getPreference();
        if (country != null) {
            if (country.getStates() != null && country.getStates().size() != 0) {
                state = country
                        .getStates().get(0).getStateNameEN();
            }
        }
        return state;
    }

    public static String selectedStateNameLanguageBased() {
        String state = "";
        Country country = MySession.getInstance(MyApp.getContext()).getPreference();
        if (country != null) {
            if (country.getStates() != null && country.getStates().size() != 0) {
                state = getLanguageString(country
                        .getStates().get(0).getStateNameEN(), country
                        .getStates().get(0).getStateNameAR());
            }
        }
        return state;
    }

    public static String selectedCountryName() {
        String countryName = "";
        Country country = MySession.getInstance(MyApp.getContext()).getPreference();
        if (country != null) {
            countryName = getLanguageString(country.getCountryNameEN(), country.getCountryNameAR());
        }
        return countryName;
    }

    public static String selectedCountryID() {
        String countryID = "";
        Country country = MySession.getInstance(MyApp.getContext()).getPreference();
        if (country != null) {
            countryID = country.getValue();
        }
        return countryID;
    }

    public static String selectedAreaName() {
        String area = "";
        Country country = MySession.getInstance(MyApp.getContext()).getPreference();
        if (country != null) {
            if (country.getStates() != null && country.getStates().size() != 0) {
                if (country.getStates().get(0).getAreas() != null && country.getStates().get(0)
                        .getAreas().size() != 0) {
                    area = getLanguageString(country
                            .getStates().get(0).getAreas().get(0).getAreaNameEN(), country
                            .getStates().get(0).getAreas().get(0).getAreaNameAR());
                }
            }
        }
        return area;
    }

    public static String getFirstItem(List<String> list) {
        if (list != null && list.size() != 0) {
            return list.get(0);
        } else {
            return "";
        }
    }

    public static String getDataFormat(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            cal.setTime(sdf.parse(date));
            return sdf.format(cal.getTime());
        } catch (Exception e) {
        }
        return "";
    }

    public static String formatCreatedAtDate(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH);
            cal.setTime(sdf.parse(date));
            return sdf.format(cal.getTime());

        } catch (Exception e) {
        }
        return "";
    }

    public static String dayOfTheWeek(String date) {
        try {
            Calendar cal = Calendar.getInstance(Locale.US);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            cal.setTime(sdf.parse(date));
            SimpleDateFormat formatOut = new SimpleDateFormat("EEEE");
            return formatOut.format(cal.getTime());
        } catch (Exception e) {
        }
        return "";
    }


    public static String formatTime(int time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
            return formatter.format(new Date(Long.valueOf(time)));
        } catch (Exception e) {
        }
        return "";
    }

    public static boolean showWeightField(List<Weight> list) {
        if (list != null && list.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean showColorField(List<Color> list) {
        if (list != null && list.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean showSizeField(List<Size> list) {
        if (list != null && list.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean addCheckMarkMorning(SelectedDeliverySlot selectedDeliverySlot, String
            deliverySlotDate) {
        if (selectedDeliverySlot.getDate().equals(deliverySlotDate)) {
            if (selectedDeliverySlot.getSelectedsSlot().trim().toLowerCase().equals("m")) {
                return true;
            }
        }
        return false;
    }

    public static boolean addCheckMarkAfterNoon(SelectedDeliverySlot selectedDeliverySlot, String
            deliverySlotDate) {
        if (selectedDeliverySlot.getDate().equals(deliverySlotDate)) {
            if (selectedDeliverySlot.getSelectedsSlot().trim().toLowerCase().equals("a")) {
                return true;
            }
        }
        return false;
    }

    public static boolean addCheckMarkEvening(SelectedDeliverySlot selectedDeliverySlot, String
            deliverySlotDate) {
        if (selectedDeliverySlot.getDate().equals(deliverySlotDate)) {
            Log.e("Slots", selectedDeliverySlot.getSelectedsSlot().trim().toLowerCase());
            if (selectedDeliverySlot.getSelectedsSlot().trim().toLowerCase().equals("e")) {
                return true;
            }
        }
        return false;
    }

    public static int reviewRating(String rating) {
        try {
            return Integer.valueOf(rating);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String orderSuccessMessage(String orderId) {
        return String.format(MyApp.getContext().getString(R.string.order_success_message), orderId);
    }

    public static String getDeliveryAddress(List<Customer> list) {
        if (list != null && list.size() != 0) {
            return list.get(0).getAddress();
        } else {
            return "";
        }
    }

    public static boolean showReviewSubmit(ReviewPost reviewPost) {
        if (reviewPost.getTitle().trim().length() != 0 || reviewPost.getDetail().trim().length()
                != 0 || reviewPost.getRatingValue() != 0) {
            return false;
        }
        return true;
    }

    public static boolean showCancelReason(String comments) {
        if (comments != null && !comments.toLowerCase().trim().equals("null")
                && comments.trim().length() != 0) {
            return true;
        }
        return false;
    }

    public static boolean showReview(OrderProductItem orderProductItem) {
        if (orderProductItem!=null&&orderProductItem.getStatusKey()!=null&&orderProductItem
                .getStatusKey().trim()
                .equals("3")) {
            return true;
        }
        return false;
    }

    public static boolean showRatingStar(String star) {
        if (star == null || star.trim().length() == 0 || star.trim().equals("null") || star.trim().equals
                ("0")) {
            return false;
        } else {
            return true;
        }
    }

    public static String setPrice(String aedPrice, String sarPrice) {
        String currency = MySession.getInstance(MyApp.getContext()).getCurrency();
        if (currency.equals(MyApp.getContext().getString(R.string.aed))) {
            return aedPrice + " " + currency;
        } else {
            return sarPrice + " " + currency;
        }
    }


    public static String setPriceARLayout(String aedPrice, String sarPrice) {
        String currency = MySession.getInstance(MyApp.getContext()).getCurrency();
        if (currency.equals(MyApp.getContext().getString(R.string.aed))) {
            return aedPrice + " " + currency + " ";
        } else {
            return sarPrice + " " + currency + " ";
        }
    }

    public static String setPrice(String aedPrice, String sarPrice, String qty) {
        try {
            String currency = MySession.getInstance(MyApp.getContext()).getCurrency();
            if (currency.equals(MyApp.getContext().getString(R.string.aed))) {
                Float total = Float.valueOf(aedPrice) * Float.valueOf(qty);
                return String.valueOf(total) + " " + currency;
            } else {
                Float total = Float.valueOf(sarPrice) * Float.valueOf(qty);
                return String.valueOf(total) + " " + currency;
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String setPrice(String price) {
        return price + " " + MySession.getInstance(MyApp.getContext()).getCurrency();
    }


    public static int getDay(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            if (date != null && date.trim().length() != 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                calendar.setTime(sdf.parse(date));
                return calendar.get(Calendar.DATE);
            }
        } catch (Exception e) {
        }
        return calendar.get(Calendar.DATE);
    }

    public static int getMonth(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            if (date != null && date.trim().length() != 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                calendar.setTime(sdf.parse(date));
                return calendar.get(Calendar.MONTH);
            }
        } catch (Exception e) {
        }
        return calendar.get(Calendar.MONTH);
    }

    public static int getYear(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            if (date != null && date.trim().length() != 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                calendar.setTime(sdf.parse(date));
                return calendar.get(Calendar.YEAR);
            }
        } catch (Exception e) {
        }
        return calendar.get(Calendar.YEAR) - 13;
    }

    public static String getSlotName(String delivery_slot) {
        if (delivery_slot.trim().toLowerCase().equals("m")) {
            return MyApp.getContext().getString(R.string.morning);
        } else if (delivery_slot.trim().toLowerCase().equals("a")) {
            return MyApp.getContext().getString(R.string.afternoon);
        } else {
            return MyApp.getContext().getString(R.string.evening);
        }
    }

    public static String deliveryDateFormatter(String date) {
        try {
            Calendar cal = Calendar.getInstance(Locale.US);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            cal.setTime(sdf.parse(date));

            /*SimpleDateFormat formatOut;

            if (MySession.getInstance(MyApp.getContext()).getLanguageKey().equals(MyApp
                    .getContext().getString(R.string.ar))) {
                formatOut = new SimpleDateFormat("dd MMM yyyy");
            } else {
                if (date.endsWith("1") && !date.endsWith("11"))
                    formatOut = new SimpleDateFormat("d'st' MMM yyyy", Locale.US);
                else if (date.endsWith("2") && !date.endsWith("12"))
                    formatOut = new SimpleDateFormat("d'nd' MMM yyyy", Locale.US);
                else if (date.endsWith("3") && !date.endsWith("13"))
                    formatOut = new SimpleDateFormat("d'rd' MMM yyyy", Locale.US);
                else
                    formatOut = new SimpleDateFormat("d'th' MMM yyyy", Locale.US);
            //}
            */
            return sdf.format(cal.getTime());

        } catch (Exception e) {
        }
        return "";
    }

    public static String getOrderProductImageURL(OrdersItem ordersItem) {
        if (ordersItem != null) {
            if (ordersItem.getItems() != null && ordersItem.getItems().size() != 0) {
                if (ordersItem.getItems().get(0).getMedia_gallery() != null && ordersItem.getItems()
                        .get(0).getMedia_gallery().size() != 0) {
                    return ordersItem.getItems().get(0).getMedia_gallery().get(0);
                }
            }
        }
        return "";
    }

    public static boolean mostRecentFilter(SortByEnum sortByEnum){
        if (sortByEnum==SortByEnum.MOST_RECENT){
            return true;
        }else {
            return false;
        }
    }

    public static boolean lowToHighFilter(SortByEnum sortByEnum){
        if (sortByEnum==SortByEnum.PRICE_LOW_HIGH){
            return true;
        }else {
            return false;
        }
    }

    public static boolean highToLowFilter(SortByEnum sortByEnum){
        if (sortByEnum==SortByEnum.PRICE_HIGH_LOW){
            return true;
        }else {
            return false;
        }
    }

}
