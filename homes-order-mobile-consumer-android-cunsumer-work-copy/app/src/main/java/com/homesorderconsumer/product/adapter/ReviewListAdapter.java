package com.homesorderconsumer.product.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ReviewItemBinding;
import com.homesorderconsumer.product.model.ReviewItem;
import com.homesorderconsumer.product.viewmodel.ReviewItemVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by innoppl on 25/03/18.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

    Activity activity;
    List<ReviewItem> list=new ArrayList<>();
    public ReviewListAdapter(Activity activity,List<ReviewItem> list) {
        this.activity = activity;
        this.list=list;
    }

    @Override
    public ReviewListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ReviewItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.review_item,
                parent,
                false);
        return new ReviewListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ReviewListAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ReviewItemBinding binding;

        public ViewHolder(ReviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ReviewItem reviewItem) {
            binding.setReviewItemVM(new ReviewItemVM(activity));
            binding.setReviewItem(reviewItem);
            binding.executePendingBindings();
        }
    }
}