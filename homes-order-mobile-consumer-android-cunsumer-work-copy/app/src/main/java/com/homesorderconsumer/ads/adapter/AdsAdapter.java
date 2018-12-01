package com.homesorderconsumer.ads.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.homesorderconsumer.ads.AdsFragment;
import com.homesorderconsumer.category.model.Adds;

import java.util.List;

/**
 * Created by innoppl on 25/03/18.
 */

public class AdsAdapter extends FragmentPagerAdapter {

    Activity activity;
    List<Adds> list;

    public AdsAdapter(FragmentManager fm, Activity activity,List<Adds> list) {
        super(fm);
        this.activity=activity;
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("ads",list.get(position));
        AdsFragment adsFragment=new AdsFragment();
        adsFragment.setArguments(bundle);
        return adsFragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}