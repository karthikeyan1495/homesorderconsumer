package com.homesorderconsumer.ads;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.ads.viewmodel.AdsVM;
import com.homesorderconsumer.category.model.Adds;
import com.homesorderconsumer.databinding.FragmentAdsBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdsFragment extends Fragment {

    FragmentAdsBinding binding;
    AdsVM adsVM;
    Adds adds=new Adds();

    public AdsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getBundleData();
        bindView(inflater, container);
        return binding.getRoot();
    }

    private void getBundleData(){
        Bundle bundle=getArguments();
        if (bundle!=null){
            adds=(Adds)bundle.getSerializable("ads");
        }
    }

    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_ads, container, false);
        adsVM=new AdsVM(getActivity());
        binding.setAdsVM(adsVM);
        binding.setAdds(adds);
    }
}
