package com.homesorderconsumer.user.myorder;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ActivityMyOrderDetailBinding;
import com.homesorderconsumer.home.viewmodel.CartAndSearchVM;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.trackorder.TrackOrder;
import com.homesorderconsumer.user.myorder.adapter.MyOrderProductItemAdapter;
import com.homesorderconsumer.user.myorder.model.OrdersItem;
import com.homesorderconsumer.user.myorder.viewmodel.MyOrderDetailVM;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.StatusBarUtil;
import com.homesorderconsumer.util.Util;

import java.util.Locale;
import java.util.Observer;

public class MyOrderDetailActivity extends AppCompatActivity implements Observer {

    ActivityMyOrderDetailBinding binding;
    MyOrderDetailVM myOrderDetailVM;
    MyOrderProductItemAdapter adapter;
    public OrdersItem ordersItem=new OrdersItem();
    public TrackOrder trackOrder=new TrackOrder();
    public String navigationFrom="";
    public String orderId="";

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataIntent();
        StatusBarUtil.setLightMode(this);
        bindView();
        setUpObserver(myOrderDetailVM);
        setupProductRecyclerView(ordersItem);
        onSwipeDownToRefresh();
        if (navigationFrom!=null&&navigationFrom.equals(NavigationEnum.ORDER_LIST.getValue())) {
            myOrderDetailVM.myOrdersDetailAPICall(ordersItem.getOrderId());
        }else if(navigationFrom!=null&&navigationFrom.equals(NavigationEnum.TRACK_ORDER.getValue())){
           // myOrderDetailVM.trackOrderAPICall(trackOrder);
        }else {
            myOrderDetailVM.myOrdersDetailAPICall(orderId);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        binding.setCartCount(String.valueOf(MySession.getInstance(this).getCartCount()));
    }

    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof MyOrderDetailVM) {
            binding.setOrdersItem(myOrderDetailVM.getOrdersItem());
            setupProductRecyclerView(myOrderDetailVM.getOrdersItem());
            try {
                runCountDownTimer(binding.timer, Long.valueOf(myOrderDetailVM.getOrdersItem()
                        .getRemainingTimeInsec()));
            }catch (Exception e) {
            }
        }
    }

    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }


    private void getDataIntent(){
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            navigationFrom=bundle.getString("navigationFrom");
            if (navigationFrom!=null&&navigationFrom.equals(NavigationEnum.TRACK_ORDER.getValue())){
                ordersItem=(OrdersItem) bundle.getSerializable("ordersItem");
                trackOrder=(TrackOrder) bundle.getSerializable("trackOrder");
            }else if (navigationFrom!=null&&navigationFrom.equals(NavigationEnum.ORDER_LIST
                    .getValue())){
                ordersItem=(OrdersItem) bundle.getSerializable("ordersItem");
            }else {
                orderId=bundle.getString("orderId");
            }
        }
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_order_detail);
        myOrderDetailVM=new MyOrderDetailVM(this);
        binding.setMyOrderDetailVM(myOrderDetailVM);
        binding.setCartAndSearchVM(new CartAndSearchVM(this));
        binding.setOrdersItem(ordersItem);
    }

    private void setupProductRecyclerView(OrdersItem ordersItem){
        adapter=new MyOrderProductItemAdapter(this,ordersItem,ordersItem.getItems(),trackOrder);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    private void onSwipeDownToRefresh(){
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
            if (navigationFrom!=null&&navigationFrom.equals(NavigationEnum.ORDER_LIST.getValue())) {
                myOrderDetailVM.myOrdersDetailAPICall(ordersItem.getOrderId());
            }else if(navigationFrom!=null&&navigationFrom.equals(NavigationEnum.TRACK_ORDER.getValue())) {
                myOrderDetailVM.trackOrderAPICall(trackOrder);
            }else {
                myOrderDetailVM.myOrdersDetailAPICall(orderId);
            }
        });
    }

    private void runCountDownTimer(TextView textView, long seconds){
        if (seconds!=0) {
            long milliSeconds = seconds * 1000;
            new CountDownTimer(milliSeconds, 1000) {
                public void onTick(long millisUntilFinished) {
                    String time = "00:";
                    long minutes = (millisUntilFinished / 1000) / 60;
                    long seconds = (millisUntilFinished / 1000) % 60;
                    if (minutes != 0) {
                        if (minutes > 9) {
                            time = String.valueOf(minutes) + ":";
                        } else {
                            time = "0" + String.valueOf(minutes) + ":";
                        }
                    }
                    if (seconds > 9) {
                        time = time + String.valueOf(seconds);
                    } else {
                        time = time + "0" + String.valueOf(seconds);
                    }
                    textView.setText(String.format(getString(R.string.cancel_time_message), time));
                }
                public void onFinish() {
                    OrdersItem ordersItem = (OrdersItem) textView.getTag();
                    ordersItem.setRemainingTime(0);
                    ordersItem.setRemainingTimeInsec(0);
                    binding.setOrdersItem(ordersItem);
                    //textView.setVisibility(View.GONE);
                }
            }.start();
        }
    }

}
