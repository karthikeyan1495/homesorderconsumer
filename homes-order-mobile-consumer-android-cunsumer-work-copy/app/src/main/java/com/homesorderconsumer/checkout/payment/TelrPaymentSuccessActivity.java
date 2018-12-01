package com.homesorderconsumer.checkout.payment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.homesorderconsumer.R;
import com.homesorderconsumer.checkout.payment.viewmodel.TelrPaymentSuccessVM;
import com.homesorderconsumer.databinding.ActivityTelrPaymentSuccessBinding;
import com.homesorderconsumer.navigationmenu.MainActivity;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.Util;
import com.telr.mobile.sdk.activty.WebviewActivity;
import com.telr.mobile.sdk.entity.response.status.StatusResponse;

import java.util.Locale;

public class TelrPaymentSuccessActivity extends AppCompatActivity {

    ActivityTelrPaymentSuccessBinding binding;
    TelrPaymentSuccessVM telrPaymentSuccessVM;
    String transactionId="";

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.getInstance().setLanguage();
        getIntentData();
        bindView();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_telr_payment_success);
        telrPaymentSuccessVM=new TelrPaymentSuccessVM(this,binding,transactionId);
        binding.setTelrPaymentSuccessVM(telrPaymentSuccessVM);
        binding.setTransactionId(transactionId);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        StatusResponse status = (StatusResponse) intent.getParcelableExtra(WebviewActivity.PAYMENT_RESPONSE);
        transactionId=status.getTrace();
        if(status.getAuth()!= null) {
            status.getAuth().getStatus();   // Authorisation status. A indicates an authorised transaction. H also indicates an authorised transaction, but where the transaction has been placed on hold. Any other value indicates that the request could not be processed.
            status.getAuth().getAvs();      /* Result of the AVS check:
                                            Y = AVS matched OK
                                            P = Partial match (for example, post-code only)
                                            N = AVS not matched
                                            X = AVS not checked
                                            E = Error, unable to check AVS */
            status.getAuth().getCode();     // If the transaction was authorised, this contains the authorisation code from the card issuer. Otherwise it contains a code indicating why the transaction could not be processed.
            status.getAuth().getMessage();  // The authorisation or processing error message.
            status.getAuth().getCa_valid();
            status.getAuth().getCardcode(); // Code to indicate the card type used in the transaction. See the code list at the end of the document for a list of card codes.
            status.getAuth().getCardlast4();// The last 4 digits of the card number used in the transaction. This is supplied for all payment types (including the Hosted Payment Page method) except for PayPal.
            status.getAuth().getCvv();      /* Result of the CVV check:
                                           Y = CVV matched OK
                                           N = CVV not matched
                                           X = CVV not checked
                                           E = Error, unable to check CVV */
            status.getAuth().getTranref(); //The payment gateway transaction reference allocated to this request.
            status.getAuth().getCardfirst6(); // The first 6 digits of the card number used in the transaction, only for version 2 is submitted in Tran -> Version
            transactionId=status.getAuth().getTranref();
        }
    }
}
