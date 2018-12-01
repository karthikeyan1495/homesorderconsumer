package com.homesorderconsumer.user.wishlist.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ProductItemBinding;
import com.homesorderconsumer.databinding.PromotionProductItemBinding;
import com.homesorderconsumer.product.model.Products;
import com.homesorderconsumer.product.viewmodel.ProductItemVM;

import java.util.List;
import java.util.Observer;

/**
 * Created by innoppl on 26/03/18.
 */

public class WishListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Observer {

    static int PROMOTION_PRODUCT = 1;
    static int NORMAL_PRODUCT = 2;

    Activity activity;
    List<Products> list;
    public ObservableBoolean isNoData;

    public WishListAdapter(Activity activity,List<Products> list,ObservableBoolean isNoData) {
        this.activity = activity;
        this.list=list;
        this.isNoData=isNoData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == PROMOTION_PRODUCT) {
            PromotionProductItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.promotion_product_item,
                    parent,
                    false);
            return new WishListAdapter.PromotionProductViewHolder(binding);
        } else {
            ProductItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.product_item,
                    parent,
                    false);
            return new WishListAdapter.ProductViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int productType=getProductType(position);
        if(productType==PROMOTION_PRODUCT){
            WishListAdapter.PromotionProductViewHolder promotionProductViewHolder=(WishListAdapter.PromotionProductViewHolder)
                    holder;
            promotionProductViewHolder.bind(list.get(position));
            setUpObserver(promotionProductViewHolder.productItemVM);
        }else if (productType==NORMAL_PRODUCT){
            WishListAdapter.ProductViewHolder productViewHolder=(WishListAdapter.ProductViewHolder)holder;
            productViewHolder.bind(list.get(position));
            setUpObserver(productViewHolder.productItemVM);
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

    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof ProductItemVM) {
            Products products=(Products)object;
            for (int i=0;i<list.size();i++){
                if (products.getProductID()==list.get(i).getProductID()){
                    list.remove(i);
                    break;
                }
            }
            if (list.size()==0){
                isNoData.set(true);
            }
            notifyDataSetChanged();
        }
    }

    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }

    private int getProductType(int position){
       if (list.get(position).getIsPromotion()!=null&&list.get(position)
                .getIsPromotion().trim().length()!=0&&list.get(position)
                .getIsPromotion().trim().toLowerCase().equals("true")){
            return PROMOTION_PRODUCT;
        }else {
            return NORMAL_PRODUCT;
        }
    }


    public class PromotionProductViewHolder extends RecyclerView.ViewHolder {
        private PromotionProductItemBinding binding;
        ProductItemVM productItemVM;

        public PromotionProductViewHolder(PromotionProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Products products) {
            productItemVM=new ProductItemVM(activity);
            binding.setProductItemVM(productItemVM);
            binding.setProducts(products);
            binding.executePendingBindings();
        }
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ProductItemBinding binding;
        ProductItemVM productItemVM;

        public ProductViewHolder(ProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Products products) {
            productItemVM=new ProductItemVM(activity);
            binding.setProductItemVM(productItemVM);
            binding.setProducts(products);
            binding.executePendingBindings();
        }
    }
}