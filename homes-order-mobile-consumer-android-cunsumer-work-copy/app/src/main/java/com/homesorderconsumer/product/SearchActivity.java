package com.homesorderconsumer.product;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.homesorderconsumer.R;
import com.homesorderconsumer.api.APIUtil;
import com.homesorderconsumer.databinding.ActivitySearchBinding;
import com.homesorderconsumer.product.adapter.ProductListAdapter;
import com.homesorderconsumer.product.model.SearchKey;
import com.homesorderconsumer.product.viewmodel.SearchVM;
import com.homesorderconsumer.util.EndlessRecyclerOnScrollListener;
import com.homesorderconsumer.util.MyContextWrapper;
import com.homesorderconsumer.util.MySnackBar;
import com.homesorderconsumer.util.Util;

import java.util.Locale;
import java.util.Observer;

public class SearchActivity extends AppCompatActivity implements Observer {

    ActivitySearchBinding binding;
    SearchVM searchVM;
    SearchKey searchKey;
    ProductListAdapter productListAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale languageType = Util.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
        setUpObserver(searchVM);
        setupKeyAction();
        setupSearchRecyclerView();
    }
    @Override
    public void update(java.util.Observable observable, Object object) {
        if (observable instanceof SearchVM) {
            if (productListAdapter!=null){
                boolean isClear=(boolean)object;
                if (isClear){
                    setupSearchRecyclerView();
                }
                productListAdapter.setData(searchVM.getProducts(),isClear);
            }
        }
    }
    public void setUpObserver(java.util.Observable observable) {
        observable.addObserver(this);
    }

    private void bindView(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_search);
        searchVM=new SearchVM(this);
        searchKey=new SearchKey();
        binding.setSearchVM(searchVM);
        binding.setSearchKey(searchKey);
        setSupportActionBar(binding.toolbar);
    }

    private void setupKeyAction(){
        binding.searchEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (searchKey.getSearchKey().trim().length()==0){
                        MySnackBar.getInstance().showNagativeSnackBar(SearchActivity.this, getString(R
                                .string
                                .please_enter_one_char));
                    }else {
                        searchKey.setDisplayText(searchKey.getSearchKey());
                        searchVM.productSearchAPICall(APIUtil.getInstance().productSearch(searchKey
                                .getSearchKey(),1),true);
                    }
                }
                return false;
            }
        });
    }

    private void setupSearchRecyclerView(){
        linearLayoutManager=new LinearLayoutManager(this);
        productListAdapter=new ProductListAdapter(this);
        binding.searchRecyclerView.setLayoutManager(linearLayoutManager);
        binding.searchRecyclerView.setAdapter(productListAdapter);
        binding.searchRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                searchVM.productSearchAPICall(APIUtil.getInstance().productSearch(searchKey
                        .getSearchKey(),current_page),false);
            }
        });
    }
}
