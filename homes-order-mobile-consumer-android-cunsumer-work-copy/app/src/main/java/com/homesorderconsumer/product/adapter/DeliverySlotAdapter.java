package com.homesorderconsumer.product.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.homesorderconsumer.R;
import com.homesorderconsumer.product.DeliverySlotFragment;
import com.homesorderconsumer.product.DeliverySlotItemFragment;
import com.homesorderconsumer.product.ProductDetailActivity;
import com.homesorderconsumer.product.model.DeliverySlots;
import com.homesorderconsumer.product.model.SelectedDeliverySlot;
import com.homesorderconsumer.util.StringUtil;

import java.util.List;

/**
 * Created by innoppl on 28/03/18.
 */

public class DeliverySlotAdapter extends FragmentPagerAdapter {

    Activity activity;
    List<DeliverySlots> list;
    DeliverySlotFragment deliverySlotFragment;
    public DeliverySlotAdapter(Activity activity, FragmentManager manager, List<DeliverySlots>
            list,DeliverySlotFragment deliverySlotFragment) {
        super(manager);
        this.activity=activity;
        this.list=list;
        this.deliverySlotFragment=deliverySlotFragment;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("slots",list.get(position));
        bundle.putInt("position",position);
        DeliverySlotItemFragment fragment=new DeliverySlotItemFragment();
        fragment.setOnDeliverySlotSelected(new OnDeliverySlotSelected() {
            @Override
            public void onSlotSelected(int position) {
                SelectedDeliverySlot selectedDeliverySlot=getSelectedDeliverySlot();
                selectedDeliverySlot.setTabPosition(position);
                if (selectedDeliverySlot.getSelectedsSlot().trim().toLowerCase().equals("m")){
                    selectedDeliverySlot.setShowText(selectedDeliverySlot.getDate()+" "+activity
                            .getString(R.string.morning));
                }else if (selectedDeliverySlot.getSelectedsSlot().trim().toLowerCase().equals("a")){
                    selectedDeliverySlot.setShowText(selectedDeliverySlot.getDate()+" "+activity
                            .getString(R.string.afternoon));
                }else if (selectedDeliverySlot.getSelectedsSlot().trim().toLowerCase().equals("e")){
                    selectedDeliverySlot.setShowText(selectedDeliverySlot.getDate()+" "+activity
                            .getString(R.string.evening));
                }
                deliverySlotFragment.dismiss();
            }
        });

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return StringUtil.dayOfTheWeek(list.get(position).getDate())+"\n"+list.get(position)
                .getDate();
    }

    public interface OnDeliverySlotSelected
    {
        public void onSlotSelected(int position);
    }


    private SelectedDeliverySlot getSelectedDeliverySlot(){
        SelectedDeliverySlot selectedDeliverySlot=new SelectedDeliverySlot();
        if (activity instanceof ProductDetailActivity){
            ProductDetailActivity productDetailActivity=(ProductDetailActivity)activity;
            selectedDeliverySlot=productDetailActivity.selectedDeliverySlot;
        }
        return selectedDeliverySlot;
    }
}