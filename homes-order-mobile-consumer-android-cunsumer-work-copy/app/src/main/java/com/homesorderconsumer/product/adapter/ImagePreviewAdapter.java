package com.homesorderconsumer.product.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.ImagePreviewItemBinding;

import java.util.List;

/**
 * Created by mac on 3/4/18.
 */

public class ImagePreviewAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Activity activity;
    List<String> list;

    public ImagePreviewAdapter(Activity activity,List<String> list) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.list=list;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        ImagePreviewItemBinding binding= DataBindingUtil.inflate(inflater, R.layout.image_preview_item, view, false);
        binding.setImageUrl(list.get(position));
        view.addView(binding.getRoot(), 0);
        return binding.getRoot();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}