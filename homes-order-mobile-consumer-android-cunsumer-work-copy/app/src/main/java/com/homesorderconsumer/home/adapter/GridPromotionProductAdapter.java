package com.homesorderconsumer.home.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.PromotionProductGridItemBinding;
import com.homesorderconsumer.product.model.Products;
import com.homesorderconsumer.product.viewmodel.ProductItemVM;
import com.homesorderconsumer.util.UIUtil;

import java.util.List;

/**
 * Created by innoppl on 24/05/18.
 */

public class GridPromotionProductAdapter extends RecyclerView.Adapter<GridPromotionProductAdapter.ViewHolder> {

    Activity activity;
    List<Products> products;
    int layoutWidth;
    public GridPromotionProductAdapter(Activity activity,List<Products> products,int layoutWidth){
        this.activity=activity;
        this.products=products;
        this.layoutWidth=layoutWidth;
    }

    @Override
    public GridPromotionProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PromotionProductGridItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.promotion_product_grid_item,
                parent,
                false);
        return new GridPromotionProductAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(GridPromotionProductAdapter.ViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private PromotionProductGridItemBinding binding;
        public ViewHolder(PromotionProductGridItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Products products) {
            binding.setProductItemVM(new ProductItemVM(activity));
            binding.setViewWidth(layoutWidth/2);
            binding.setProducts(products);
            binding.executePendingBindings();
        }
    }

    private int itemWidthCalculation(){
        int width= UIUtil.screenWidth();
        width=width-((int)UIUtil.convertDpToPixel(activity.getResources().getDimension(R.dimen
                .home_screen_card_left))+(int)UIUtil.convertDpToPixel(activity.getResources().getDimension(R.dimen
                .home_screen_card_right)));
        return width/2;
    }
}