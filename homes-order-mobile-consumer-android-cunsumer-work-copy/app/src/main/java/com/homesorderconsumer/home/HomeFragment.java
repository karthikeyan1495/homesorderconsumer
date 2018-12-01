package com.homesorderconsumer.home;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.homesorderconsumer.R;
import com.homesorderconsumer.ads.adapter.AdsAdapter;
import com.homesorderconsumer.category.model.Adds;
import com.homesorderconsumer.category.model.Child;
import com.homesorderconsumer.databinding.FragmentHomeBinding;
import com.homesorderconsumer.home.adapter.GridCategoryAdapter;
import com.homesorderconsumer.home.adapter.GridPromotionProductAdapter;
import com.homesorderconsumer.home.viewmodel.HomeVM;
import com.homesorderconsumer.product.model.Products;
import com.homesorderconsumer.util.ReaderViewPagerTransformer;

import java.util.List;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements Observer {

    FragmentHomeBinding binding;
    HomeVM homeVM;
    GridCategoryAdapter foodCategoryAdapter;
    LinearLayoutManager foodLayoutManager;

    GridCategoryAdapter fashionCategoryAdapter;
    LinearLayoutManager fashionLayoutManager;

    AdsAdapter adsAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bindView(inflater, container);
        //initView();
        setUpObserver(homeVM);
        //setAdsViewPager();
        return binding.getRoot();
    }

    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof HomeVM) {
            if (homeVM.getCategories().getCategory().size() > 0) {
                setupFoodRecyclerView(binding.foodLayout.getHeight(), homeVM.getCategories()
                        .getCategory().get(0).getChild());
                binding.setFoodImage(homeVM.getCategories()
                        .getCategory().get(0).getImage());
                if (homeVM.getCategories().getCategory().size() > 1) {
                    setupFashionRecyclerView(binding.foodLayout.getHeight(), homeVM.getCategories()
                            .getCategory().get(1).getChild());
                    binding.setFashionImage(homeVM.getCategories()
                            .getCategory().get(1).getImage());
                }
            }
            if (homeVM.getCategories().getPromotedProducts()!=null) {
                setFoodPromotionRecyclerView(homeVM.getCategories().getPromotedProducts().getFood());
                setFashionPromotionRecyclerView(homeVM.getCategories().getPromotedProducts().getFashion());
            }
            setAdsViewPager(homeVM.getCategories().getAdds());
        }
    }

    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }

    private void initView() {
        binding.foodLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                /*if (foodCategoryAdapter == null) {
                    setupFoodRecyclerView(binding.foodLayout.getHeight());
                }
                if (fashionCategoryAdapter == null) {
                    setupFashionRecyclerView(binding.foodLayout.getHeight());
                }*/
            }
        });
    }

    private void bindView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeVM = new HomeVM(getActivity());
        binding.setHomeVM(homeVM);
    }

    private void setupFoodRecyclerView(int viewHeight, List<Child> list) {
        foodCategoryAdapter = new GridCategoryAdapter(getActivity(), viewHeight, list);
        foodLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false);
        binding.foodRecyclerView.setLayoutManager(foodLayoutManager);
        binding.foodRecyclerView.setAdapter(foodCategoryAdapter);
       /* new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                //foodLayoutManager.smoothScrollToPosition(0);
               // foodLayoutManager.scrollToPosition(1);

            }
        }, 1000);*/

    }

    private void setupFashionRecyclerView(int viewHeight, List<Child> list) {
        fashionCategoryAdapter = new GridCategoryAdapter(getActivity(), viewHeight, list);
        fashionLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false);
        binding.fashionRecyclerView.setLayoutManager(fashionLayoutManager);
        binding.fashionRecyclerView.setAdapter(fashionCategoryAdapter);
        //fashionLayoutManager.scrollToPosition(0);
    }

    private void setFoodPromotionRecyclerView(List<Products> list){
        GridPromotionProductAdapter adapter=new GridPromotionProductAdapter(getActivity(),list,
                binding.foodCard.getWidth());
        binding.foodPromotionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        binding.foodPromotionRecyclerView.setAdapter(adapter);
    }

    private void setFashionPromotionRecyclerView(List<Products> list){
        GridPromotionProductAdapter adapter=new GridPromotionProductAdapter(getActivity(),list,binding.foodCard.getWidth());
        binding.fashionPromotionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager
                .HORIZONTAL, false));
        binding.fashionPromotionRecyclerView.setAdapter(adapter);
    }

    private void setAdsViewPager(List<Adds> list) {
        adsAdapter = new AdsAdapter(getActivity().getSupportFragmentManager(),
                getActivity(),list);
        binding.adsViewPager.setAdapter(adsAdapter);
        binding.adsViewPager.setPageTransformer(true, new ReaderViewPagerTransformer
                (ReaderViewPagerTransformer.TransformType.DEPTH));
        changeAds();
    }
    private void changeAds() {
        Observable.interval(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> {
                    if (adsAdapter.getCount() == binding.adsViewPager.getCurrentItem() + 1) {
                        binding.adsViewPager.setCurrentItem(0);
                    } else {
                        binding.adsViewPager.setCurrentItem(binding.adsViewPager.getCurrentItem() + 1);
                    }
                });
    }
}
