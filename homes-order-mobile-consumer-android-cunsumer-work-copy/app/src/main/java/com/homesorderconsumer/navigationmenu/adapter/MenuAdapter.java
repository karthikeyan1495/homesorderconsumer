package com.homesorderconsumer.navigationmenu.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.MenuItemBinding;
import com.homesorderconsumer.navigationmenu.OnNavigationMenuListener;
import com.homesorderconsumer.navigationmenu.model.AppMenu;
import com.homesorderconsumer.navigationmenu.viewmodel.MenuItemVM;

import java.util.List;

/**
 * Created by mac on 3/1/18.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{

    Activity activity;
    List<AppMenu> menus;
    OnNavigationMenuListener listener;


    public MenuAdapter(Activity activity, List<AppMenu> menus, OnNavigationMenuListener listener){
        this.activity=activity;
        this.menus=menus;
        this.listener=listener;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MenuItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.menu_item, parent, false);
        return new MenuAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position) {
        holder.bind(menus.get(position));
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private MenuItemBinding binding;
        public ViewHolder(MenuItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AppMenu menu) {
            binding.setMenuItemVM(new MenuItemVM(activity,listener));
            binding.setAppMenu(menu);
            binding.executePendingBindings();
        }
    }
}
