package com.homesorderconsumer.category.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.homesorderconsumer.category.CategoryActivity;
import com.homesorderconsumer.category.model.Child;
import com.homesorderconsumer.product.ProductListActivity;

/**
 * Created by innoppl on 25/03/18.
 */

public class CategoryItemVM {

    Activity activity;

    public CategoryItemVM(@NonNull Activity activity){
        this.activity=activity;
    }

    public void onClickItem(View view, Child child){
        if (child.getChild().size()==0) {
            Intent intent = new Intent(activity, ProductListActivity.class);
            intent.putExtra("child", child);
            activity.startActivity(intent);
        }else {
            Intent intent=new Intent(activity, CategoryActivity.class);
            intent.putExtra("child",child);
            activity.startActivity(intent);
        }
    }
}
