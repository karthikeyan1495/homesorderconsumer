package com.homesorderconsumer.trackorder;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.FragmentTrackOrderBinding;
import com.homesorderconsumer.trackorder.viewmodel.TrackOrderVM;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackOrderFragment extends Fragment {

    FragmentTrackOrderBinding binding;
    TrackOrderVM trackOrderVM;

    public TrackOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        return binding.getRoot();
    }

    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_track_order, container, false);
        trackOrderVM=new TrackOrderVM(getActivity(),binding);
        binding.setTrackOrderVM(trackOrderVM);
        binding.setTrackOrder(new TrackOrder());
    }
}
