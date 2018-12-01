package com.homesorderconsumer.product.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.FeaturedProductItemBinding;
import com.homesorderconsumer.databinding.ProductItemBinding;
import com.homesorderconsumer.databinding.PromotionProductItemBinding;
import com.homesorderconsumer.product.model.Products;
import com.homesorderconsumer.product.viewmodel.ProductItemVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 25/03/18.
 */

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static int FEATURED_PRODUCT=1;
    static int PROMOTION_PRODUCT=2;
    static int NORMAL_PRODUCT=3;

    Activity activity;

    List<Products> list=new ArrayList<>();

    public ProductListAdapter(Activity activity){
        this.activity=activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType==FEATURED_PRODUCT){
            FeaturedProductItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.featured_product_item, parent,
                    false);
            return new ProductListAdapter.FeaturedProductViewHolder(binding);
        }else if (viewType==PROMOTION_PRODUCT) {
            PromotionProductItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.promotion_product_item,
                    parent,
                    false);
            return new ProductListAdapter.PromotionProductViewHolder(binding);
        }else {
            ProductItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.product_item,
                    parent,
                    false);
            return new ProductListAdapter.ProductViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int productType=getProductType(position);
        if (productType==FEATURED_PRODUCT){
            FeaturedProductViewHolder featuredProductViewHolder=(FeaturedProductViewHolder)holder;
            featuredProductViewHolder.bind(list.get(position));
        }else if(productType==PROMOTION_PRODUCT){
            PromotionProductViewHolder promotionProductViewHolder=(PromotionProductViewHolder)
                    holder;
            promotionProductViewHolder.bind(list.get(position));
        }else if (productType==NORMAL_PRODUCT){
            ProductViewHolder productViewHolder=(ProductViewHolder)holder;
            productViewHolder.bind(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getProductType(position);
    }

    public void setData(List<Products> products,boolean isClear){
        if (isClear){
            list.clear();
        }
        list.addAll(products);
        notifyDataSetChanged();
    }


    private int getProductType(int position){
            if (list.get(position).isFeaturedProduct()){
                return FEATURED_PRODUCT;
            }else if (list.get(position).getIsPromotion()!=null&&list.get(position)
                    .getIsPromotion().trim().length()!=0&&list.get(position)
                    .getIsPromotion().trim().toLowerCase().equals("true")){
                return PROMOTION_PRODUCT;
            }else {
                return NORMAL_PRODUCT;
            }
    }


    public class FeaturedProductViewHolder extends RecyclerView.ViewHolder {
        private FeaturedProductItemBinding binding;
        public FeaturedProductViewHolder(FeaturedProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Products products) {
            binding.setProductItemVM(new ProductItemVM(activity));
            binding.setProducts(products);
            binding.executePendingBindings();
        }
    }

    public class PromotionProductViewHolder extends RecyclerView.ViewHolder {
        private PromotionProductItemBinding binding;
        public PromotionProductViewHolder(PromotionProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Products products) {
            binding.setProductItemVM(new ProductItemVM(activity));
            binding.setProducts(products);
            binding.executePendingBindings();
        }
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ProductItemBinding binding;
        public ProductViewHolder(ProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Products products) {
            binding.setProductItemVM(new ProductItemVM(activity));
            binding.setProducts(products);
            binding.executePendingBindings();
        }
    }
}