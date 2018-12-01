package com.homesorderconsumer.user.myorder;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.FragmentMyOrderBinding;
import com.homesorderconsumer.user.myorder.adapter.MyOrderAdapter;
import com.homesorderconsumer.user.myorder.model.OrdersItem;
import com.homesorderconsumer.user.myorder.viewmodel.MyOrderVM;

import java.util.List;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment implements Observer {

    FragmentMyOrderBinding binding;
    MyOrderVM myOrderVM;
    MyOrderAdapter myOrderAdapter;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        onSwipeDownToRefresh();
        setUpObserver(myOrderVM);
        return binding.getRoot();
    }

    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof MyOrderVM) {
            setupOrdersRecyclerView(myOrderVM.getOrdersItems());
        }
    }

    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }

    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_my_order, container, false);
        myOrderVM=new MyOrderVM(getActivity());
        binding.setMyOrderVM(myOrderVM);
    }

    private void setupOrdersRecyclerView(List<OrdersItem> list){
        myOrderAdapter=new MyOrderAdapter(getActivity(),list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.setCancelOrderListener(() -> {
            myOrderVM.myOrdersAPICall();
        });
    }

    private void onSwipeDownToRefresh(){
        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(false);
        });
    }
}