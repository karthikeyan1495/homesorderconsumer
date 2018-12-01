package com.homesorderconsumer.product;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.FragmentSortingOptionBinding;
import com.homesorderconsumer.product.viewmodel.SortingOptionVM;

/**
 * Created by innoppl on 27/03/18.
 */

public class SortingOptionFragment extends DialogFragment {

    FragmentSortingOptionBinding binding;
    SortingOptionVM sortingOptionVM;
    SortDialogListener sortDialogListener;
    SortByEnum sortByEnum=SortByEnum.CATEGORY_BASED;

    public SortingOptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        bindView(inflater, container);
        return binding.getRoot();
    }

    public void setSortDialogListener(SortByEnum sortByEnum,SortDialogListener sortDialogListener){
        this.sortDialogListener=sortDialogListener;
        this.sortByEnum=sortByEnum;
    }

    private void bindView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sorting_option, container, false);
        sortingOptionVM = new SortingOptionVM(getActivity(),this,sortDialogListener);
        binding.setSortingOptionVM(sortingOptionVM);
        binding.setSortByEnum(sortByEnum);
    }

    public interface SortDialogListener{
        void sortOption(SortByEnum sortByEnum);
    }

}
