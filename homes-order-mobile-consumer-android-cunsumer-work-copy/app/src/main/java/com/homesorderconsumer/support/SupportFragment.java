package com.homesorderconsumer.support;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homesorderconsumer.R;
import com.homesorderconsumer.databinding.FragmentSupportBinding;
import com.homesorderconsumer.sharedpreferences.MySession;
import com.homesorderconsumer.support.model.Support;
import com.homesorderconsumer.support.viewmodel.SupportVM;
import com.homesorderconsumer.user.login.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment {

    Support support;

    FragmentSupportBinding binding;
    SupportVM supportVM;
    public SupportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        return binding.getRoot();
    }

    private void bindView(LayoutInflater inflater, ViewGroup container){
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_support, container, false);
        supportVM=new SupportVM(getActivity(),binding);
        binding.setSupportVM(supportVM);
        binding.setSupport(getSupport());
    }
    private Support getSupport(){
        support=new Support();
        if (MySession.getInstance(getActivity()).isLogin()){
            User user=MySession.getInstance(getActivity()).getUser();
            support.setName(user.getProfile().getUserdetails().getFirstname()+" "+user.getProfile
                    ().getUserdetails().getLastname());
            support.setEmail(user.getProfile().getEmail());
        }
        return support;
    }

}
