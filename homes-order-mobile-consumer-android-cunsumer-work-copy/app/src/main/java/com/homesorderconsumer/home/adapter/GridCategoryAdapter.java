package com.homesorderconsumer.home.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.category.model.Child;
import com.homesorderconsumer.databinding.GridCategoryItemBinding;
import com.homesorderconsumer.home.viewmodel.GridCategoryItemVM;

import java.util.List;

/**
 * Created by innoppl on 24/03/18.
 */

public class GridCategoryAdapter extends RecyclerView.Adapter<GridCategoryAdapter.ViewHolder> {

    Activity activity;
    int viewHeight;
    List<Child> list;

    public GridCategoryAdapter(Activity activity,int viewHeight,List<Child> list){
        this.activity=activity;
        this.viewHeight=viewHeight;
        this.list=list;
    }

    @Override
    public GridCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        GridCategoryItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.grid_category_item, parent,
                false);
        return new GridCategoryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(GridCategoryAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private GridCategoryItemBinding binding;
        public ViewHolder(GridCategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Child child) {
            binding.setViewHeight(viewHeight);
            binding.setGridCategoryItemVM(new GridCategoryItemVM(activity));
            binding.setChild(child);
            binding.executePendingBindings();
        }
    }
}