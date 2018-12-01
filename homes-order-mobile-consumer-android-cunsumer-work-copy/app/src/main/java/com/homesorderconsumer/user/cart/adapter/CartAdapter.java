package com.homesorderconsumer.user.cart.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.CartItemBinding;
import com.homesorderconsumer.user.cart.CartActivity;
import com.homesorderconsumer.user.cart.model.CartItem;
import com.homesorderconsumer.user.cart.viewmodel.CartItemVM;

import java.util.List;
import java.util.Observer;

/**
 * Created by innoppl on 26/03/18.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> implements Observer {

    Activity activity;
    List<CartItem> list;
    CartActivity.OnCartUpdateListener onCartUpdateListener;

    public CartAdapter(Activity activity,List<CartItem> list){
        this.activity=activity;
        this.list=list;
    }

    public void setOnCartUpdateListener(CartActivity.OnCartUpdateListener onCartUpdateListener){
        this.onCartUpdateListener=onCartUpdateListener;
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CartItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.cart_item, parent,
                false);
        return new CartAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
        setUpObserver(holder.cartItemVM);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof CartItemVM) {
            CartItem cartItem=(CartItem)object;
            if (cartItem.isRemove()) {
                list.remove(cartItem);
            }
            notifyDataSetChanged();
            if (onCartUpdateListener!=null){
                onCartUpdateListener.onCartUpdated(list);
            }
        }
    }

    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CartItemBinding binding;
        CartItemVM cartItemVM;
        public ViewHolder(CartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CartItem cartItem) {
            cartItemVM=new CartItemVM(activity);
            binding.setCartItemVM(cartItemVM);
            binding.setCartItem(cartItem);
            binding.executePendingBindings();
        }
    }


}