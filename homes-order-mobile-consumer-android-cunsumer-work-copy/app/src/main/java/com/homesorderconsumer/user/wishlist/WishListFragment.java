package com.homesorderconsumer.user.wishlist;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.FragmentWishListBinding;
import com.homesorderconsumer.product.model.Products;
import com.homesorderconsumer.user.wishlist.adapter.WishListAdapter;
import com.homesorderconsumer.user.wishlist.viewmodel.WishListVM;

import java.util.List;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishListFragment extends Fragment implements Observer {


    FragmentWishListBinding binding;
    WishListVM wishListVM;
    WishListAdapter wishListAdapter;

    public WishListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        setUpObserver(wishListVM);
        return binding.getRoot();
    }

    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof WishListVM) {
            setupProductRecyclerView(wishListVM.getWishList());
        }
    }
    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }
    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_wish_list, container, false);
        wishListVM=new WishListVM(getActivity());
        binding.setWishListVM(wishListVM);
    }

    private void setupProductRecyclerView(List<Products> list){
        wishListAdapter=new WishListAdapter(getActivity(),list,wishListVM.isNoData);
        binding.wishlistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.wishlistRecyclerView.setAdapter(wishListAdapter);
    }

}
