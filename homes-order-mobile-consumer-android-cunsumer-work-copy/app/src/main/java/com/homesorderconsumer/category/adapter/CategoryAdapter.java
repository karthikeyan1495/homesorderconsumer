package com.homesorderconsumer.category.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.category.model.Child;
import com.homesorderconsumer.category.viewmodel.CategoryItemVM;
import com.homesorderconsumer.databinding.CategoryItemBinding;

import java.util.List;

/**
 * Created by innoppl on 25/03/18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Activity activity;
    int viewHeight;
    List<Child> list;

    public CategoryAdapter(Activity activity,int viewHeight,List<Child> list){
        this.activity=activity;
        this.viewHeight=viewHeight;
        this.list=list;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CategoryItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.category_item, parent,
                false);
        return new CategoryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CategoryItemBinding binding;
        public ViewHolder(CategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Child child) {
            binding.setViewHeight(viewHeight);
            binding.setCategoryItemVM(new CategoryItemVM(activity));
            binding.setChild(child);
            binding.executePendingBindings();
        }
    }
}