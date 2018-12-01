package com.homesorderconsumer.checkout.payment;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

import com.homesorderconsumer.BuildConfig;
import com.homesorderconsumer.R;
import com.homesorderconsumer.checkout.model.DeliveryAddress;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.util.MySnackBar;
import com.telr.mobile.sdk.activty.WebviewActivity;
import com.telr.mobile.sdk.entity.request.payment.Address;
import com.telr.mobile.sdk.entity.request.payment.App;
import com.telr.mobile.sdk.entity.request.payment.Billing;
import com.telr.mobile.sdk.entity.request.payment.MobileRequest;
import com.telr.mobile.sdk.entity.request.payment.Name;
import com.telr.mobile.sdk.entity.request.payment.Tran;

import java.math.BigInteger;
import java.util.Random;

import static com.homesorderconsumer.MyApp.getContext;

/**
 * Created by innoppl on 14/06/18.
 */

public class PaymentUtil {


    public static final String KEY = "wMnX4@cbtsp~Vxpg";        // Auth key
    public static final String STORE_ID = "20359";              // Store id
    public static final boolean isSecurityEnabled = true;      // Mark false to test on simulator, True to test on actual device and Production

    public static final String PAYMENT_MODE = "0";            // Test mode : Test mode of zero
    // indicates a live transaction. If this is set to any other value the transaction will be treated as a test.



    public static void telrPaymentGatewayInitialization(Activity activity, String amount,
                                                       DeliveryAddress deliveryAddress){
        MobileRequest mobileRequest=telrPaymentRequest(activity, amount,deliveryAddress);
        if (mobileRequest!=null) {
            Intent intent = new Intent(activity, WebviewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(WebviewActivity.EXTRA_MESSAGE, mobileRequest);
            intent.putExtra(WebviewActivity.SUCCESS_ACTIVTY_CLASS_NAME, "com.homesorderconsumer.checkout.payment.TelrPaymentSuccessActivity");
            intent.putExtra(WebviewActivity.FAILED_ACTIVTY_CLASS_NAME, "com.homesorderconsumer.checkout.payment.TelrPaymentFailedActivity");
            intent.putExtra(WebviewActivity.IS_SECURITY_ENABLED, isSecurityEnabled);
            activity.startActivity(intent);
        }else {
            MySnackBar.getInstance().showNagativeSnackBar(activity, activity.getString(R.string.something_went_wrong_while_retrieving_information));
        }
    }

    private static MobileRequest telrPaymentRequest(Activity activity, String amount, DeliveryAddress deliveryAddress) {
        try {
            MobileRequest mobile = new MobileRequest();
            mobile.setStore(STORE_ID);                       // Store ID
            mobile.setKey(KEY);                              // Authentication Key : The Authentication Key will be supplied by Telr as part of the Mobile API setup process after you request that this integration type is enabled for your account. This should not be stored permanently within the App.
            App app = new App();
            app.setId(Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID));                          // Application installation ID
            app.setName(getContext().getString(R.string.app_name));                    // Application name
            if (MySession.getInstance(activity).isLogin()) {
                app.setUser(MySession.getInstance(activity).getUser().getToken());
                //
                // Application user
                // ID : Your
                // reference for
                // the customer/user that is running the App. This should relate to their account within your systems.
            } else {
                app.setUser(String.valueOf(new Random()));                           // Application user
                // ID : Your
                // reference for
                // the customer/user that is running the App. This should relate to their account within your systems.
            }
            app.setVersion(BuildConfig.VERSION_NAME);                         // Application version
            app.setSdk("123");
            mobile.setApp(app);
            Tran tran = new Tran();
            tran.setTest(PAYMENT_MODE);                              // Test mode : Test mode of zero
            // indicates a live transaction. If this is set to any other value the transaction will be treated as a test.
            tran.setType("auth");                           /* Transaction type
                                                            'auth'   : Seek authorisation from the card issuer for the amount specified. If authorised, the funds will be reserved but will not be debited until such time as a corresponding capture command is made. This is sometimes known as pre-authorisation.
                                                            'sale'   : Immediate purchase request. This has the same effect as would be had by performing an auth transaction followed by a capture transaction for the full amount. No additional capture stage is required.
                                                            'verify' : Confirm that the card details given are valid. No funds are reserved or taken from the card.
                                                        */
            tran.setClazz("paypage");                       // Transaction class only 'paypage' is allowed on mobile, which means 'use the hosted payment page to capture and process the card details'
            tran.setCartid(String.valueOf(new BigInteger(128, new Random()))); //// Transaction cart ID : An example use of the cart ID field would be your own transaction or order reference.
            tran.setDescription(getContext().getString(R.string.app_name));         // Transaction description
            tran.setCurrency(MySession.getInstance(activity).getCurrency().toUpperCase());
            //
            // Transaction
            // currency :
            // Currency must
            // be sent as a 3 character ISO code. A list of currency codes can be found at the end of this document. For voids or refunds, this must match the currency of the original transaction.
            tran.setAmount(amount);                         // Transaction amount : The transaction amount must be sent in major units, for example 9 dollars 50 cents must be sent as 9.50 not 950. There must be no currency symbol, and no thousands separators. Thedecimal part must be separated using a dot.
            //tran.setRef();                                // (Optinal) Previous transaction reference : The previous transaction reference is required for any continuous authority transaction. It must contain the reference that was supplied in the response for the original transaction.
            mobile.setTran(tran);
            Billing billing = new Billing();
            Address address = new Address();
            address.setCity(deliveryAddress.getCity());                       // City : the minimum
            // required details for
            // a transaction to be processed
            address.setCountry(deliveryAddress.getCountry_id());                       // Country : Country must
            // be sent as a 2
            // character ISO code. A list of country codes can be found at the end of this document. the minimum required details for a transaction to be processed
            address.setRegion(deliveryAddress.getRegion());                     // Region
            address.setLine1(deliveryAddress.getAddress_line1());                 // Street address – line 1: the
            // minimum required
            // details for a transaction to be processed
            address.setLine2(deliveryAddress.getAddress_line2());               // (Optinal)
            //address.setLine3("SIT G=Towe");               // (Optinal)
            //address.setZip("SIT G=Towe");                 // (Optinal)
            billing.setAddress(address);
            Name name = new Name();
            name.setFirst(deliveryAddress.getFirstname());                          // Forename : the minimum
            // required details
            // for a transaction to be processed
            name.setLast(deliveryAddress.getLastname());                          // Surname : the minimum
            // required details
            // for a transaction to be processed
            name.setTitle("Mrs");                           // Title
            billing.setName(name);
            billing.setEmail(deliveryAddress.getEmail());                    // TODO: Insert your email
            // here : the
            // minimum required details for a transaction to be processed.
            // billing.setPhone("588519952");                // Phone number, required if enabled in your merchant dashboard.
            mobile.setBilling(billing);
            return mobile;
        }catch (Exception e){
        }
        return null;
    }

}
